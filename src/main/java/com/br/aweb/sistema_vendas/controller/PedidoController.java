package com.br.aweb.sistema_vendas.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.aweb.sistema_vendas.model.Pedido;
import com.br.aweb.sistema_vendas.model.StatusPedido;
import com.br.aweb.sistema_vendas.service.ClienteService;
import com.br.aweb.sistema_vendas.service.PedidoService;
import com.br.aweb.sistema_vendas.service.ProdutoService;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public PedidoController(PedidoService pedidoService, ClienteService clienteService,
            ProdutoService produtoService) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    // LISTAR TODOS OS PEDIDOS
    @GetMapping
    public ModelAndView listarPedidos() {
        return new ModelAndView("pedido/list",
                Map.of("pedidos", pedidoService.listarTodos()));
    }

    // FORMULÁRIO DE NOVO PEDIDO
    @GetMapping("/novo")
    public ModelAndView novoPedidoForm() {
        return new ModelAndView("pedido/form",
                Map.of(
                        "pedido", new Pedido(),
                        "clientes", clienteService.listarTodos(),
                        "produtos", produtoService.listarTodos()));
    }

    // CRIAR NOVO PEDIDO
    @PostMapping("/novo")
    public String criarPedido(@RequestParam Long clienteId) {
        var optionalCliente = clienteService.buscarPorId(clienteId);
        if (!optionalCliente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }

        Pedido pedido = pedidoService.criarPedido(optionalCliente.get());
        return "redirect:/pedidos/edit/" + pedido.getId();
    }

    // FORMULÁRIO DE EDIÇÃO DE PEDIDO
    @GetMapping("/edit/{id}")
    public ModelAndView editarPedidoForm(@PathVariable Long id) {

        var optionalPedido = pedidoService.buscarPorId(id);
        if (!optionalPedido.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Pedido pedido = optionalPedido.get();

        // Não permite editar pedidos cancelados
        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido cancelado não pode ser editado");
        }

        return new ModelAndView("pedido/edit",
                Map.of(
                        "pedido", pedido,
                        "produtos", produtoService.listarTodos()));
    }

    // ADICIONAR ITEM AO PEDIDO
    @PostMapping("/{pedidoId}/adicionar-item")
    public String adicionarItem(@PathVariable Long pedidoId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade,
            RedirectAttributes redirectAttributes) {
        try {
            pedidoService.adicionarItem(pedidoId, produtoId, quantidade);
        } catch (IllegalArgumentException | IllegalStateException e) {
            // Volta para a edição exibindo o motivo (ex.: estoque insuficiente)
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pedidos/edit/" + pedidoId;
    }

    // REMOVER ITEM DO PEDIDO
    @PostMapping("/{pedidoId}/remover-item/{itemId}")
    public String removerItem(@PathVariable Long pedidoId, @PathVariable Long itemId,
            RedirectAttributes redirectAttributes) {
        try {
            pedidoService.removerItem(pedidoId, itemId);
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pedidos/edit/" + pedidoId;
    }

    // FINALIZAR PEDIDO
    @PostMapping("/{id}/finalizar")
    public String finalizarPedido(@PathVariable Long id) {
        return "redirect:/pedidos";
    }

    // CANCELAR PEDIDO - FORMULÁRIO DE CONFIRMAÇÃO
    @GetMapping("/cancelar/{id}")
    public ModelAndView cancelarPedidoForm(@PathVariable Long id) {
        var optionalPedido = pedidoService.buscarPorId(id);
        if (!optionalPedido.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new ModelAndView("pedido/cancelar",
                Map.of("pedido", optionalPedido.get()));
    }

    // CANCELAR PEDIDO - AÇÃO
    @PostMapping("/cancelar/{id}")
    public String cancelarPedido(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            pedidoService.cancelarPedido(id);
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pedidos";
    }

    // DETALHES DO PEDIDO
    @GetMapping("/detalhes/{id}")
    public ModelAndView detalhesPedido(@PathVariable Long id) {
        var optionalPedido = pedidoService.buscarPorId(id);
        if (!optionalPedido.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new ModelAndView("pedido/detalhes",
                Map.of("pedido", optionalPedido.get()));
    }

}

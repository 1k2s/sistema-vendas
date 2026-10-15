package com.br.aweb.sistema_vendas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // O POST /login é processado pelo próprio Spring Security
    @GetMapping("/login")
    public String exibirLogin() {
        return "login";
    }

    // Página de confirmação; o POST /logout é processado pelo Spring Security
    @GetMapping("/logout")
    public String exibirLogout() {
        return "logout";
    }

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }

}

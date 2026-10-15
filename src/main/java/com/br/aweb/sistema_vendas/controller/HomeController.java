package com.br.aweb.sistema_vendas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Página inicial com atalhos para os cadastros
    @GetMapping("/")
    public String home() {
        return "index";
    }

}

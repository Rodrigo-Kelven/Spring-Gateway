package com.example.products.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProdutoController {

    @GetMapping("/{id}")
    public String buscarProduto(@PathVariable String id) {
        return "Produto " + id;
    }
}
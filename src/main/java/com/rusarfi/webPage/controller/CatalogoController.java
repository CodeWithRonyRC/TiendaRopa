package com.rusarfi.webPage.controller;

import com.rusarfi.webPage.model.Producto;
import com.rusarfi.webPage.repository.ProductoRepository;
import com.rusarfi.webPage.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CatalogoController {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @GetMapping("/catalogo")
    public String mostrarCatalogo(@RequestParam(required = false) Integer categoria,
                                  @RequestParam(required = false) Double precioMax,
                                  @RequestParam(required = false) Boolean enStock,
                                  Model model) {

        List<Producto> productos = productoRepository.findAll();

        // 🔎 Filtro por categoría
        if (categoria != null) {
            productos = productos.stream()
                    .filter(p -> p.getCategoria() != null && p.getCategoria().getId().equals(categoria))
                    .toList();
        }

        // 🔎 Filtro por precio máximo
        if (precioMax != null) {
            productos = productos.stream()
                    .filter(p -> p.getPrecio() != null && p.getPrecio().doubleValue() <= precioMax)
                    .toList();
        }

        // 🔎 Filtro por stock
        if (Boolean.TRUE.equals(enStock)) {
            productos = productos.stream()
                    .filter(p -> p.getStock() != null && p.getStock() > 0)
                    .toList();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaRepository.findAll());

        return "catalogo.component";
    }
}

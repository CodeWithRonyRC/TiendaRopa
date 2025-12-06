package com.rusarfi.webPage.controller;

import com.rusarfi.webPage.model.*;
import com.rusarfi.webPage.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ImagenProductoRepository imagenProductoRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    // ✅ LISTAR PRODUCTOS
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "inventario";
    }

    // ✅ FORMULARIO NUEVO PRODUCTO
    @GetMapping("/nuevo")
    public String nuevoProducto(Model model) {
        model.addAttribute("productoForm", new Producto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "producto_form";
    }

    // ✅ CREAR O EDITAR PRODUCTO (maneja ambos casos y conserva la imagen si no se actualiza)
    @PostMapping("/guardar")
    public String guardarOActualizarProducto(
            @ModelAttribute("productoForm") Producto producto,
            @RequestParam("imagenes") List<MultipartFile> imagenes) throws IOException {

        // 🔹 Vincular categoría correctamente
        if (producto.getCategoria() != null && producto.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId()).orElse(null);
            producto.setCategoria(categoria);
        }

        // 🔹 Si el producto ya existe (edición)
        if (producto.getId() != null && productoRepository.existsById(producto.getId())) {
            Producto productoExistente = productoRepository.findById(producto.getId()).orElse(null);

            if (productoExistente != null) {
                // 🔹 MANTENER las imágenes existentes
                producto.setImagenProductos(productoExistente.getImagenProductos());

                // 🔹 AGREGAR nuevas imágenes SIN eliminar las existentes
                if (imagenes != null && !imagenes.isEmpty() && !imagenes.get(0).isEmpty()) {
                    guardarImagenes(imagenes, producto);
                }
            }
        } else {
            // 🔹 Si es nuevo producto: guardarlo y luego sus imágenes
            producto = productoRepository.save(producto);
            if (imagenes != null && !imagenes.isEmpty() && !imagenes.get(0).isEmpty()) {
                guardarImagenes(imagenes, producto);
            }
        }

        // 🔹 Guardar o actualizar producto
        productoRepository.save(producto);

        return "redirect:/inventario/editar/" + producto.getId();
    }

    // ✅ EDITAR PRODUCTO (solo muestra el formulario con datos cargados)
    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID no válido: " + id));

        // 🔹 Obtener imágenes del producto
        List<ImagenProducto> imagenes = imagenProductoRepository.findByProductoId(id);

        // 🔹 Ajustar rutas para que Thymeleaf pueda mostrarlas correctamente
        for (ImagenProducto img : imagenes) {
            if (img.getUrlImagen() != null && !img.getUrlImagen().startsWith("/uploads/")) {
                img.setUrlImagen("/uploads/" + Paths.get(img.getUrlImagen()).getFileName());
            }
        }

        producto.setImagenProductos(imagenes);

        model.addAttribute("productoForm", producto);
        model.addAttribute("categorias", categoriaRepository.findAll());

        return "producto_form";
    }

    // ✅ Guardar imágenes en carpeta y base de datos
    private void guardarImagenes(List<MultipartFile> imagenes, Producto producto) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

        for (MultipartFile file : imagenes) {
            if (!file.isEmpty()) {
                //String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                String fileName =file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                ImagenProducto imagen = new ImagenProducto();
                imagen.setUrlImagen("/uploads/" + fileName);
                imagen.setProducto(producto);
                imagenProductoRepository.save(imagen);
            }
        }
    }

    // ✅ VISTA DE CONFIRMACIÓN DE ELIMINACIÓN
    @GetMapping("/eliminar/{id}")
    public String confirmarEliminar(@PathVariable Integer id, Model model) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID no válido: " + id));
        model.addAttribute("producto", producto);
        return "producto_eliminar";
    }

    // ✅ ELIMINAR DEFINITIVAMENTE
    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        productoRepository.deleteById(id);
        return "redirect:/inventario";
    }

    // ✅ ELIMINAR IMAGEN INDIVIDUAL
    @GetMapping("/eliminar-imagen/{imagenId}")
    public String eliminarImagenIndividual(@PathVariable Integer imagenId) {
        try {
            // 🔹 Buscar la imagen en la base de datos
            ImagenProducto imagen = imagenProductoRepository.findById(imagenId)
                    .orElseThrow(() -> new IllegalArgumentException("ID de imagen no válido: " + imagenId));

            // 🔹 Guardar el ID del producto para redirigir después
            Integer productoId = imagen.getProducto().getId();

            // 🔹 Eliminar el archivo físico del sistema de archivos
            String urlImagen = imagen.getUrlImagen();
            if (urlImagen != null && urlImagen.startsWith("/uploads/")) {
                String fileName = urlImagen.substring("/uploads/".length());
                Path imagePath = Paths.get(UPLOAD_DIR).resolve(fileName);
                try {
                    Files.deleteIfExists(imagePath);
                    System.out.println("✅ Archivo eliminado: " + imagePath);
                } catch (IOException e) {
                    System.err.println("❌ Error al eliminar archivo físico: " + e.getMessage());
                    // Continuamos aunque falle la eliminación del archivo físico
                }
            }

            // 🔹 Eliminar el registro de la base de datos
            imagenProductoRepository.delete(imagen);
            System.out.println("✅ Imagen eliminada de la BD, ID: " + imagenId);

            // 🔹 Redirigir de vuelta al formulario de edición del producto
            return "redirect:/inventario/editar/" + productoId;

        } catch (Exception e) {
            System.err.println("❌ Error al eliminar imagen: " + e.getMessage());
            // En caso de error, redirigir al inventario
            return "redirect:/inventario";
        }
    }

}

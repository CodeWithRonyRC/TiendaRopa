package com.rusarfi.webPage.repository;
import com.rusarfi.webPage.model.ImagenProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, Integer> {
    List<ImagenProducto> findByProductoId(Integer id);
    void deleteAllByProductoId(Integer id);
}

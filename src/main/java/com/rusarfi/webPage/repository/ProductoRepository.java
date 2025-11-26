package com.rusarfi.webPage.repository;

import com.rusarfi.webPage.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {
}

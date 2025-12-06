package com.rusarfi.webPage.repository;

import com.rusarfi.webPage.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
}

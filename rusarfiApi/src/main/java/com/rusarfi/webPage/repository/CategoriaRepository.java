package com.rusarfi.webPage.repository;

import com.rusarfi.webPage.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {
}

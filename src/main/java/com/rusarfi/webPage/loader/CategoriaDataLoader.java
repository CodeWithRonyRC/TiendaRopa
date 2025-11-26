package com.rusarfi.webPage.loader;
import com.rusarfi.webPage.model.Categoria;
import com.rusarfi.webPage.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoriaDataLoader implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() == 0) {
            Categoria cat1 = Categoria.builder()
                    .nombre("Jovencitas")
                    .descripcion("Prendas de vestir para jovencitas")
                    .build();

            Categoria cat2 = Categoria.builder()
                    .nombre("Jovenes")
                    .descripcion("Prendas de vestir para jovenes")
                    .build();

            Categoria cat3 = Categoria.builder()
                    .nombre("Niños")
                    .descripcion("Prendas de vestir para niños y niñas")
                    .build();



            categoriaRepository.save(cat1);
            categoriaRepository.save(cat2);
            categoriaRepository.save(cat3);

            System.out.println("✅ Categorías precargadas en la base de datos.");
        } else {
            System.out.println("ℹ️ Categorías ya existentes, no se insertaron nuevas.");
        }
    }
}
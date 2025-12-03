package com.rusarfi.webPage.config;
import com.rusarfi.webPage.model.Usuario;
import com.rusarfi.webPage.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    @PostConstruct
    public void init() {
        String adminEmail = "admin@rusarfi.com";
        // Verificar si ya existe
        if (usuarioRepository.findByEmailUsuario(adminEmail).isEmpty()) {
            Usuario admin = Usuario.builder()
                    .nombreUsuario("Administrador")                   
                    .apellidoUsuario("Sistema") 
                    .direccion("Oficina Central")                   
                    .emailUsuario(adminEmail)
                    .passwordUsuario(passwordEncoder.encode("1234"))
                    .rol(Usuario.Rol.ADMIN)
                    .createdAt(LocalDateTime.now())
                    .createdBy("system")
                    .build();

            usuarioRepository.save(admin);
        }
    }
}

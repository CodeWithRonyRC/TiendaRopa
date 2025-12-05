package com.rusarfi.webPage.controller;

import com.rusarfi.webPage.model.Usuario;
import com.rusarfi.webPage.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioRestController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // 📄 1. Listar usuarios
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // 📄 2. Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Integer id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ➕ 3. Crear nuevo usuario
    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        usuario.setPasswordUsuario(passwordEncoder.encode(usuario.getPasswordUsuario()));
        return usuarioRepository.save(usuario);
    }

    // ✏️ 4. Actualizar usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findById(id);

        if (existente.isPresent()) {
            Usuario u = existente.get();
            u.setNombreUsuario(usuario.getNombreUsuario());
            u.setRol(usuario.getRol());

            // 🔒 Encripta la contraseña si cambió
            if (!usuario.getPasswordUsuario().equals(u.getPasswordUsuario())) {
                u.setPasswordUsuario(passwordEncoder.encode(usuario.getPasswordUsuario()));
            }

            return ResponseEntity.ok(usuarioRepository.save(u));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ❌ 5. Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

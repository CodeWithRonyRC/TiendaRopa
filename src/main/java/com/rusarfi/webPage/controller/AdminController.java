package com.rusarfi.webPage.controller;
import com.rusarfi.webPage.model.Usuario;
import com.rusarfi.webPage.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
@Controller
@RequestMapping("/admin/usuarios")
@RequiredArgsConstructor
public class AdminController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // 📄 1. Listar usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios";
    }

    // ➕ 2. Mostrar formulario para crear usuario
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", Usuario.Rol.values());
        return "usuario_form";
    }

    // 💾 3. Guardar nuevo usuario o actualizar existente
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        // Verifica si el usuario ya existe para evitar volver a encriptar una contraseña
        Optional<Usuario> existente = usuario.getId() != null
                ? usuarioRepository.findById(usuario.getId())
                : Optional.empty();

        if (existente.isEmpty() ||
                !usuario.getPasswordUsuario().equals(existente.get().getPasswordUsuario())) {
            // 🔒 Encripta la contraseña solo si es nueva o fue cambiada
            usuario.setPasswordUsuario(passwordEncoder.encode(usuario.getPasswordUsuario()));
        }

        usuarioRepository.save(usuario);
        return "redirect:/admin/usuarios";
    }

    // ✏️ 4. Editar usuario
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            model.addAttribute("usuario", usuarioOpt.get());
            model.addAttribute("roles", Usuario.Rol.values());
            return "usuario_form";
        } else {
            return "redirect:/admin/usuarios";
        }
    }

    // ❌ 5. Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Integer id) {
        usuarioRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }
}

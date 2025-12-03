package com.rusarfi.webPage.controller;

import com.rusarfi.webPage.model.Usuario;
import com.rusarfi.webPage.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistroController extends BaseController {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/registro")
	public String mostrarFormularioRegistro(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "registro";
	}

	@PostMapping("/registro/guardar")
	public String registrarUsuario(@ModelAttribute Usuario usuario) {
		usuario.setPasswordUsuario(passwordEncoder.encode(usuario.getPasswordUsuario()));

		usuario.setRol(Usuario.Rol.CLIENTE);

		usuarioRepository.save(usuario);

		return "redirect:/login?registrado";
	}
}

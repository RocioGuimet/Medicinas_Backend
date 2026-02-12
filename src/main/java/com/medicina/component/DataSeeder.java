package com.medicina.component;

import com.medicina.entity.Usuario;
import com.medicina.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminUser = System.getenv("ADMIN_USERNAME");
        String adminPass = System.getenv("ADMIN_PASSWORD");

        // --- LÍNEA DE LIMPIEZA TEMPORAL para eliminar usuarios antiguos---
        usuarioRepository.deleteAll();
        System.out.println("⚠️ TABLA DE USUARIOS LIMPIADA POR COMPLETO");
        // ----------------------------------

        if (adminUser != null && adminPass != null && usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setUsername(adminUser);
            admin.setPassword(passwordEncoder.encode(adminPass));
            admin.setRol("ROLE_ADMIN");

            usuarioRepository.save(admin);
            System.out.println("✅ Usuario administrador inicial creado con éxito.");
        }
    }
}
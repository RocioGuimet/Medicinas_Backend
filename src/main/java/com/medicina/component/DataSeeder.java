package com.medicina.component;

import com.medicina.entity.Usuario;
import com.medicina.entity.Medicina;
import com.medicina.entity.Sintoma;
import com.medicina.repository.UsuarioRepository;
import com.medicina.repository.MedicinaRepository;
import com.medicina.repository.SintomaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final MedicinaRepository medicinaRepository;
    private final SintomaRepository sintomaRepository;

    public DataSeeder(UsuarioRepository usuarioRepository,
                      PasswordEncoder passwordEncoder,
                      MedicinaRepository medicinaRepository,
                      SintomaRepository sintomaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.medicinaRepository = medicinaRepository;
        this.sintomaRepository = sintomaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedUsuarioAdmin();
        seedMedicinasYSintomas();
    }

    private void seedUsuarioAdmin() {
        String adminUser = System.getenv("ADMIN_USERNAME");
        String adminPass = System.getenv("ADMIN_PASSWORD");

        if (adminUser != null && adminPass != null && usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setUsername(adminUser);
            admin.setPassword(passwordEncoder.encode(adminPass));
            admin.setRol("ROLE_ADMIN");

            usuarioRepository.save(admin);
            System.out.println("✅ Usuario administrador inicial creado con éxito.");
        }
    }

    private void seedMedicinasYSintomas() {
        medicinaRepository.deleteAll();
        sintomaRepository.deleteAll();
        if (medicinaRepository.count() == 0) {

            Sintoma insomnio = new Sintoma();
            insomnio.setNombre("Insomnio");
            insomnio.setDescripcion("Dificultad para dormir");

            Sintoma ansiedad = new Sintoma();
            ansiedad.setNombre("Ansiedad");
            ansiedad.setDescripcion("Nerviosismo, inquietud o estrés");

            Sintoma digestion = new Sintoma();
            digestion.setNombre("Mala digestión");
            digestion.setDescripcion("Dolor de estómago o pesadez");

            sintomaRepository.saveAll(List.of(insomnio, ansiedad, digestion));

            Medicina manzanilla = new Medicina();
            manzanilla.setNombre("Manzanilla");
            manzanilla.setDescripcion("Flores calmantes para digestión");
            manzanilla.setModoUso("Infusión: 1 cucharada/saquito por taza de agua hirviendo");
            manzanilla.setNombreCientifico("Matricaria chamomilla");
            manzanilla.setSintomasQueAlivia(List.of(ansiedad, digestion));

            Medicina valeriana = new Medicina();
            valeriana.setNombre("Valeriana");
            valeriana.setDescripcion("Raíz conocida por sus propiedades sedantes");
            valeriana.setModoUso("Infusión o gotas antes de dormir");
            valeriana.setNombreCientifico("Valeriana officinalis");
            valeriana.setSintomasQueAlivia(List.of(insomnio, ansiedad));

            medicinaRepository.saveAll(List.of(manzanilla, valeriana));

            System.out.println("✅ Medicinas y síntomas vinculados cargados con éxito.");
        }
    }
}
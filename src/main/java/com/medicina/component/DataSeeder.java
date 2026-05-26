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
import org.springframework.core.env.Environment;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final MedicinaRepository medicinaRepository;
    private final SintomaRepository sintomaRepository;
    private final Environment env;

    public DataSeeder(UsuarioRepository usuarioRepository,
                      PasswordEncoder passwordEncoder,
                      MedicinaRepository medicinaRepository,
                      SintomaRepository sintomaRepository,
                      Environment env) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.medicinaRepository = medicinaRepository;
        this.sintomaRepository = sintomaRepository;
        this.env = env;
    }

    @Override
    public void run(String... args) throws Exception {
        seedUsuarioAdmin();
        seedMedicinasYSintomas();
    }

    private void seedUsuarioAdmin() {
        // Leer desde variables de entorno o desde application-*.properties (Spring Environment)


        // Si no existen, usar valores por defecto para facilitar despliegues rápidos (prototipo)
        String adminUser = env.getProperty("ADMIN_USERNAME", "admin");
        String adminPass = env.getProperty("ADMIN_PASSWORD", "admin123");

        boolean providedInEnv = env.containsProperty("ADMIN_USERNAME") && env.containsProperty("ADMIN_PASSWORD");

        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setUsername(adminUser);
            admin.setPassword(passwordEncoder.encode(adminPass));
            admin.setRol("ROLE_ADMIN");

            usuarioRepository.save(admin);
            if (providedInEnv) {
                System.out.println("✅ Usuario administrador inicial creado con las variables de entorno.");
            } else {
                System.out.println("⚠️ Usuario administrador creado con valores por defecto (admin/admin123). Recomendable cambiar credenciales en entorno de producción.");
            }
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

            List<Sintoma> sintomasGuardados = sintomaRepository.saveAll(List.of(insomnio, ansiedad, digestion));

            Sintoma sInsomnio = sintomasGuardados.get(0);
            Sintoma sAnsiedad = sintomasGuardados.get(1);
            Sintoma sDigestion = sintomasGuardados.get(2);

            Medicina manzanilla = new Medicina();
            manzanilla.setNombre("Manzanilla");
            manzanilla.setDescripcion("Flores calmantes para digestión");
            manzanilla.setModoUso("Infusión: 1 cucharada/saquito por taza de agua hirviendo");
            manzanilla.setNombreCientifico("Matricaria chamomilla");
            manzanilla.getSintomasQueAlivia().add(sAnsiedad);
            manzanilla.getSintomasQueAlivia().add(sDigestion);
            medicinaRepository.save(manzanilla);

            Medicina valeriana = new Medicina();
            valeriana.setNombre("Valeriana");
            valeriana.setDescripcion("Raíz conocida por sus propiedades sedantes");
            valeriana.setModoUso("Infusión o gotas antes de dormir");
            valeriana.setNombreCientifico("Valeriana officinalis");
            valeriana.getSintomasQueAlivia().add(sInsomnio);
            valeriana.getSintomasQueAlivia().add(sAnsiedad);
            medicinaRepository.save(valeriana);

            System.out.println("✅ Medicinas y síntomas vinculados cargados con éxito.");
        }
    }
}
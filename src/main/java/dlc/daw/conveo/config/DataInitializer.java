package dlc.daw.conveo.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dlc.daw.conveo.model.AsignacionTutorEmpresa;
import dlc.daw.conveo.model.Centro;
import dlc.daw.conveo.model.Convenio;
import dlc.daw.conveo.model.Estudiante;
import dlc.daw.conveo.model.Rol;
import dlc.daw.conveo.model.SeguimientoTutor;
import dlc.daw.conveo.model.Titulacion;
import dlc.daw.conveo.model.TutorEmpresa;
import dlc.daw.conveo.model.Usuario;
import dlc.daw.conveo.repository.AsignacionTutorEmpresaRepository;
import dlc.daw.conveo.repository.CentroRepository;
import dlc.daw.conveo.repository.ConvenioRepository;
import dlc.daw.conveo.repository.EstudianteRepository;
import dlc.daw.conveo.repository.SeguimientoTutorRepository;
import dlc.daw.conveo.repository.TitulacionRepository;
import dlc.daw.conveo.repository.TutorEmpresaRepository;
import dlc.daw.conveo.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final CentroRepository centroRepository;
    private final TitulacionRepository titulacionRepository;
    private final ConvenioRepository convenioRepository;
    private final TutorEmpresaRepository tutorEmpresaRepository;
    private final EstudianteRepository estudianteRepository;
    private final AsignacionTutorEmpresaRepository asignacionRepository;
    private final SeguimientoTutorRepository seguimientoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository,
            CentroRepository centroRepository,
            TitulacionRepository titulacionRepository,
            ConvenioRepository convenioRepository,
            TutorEmpresaRepository tutorEmpresaRepository,
            EstudianteRepository estudianteRepository,
            AsignacionTutorEmpresaRepository asignacionRepository,
            SeguimientoTutorRepository seguimientoRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.centroRepository = centroRepository;
        this.titulacionRepository = titulacionRepository;
        this.convenioRepository = convenioRepository;
        this.tutorEmpresaRepository = tutorEmpresaRepository;
        this.estudianteRepository = estudianteRepository;
        this.asignacionRepository = asignacionRepository;
        this.seguimientoRepository = seguimientoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // ============================================================
        // USUARIOS
        // ============================================================

        var admin = new Usuario(null, "admin@conveo.local",
                passwordEncoder.encode("admin1234"), Rol.ADMIN, true);
        usuarioRepository.save(admin);

        var rrhh = new Usuario(null, "rrhh@conveo.local",
                passwordEncoder.encode("rrhh1234"), Rol.RRHH, true);
        usuarioRepository.save(rrhh);

        var usuarioTutor1 = new Usuario(null, "marta.perez@empresa.es",
                passwordEncoder.encode("tutor1234"), Rol.TUTOR_EMPRESA, true);
        usuarioRepository.save(usuarioTutor1);

        var usuarioTutor2 = new Usuario(null, "ana.martin@empresa.es",
                passwordEncoder.encode("tutor1234"), Rol.TUTOR_EMPRESA, true);
        usuarioRepository.save(usuarioTutor2);

        var usuarioTutor3 = new Usuario(null, "jorge.fernandez@empresa.es",
                passwordEncoder.encode("tutor1234"), Rol.TUTOR_EMPRESA, true);
        usuarioRepository.save(usuarioTutor3);

        // ============================================================
        // CENTROS
        // ============================================================

        var centro1 = new Centro(null, "IES Fernando Wirtz",
                "Rúa Caballeros, s/n, 15009 A Coruña",
                "ies.fernando.wirtz@edu.xunta.gal",
                true, LocalDate.of(2020, 1, 10), null);
        centroRepository.save(centro1);

        var centro2 = new Centro(null, "Liceo La Paz",
                "Rúa Manuel Murguía, 32, 15011 A Coruña",
                "info@liceolapaz.com",
                true, LocalDate.of(2019, 9, 1), null);
        centroRepository.save(centro2);

        var centro3 = new Centro(null, "IES San Clemente",
                "Rúa San Clemente, s/n, 15705 Santiago de Compostela",
                "ies.sanclemente@edu.xunta.gal",
                true, LocalDate.of(2021, 3, 15), null);
        centroRepository.save(centro3);

        var centro4 = new Centro(null, "Universidade da Coruña",
                "Rúa da Maestranza, 9, 15001 A Coruña",
                "informacion@udc.gal",
                true, LocalDate.of(2022, 10, 1), null);
        centroRepository.save(centro4);

        // ============================================================
        // TITULACIONES
        // ============================================================

        var daw = new Titulacion(null, "Desarrollo de Aplicaciones Web", Titulacion.Nivel.CFGS);
        titulacionRepository.save(daw);

        var dam = new Titulacion(null, "Desarrollo de Aplicaciones Multiplataforma", Titulacion.Nivel.CFGS);
        titulacionRepository.save(dam);

        var smr = new Titulacion(null, "Sistemas Microinformáticos y Redes", Titulacion.Nivel.CFGM);
        titulacionRepository.save(smr);

        var asir = new Titulacion(null, "Administración de Sistemas Informáticos en Red", Titulacion.Nivel.CFGS);
        titulacionRepository.save(asir);

        // ============================================================
        // CONVENIOS
        // ============================================================

        var convenio1 = new Convenio(null, "Convenio IES Fernando Wirtz 2025-2026",
                centro1, LocalDate.of(2025, 9, 1), LocalDate.of(2026, 6, 30), true);
        convenioRepository.save(convenio1);

        var convenio2 = new Convenio(null, "Convenio Liceo La Paz 2025-2026",
                centro2, LocalDate.of(2025, 9, 1), LocalDate.now().plusDays(12), true);
        convenioRepository.save(convenio2);

        var convenio3 = new Convenio(null, "Convenio IES Fernando Wirtz 2024-2025",
                centro1, LocalDate.of(2024, 9, 1), LocalDate.of(2025, 6, 30), false);
        convenioRepository.save(convenio3);

        var convenio4 = new Convenio(null, "Convenio IES San Clemente 2025-2026",
                centro3, LocalDate.of(2025, 10, 1), LocalDate.of(2026, 5, 31), true);
        convenioRepository.save(convenio4);

        var convenio5 = new Convenio(null, "Convenio Universidade da Coruña 2025-2026",
                centro4, LocalDate.of(2025, 10, 1), LocalDate.of(2026, 6, 30), true);
        convenioRepository.save(convenio5);

        // ============================================================
        // TUTORES DE EMPRESA
        // ============================================================

        var tutor1 = new TutorEmpresa(null, "Marta", "Pérez Gómez",
                "marta.perez@empresa.es", "12345678A", "Jefa de Desarrollo",
                LocalDate.of(2022, 1, 15), null, true, usuarioTutor1);
        tutorEmpresaRepository.save(tutor1);

        var tutor2 = new TutorEmpresa(null, "Ana", "Martín Ruiz",
                "ana.martin@empresa.es", "87654321B", "Directora de RRHH",
                LocalDate.of(2021, 6, 1), null, true, usuarioTutor2);
        tutorEmpresaRepository.save(tutor2);

        var tutor3 = new TutorEmpresa(null, "Jorge", "Fernández Soto",
                "jorge.fernandez@empresa.es", "11223344C", "Responsable de Infraestructura",
                LocalDate.of(2023, 3, 10), null, true, usuarioTutor3);
        tutorEmpresaRepository.save(tutor3);

        var tutor4 = new TutorEmpresa(null, "Laura", "Gómez Pardo",
                "laura.gomez@empresa.es", "55667788D", "Técnica Senior",
                LocalDate.of(2020, 5, 20), LocalDate.of(2024, 12, 31), false, null);
        tutorEmpresaRepository.save(tutor4);

        // ============================================================
        // ESTUDIANTES
        // ============================================================

        var e1 = new Estudiante(null, "Pedro", "Martínez Rubio",
                "pedro.mr@gmail.com", "611111111", "33445566E",
                LocalDate.of(2026, 3, 1), LocalDate.now().plusDays(12),
                true, centro2, daw, convenio2, tutor2);
        estudianteRepository.save(e1);

        var e2 = new Estudiante(null, "Lucía", "Sánchez Vega",
                "lucia.sv@gmail.com", "622222222", "44556677F",
                LocalDate.of(2026, 3, 1), LocalDate.of(2026, 6, 30),
                true, centro1, dam, convenio1, tutor1);
        estudianteRepository.save(e2);

        var e3 = new Estudiante(null, "Marcos", "Díaz Iglesias",
                "marcos.di@gmail.com", "633333333", "55667788G",
                LocalDate.of(2026, 2, 1), LocalDate.of(2026, 5, 31),
                true, centro3, daw, convenio4, tutor3);
        estudianteRepository.save(e3);

        var e4 = new Estudiante(null, "Elena", "Torres Blanco",
                "elena.tb@gmail.com", "644444444", "66778899H",
                LocalDate.of(2026, 1, 15), LocalDate.of(2026, 5, 15),
                true, centro4, asir, convenio5, tutor3);
        estudianteRepository.save(e4);

        var e5 = new Estudiante(null, "Rubén", "Moreno Castro",
                "ruben.mc@gmail.com", "655555555", "77889900I",
                LocalDate.of(2026, 3, 10), LocalDate.of(2026, 6, 30),
                true, centro2, smr, convenio2, null);
        estudianteRepository.save(e5);

        var e6 = new Estudiante(null, "Sofía", "Navarro Pérez",
                "sofia.np@gmail.com", "666666666", "88990011J",
                LocalDate.of(2024, 10, 1), LocalDate.of(2025, 3, 31),
                false, centro1, daw, convenio3, null);
        estudianteRepository.save(e6);

        var e7 = new Estudiante(null, "Álvaro", "Romero Gil",
                "alvaro.rg@gmail.com", "677777777", "99001122K",
                LocalDate.of(2024, 10, 1), LocalDate.of(2025, 6, 30),
                false, centro1, dam, convenio3, null);
        estudianteRepository.save(e7);

        // ============================================================
        // ASIGNACIONES
        // ============================================================

        asignacionRepository.save(new AsignacionTutorEmpresa(
                null, e1, tutor2, LocalDate.of(2026, 3, 1), null));

        asignacionRepository.save(new AsignacionTutorEmpresa(
                null, e2, tutor1, LocalDate.of(2026, 3, 1), null));

        asignacionRepository.save(new AsignacionTutorEmpresa(
                null, e3, tutor3, LocalDate.of(2026, 2, 1), null));

        asignacionRepository.save(new AsignacionTutorEmpresa(
                null, e4, tutor3, LocalDate.of(2026, 1, 15), null));

        asignacionRepository.save(new AsignacionTutorEmpresa(
                null, e6, tutor1, LocalDate.of(2024, 10, 1), LocalDate.of(2025, 3, 31)));

        asignacionRepository.save(new AsignacionTutorEmpresa(
                null, e7, tutor1, LocalDate.of(2024, 10, 1), LocalDate.of(2025, 6, 30)));

        asignacionRepository.save(new AsignacionTutorEmpresa(
                null, e6, tutor4, LocalDate.of(2024, 9, 1), LocalDate.of(2024, 10, 1)));

        // ============================================================
        // SEGUIMIENTOS
        // ============================================================

        // Lucía (e2) - tutor1 Marta: dos intermedios + final apto
        seguimientoRepository.save(new SeguimientoTutor(
                null, e2, tutor1,
                LocalDate.of(2026, 3, 15),
                SeguimientoTutor.TipoSeguimiento.SEGUIMIENTO,
                4,
                "Buena adaptación al entorno de trabajo. Muestra iniciativa y resuelve dudas de forma autónoma.",
                null));

        seguimientoRepository.save(new SeguimientoTutor(
                null, e2, tutor1,
                LocalDate.of(2026, 4, 20),
                SeguimientoTutor.TipoSeguimiento.SEGUIMIENTO,
                5,
                "Excelente progreso. Ha participado en el desarrollo de un módulo completo con buenas prácticas.",
                null));

        seguimientoRepository.save(new SeguimientoTutor(
                null, e2, tutor1,
                LocalDate.of(2026, 6, 25),
                SeguimientoTutor.TipoSeguimiento.FINAL,
                5,
                "Alumna muy destacada. Ha superado las expectativas del equipo y demostrado madurez profesional.",
                true));

        // Marcos (e3) - tutor3 Jorge: un intermedio
        seguimientoRepository.save(new SeguimientoTutor(
                null, e3, tutor3,
                LocalDate.of(2026, 3, 10),
                SeguimientoTutor.TipoSeguimiento.SEGUIMIENTO,
                3,
                "Rendimiento correcto aunque necesita mejorar la comunicación con el equipo.",
                null));

        // Elena (e4) - tutor3 Jorge: intermedio + final no apto
        seguimientoRepository.save(new SeguimientoTutor(
                null, e4, tutor3,
                LocalDate.of(2026, 2, 20),
                SeguimientoTutor.TipoSeguimiento.SEGUIMIENTO,
                2,
                "Dificultades para adaptarse al ritmo de trabajo. Se le han proporcionado recursos adicionales.",
                null));

        seguimientoRepository.save(new SeguimientoTutor(
                null, e4, tutor3,
                LocalDate.of(2026, 5, 10),
                SeguimientoTutor.TipoSeguimiento.FINAL,
                3,
                "Ha mejorado respecto al inicio aunque no ha alcanzado el nivel esperado para el puesto.",
                false));

        // Sofía (e6) - tutor1 Marta: histórico completo con final apto
        seguimientoRepository.save(new SeguimientoTutor(
                null, e6, tutor1,
                LocalDate.of(2024, 11, 15),
                SeguimientoTutor.TipoSeguimiento.SEGUIMIENTO,
                4,
                "Buen desempeño general. Integración rápida en el equipo.",
                null));

        seguimientoRepository.save(new SeguimientoTutor(
                null, e6, tutor1,
                LocalDate.of(2025, 3, 28),
                SeguimientoTutor.TipoSeguimiento.FINAL,
                4,
                "Alumna con buen nivel técnico y actitud positiva. Recomendada para futuras colaboraciones.",
                true));
    }
}
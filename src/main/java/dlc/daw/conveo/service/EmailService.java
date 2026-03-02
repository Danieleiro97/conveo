package dlc.daw.conveo.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarRecordatorioFinPracticas(String destinatario, String nombreEstudiante,
                                               String fechaFin, long diasRestantes) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Recordatorio: fin de prácticas próximo");
        mensaje.setText(
            "Hola,\n\n" +
            "Las prácticas de " + nombreEstudiante + " finalizan el " + fechaFin + ".\n" +
            "Quedan " + diasRestantes + " días.\n\n" +
            "Por favor, revisa el cierre y la documentación pendiente.\n\n" +
            "Gracias.\nSistema Conveo"
        );
        mailSender.send(mensaje);
    }
}
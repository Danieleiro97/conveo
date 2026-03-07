package dlc.daw.conveo.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seguimientos_tutor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeguimientoTutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tutor_empresa_id")
    private TutorEmpresa tutorEmpresa;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSeguimiento tipo; // SEGUIMIENTO / FINAL

    @Column(nullable = false)
    private Integer valoracion; // 1 a 5

    @Column(length = 1000)
    private String observaciones;

    private Boolean apto;

    public enum TipoSeguimiento {
        SEGUIMIENTO, FINAL
    }
}
package com.aluracursos.forohub.domain.topico;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Column(unique = true)
    private String mensaje;

    private String autor;
    private String curso;

    @Enumerated(EnumType.STRING)
    private StatusTopico status;

    private LocalDateTime fechaCreacion;
    private Boolean activo;

    public Topico() {}

    public Topico(DatosRegistroTopico datos) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.autor = datos.autor();
        this.curso = datos.curso();
        this.status = StatusTopico.ABIERTO;
        this.fechaCreacion = LocalDateTime.now();
        this.activo = true;
    }

    public void actualizarDatos(DatosActualizarTopico datos) {
        if (datos.titulo() != null) this.titulo = datos.titulo();
        if (datos.mensaje() != null) this.mensaje = datos.mensaje();
        if (datos.status() != null) this.status = datos.status();
    }

    public void desactivar() { this.activo = false; }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public String getAutor() { return autor; }
    public String getCurso() { return curso; }
    public StatusTopico getStatus() { return status; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public Boolean getActivo() { return activo; }
}
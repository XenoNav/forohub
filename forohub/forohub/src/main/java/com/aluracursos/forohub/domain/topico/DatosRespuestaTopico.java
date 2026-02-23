package com.aluracursos.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        String curso,
        StatusTopico status,
        LocalDateTime fechaCreacion
) {
    public DatosRespuestaTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getAutor(), topico.getCurso(), topico.getStatus(),
                topico.getFechaCreacion());
    }
}

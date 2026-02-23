package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.topico.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Registrar un nuevo tópico")
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datos,
            UriComponentsBuilder uriComponentsBuilder) {

        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity.badRequest().build();
        }

        Topico topico = new Topico(datos);
        topicoRepository.save(topico);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosRespuestaTopico(topico));
    }

    @GetMapping
    @Operation(summary = "Listar todos los tópicos activos")
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        return ResponseEntity.ok(topicoRepository.findByActivoTrue(pageable)
                .map(DatosListadoTopico::new));
    }

    @SuppressWarnings("null")
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un tópico por ID")
    public ResponseEntity<DatosRespuestaTopico> retornarDatosTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @SuppressWarnings("null")
    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualizar un tópico")
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datos) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.actualizarDatos(datos);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @SuppressWarnings("null")
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar un tópico (borrado lógico)")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivar();
        return ResponseEntity.noContent().build();
    }
}
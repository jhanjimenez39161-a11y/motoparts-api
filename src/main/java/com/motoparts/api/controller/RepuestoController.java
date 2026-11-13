package com.motoparts.api.controller;

import com.motoparts.api.dto.RepuestoRequestDTO;
import com.motoparts.api.model.Repuesto;
import com.motoparts.api.repository.RepuestoRepository;
import com.motoparts.api.service.RepuestoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {

    private final RepuestoService repuestoService;
    private final RepuestoRepository repuestoRepository;

    public RepuestoController(RepuestoService repuestoService, RepuestoRepository repuestoRepository) {
        this.repuestoService = repuestoService;
        this.repuestoRepository = repuestoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Repuesto>> listarRepuestos() {
        List<Repuesto> repuestos = repuestoService.listarTodos();
        return ResponseEntity.ok(repuestos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> obtenerRepuestoPorId(@PathVariable Long id) {
        Optional<Repuesto> repuesto = repuestoService.buscarPorId(id);
        return repuesto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Repuesto> crearRepuesto(@Valid @RequestBody RepuestoRequestDTO dto) {
        Repuesto nuevoRepuesto = repuestoService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRepuesto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repuesto> actualizarRepuesto(@PathVariable Long id, @Valid @RequestBody RepuestoRequestDTO detallesRepuesto) {
        Optional<Repuesto> repuestoExistente = repuestoRepository.findById(id);
        
        if (repuestoExistente.isPresent()) {
            Repuesto repuesto = repuestoExistente.get();
            repuesto.setNombre(detallesRepuesto.getNombre());
            repuesto.setPrecio(detallesRepuesto.getPrecio());
            repuesto.setStock(detallesRepuesto.getStock());
            
            Repuesto repuestoActualizado = repuestoRepository.save(repuesto);
            return ResponseEntity.ok(repuestoActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRepuesto(@PathVariable Long id) {
        if (repuestoRepository.existsById(id)) {
            repuestoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint adicional para demostrar el consumo de la API externa (Conversión a Dólares)
    @GetMapping("/{id}/precio-dolares")
    public ResponseEntity<Double> obtenerPrecioEnDolares(@PathVariable Long id) {
        Double precioDolares = repuestoService.obtenerPrecioEnDolares(id);
        if (precioDolares > 0) {
            return ResponseEntity.ok(precioDolares);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
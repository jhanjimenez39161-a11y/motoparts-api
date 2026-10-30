package com.motoparts.api.controller;

import com.motoparts.api.model.Repuesto;
import com.motoparts.api.repository.RepuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {

    @Autowired
    private RepuestoRepository repuestoRepository;

    @GetMapping
    public ResponseEntity<List<Repuesto>> listarRepuestos() {
        List<Repuesto> repuestos = repuestoRepository.findAll();
        return ResponseEntity.ok(repuestos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> obtenerRepuestoPorId(@PathVariable Long id) {
        Optional<Repuesto> repuesto = repuestoRepository.findById(id);
        if (repuesto.isPresent()) {
            return ResponseEntity.ok(repuesto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Repuesto> crearRepuesto(@RequestBody Repuesto repuesto) {
        Repuesto nuevoRepuesto = repuestoRepository.save(repuesto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRepuesto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repuesto> actualizarRepuesto(@PathVariable Long id, @RequestBody Repuesto detallesRepuesto) {
        Optional<Repuesto> repuestoExistente = repuestoRepository.findById(id);
        
        if (repuestoExistente.isPresent()) {
            Repuesto repuesto = repuestoExistente.get();
            repuesto.setNombre(detallesRepuesto.getNombre());
            repuesto.setCategoria(detallesRepuesto.getCategoria());
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
            repuestoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Repuesto>> buscarPorCategoria(@RequestParam String categoria) {
        List<Repuesto> repuestos = repuestoRepository.findByCategoria(categoria);
        return ResponseEntity.ok(repuestos);
    }
}
package com.motoparts.api.controller;

import com.motoparts.api.dto.RepuestoRequestDTO;
import com.motoparts.api.model.Repuesto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {

    private static List<Repuesto> listaRepuestos = new ArrayList<>();
    private static Long contadorId = 1L;

    static {
        listaRepuestos.add(new Repuesto(contadorId++, "Pastillas de Freno Nissin", "Frenos", 45000.0, 15));
        listaRepuestos.add(new Repuesto(contadorId++, "Bombillo LED Auxiliar", "Iluminación", 85000.0, 8));
    }

    // 1. GET: Listar todos
    @GetMapping
    public ResponseEntity<List<Repuesto>> obtenerTodos() {
        return ResponseEntity.ok(listaRepuestos);
    }

    // 2. GET con PathVariable: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> obtenerPorId(@PathVariable Long id) {
        Optional<Repuesto> repuesto = listaRepuestos.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();

        if (repuesto.isPresent()) {
            return ResponseEntity.ok(repuesto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 3. GET con RequestParam: Filtrar por categoría
    @GetMapping("/buscar")
    public ResponseEntity<List<Repuesto>> buscarPorCategoria(@RequestParam String categoria) {
        List<Repuesto> filtrados = listaRepuestos.stream()
                .filter(r -> r.getCategoria().equalsIgnoreCase(categoria))
                .toList();
        
        return ResponseEntity.ok(filtrados);
    }

    // 4. POST con RequestBody y DTO: Crear nuevo repuesto
    @PostMapping
    public ResponseEntity<Repuesto> crearRepuesto(@RequestBody RepuestoRequestDTO requestDTO) {
        if (requestDTO.nombre() == null || requestDTO.precio() == null) {
            return ResponseEntity.badRequest().build();
        }

        Repuesto nuevoRepuesto = new Repuesto(
                contadorId++,
                requestDTO.nombre(),
                requestDTO.categoria(),
                requestDTO.precio(),
                requestDTO.stock() != null ? requestDTO.stock() : 0
        );

        listaRepuestos.add(nuevoRepuesto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRepuesto);
    }
}
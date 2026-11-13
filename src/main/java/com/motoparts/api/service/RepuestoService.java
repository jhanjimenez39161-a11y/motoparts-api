package com.motoparts.api.service;

import com.motoparts.api.dto.RepuestoRequestDTO;
import com.motoparts.api.model.Categoria;
import com.motoparts.api.model.Repuesto;
import com.motoparts.api.repository.CategoriaRepository;
import com.motoparts.api.repository.RepuestoRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepuestoService {

    private static final Logger logger = LoggerFactory.getLogger(RepuestoService.class);

    private final RepuestoRepository repuestoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MonedaExternalService monedaExternalService;
    private final Counter repuestosCreacionesCounter;

    public RepuestoService(RepuestoRepository repuestoRepository,
                           CategoriaRepository categoriaRepository,
                           MonedaExternalService monedaExternalService,
                           MeterRegistry meterRegistry) {
        this.repuestoRepository = repuestoRepository;
        this.categoriaRepository = categoriaRepository;
        this.monedaExternalService = monedaExternalService;
        
        // Métrica personalizada requerida por la actividad
        this.repuestosCreacionesCounter = meterRegistry.counter("motoparts.repuestos.creados.total");
    }

    public List<Repuesto> listarTodos() {
        logger.info("INFO: Consultando el listado completo de repuestos en el inventario.");
        return repuestoRepository.findAll();
    }

    public Optional<Repuesto> buscarPorId(Long id) {
        logger.info("INFO: Buscando repuesto con ID: {}", id);
        return repuestoRepository.findById(id);
    }

    public Repuesto guardar(RepuestoRequestDTO dto) {
        logger.info("INFO: Intentando registrar un nuevo repuesto: {}", dto.getNombre());
        
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> {
                    logger.error("ERROR: No se encontró la categoría con ID: {}", dto.getCategoriaId());
                    return new RuntimeException("Categoría no encontrada");
                });

        Repuesto repuesto = new Repuesto();
        repuesto.setNombre(dto.getNombre());
        repuesto.setPrecio(dto.getPrecio());
        repuesto.setStock(dto.getStock());
        repuesto.setCategoria(categoria);

        Repuesto guardado = repuestoRepository.save(repuesto);
        
        // Incrementamos la métrica personalizada
        repuestosCreacionesCounter.increment();
        logger.info("INFO: Repuesto guardado exitosamente con ID: {}. Métrica de creación incrementada.", guardado.getId());
        
        return guardado;
    }

    public void eliminar(Long id) {
        logger.warn("WARN: Eliminando repuesto con ID: {}", id);
        repuestoRepository.deleteById(id);
    }

    // Método para demostrar el uso de la API externa de conversión de moneda
    public Double obtenerPrecioEnDolares(Long id) {
        Optional<Repuesto> repuestoOpt = repuestoRepository.findById(id);
        if (repuestoOpt.isPresent()) {
            Double tasaDolar = monedaExternalService.obtenerTasaDolar();
            if (tasaDolar > 0) {
                logger.info("INFO: Conversión de precio a USD exitosa para el repuesto ID: {}", id);
                return repuestoOpt.get().getPrecio() * tasaDolar;
            }
        }
        logger.warn("WARN: No se pudo realizar la conversión a dólares para el repuesto ID: {}", id);
        return 0.0;
    }
}
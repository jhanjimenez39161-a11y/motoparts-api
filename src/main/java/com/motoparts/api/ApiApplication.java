package com.motoparts.api;

import com.motoparts.api.model.Repuesto;
import com.motoparts.api.repository.RepuestoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(RepuestoRepository repository) {
        return args -> {
            Repuesto r1 = new Repuesto();
            r1.setNombre("Pastillas de Freno");
            r1.setCategoria("Frenos");
            r1.setPrecio(45000.0);
            r1.setStock(10);
            repository.save(r1);

            Repuesto r2 = new Repuesto();
            r2.setNombre("Cadena de Transmisión");
            r2.setCategoria("Transmisión");
            r2.setPrecio(85000.0);
            r2.setStock(5);
            repository.save(r2);
        };
    }
}
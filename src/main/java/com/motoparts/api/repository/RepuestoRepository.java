package com.motoparts.api.repository;

import com.motoparts.api.model.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {
    List<Repuesto> findByCategoria(String categoria);
}
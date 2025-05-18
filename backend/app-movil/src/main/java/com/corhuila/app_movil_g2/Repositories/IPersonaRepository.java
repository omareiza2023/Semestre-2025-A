package com.corhuila.app_movil_g2.Repositories;

import com.corhuila.app_movil_g2.Models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonaRepository extends JpaRepository<Persona, Long> {
}
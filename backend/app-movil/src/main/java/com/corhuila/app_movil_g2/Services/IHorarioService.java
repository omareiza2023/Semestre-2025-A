package com.corhuila.app_movil_g2.Services;

import com.corhuila.app_movil_g2.Models.Horario;
import java.util.List;
import java.util.Optional;

public interface IHorarioService {
    List<Horario> findAllActive(); // Listar solo activos
    List<Horario> findAll(); // Listar todos

    Optional<Horario> findById(Long id);
    Optional<Horario> findByIdActive(Long id); // Buscar activo por ID

    Horario save(Horario horario);

    void softDeleteById(Long id); // Cambiar estado a "INACTIVO"

    List<Horario> findByBarberoIdActive(Long barberoId); // Horarios activos de un barbero
    List<Horario> findByBarberoIdAndDiaActive(Long barberoId, String diasSemana); // Horarios activos de un barbero para un día específico
}

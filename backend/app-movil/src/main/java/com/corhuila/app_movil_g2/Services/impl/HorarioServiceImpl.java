package com.corhuila.app_movil_g2.Services.impl;

import com.corhuila.app_movil_g2.Models.Horario;
import com.corhuila.app_movil_g2.Models.Usuario; // Necesario para verificar barbero
import com.corhuila.app_movil_g2.Repositories.IHorarioRepository;
import com.corhuila.app_movil_g2.Repositories.IUsuarioRepository; // Necesario para verificar barbero
import com.corhuila.app_movil_g2.Services.IHorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioServiceImpl implements IHorarioService {

    @Autowired
    private IHorarioRepository horarioRepository;

     @Autowired
    private IUsuarioRepository usuarioRepository; // Para verificar si el usuario es barbero


    private static final String ESTADO_ACTIVO = "ACTIVO";
    private static final String ESTADO_INACTIVO = "INACTIVO"; // Para borrado lógico


    @Override
    @Transactional(readOnly = true)
    public List<Horario> findAllActive() {
        return horarioRepository.findByEstadoNot(ESTADO_INACTIVO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Horario> findAll() {
        return horarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Horario> findById(Long id) {
        return horarioRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Horario> findByIdActive(Long id) {
        return horarioRepository.findByIdAndEstadoNot(id, ESTADO_INACTIVO);
    }

    @Override
    @Transactional
    public Horario save(Horario horario) {
         // Opcional: Validar que el usuario asociado sea un barbero
         Optional<Usuario> usuario = usuarioRepository.findById(horario.getUsuario().getId());
         if (usuario.isEmpty() || !usuario.get().getRol().getNombreRol().equals("BARBERO")) {
              throw new IllegalArgumentException("El usuario asociado al horario debe tener el rol BARBERO.");
         }
         if (horario.getEstado() == null) {
             horario.setEstado(ESTADO_ACTIVO); // Estado por defecto al guardar si no viene
         }
        return horarioRepository.save(horario);
    }

    @Override
    @Transactional
    public void softDeleteById(Long id) {
        Optional<Horario> horarioOptional = horarioRepository.findById(id);
        if (horarioOptional.isPresent()) {
            Horario horario = horarioOptional.get();
            horario.setEstado(ESTADO_INACTIVO); // Marcar como inactivo
            horarioRepository.save(horario); // Guardar el cambio de estado
        }
        // Opcional: lanzar excepción si no se encuentra el horario
    }

    @Override
    @Transactional(readOnly = true)
    public List<Horario> findByBarberoIdActive(Long barberoId) {
         // Opcional: Validar que el usuario asociado sea un barbero
         Optional<Usuario> usuario = usuarioRepository.findById(barberoId);
         if (usuario.isEmpty() || !usuario.get().getRol().getNombreRol().equals("BARBERO")) {
              return List.of(); // O lanzar excepción
         }
        return horarioRepository.findByUsuarioIdAndEstadoNot(barberoId, ESTADO_INACTIVO);
    }

    @Override
    @Transactional(readOnly = true)
     public List<Horario> findByBarberoIdAndDiaActive(Long barberoId, String diasSemana) {
          // Opcional: Validar que el usuario asociado sea un barbero
         Optional<Usuario> usuario = usuarioRepository.findById(barberoId);
         if (usuario.isEmpty() || !usuario.get().getRol().getNombreRol().equals("BARBERO")) {
              return List.of(); // O lanzar excepción
         }
         return horarioRepository.findByUsuarioIdAndDiasSemanaAndEstadoNot(barberoId, diasSemana, ESTADO_INACTIVO);
     }
}

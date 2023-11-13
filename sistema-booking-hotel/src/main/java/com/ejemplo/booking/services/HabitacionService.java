package com.ejemplo.booking.services;

import com.ejemplo.booking.model.Habitacion;
import com.ejemplo.booking.repositories.HabitacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    public HabitacionService(HabitacionRepository habitacionRepository){
        this.habitacionRepository = habitacionRepository;
    }
    public ResponseEntity<List<Habitacion>> listarHabitaciones() {
        try {
            List<Habitacion> habitaciones = habitacionRepository.findAll();
            return ResponseEntity.ok(habitaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<List<Habitacion>> obtenerHabitacionesDisponibles() {
        try {
            List<Habitacion> habitaciones = habitacionRepository.findAll();
            List<Habitacion> habitacionesDisponibles = new ArrayList<>();

            for (Habitacion habitacion : habitaciones) {
                if (habitacion.isDisponible()) {
                    habitacionesDisponibles.add(habitacion);
                }
            }

            return ResponseEntity.ok(habitacionesDisponibles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity agregarHabitacion(Habitacion habitacion) {
        try {
            habitacionRepository.save(habitacion);
            return ResponseEntity.status(CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity obtenerHabitacionPorId(Integer id) {
        try {
            Habitacion habitacion = habitacionRepository.findById(id).get();
            return ResponseEntity.ok(habitacion);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity actualizarHabitacion(Integer id, Habitacion habitacion) {
        try{
            Habitacion habitacionActual = habitacionRepository.findById(id).get();
            habitacionActual.setTipo(habitacion.getTipo());
            habitacionActual.setDisponible(habitacion.isDisponible());
            habitacionActual.setPrecio(habitacion.getPrecio());
            habitacionActual.setCapacidad(habitacion.getCapacidad());
            habitacionRepository.save(habitacionActual);
            return ResponseEntity.status(OK).build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity borrarHabitacion(Integer id) {
        try {
            habitacionRepository.deleteById(id);
            return ResponseEntity.status(OK).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity obtenerReservasDeHabitacion(Integer id) {
        try {
            Habitacion habitacion = habitacionRepository.findById(id).get();
            return ResponseEntity.ok(habitacion.getReservas());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}

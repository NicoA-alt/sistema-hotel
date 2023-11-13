package com.ejemplo.booking.services;

import com.ejemplo.booking.model.Reserva;
import com.ejemplo.booking.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository){ this.reservaRepository = reservaRepository;}
    public ResponseEntity<List<Reserva>> obtenerReservas() {
        try {
            List<Reserva> reservas = reservaRepository.findAll();
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public boolean fechasDisponibles(Reserva nuevaReserva) {
        List<Reserva> reservas = reservaRepository.findReservasByHabitacionAndFechas(
                nuevaReserva.getHabitacion(),
                nuevaReserva.getFechaInicio(),
                nuevaReserva.getFechaFin()
        );

        return reservas.isEmpty();
    }
    public ResponseEntity crearReserva(Reserva reserva) {
        try{
            if (!fechasDisponibles(reserva)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Las fechas seleccionadas no están disponibles");
            }
            if (reserva.getHabitacion().getCapacidad() < reserva.getHuespedes().size()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("La cantidad de huéspedes supera la capacidad de la habitación");
            }

            reservaRepository.save(reserva);
            return ResponseEntity.status(CREATED).build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity obtenerReservaPorId(Integer id) {
        try {
            Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Reserva no encontrada"));
            return ResponseEntity.ok(reserva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity actualizarReserva(Integer id, Reserva reserva) {
        try {
            Reserva reservaActual = reservaRepository.findById(id).get();
            reservaActual.setFechaInicio(reserva.getFechaInicio());
            reservaActual.setFechaFin(reserva.getFechaFin());
            reservaActual.setHabitacion(reserva.getHabitacion());
            reservaActual.setHuespedes(reserva.getHuespedes());
            reservaRepository.save(reservaActual);
            return ResponseEntity.status(OK).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity cancelarReserva(Integer id) {
        try {
            reservaRepository.deleteById(id);
            return ResponseEntity.status(OK).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
package com.ejemplo.booking.controller;

import com.ejemplo.booking.model.Huesped;
import com.ejemplo.booking.model.Reserva;
import com.ejemplo.booking.services.HuespedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/huesped")
@CrossOrigin(origins = "*")
public class HuespedController {

    @Autowired
    private HuespedService huespedService;

    @PostMapping("")
    public ResponseEntity<Huesped> registrarHuesped(@RequestBody Huesped huesped) {
        return huespedService.registrarHuesped(huesped);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Huesped> actualizarHuesped(@PathVariable Integer id, @RequestBody Huesped huesped) {
        return huespedService.actualizarHuesped(id, huesped);
    }
    @GetMapping("")
    public ResponseEntity<List<Huesped>> listarHuespedes() {
        return huespedService.listarHuespedes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Huesped> obtenerDetallesHuesped(@PathVariable Integer id) {
        return this.huespedService.obtenerHuespedPorId(id);
    }
    @GetMapping("/dni/{dni}")
    public ResponseEntity<Huesped> obtenerHuespedPorDni(@PathVariable String dni) {
        return this.huespedService.obtenerHuespedPorDni(dni);
    }
    @GetMapping("/{id}/reserva")
    public ResponseEntity<List<Reserva>> getReservaHuesped(@PathVariable Integer id){
        return huespedService.getReservaHuesped(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity borrarHuesped(@PathVariable Integer id) {
        return huespedService.borrarHuesped(id);
    }
}
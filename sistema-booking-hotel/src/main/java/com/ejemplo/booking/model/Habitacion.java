package com.ejemplo.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Habitacion {

    @Id
    @NotNull
    private int numero;
    @NotNull
    private TipoHabitacion tipo;
    @NotNull
    private boolean disponible;
    @NotNull
    private double precio;
    @NotNull
    private int capacidad;

    // Relación con Reservas
    @OneToMany(mappedBy = "habitacion",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reserva> reservas;

}


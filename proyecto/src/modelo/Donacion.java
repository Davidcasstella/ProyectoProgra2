package modelo;
import java.util.*;
import controlador.*;
import java.util.*;
import modelo.*;

import interfaces.*;

public class Donacion {
    private int id;
    private Donante donante;
    private Date fechaDonacion;
    private Animal animalAsignado; // Puede ser null si aún no se asigna

    public Donacion() {}

    public Donacion(int id, Donante donante, Date fechaDonacion, Animal animalAsignado) {
        this.id = id;
        this.donante = donante;
        this.fechaDonacion = fechaDonacion;
        this.animalAsignado = animalAsignado;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Donante getDonante() {
        return donante;
    }
    public void setDonante(Donante donante) {
        this.donante = donante;
    }
    public Date getFechaDonacion() {
        return fechaDonacion;
    }
    public void setFechaDonacion(Date fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }
    public Animal getAnimalAsignado() {
        return animalAsignado;
    }
    public void setAnimalAsignado(Animal animalAsignado) {
        this.animalAsignado = animalAsignado;
    }
}
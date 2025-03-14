package modelo;
import java.util.*;
import controlador.*;
import java.util.*;
import modelo.*;
import repositorios.*;
import interfaces.*;

public class Donante {
    private int id;
    private String nombreCompleto;
    private String telefono;
    private TipoDonacion tipoDonacion;

    public Donante() {}

    public Donante(int id, String nombreCompleto, String telefono, TipoDonacion tipoDonacion) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.tipoDonacion = tipoDonacion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public TipoDonacion getTipoDonacion() {
        return tipoDonacion;
    }
    public void setTipoDonacion(TipoDonacion tipoDonacion) {
        this.tipoDonacion = tipoDonacion;
    }
}

package modelo;
import controlador.*;
import java.util.*;
import modelo.*;
import repositorios.*;
import interfaces.*;

public abstract class Animal {
    protected int id;
    protected String nombre;
    protected String especie;
    protected String estadoSalud;

    public Animal() {}

    public Animal(int id, String nombre, String especie, String estadoSalud) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.estadoSalud = estadoSalud;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEspecie() {
        return especie;
    }
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    public String getEstadoSalud() {
        return estadoSalud;
    }
    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }
}

package modelo;
import controlador.*;
import java.util.*;
import modelo.*;

import interfaces.*;

public  class Animal {
    protected int id;
    protected String nombre;
    protected String tipoAnimal;
    protected String estadoSalud;

    public Animal() {}

    public Animal(int id, String nombre, String tipoAnimal, String estadoSalud) {
        this.id = id;
        this.nombre = nombre;
        this.tipoAnimal = tipoAnimal;
        this.estadoSalud = estadoSalud;
    }

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

    public String getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    

    

}
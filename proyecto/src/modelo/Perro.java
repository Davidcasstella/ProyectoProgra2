package modelo;
import java.util.*;
import controlador.*;
import java.util.*;
import modelo.*;
import repositorios.*;
import interfaces.*;

public class Perro extends Animal {
    public Perro(int id, String nombre, String estadoSalud) {
        super(id, nombre, "Perro", estadoSalud);
    }
}
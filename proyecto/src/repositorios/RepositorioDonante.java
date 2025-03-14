package repositorios;
import java.util.*;

import controlador.*;
import java.util.*;
import modelo.*;
import repositorios.*;
import interfaces.*;

    


public class RepositorioDonante implements ICRUD<Donante> {
    private List<Donante> donantes = new ArrayList<>();
    private int contadorId = 1;

    @Override
    public void agregar(Donante donante) throws Exception {
        // Validación para evitar duplicados (mismo nombre y teléfono)
        for (Donante d : donantes) {
            if (d.getNombreCompleto().equalsIgnoreCase(donante.getNombreCompleto()) &&
                d.getTelefono().equals(donante.getTelefono())) {
                throw new Exception("Donante duplicado.");
            }
        }
        donante.setId(contadorId++);
        donantes.add(donante);
    }

    @Override
    public void actualizar(Donante donante) throws Exception {
        Donante existente = buscarPorId(donante.getId());
        if (existente == null) {
            throw new Exception("Donante no encontrado.");
        }
        existente.setNombreCompleto(donante.getNombreCompleto());
        existente.setTelefono(donante.getTelefono());
        existente.setTipoDonacion(donante.getTipoDonacion());
    }

    @Override
    public void eliminar(Donante donante) throws Exception {
        if (!donantes.remove(donante)) {
            throw new Exception("Donante no encontrado para eliminar.");
        }
    }

    @Override
    public Donante buscarPorId(int id) {
        for (Donante d : donantes) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    @Override
    public List<Donante> obtenerTodos() {
        return donantes;
    }
}
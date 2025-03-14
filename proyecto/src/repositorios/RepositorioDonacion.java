package repositorios;
import java.util.*;
import controlador.*;
import java.util.*;
import modelo.*;
import repositorios.*;
import interfaces.*;


public class RepositorioDonacion implements ICRUD<Donacion> {
    private List<Donacion> donaciones = new ArrayList<>();
    private int contadorId = 1;

    @Override
    public void agregar(Donacion donacion) throws Exception {
        donacion.setId(contadorId++);
        donaciones.add(donacion);
    }

    @Override
    public void actualizar(Donacion donacion) throws Exception {
        Donacion existente = buscarPorId(donacion.getId());
        if (existente == null) {
            throw new Exception("Donación no encontrada.");
        }
        existente.setDonante(donacion.getDonante());
        existente.setFechaDonacion(donacion.getFechaDonacion());
        existente.setAnimalAsignado(donacion.getAnimalAsignado());
    }

    @Override
    public void eliminar(Donacion donacion) throws Exception {
        if (!donaciones.remove(donacion)) {
            throw new Exception("Donación no encontrada para eliminar.");
        }
    }

    @Override
    public Donacion buscarPorId(int id) {
        for (Donacion d : donaciones) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    @Override
    public List<Donacion> obtenerTodos() {
        return donaciones;
    }
}
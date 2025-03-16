package controlador;

import java.util.ArrayList;
import java.util.List;
import interfaces.ICRUD;
import modelo.Animal;

public class crudAnimal implements ICRUD<Animal> {
    private List<Animal> animales = new ArrayList<>();
    private int contadorId = 1;

    @Override
    public void agregar(Animal animal) throws Exception {
        // Evitar duplicados: mismo nombre y tipo de animal
        for (Animal a : animales) {
            if (a.getNombre().equalsIgnoreCase(animal.getNombre())
                && a.getTipoAnimal().equalsIgnoreCase(animal.getTipoAnimal())) {
                throw new Exception("Animal duplicado.");
            }
        }
        animal.setId(contadorId++);
        animales.add(animal);
    }

    @Override
    public void actualizar(Animal animal) throws Exception {
        Animal existente = buscarPorId(animal.getId());
        if (existente == null) {
            throw new Exception("Animal no encontrado.");
        }
        existente.setNombre(animal.getNombre());
        existente.setTipoAnimal(animal.getTipoAnimal());
        existente.setEstadoSalud(animal.getEstadoSalud());
    }

    @Override
    public void eliminar(Animal animal) throws Exception {
        if (!animales.remove(animal)) {
            throw new Exception("Animal no encontrado para eliminar.");
        }
    }

    @Override
    public Animal buscarPorId(int id) {
        for (Animal a : animales) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    @Override
    public List<Animal> obtenerTodos() {
        return animales;
    }
}

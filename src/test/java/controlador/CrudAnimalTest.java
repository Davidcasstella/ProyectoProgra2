package controlador;

import co.edu.uptc.modelo.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import co.edu.uptc.controlador.*;

class CrudAnimalTest {

    private crudAnimal crudAnimal;

    @BeforeEach
    void setUp() {
        crudAnimal = new crudAnimal();
        crudAnimal.obtenerTodos().clear(); // Limpiar la lista antes de cada prueba
    }

    @Test
    void testAgregarAnimal() throws Exception {
        Animal animal = new Animal(0, "Perro", "Bulldog", "BIEN");
        crudAnimal.agregar(animal);

        assertEquals(1, crudAnimal.obtenerTodos().size());  // Verifica que se agregó el animal
    }

    @Test
    void testActualizarAnimal() throws Exception {
        Animal animal = new Animal(0, "Perro", "Bulldog", "BIEN");
        crudAnimal.agregar(animal);
        animal.setNombre("Gato");

        crudAnimal.actualizar(animal);

        assertEquals("Gato", crudAnimal.buscarPorId(animal.getId()).getNombre());
    }

    @Test
    void testEliminarAnimal() throws Exception {
        Animal animal = new Animal(0, "Perro", "Bulldog", "BIEN");
        crudAnimal.agregar(animal);
        crudAnimal.eliminar(animal);

        assertNull(crudAnimal.buscarPorId(animal.getId()));  // Verifica que el animal ha sido eliminado
    }

    @Test
    void testListarAnimales() throws Exception {
        Animal animal1 = new Animal(0, "Perro", "Bulldog", "BIEN");
        Animal animal2 = new Animal(0, "Gato", "Persa", "REGULAR");

        crudAnimal.agregar(animal1);
        crudAnimal.agregar(animal2);

        assertEquals(2, crudAnimal.obtenerTodos().size());  // Verifica que se han listado los animales correctamente
    }
}

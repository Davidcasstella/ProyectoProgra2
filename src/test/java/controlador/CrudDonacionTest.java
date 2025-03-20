package controlador;

import co.edu.uptc.modelo.Donacion;
import co.edu.uptc.modelo.Donante;
import co.edu.uptc.modelo.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import co.edu.uptc.controlador.*;

class CrudDonacionTest {

    private crudDonacion crudDonacion;
    private crudDonante crudDonante;
    private crudAnimal crudAnimal;

    @BeforeEach
    void setUp() {
        // Limpiamos las colecciones de datos antes de cada prueba.
        crudDonante = new crudDonante();
        crudAnimal = new crudAnimal();
        crudDonacion = new crudDonacion(crudDonante, crudAnimal);

        // Limpieza de datos de prueba previos
        crudDonante.obtenerTodos().clear();
        crudAnimal.obtenerTodos().clear();
        crudDonacion.obtenerTodos().clear();
    }

    @Test
    void testAsignarDonacion() throws Exception {
        Donante donante = new Donante();
        donante.setNombreCompleto("Juan Perez");
        donante.setTelefono("123456789");
        donante.setTipoDonacion("DINERO");

        crudDonante.agregar(donante);

        Animal animal = new Animal(0, "Perro", "Bulldog", "BIEN");
        crudAnimal.agregar(animal);

        Donacion donacion = new Donacion();
        donacion.setDonante(donante);
        donacion.setAnimalAsignado(animal);

        crudDonacion.agregar(donacion);

        // Verifica que se asignó la donación correctamente
        assertEquals(1, crudDonacion.obtenerTodos().size());
    }

    @Test
    void testListarDonaciones() throws Exception {
        Donante donante = new Donante();
        donante.setNombreCompleto("Juan Perez");
        donante.setTelefono("123456789");
        donante.setTipoDonacion("DINERO");

        crudDonante.agregar(donante);

        Animal animal = new Animal(0, "Perro", "Bulldog", "BIEN");
        crudAnimal.agregar(animal);

        // Crear y agregar la primera donación
        Donacion donacion1 = new Donacion();
        donacion1.setDonante(donante);
        donacion1.setAnimalAsignado(animal);
        crudDonacion.agregar(donacion1);

        // Crear y agregar la segunda donación, pero sin asignar un animal
        Donacion donacion2 = new Donacion();
        donacion2.setDonante(donante);
        donacion2.setAnimalAsignado(null);
        crudDonacion.agregar(donacion2);

        // Verifica que se han listado las donaciones correctamente
        assertEquals(2, crudDonacion.obtenerTodos().size());  
    }
}

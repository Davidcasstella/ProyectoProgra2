package controlador;


import co.edu.uptc.modelo.Donante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import co.edu.uptc.controlador.*;

class CrudDonanteTest {

    private crudDonante crudDonante;

    @BeforeEach
    void setUp() {
        crudDonante = new crudDonante();
        crudDonante.obtenerTodos().clear(); // Limpiar la lista antes de cada prueba
    }

    @Test
    void testAgregarDonante() throws Exception {
        Donante donante = new Donante();
        donante.setNombreCompleto("Juan Perez");
        donante.setTelefono("123456789");
        donante.setTipoDonacion("DINERO");

        crudDonante.agregar(donante);

        assertEquals(1, crudDonante.obtenerTodos().size());  // Verifica que se agregó el donante
    }

    @Test
    void testActualizarDonante() throws Exception {
        Donante donante = new Donante();
        donante.setNombreCompleto("Juan Perez");
        donante.setTelefono("123456789");
        donante.setTipoDonacion("DINERO");

        crudDonante.agregar(donante);
        donante.setNombreCompleto("Carlos Perez");

        crudDonante.actualizar(donante);

        assertEquals("Carlos Perez", crudDonante.buscarPorId(donante.getId()).getNombreCompleto());
    }

    @Test
    void testEliminarDonante() throws Exception {
        Donante donante = new Donante();
        donante.setNombreCompleto("Juan Perez");
        donante.setTelefono("123456789");
        donante.setTipoDonacion("DINERO");

        crudDonante.agregar(donante);
        crudDonante.eliminar(donante);

        assertNull(crudDonante.buscarPorId(donante.getId()));  // Verifica que el donante ha sido eliminado
    }

    @Test
    void testListarDonantes() throws Exception {
        Donante donante1 = new Donante();
        donante1.setNombreCompleto("Juan Perez");
        donante1.setTelefono("123456789");
        donante1.setTipoDonacion("DINERO");

        Donante donante2 = new Donante();
        donante2.setNombreCompleto("Ana López");
        donante2.setTelefono("987654321");
        donante2.setTipoDonacion("ALIMENTO");

        crudDonante.agregar(donante1);
        crudDonante.agregar(donante2);

        assertEquals(2, crudDonante.obtenerTodos().size()); // Verifica que se han listado los donantes correctamente
    }
}
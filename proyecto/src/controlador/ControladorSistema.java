package controlador;

import managment.GestionReportes;

import java.util.Date;
import java.util.List;
import modelo.Donacion;
import modelo.Donante;
import modelo.Animal;

public class ControladorSistema {
    private crudDonante crudDonante;
    private crudAnimal crudAnimal;
    private crudDonacion crudDonacion;
    private GestionReportes gestionReportes;

    public ControladorSistema(crudDonante cd, crudAnimal ca, crudDonacion cdo) {
        this.crudDonante = cd;
        this.crudAnimal = ca;
        this.crudDonacion = cdo;
        // Instanciamos la clase que maneja los reportes, pasándole el crudDonacion
        this.gestionReportes = new GestionReportes(cdo);
    }

    // ------------------ CRUD Donante ------------------
    public void agregarDonante(String nombre, String telefono, String tipoDonacion) throws Exception {
        Donante donante = new Donante();
        donante.setNombreCompleto(nombre);
        donante.setTelefono(telefono);
        donante.setTipoDonacion(tipoDonacion);
        crudDonante.agregar(donante);
    }

    public void actualizarDonante(int id, String nombre, String telefono, String tipoDonacion) throws Exception {
        Donante donante = crudDonante.buscarPorId(id);
        if (donante == null) {
            throw new Exception("Donante no encontrado.");
        }
        donante.setNombreCompleto(nombre);
        donante.setTelefono(telefono);
        donante.setTipoDonacion(tipoDonacion);
        crudDonante.actualizar(donante);
    }

    public void eliminarDonante(int id) throws Exception {
        Donante donante = crudDonante.buscarPorId(id);
        if (donante == null) {
            throw new Exception("Donante no encontrado.");
        }
        crudDonante.eliminar(donante);
    }

    public List<Donante> listarDonantes() {
        return crudDonante.obtenerTodos();
    }

    // ------------------ CRUD Animal ------------------
    public void agregarAnimal(String tipoAnimal, String nombre, String estadoSalud) throws Exception {
        Animal animal = new Animal();
        animal.setNombre(nombre);
        animal.setTipoAnimal(tipoAnimal);
        animal.setEstadoSalud(estadoSalud);
        crudAnimal.agregar(animal);
    }

    public void actualizarAnimal(int id, String nombre, String tipoAnimal, String estadoSalud) throws Exception {
        Animal animal = crudAnimal.buscarPorId(id);
        if (animal == null) {
            throw new Exception("Animal no encontrado.");
        }
        animal.setNombre(nombre);
        animal.setTipoAnimal(tipoAnimal);
        animal.setEstadoSalud(estadoSalud);
        crudAnimal.actualizar(animal);
    }

    public void eliminarAnimal(int id) throws Exception {
        Animal animal = crudAnimal.buscarPorId(id);
        if (animal == null) {
            throw new Exception("Animal no encontrado.");
        }
        crudAnimal.eliminar(animal);
    }

    public List<Animal> listarAnimales() {
        return crudAnimal.obtenerTodos();
    }

    // ------------------ Donaciones ------------------
    public void asignarDonacion(int donanteId, int animalId) throws Exception {
        Donante donante = crudDonante.buscarPorId(donanteId);
        Animal animal = crudAnimal.buscarPorId(animalId);
        if (donante == null || animal == null) {
            throw new Exception("Donante o Animal no encontrado.");
        }
        Donacion donacion = new Donacion();
        donacion.setDonante(donante);
        donacion.setAnimalAsignado(animal);
        donacion.setFechaDonacion(new Date());
        crudDonacion.agregar(donacion);
    }

    public List<Donacion> listarDonaciones() {
        return crudDonacion.obtenerTodos();
    }

    // ------------------ Reportes (delegados a GestionReportes) ------------------
    public String generarReporteDonacionesPorMes(int mes, int anio) {
        return gestionReportes.generarReporteDonacionesPorMes(mes, anio);
    }

    public String generarReporteAnimalesAtendidos() {
        return gestionReportes.generarReporteAnimalesAtendidos();
    }
}

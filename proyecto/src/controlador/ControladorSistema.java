package controlador;
import java.util.*;
import modelo.*;
import repositorios.*;
import interfaces.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


public class ControladorSistema {
    private RepositorioDonante repositorioDonante;
    private RepositorioAnimal repositorioAnimal;
    private RepositorioDonacion repositorioDonacion;

    public ControladorSistema(RepositorioDonante rd, RepositorioAnimal ra, RepositorioDonacion rdo) {
        this.repositorioDonante = rd;
        this.repositorioAnimal = ra;
        this.repositorioDonacion = rdo;
    }

    // CRUD para Donante
    public void agregarDonante(String nombre, String telefono, TipoDonacion tipo) throws Exception {
        Donante donante = new Donante();
        donante.setNombreCompleto(nombre);
        donante.setTelefono(telefono);
        donante.setTipoDonacion(tipo);
        repositorioDonante.agregar(donante);
    }

    public void actualizarDonante(int id, String nombre, String telefono, TipoDonacion tipo) throws Exception {
        Donante donante = repositorioDonante.buscarPorId(id);
        if (donante == null) {
            throw new Exception("Donante no encontrado.");
        }
        donante.setNombreCompleto(nombre);
        donante.setTelefono(telefono);
        donante.setTipoDonacion(tipo);
        repositorioDonante.actualizar(donante);
    }

    public void eliminarDonante(int id) throws Exception {
        Donante donante = repositorioDonante.buscarPorId(id);
        if (donante == null) {
            throw new Exception("Donante no encontrado.");
        }
        repositorioDonante.eliminar(donante);
    }

    public List<Donante> listarDonantes() {
        return repositorioDonante.obtenerTodos();
    }

    // CRUD para Animal
    public void agregarAnimal(String tipoAnimal, String nombre, String estadoSalud) throws Exception {
        Animal animal;
        if (tipoAnimal.equalsIgnoreCase("Perro")) {
            animal = new Perro(0, nombre, estadoSalud); // El id se asigna en el repositorio
        } else if (tipoAnimal.equalsIgnoreCase("Gato")) {
            animal = new Gato(0, nombre, estadoSalud);
        } else {
            throw new Exception("Tipo de animal no soportado.");
        }
        repositorioAnimal.agregar(animal);
    }

    public void actualizarAnimal(int id, String nombre, String estadoSalud) throws Exception {
        Animal animal = repositorioAnimal.buscarPorId(id);
        if (animal == null) {
            throw new Exception("Animal no encontrado.");
        }
        animal.setNombre(nombre);
        animal.setEstadoSalud(estadoSalud);
        repositorioAnimal.actualizar(animal);
    }

    public void eliminarAnimal(int id) throws Exception {
        Animal animal = repositorioAnimal.buscarPorId(id);
        if (animal == null) {
            throw new Exception("Animal no encontrado.");
        }
        repositorioAnimal.eliminar(animal);
    }

    public List<Animal> listarAnimales() {
        return repositorioAnimal.obtenerTodos();
    }

    // Asignar una donación a un animal
    public void asignarDonacion(int donanteId, int animalId) throws Exception {
        Donante donante = repositorioDonante.buscarPorId(donanteId);
        Animal animal = repositorioAnimal.buscarPorId(animalId);
        if (donante == null || animal == null) {
            throw new Exception("Donante o Animal no encontrado.");
        }
        Donacion donacion = new Donacion();
        donacion.setDonante(donante);
        donacion.setAnimalAsignado(animal);
        donacion.setFechaDonacion(new Date());
        repositorioDonacion.agregar(donacion);
    }

    public List<Donacion> listarDonaciones() {
        return repositorioDonacion.obtenerTodos();
    }

    // Generar Reporte de Donaciones por mes (se guarda en un archivo TXT)
    public String generarReporteDonacionesPorMes(int mes, int anio) {
        List<Donacion> donaciones = repositorioDonacion.obtenerTodos();
        StringBuilder reporte = new StringBuilder();
        reporte.append("Reporte de Donaciones para " + mes + "/" + anio + "\n");
        for (Donacion d : donaciones) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(d.getFechaDonacion());
            int mesDonacion = cal.get(Calendar.MONTH) + 1;
            int anioDonacion = cal.get(Calendar.YEAR);
            if (mesDonacion == mes && anioDonacion == anio) {
                reporte.append("ID: " + d.getId() + " - Donante: " + d.getDonante().getNombreCompleto() +
                        " - Tipo: " + d.getDonante().getTipoDonacion() +
                        " - Fecha: " + d.getFechaDonacion() + "\n");
            }
        }
        try {
            Files.write(Paths.get("ReporteDonaciones.txt"), reporte.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reporte.toString();
    }

    // Generar Reporte de Animales Atendidos (se guarda en un archivo TXT)
    public String generarReporteAnimalesAtendidos() {
        List<Donacion> donaciones = repositorioDonacion.obtenerTodos();
        StringBuilder reporte = new StringBuilder();
        reporte.append("Reporte de Animales Atendidos\n");
        for (Donacion d : donaciones) {
            if (d.getAnimalAsignado() != null) {
                reporte.append("Animal ID: " + d.getAnimalAsignado().getId() +
                        " - Nombre: " + d.getAnimalAsignado().getNombre() +
                        " - Especie: " + d.getAnimalAsignado().getEspecie() + "\n");
            }
        }
        try {
            Files.write(Paths.get("ReporteAnimalesAtendidos.txt"), reporte.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reporte.toString();
    }
}
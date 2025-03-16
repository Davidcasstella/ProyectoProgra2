package controlador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import modelo.Donacion;
import modelo.Donante;
import modelo.Animal;

public class ControladorSistema {
    private crudDonante crudDonante;
    private crudAnimal crudAnimal;
    private crudDonacion crudDonacion;

    public ControladorSistema(crudDonante cd, crudAnimal ca, crudDonacion cdo) {
        this.crudDonante = cd;
        this.crudAnimal = ca;
        this.crudDonacion = cdo;
    }

    // CRUD para Donante
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

    // CRUD para Animal
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

    // Asignar una donación a un animal
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

    // Reporte de Donaciones por mes (se guarda en la carpeta "Managment")
    public String generarReporteDonacionesPorMes(int mes, int anio) {
        List<Donacion> donaciones = crudDonacion.obtenerTodos();
        StringBuilder reporte = new StringBuilder();
        reporte.append("Reporte de donaciones recibidas por mes.\n");
        reporte.append("Mes: " + mes + ", Año: " + anio + "\n");

        for (Donacion d : donaciones) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(d.getFechaDonacion());
            int mesDonacion = cal.get(Calendar.MONTH) + 1;
            int anioDonacion = cal.get(Calendar.YEAR);
            if (mesDonacion == mes && anioDonacion == anio) {
                reporte.append("ID: " + d.getId() 
                             + " - Donante: " + d.getDonante().getNombreCompleto()
                             + " - Tipo de donación: " + d.getDonante().getTipoDonacion()
                             + " - Fecha: " + d.getFechaDonacion() + "\n");
            }
        }

        try {
            // 1) Crear la carpeta "Managment" si no existe
            Files.createDirectories(Paths.get("Managment"));
            // 2) Guardar el reporte
            Files.write(Paths.get("Managment/ReporteDonacionesRecibidasPorMes.txt"),
                        reporte.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reporte.toString();
    }

    // Reporte de Animales Atendidos (se guarda en la carpeta "Managment")
    public String generarReporteAnimalesAtendidos() {
        List<Donacion> donaciones = crudDonacion.obtenerTodos();
        StringBuilder reporte = new StringBuilder();
        reporte.append("Reporte de animales atendidos.\n");

        for (Donacion d : donaciones) {
            if (d.getAnimalAsignado() != null) {
                reporte.append("Animal ID: " + d.getAnimalAsignado().getId()
                             + " - Nombre: " + d.getAnimalAsignado().getNombre()
                             + " - Tipo: " + d.getAnimalAsignado().getTipoAnimal() + "\n");
            }
        }

        try {
            // 1) Crear la carpeta "Managment" si no existe
            Files.createDirectories(Paths.get("Managment"));
            // 2) Guardar el reporte
            Files.write(Paths.get("Managment/ReporteDeAnimalesAtendidos.txt"),
                        reporte.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reporte.toString();
    }
}

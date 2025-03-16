package managment;

import controlador.crudDonacion;
import modelo.Donacion;
import modelo.Donante;
import modelo.Animal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * GestionReportes se encarga de generar los reportes de donaciones y animales atendidos.
 * Los archivos se guardan en la carpeta "Persistencia" (creada automáticamente si no existe).
 */
public class GestionReportes {

    private crudDonacion crudDonacion;

    public GestionReportes(crudDonacion cDonacion) {
        this.crudDonacion = cDonacion;
    }

    /**
     * Genera el reporte de donaciones recibidas en un mes y año específicos.
     * Crea la carpeta "Persistencia" si no existe y escribe el archivo de reporte.
     */
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
                Donante donante = d.getDonante();
                reporte.append("ID: ").append(d.getId())
                       .append(" - Donante: ").append(donante.getNombreCompleto())
                       .append(" - Tipo de donación: ").append(donante.getTipoDonacion())
                       .append(" - Fecha: ").append(d.getFechaDonacion()).append("\n");
            }
        }

        try {
            // Crear la carpeta Persistencia si no existe
            Files.createDirectories(Paths.get("Persistencia"));
            // Guardar el archivo de reporte
            Files.write(Paths.get("Persistencia/ReporteDonacionesRecibidasPorMes.txt"),
                        reporte.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reporte.toString();
    }

    /**
     * Genera el reporte de animales atendidos.
     * Crea la carpeta "Persistencia" si no existe y escribe el archivo de reporte.
     */
    public String generarReporteAnimalesAtendidos() {
        List<Donacion> donaciones = crudDonacion.obtenerTodos();
        StringBuilder reporte = new StringBuilder();
        reporte.append("Reporte de animales atendidos.\n");

        for (Donacion d : donaciones) {
            Animal animal = d.getAnimalAsignado();
            if (animal != null) {
                reporte.append("Animal ID: ").append(animal.getId())
                       .append(" - Nombre: ").append(animal.getNombre())
                       .append(" - Tipo: ").append(animal.getTipoAnimal()).append("\n");
            }
        }

        try {
            // Crear la carpeta Persistencia si no existe
            Files.createDirectories(Paths.get("Persistencia"));
            // Guardar el archivo de reporte
            Files.write(Paths.get("Persistencia/ReporteDeAnimalesAtendidos.txt"),
                        reporte.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reporte.toString();
    }
}

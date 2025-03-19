package managment;

import controlador.crudDonacion;
import modelo.Donacion;
import modelo.Donante;
import modelo.Animal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * GestionReportes se encarga de generar y guardar los reportes de donaciones
 * y animales atendidos en archivos de texto, con un formato detallado.
 */
public class GestionReportes {

    private crudDonacion crudDonacion;

    public GestionReportes(crudDonacion cDonacion) {
        this.crudDonacion = cDonacion;
    }

    /**
     * Genera el reporte de donaciones recibidas en un mes y año específicos,
     * mostrando más información sobre quién donó, el teléfono, tipo de donación,
     * a qué animal ayudó (si corresponde), y la fecha de la donación.
     */
    public String generarReporteDonacionesPorMes(int mes, int anio) {
        List<Donacion> donaciones = crudDonacion.obtenerTodos();
        StringBuilder reporte = new StringBuilder();

        // Encabezado
        reporte.append("**************************************************************\n");
        reporte.append("       REPORTE DE DONACIONES RECIBIDAS POR MES (DETALLADO)     \n");
        reporte.append("**************************************************************\n");
        reporte.append("Mes: ").append(mes).append(" | Año: ").append(anio).append("\n");
        reporte.append("Generado: ").append(fechaActual()).append("\n\n");

        // Cabecera de columnas (ID Donacion, Donante, Teléfono, Tipo Donación, Animal, Fecha)
        reporte.append(String.format("%-5s | %-20s | %-12s | %-15s | %-15s | %-25s\n",
                "ID", "Donante", "Telefono", "Tipo Donación", "Animal", "Fecha Donación"));
        reporte.append("-----------------------------------------------------------------------------------------------\n");

        int contador = 0;
        for (Donacion d : donaciones) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(d.getFechaDonacion());
            int mesDonacion = cal.get(Calendar.MONTH) + 1;
            int anioDonacion = cal.get(Calendar.YEAR);

            if (mesDonacion == mes && anioDonacion == anio) {
                Donante donante = d.getDonante();
                Animal animal = d.getAnimalAsignado();

                String nombreDonante   = (donante != null) ? donante.getNombreCompleto() : "SIN DONANTE";
                String telDonante      = (donante != null) ? donante.getTelefono()       : "N/A";
                String tipoDonacion    = (donante != null) ? donante.getTipoDonacion()   : "N/A";
                String nombreAnimal    = (animal  != null) ? animal.getNombre()          : "SIN ANIMAL";

                reporte.append(String.format("%-5d | %-20s | %-12s | %-15s | %-15s | %-25s\n",
                        d.getId(),
                        nombreDonante,
                        telDonante,
                        tipoDonacion,
                        nombreAnimal,
                        d.getFechaDonacion()
                ));
                contador++;
            }
        }

        reporte.append("\nTotal de Donaciones en ")
               .append(mes).append("/").append(anio)
               .append(": ").append(contador).append("\n");

        // Guardar reporte en archivo dentro de src/Managment
        try {
            Files.createDirectories(Paths.get("src/Managment"));  // Crea la carpeta si no existe
            Files.write(Paths.get("src/Managment/ReporteDonacionesRecibidasPorMes.txt"),
                        reporte.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reporte.toString();
    }

    /**
     * Genera el reporte de animales atendidos, mostrando quién los ayudó (donante),
     * con qué tipo de donación, teléfono del donante, estado de salud del animal,
     * y fecha de la donación.
     */
    public String generarReporteAnimalesAtendidos() {
        List<Donacion> donaciones = crudDonacion.obtenerTodos();
        StringBuilder reporte = new StringBuilder();

        // Encabezado
        reporte.append("****************************************************************\n");
        reporte.append("     REPORTE DE ANIMALES ATENDIDOS (CON DONANTE Y DETALLES)      \n");
        reporte.append("****************************************************************\n");
        reporte.append("Generado: ").append(fechaActual()).append("\n\n");

        // Cabecera de columnas
        // ID Animal, Nombre Animal, Estado Salud, Donante, Tipo Donacion, Teléfono, Fecha
        reporte.append(String.format("%-5s | %-15s | %-12s | %-20s | %-15s | %-12s | %-25s\n",
                "ID", "Animal", "Salud", "Donante", "Tipo Don.", "Teléfono", "Fecha Donación"));
        reporte.append("-----------------------------------------------------------------------------------------------------\n");

        int contador = 0;
        for (Donacion d : donaciones) {
            Animal animal = d.getAnimalAsignado();
            Donante donante = d.getDonante();

            if (animal != null) {
                String nombreAnimal  = animal.getNombre();
                String estadoSalud   = animal.getEstadoSalud();
                int   idAnimal       = animal.getId();

                String nombreDonante = (donante != null) ? donante.getNombreCompleto() : "SIN DONANTE";
                String tipoDonacion  = (donante != null) ? donante.getTipoDonacion()   : "N/A";
                String telDonante    = (donante != null) ? donante.getTelefono()       : "N/A";
                Date   fechaDon      = d.getFechaDonacion();

                reporte.append(String.format("%-5d | %-15s | %-12s | %-20s | %-15s | %-12s | %-25s\n",
                        idAnimal,
                        nombreAnimal,
                        estadoSalud,
                        nombreDonante,
                        tipoDonacion,
                        telDonante,
                        fechaDon
                ));
                contador++;
            }
        }

        reporte.append("\nTotal de Animales Atendidos: ").append(contador).append("\n");

        // Guardar reporte en archivo dentro de src/Managment
        try {
            Files.createDirectories(Paths.get("src/Managment"));
            Files.write(Paths.get("src/Managment/ReporteDeAnimalesAtendidos.txt"),
                        reporte.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reporte.toString();
    }

    /**
     * Método auxiliar para obtener la fecha/hora actual en formato legible.
     */
    private String fechaActual() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return ahora.format(formatter);
    }
}

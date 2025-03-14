package vista;
import java.util.*;
import controlador.*;
import java.util.*;
import modelo.*;
import repositorios.*;
import interfaces.*;

public class VistaSistema {
    private ControladorSistema controlador;
    private Scanner scanner;

    public VistaSistema(ControladorSistema controlador) {
        this.controlador = controlador;
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intente de nuevo.");
                continue;
            }
            try {
                switch (opcion) {
                    case 1:
                        registrarDonante();
                        break;
                    case 2:
                        registrarAnimal();
                        break;
                    case 3:
                        asignarDonacion();
                        break;
                    case 4:
                        generarReporteDonaciones();
                        break;
                    case 5:
                        generarReporteAnimales();
                        break;
                    case 6:
                        listarDonantes();
                        break;
                    case 7:
                        listarAnimales();
                        break;
                    case 8:
                        listarDonaciones();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n--- Sistema de Registro de Donaciones ---");
        System.out.println("1. Registrar Donante");
        System.out.println("2. Registrar Animal");
        System.out.println("3. Asignar Donación a Animal");
        System.out.println("4. Generar Reporte de Donaciones por Mes");
        System.out.println("5. Generar Reporte de Animales Atendidos");
        System.out.println("6. Listar Donantes");
        System.out.println("7. Listar Animales");
        System.out.println("8. Listar Donaciones");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void registrarDonante() throws Exception {
        System.out.print("Ingrese nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese tipo de donación (DINERO, ALIMENTO, MEDICAMENTOS): ");
        String tipoStr = scanner.nextLine();
        TipoDonacion tipo = TipoDonacion.valueOf(tipoStr.toUpperCase());
        controlador.agregarDonante(nombre, telefono, tipo);
        System.out.println("Donante registrado exitosamente.");
    }

    private void registrarAnimal() throws Exception {
        System.out.print("Ingrese tipo de animal (Perro, Gato): ");
        String tipoAnimal = scanner.nextLine();
        System.out.print("Ingrese nombre del animal: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese estado de salud: ");
        String estado = scanner.nextLine();
        controlador.agregarAnimal(tipoAnimal, nombre, estado);
        System.out.println("Animal registrado exitosamente.");
    }

    private void asignarDonacion() throws Exception {
        System.out.print("Ingrese ID del donante: ");
        int idDonante = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese ID del animal: ");
        int idAnimal = Integer.parseInt(scanner.nextLine());
        controlador.asignarDonacion(idDonante, idAnimal);
        System.out.println("Donación asignada exitosamente.");
    }

    private void generarReporteDonaciones() {
        System.out.print("Ingrese mes (número): ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese año: ");
        int anio = Integer.parseInt(scanner.nextLine());
        String reporte = controlador.generarReporteDonacionesPorMes(mes, anio);
        System.out.println("Reporte generado:\n" + reporte);
    }

    private void generarReporteAnimales() {
        String reporte = controlador.generarReporteAnimalesAtendidos();
        System.out.println("Reporte generado:\n" + reporte);
    }

    private void listarDonantes() {
        System.out.println("Lista de Donantes:");
        for (Donante d : controlador.listarDonantes()) {
            System.out.println("ID: " + d.getId() + " | Nombre: " + d.getNombreCompleto() +
                    " | Teléfono: " + d.getTelefono() + " | Tipo: " + d.getTipoDonacion());
        }
    }

    private void listarAnimales() {
        System.out.println("Lista de Animales:");
        for (Animal a : controlador.listarAnimales()) {
            System.out.println("ID: " + a.getId() + " | Nombre: " + a.getNombre() +
                    " | Especie: " + a.getEspecie() + " | Estado: " + a.getEstadoSalud());
        }
    }

    private void listarDonaciones() {
        System.out.println("Lista de Donaciones:");
        for (Donacion d : controlador.listarDonaciones()) {
            String animalNombre = (d.getAnimalAsignado() != null) ? d.getAnimalAsignado().getNombre() : "No asignado";
            System.out.println("ID: " + d.getId() + " | Donante: " + d.getDonante().getNombreCompleto() +
                    " | Animal: " + animalNombre + " | Fecha: " + d.getFechaDonacion());
        }
    }
}
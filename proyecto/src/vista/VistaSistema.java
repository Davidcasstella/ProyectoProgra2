package vista;

import controlador.ControladorSistema;
import modelo.Donante;
import modelo.Animal;
import modelo.Donacion;

import java.util.List;
import java.util.Scanner;

public class VistaSistema {
    private ControladorSistema controlador;
    private Scanner scanner;

    public VistaSistema(ControladorSistema controlador) {
        this.controlador = controlador;
        this.scanner = new Scanner(System.in);
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

        System.out.println("Seleccione el tipo de donación:");
        System.out.println("1. DINERO");
        System.out.println("2. ALIMENTO");
        System.out.println("3. MEDICAMENTOS");
        System.out.print("Opción: ");
        int opcionTipo = Integer.parseInt(scanner.nextLine());
        String tipoDonacion;
        switch (opcionTipo) {
            case 1:
                tipoDonacion = "DINERO";
                break;
            case 2:
                tipoDonacion = "ALIMENTO";
                break;
            case 3:
                tipoDonacion = "MEDICAMENTOS";
                break;
            default:
                throw new Exception("Opción de tipo de donación inválida.");
        }

        controlador.agregarDonante(nombre, telefono, tipoDonacion);
        System.out.println("Donante registrado exitosamente.");
    }

    private void registrarAnimal() throws Exception {
        System.out.print("Ingrese el tipo de animal (ej: Perro, Gato, Conejo, etc.): ");
        String tipo = scanner.nextLine();
        System.out.print("Ingrese nombre del animal: ");
        String nombre = scanner.nextLine();

        System.out.println("Seleccione el estado de salud:");
        System.out.println("1. MAL");
        System.out.println("2. REGULAR");
        System.out.println("3. BIEN");
        System.out.println("4. URGENTE");
        System.out.print("Opción: ");
        int opcionEstado = Integer.parseInt(scanner.nextLine());
        String estadoSalud;
        switch (opcionEstado) {
            case 1:
                estadoSalud = "MAL";
                break;
            case 2:
                estadoSalud = "REGULAR";
                break;
            case 3:
                estadoSalud = "BIEN";
                break;
            case 4:
                estadoSalud = "URGENTE";
                break;
            default:
                throw new Exception("Opción de estado de salud inválida.");
        }

        controlador.agregarAnimal(tipo, nombre, estadoSalud);
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
                               " | Teléfono: " + d.getTelefono() +
                               " | Tipo de donación: " + d.getTipoDonacion());
        }
    }

    private void listarAnimales() {
        System.out.println("Lista de Animales:");
        for (Animal a : controlador.listarAnimales()) {
            System.out.println("ID: " + a.getId() + " | Nombre: " + a.getNombre() +
                               " | Tipo: " + a.getTipoAnimal() +
                               " | Estado: " + a.getEstadoSalud());
        }
    }

    private void listarDonaciones() {
        System.out.println("Lista de Donaciones:");
        for (Donacion d : controlador.listarDonaciones()) {
            String animalNombre = (d.getAnimalAsignado() != null) ? d.getAnimalAsignado().getNombre() : "No asignado";
            System.out.println("ID: " + d.getId() +
                               " | Donante: " + d.getDonante().getNombreCompleto() +
                               " | Animal: " + animalNombre +
                               " | Fecha: " + d.getFechaDonacion());
        }
    }
}

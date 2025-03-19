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
                        gestionarDonante();
                        break;
                    case 2:
                        gestionarAnimal();
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
        System.out.println("\n--- Bienvenido al Sistema de Registro de Donaciones ---");
        System.out.println("1. Gestionar/Registrar Donante");
        System.out.println("2. Gestionar/Registrar Animal");
        System.out.println("3. Asignar Donación a Animal");
        System.out.println("4. Generar Reporte de Donaciones por Mes");
        System.out.println("5. Generar Reporte de Animales Atendidos");
        System.out.println("6. Listar Donaciones");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void gestionarDonante() throws Exception {
        int opcion = -1;
        while (opcion != 5) {
            System.out.println("\n--- Gestión de Donantes ---");
            System.out.println("1. Registrar Donante");
            System.out.println("2. Actualizar Donante");
            System.out.println("3. Eliminar Donante");
            System.out.println("4. Listar Donantes");
            System.out.println("5. Volver a menu Principal");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    registrarDonante();
                    break;
                case 2:
                    actualizarDonante();
                    break;
                case 3:
                    eliminarDonante();
                    break;
                case 4:
                    listarDonantes();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void gestionarAnimal() throws Exception {
        int opcion = -1;
        while (opcion != 5) {
            System.out.println("\n--- Gestión de Animales ---");
            System.out.println("1. Registrar Animal");
            System.out.println("2. Actualizar Animal");
            System.out.println("3. Eliminar Animal");
            System.out.println("4. Listar Animales");
            System.out.println("5. Volver a menu Principal");
            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    registrarAnimal();
                    break;
                case 2:
                    actualizarAnimal();
                    break;
                case 3:
                    eliminarAnimal();
                    break;
                case 4:
                    listarAnimales();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    // Métodos CRUD para Donantes y Animales (registrar, actualizar, eliminar, listar)

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

    private void actualizarDonante() throws Exception {
        System.out.print("Ingrese ID del donante a actualizar: ");
        int idDonante = Integer.parseInt(scanner.nextLine());
        Donante d = controlador.listarDonantes().stream()
                               .filter(donante -> donante.getId() == idDonante)
                               .findFirst()
                               .orElseThrow(() -> new Exception("Donante no encontrado"));

        System.out.print("Ingrese el nuevo nombre: ");
        d.setNombreCompleto(scanner.nextLine());
        System.out.print("Ingrese el nuevo teléfono: ");
        d.setTelefono(scanner.nextLine());
        System.out.print("Ingrese el nuevo tipo de donación: ");
        d.setTipoDonacion(scanner.nextLine());

        controlador.actualizarDonante(d.getId(), d.getNombreCompleto(), d.getTelefono(), d.getTipoDonacion());
        System.out.println("Donante actualizado exitosamente.");
    }

    private void eliminarDonante() throws Exception {
        System.out.print("Ingrese ID del donante a eliminar: ");
        int idDonante = Integer.parseInt(scanner.nextLine());
        controlador.eliminarDonante(idDonante);
        System.out.println("Donante eliminado exitosamente.");
    }

    private void listarDonantes() {
        System.out.println("Lista de Donantes:");
        for (Donante d : controlador.listarDonantes()) {
            System.out.println("ID: " + d.getId() + " | Nombre: " + d.getNombreCompleto() +
                               " | Teléfono: " + d.getTelefono() +
                               " | Tipo de donación: " + d.getTipoDonacion());
        }
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

    private void actualizarAnimal() throws Exception {
        System.out.print("Ingrese ID del animal a actualizar: ");
        int idAnimal = Integer.parseInt(scanner.nextLine());
        Animal a = controlador.listarAnimales().stream()
                               .filter(animal -> animal.getId() == idAnimal)
                               .findFirst()
                               .orElseThrow(() -> new Exception("Animal no encontrado"));

        System.out.print("Ingrese el nuevo nombre del animal: ");
        a.setNombre(scanner.nextLine());
        System.out.print("Ingrese el nuevo tipo de animal: ");
        a.setTipoAnimal(scanner.nextLine());
        System.out.print("Ingrese el nuevo estado de salud: ");
        a.setEstadoSalud(scanner.nextLine());

        controlador.actualizarAnimal(a.getId(), a.getNombre(), a.getTipoAnimal(), a.getEstadoSalud());
        System.out.println("Animal actualizado exitosamente.");
    }

    private void eliminarAnimal() throws Exception {
        System.out.print("Ingrese ID del animal a eliminar: ");
        int idAnimal = Integer.parseInt(scanner.nextLine());
        controlador.eliminarAnimal(idAnimal);
        System.out.println("Animal eliminado exitosamente.");
    }

    private void listarAnimales() {
        System.out.println("Lista de Animales:");
        for (Animal a : controlador.listarAnimales()) {
            System.out.println("ID: " + a.getId() + " | Nombre: " + a.getNombre() +
                               " | Tipo: " + a.getTipoAnimal() +
                               " | Estado: " + a.getEstadoSalud());
        }
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

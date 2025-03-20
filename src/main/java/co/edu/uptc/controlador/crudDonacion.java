package co.edu.uptc.controlador;

import co.edu.uptc.interfaces.ICRUD;
import co.edu.uptc.modelo.Donacion;
import co.edu.uptc.modelo.Donante;
import co.edu.uptc.modelo.Animal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class crudDonacion implements ICRUD<Donacion> {
    private List<Donacion> donaciones = new ArrayList<>();
    private int contadorId = 1;

    // Rutas y dependencias
    private static final String FILE_PATH = "src/main/java/co/edu/uptc/Persistencia/donaciones.txt";
    private crudDonante crudDonante;
    private crudAnimal crudAnimal;

    public crudDonacion(crudDonante cDonante, crudAnimal cAnimal) {
        this.crudDonante = cDonante;
        this.crudAnimal = cAnimal;
        // Cargar datos al iniciar
        loadFromFile();
    }

    @Override
    public void agregar(Donacion donacion) throws Exception {
        donacion.setId(contadorId++);
        donaciones.add(donacion);
        saveToFile();
    }

    @Override
    public void actualizar(Donacion donacion) throws Exception {
        Donacion existente = buscarPorId(donacion.getId());
        if (existente == null) {
            throw new Exception("Donación no encontrada.");
        }
        existente.setDonante(donacion.getDonante());
        existente.setAnimalAsignado(donacion.getAnimalAsignado());
        existente.setFechaDonacion(donacion.getFechaDonacion());
        saveToFile();
    }

    @Override
    public void eliminar(Donacion donacion) throws Exception {
        if (!donaciones.remove(donacion)) {
            throw new Exception("Donación no encontrada para eliminar.");
        }
        saveToFile();
    }

    @Override
    public Donacion buscarPorId(int id) {
        for (Donacion d : donaciones) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    @Override
    public List<Donacion> obtenerTodos() {
        return donaciones;
    }

    // ---------------------------
    // Métodos de persistencia CSV
    // ---------------------------

    private void loadFromFile() {
        if (!Files.exists(Paths.get(FILE_PATH))) {
            return; // Si no existe, no hay nada que cargar
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Formato: idDonacion;idDonante;idAnimal;fechaDonacionEnMilis
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    int idDonacion = Integer.parseInt(partes[0]);
                    int idDonante = Integer.parseInt(partes[1]);
                    int idAnimal = Integer.parseInt(partes[2]);
                    long fechaMilis = Long.parseLong(partes[3]);

                    Donante donante = crudDonante.buscarPorId(idDonante);
                    Animal animal = crudAnimal.buscarPorId(idAnimal);

                    Donacion donacion = new Donacion();
                    donacion.setId(idDonacion);
                    donacion.setDonante(donante);
                    donacion.setAnimalAsignado(animal);
                    donacion.setFechaDonacion(new Date(fechaMilis));

                    donaciones.add(donacion);

                    // Ajustar contadorId
                    if (idDonacion >= contadorId) {
                        contadorId = idDonacion + 1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        // Crear la carpeta Persistencia si no existe
        try {
            Files.createDirectories(Paths.get("Persistencia"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Donacion d : donaciones) {
                int idDonacion = d.getId();
                int idDonante = (d.getDonante() != null) ? d.getDonante().getId() : 0;
                int idAnimal = (d.getAnimalAsignado() != null) ? d.getAnimalAsignado().getId() : 0;
                long fechaMilis = (d.getFechaDonacion() != null) ? d.getFechaDonacion().getTime() : 0L;

                // Formato CSV: idDonacion;idDonante;idAnimal;fechaDonacionEnMilis
                String linea = idDonacion + ";" 
                             + idDonante + ";" 
                             + idAnimal + ";" 
                             + fechaMilis;

                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package co.edu.uptc.controlador;

import co.edu.uptc.interfaces.ICRUD;
import co.edu.uptc.modelo.Donante;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class crudDonante implements ICRUD<Donante> {
    private List<Donante> donantes = new ArrayList<>();
    private int contadorId = 1;

    private static final String FILE_PATH = "src/main/java/co/edu/uptc/Persistencia/donantes.txt";

    public crudDonante() {
        loadFromFile();
    }

    @Override
    public void agregar(Donante donante) throws Exception {
        for (Donante d : donantes) {
            if (d.getNombreCompleto().equalsIgnoreCase(donante.getNombreCompleto())
                && d.getTelefono().equals(donante.getTelefono())) {
                throw new Exception("Donante duplicado.");
            }
        }
        donante.setId(contadorId++);
        donantes.add(donante);
        saveToFile();
    }

    @Override
    public void actualizar(Donante donante) throws Exception {
        Donante existente = buscarPorId(donante.getId());
        if (existente == null) {
            throw new Exception("Donante no encontrado.");
        }
        existente.setNombreCompleto(donante.getNombreCompleto());
        existente.setTelefono(donante.getTelefono());
        existente.setTipoDonacion(donante.getTipoDonacion());
        saveToFile();
    }

    @Override
    public void eliminar(Donante donante) throws Exception {
        if (!donantes.remove(donante)) {
            throw new Exception("Donante no encontrado para eliminar.");
        }
        saveToFile();
    }

    @Override
    public Donante buscarPorId(int id) {
        for (Donante d : donantes) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    @Override
    public List<Donante> obtenerTodos() {
        return donantes;
    }

    // Método para registrar un donante
    public void registrarDonante(String nombreCompleto, String telefono, String tipoDonacion) throws Exception {
        Donante nuevoDonante = new Donante(0, nombreCompleto, telefono, tipoDonacion);
        agregar(nuevoDonante);
    }

    // Métodos de persistencia CSV
    private void loadFromFile() {
        if (!Files.exists(Paths.get(FILE_PATH))) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    Donante d = new Donante();
                    d.setId(Integer.parseInt(partes[0]));
                    d.setNombreCompleto(partes[1]);
                    d.setTelefono(partes[2]);
                    d.setTipoDonacion(partes[3]);
                    donantes.add(d);

                    if (d.getId() >= contadorId) {
                        contadorId = d.getId() + 1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try {
            Files.createDirectories(Paths.get("Persistencia"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Donante d : donantes) {
                String linea = d.getId() + ";" + d.getNombreCompleto() + ";" + d.getTelefono() + ";" + d.getTipoDonacion();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

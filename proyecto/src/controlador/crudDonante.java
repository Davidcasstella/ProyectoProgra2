package controlador;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import interfaces.ICRUD;
import modelo.Donante;

public class crudDonante implements ICRUD<Donante> {
    private List<Donante> donantes = new ArrayList<>();
    private int contadorId = 1;
    
    // Ruta del archivo donde se guardarán los Donantes
    private static final String FILE_PATH = "Persistencia/donantes.txt";

    public crudDonante() {
        // Cargar los datos del archivo al iniciar
        loadFromFile();
    }

    @Override
    public void agregar(Donante donante) throws Exception {
        // Evitar duplicados
        for (Donante d : donantes) {
            if (d.getNombreCompleto().equalsIgnoreCase(donante.getNombreCompleto())
                && d.getTelefono().equals(donante.getTelefono())) {
                throw new Exception("Donante duplicado.");
            }
        }
        donante.setId(contadorId++);
        donantes.add(donante);
        // Guardar en archivo
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
        // Guardar en archivo
        saveToFile();
    }

    @Override
    public void eliminar(Donante donante) throws Exception {
        if (!donantes.remove(donante)) {
            throw new Exception("Donante no encontrado para eliminar.");
        }
        // Guardar en archivo
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

    // -------------------------------------------------------
    // Métodos para persistir datos en un archivo de texto
    // -------------------------------------------------------

    private void loadFromFile() {
        // Si el archivo no existe, no hacemos nada (será creado al guardar)
        if (!Files.exists(Paths.get(FILE_PATH))) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Formato CSV simple: id;nombreCompleto;telefono;tipoDonacion
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    Donante d = new Donante();
                    d.setId(Integer.parseInt(partes[0]));
                    d.setNombreCompleto(partes[1]);
                    d.setTelefono(partes[2]);
                    d.setTipoDonacion(partes[3]);
                    donantes.add(d);

                    // Ajustar el contadorId para evitar colisiones
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
        // Crear carpeta Persistencia si no existe
        try {
            Files.createDirectories(Paths.get("Persistencia"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Donante d : donantes) {
                // id;nombreCompleto;telefono;tipoDonacion
                String linea = d.getId() + ";" 
                             + d.getNombreCompleto() + ";" 
                             + d.getTelefono() + ";" 
                             + d.getTipoDonacion();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

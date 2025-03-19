package controlador;

import interfaces.ICRUD;
import modelo.Animal;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class crudAnimal implements ICRUD<Animal> {
    private List<Animal> animales = new ArrayList<>();
    private int contadorId = 1;

    private static final String FILE_PATH = "Persistencia/animales.txt";

    public crudAnimal() {
        loadFromFile();
    }

    @Override
    public void agregar(Animal animal) throws Exception {
        for (Animal a : animales) {
            if (a.getNombre().equalsIgnoreCase(animal.getNombre())
                && a.getTipoAnimal().equalsIgnoreCase(animal.getTipoAnimal())) {
                throw new Exception("Animal duplicado.");
            }
        }
        animal.setId(contadorId++);
        animales.add(animal);
        saveToFile();
    }

    @Override
    public void actualizar(Animal animal) throws Exception {
        Animal existente = buscarPorId(animal.getId());
        if (existente == null) {
            throw new Exception("Animal no encontrado.");
        }
        existente.setNombre(animal.getNombre());
        existente.setTipoAnimal(animal.getTipoAnimal());
        existente.setEstadoSalud(animal.getEstadoSalud());
        saveToFile();
    }

    @Override
    public void eliminar(Animal animal) throws Exception {
        if (!animales.remove(animal)) {
            throw new Exception("Animal no encontrado para eliminar.");
        }
        saveToFile();
    }

    @Override
    public Animal buscarPorId(int id) {
        for (Animal a : animales) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    @Override
    public List<Animal> obtenerTodos() {
        return animales;
    }

    // Método para registrar un animal
    public void registrarAnimal(String nombre, String tipoAnimal, String estadoSalud) throws Exception {
        Animal nuevoAnimal = new Animal(0, nombre, tipoAnimal, estadoSalud);
        agregar(nuevoAnimal);
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
                    Animal a = new Animal();
                    a.setId(Integer.parseInt(partes[0]));
                    a.setNombre(partes[1]);
                    a.setTipoAnimal(partes[2]);
                    a.setEstadoSalud(partes[3]);
                    animales.add(a);

                    if (a.getId() >= contadorId) {
                        contadorId = a.getId() + 1;
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
            for (Animal a : animales) {
                String linea = a.getId() + ";" + a.getNombre() + ";" + a.getTipoAnimal() + ";" + a.getEstadoSalud();
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

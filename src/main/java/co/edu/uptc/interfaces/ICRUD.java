package co.edu.uptc.interfaces;

import java.util.List;

public interface ICRUD<T> {
    void agregar(T objeto) throws Exception;
    void actualizar(T objeto) throws Exception;
    void eliminar(T objeto) throws Exception;
    T buscarPorId(int id);
    List<T> obtenerTodos();
}

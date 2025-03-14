package interfaces;
import java.util.*;

import java.util.*;
import modelo.*;
import repositorios.*;
import interfaces.*;

public interface ICRUD<T> {
    void agregar(T objeto) throws Exception;
    void actualizar(T objeto) throws Exception;
    void eliminar(T objeto) throws Exception;
    T buscarPorId(int id);
    List<T> obtenerTodos();
}
import java.util.*;
import repositorios.*;
import controlador.*;
import vista.*;

public class App {
    public static void main(String[] args) {
        RepositorioDonante repoDonante = new RepositorioDonante();
        RepositorioAnimal repoAnimal = new RepositorioAnimal();
        RepositorioDonacion repoDonacion = new RepositorioDonacion();

        ControladorSistema controlador = new ControladorSistema(repoDonante, repoAnimal, repoDonacion);
        VistaSistema vista = new VistaSistema(controlador);
        vista.iniciar();
    }



    
    
}

import java.util.*;

import controlador.*;
import vista.*;

public class App {
    public static void main(String[] args) {
        crudDonante cd = new crudDonante();
        crudAnimal ca = new crudAnimal();
        crudDonacion cdo = new crudDonacion();

        ControladorSistema controlador = new ControladorSistema(cd, ca, cdo);
        VistaSistema vista = new VistaSistema(controlador);
        vista.iniciar();
    }
}

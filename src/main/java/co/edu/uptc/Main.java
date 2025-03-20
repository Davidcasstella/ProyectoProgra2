package co.edu.uptc;

import java.util.*;

import co.edu.uptc.controlador.*;
import co.edu.uptc.vista.*;

public class Main {
    public static void main(String[] args) {
        crudDonante cd = new crudDonante();
        crudAnimal ca = new crudAnimal();
        crudDonacion cdo = new crudDonacion(cd, ca);


        ControladorSistema controlador = new ControladorSistema(cd, ca, cdo);
        VistaSistema vista = new VistaSistema(controlador);
        vista.iniciar();
    }
}

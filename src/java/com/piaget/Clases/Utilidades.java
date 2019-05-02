
package com.piaget.Clases;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Marcelo
 */
public class Utilidades {
     /* Este metodo devuelve el rut en formato integer */
    public static int ValiRutInt(String run) {
        int rutValido = 0;
        try {
            run = run.toUpperCase();
            run = run.replace(".", "");
            String[] run_dv = run.split("-");

            if (run_dv.length == 2) {
                int runAux = Integer.parseInt(run_dv[0]);
                char dv = run_dv[1].charAt(0);

                if (ValidarRut(runAux, dv)) {
                    rutValido = runAux;
                }

            }
        } catch (NumberFormatException EX) {
            /**
             * *************
             */
        }
        return rutValido;
    }

    /* Se encarga de devolver el digito del rut */
    public static char ValDigito(String rut) {
        char digitoValido = 0;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            String[] rut_dv = rut.split("-");

            if (rut_dv.length == 2) {
                char dv = rut_dv[1].charAt(0);
                digitoValido = dv;
            }
        } catch (Exception eX) {
            /* ..... */
        }
        return digitoValido;
    }
    
    /* Devuelve el año de una fecha entregada */
    public static String darformatano(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(date);
    }

    /* Se encarga de devolver el rut en formato String */
    public static String ValiRut(String rut) {
        String rutValido = "";
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            String[] rut_dv = rut.split("-");

            if (rut_dv.length == 2) {
                int rutAux = Integer.parseInt(rut_dv[0]);
                char dv = rut_dv[1].charAt(0);

                if (ValidarRut(rutAux, dv)) {
                    /* Devuelve un int con los numeros del Rut */
                    rutValido = Integer.toString(rutAux);
                }
            }

        } catch (NumberFormatException eX) {
            /* ..... */
        }
        return rutValido;
    }
    
    /* Este metodo se ocupa para devolver el valor itemlabel de un selectOneMenu
    Nota: se debe entregar la lista a recorrer de tipo SelectItem y el objeto value 
    devuelto por el SelectOneMenu */
    public static String labelSelected(List<SelectItem> listaRecorrer, Object value) {
        String lblSelected = "";
        try {
            //Ocupamos un For para recorrer la lista de cursos y obtener el label del combo
            for (int i = 0; i < listaRecorrer.size(); i++) {
                if (listaRecorrer.get(i).getValue() == value) {
                    lblSelected = listaRecorrer.get(i).getLabel();
                }
            }
        } catch (Exception eX) {
            /* Do something */
        }
        return lblSelected;
    }   
    

    public static boolean ValidarRut(int rutx, char dvx) {
        boolean validacion = false;
        try {
            int m = 0, s = 1;
            for (; rutx != 0; rutx /= 10) {
                s = (s + rutx % 10 * (9 - m++ % 6)) % 11;
            }
            /* Verifica que el digito verificador sea igual al char obtenido */
            if (dvx == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }
        } catch (Exception eX) {
            /* do something */
        }
        return validacion;
    }
    
    //Devuelve el año de cualquier fecha
    public static int getAnoFromDate(Date date){
        int result = -1;
        if (date != null){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            result = cal.get(Calendar.YEAR);            
        }
        return result;
    }
 /* Retorna la base de nuestra aplicación */
    public static String baseUrl() {
        return "http://localhost:8086/piagetSAE/";
    }

    /* Retorna el path Login */
    public static String basePathLogin() {
        return "/piagetSAE/";
    }


}

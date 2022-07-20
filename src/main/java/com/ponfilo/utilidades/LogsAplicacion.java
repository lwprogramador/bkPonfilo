package com.ponfilo.utilidades;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class LogsAplicacion {


    public static void imprimirLogInf(String Origen, String Mensaje, String Usuario) {
        FileWriter salida = null;

        Calendar fechaCalendario = java.util.Calendar.getInstance(java.util.Locale.getDefault());
        String fechaArchivo = fechaCalendario.get(Calendar.DATE) + "-" + (fechaCalendario.get(Calendar.MONTH) + 1) + "-" + fechaCalendario.get(Calendar.YEAR);
        Date fechaSitema = fechaCalendario.getTime();
        String fechaLog = fechaSitema.toString();

        try {
            if(!new File(Propiedades.getAppProp("path.loginformativo", "")).exists()){
                new File(Propiedades.getAppProp("path.loginformativo", "")).mkdirs();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        try{
            StringBuilder contLog = new StringBuilder();
            contLog.append(Propiedades.LINEASEPARADOR + fechaLog + " || " + Origen + " || " + Usuario + Propiedades.RUTASEPARADOR + Mensaje + Propiedades.LINEASEPARADOR + Propiedades.LINEASEPARADOR);

            salida = new FileWriter(Propiedades.getAppProp("path.loginformativo", "") + Propiedades.RUTASEPARADOR + Propiedades.getAppProp("app.nombre", "") + " " + fechaArchivo + ".txt", true);
            salida.write(contLog.toString());
            salida.close();
            if (Propiedades.getAppProp("app.ambiente", "DEV").equals("DEV")) {
                System.out.println(contLog);
            }
            contLog.setLength(0);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void imprimirLogError(String Origen, String Mensaje, String Usuario, Exception Error) {
        FileWriter salida = null;

        Calendar fechaCalendario = java.util.Calendar.getInstance(java.util.Locale.getDefault());
        String fechaArchivo = fechaCalendario.get(Calendar.DATE) + "-" + (fechaCalendario.get(Calendar.MONTH) + 1) + "-" + fechaCalendario.get(Calendar.YEAR);
        Date fechaSitema = fechaCalendario.getTime();
        String fechaLog = fechaSitema.toString();

        try {
            if(!new File(Propiedades.getAppProp("path.logerrores", "")).exists()){
                new File(Propiedades.getAppProp("path.logerrores", "")).mkdirs();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        try{
            StringBuilder contLog = new StringBuilder();
            contLog.append(Propiedades.LINEASEPARADOR + fechaLog + " || " + Origen + " || " + Usuario + Propiedades.LINEASEPARADOR
                    + Mensaje + Propiedades.LINEASEPARADOR
                    + Arrays.toString(Error.getStackTrace()).replaceAll(", ", "\n") + Propiedades.LINEASEPARADOR);

            salida = new FileWriter(Propiedades.getAppProp("path.logerrores", "") + Propiedades.RUTASEPARADOR + Propiedades.getAppProp("app.nombre", "") + " " + fechaArchivo + ".txt", true);
            salida.write(contLog.toString());
            salida.close();
            if (Propiedades.getAppProp("app.ambiente", "DEV").equals("DEV")) {
                System.out.println(contLog);
            }
            contLog.setLength(0);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

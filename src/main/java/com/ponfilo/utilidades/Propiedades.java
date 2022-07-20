package com.ponfilo.utilidades;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Propiedades {
    public static char RUTASEPARADOR = File.separatorChar;
    public static String LINEASEPARADOR = System.lineSeparator();
    public static String SPLITSEPARADOR = "|";


    public static String getAppProp(String propiedad, String valDefecto){
        String valRetorno = "";
        try{
            Properties propiedades = new Properties();
            propiedades.load(Propiedades.class.getClassLoader().getResourceAsStream("Propiedades/aplicacion.properties"));
            valRetorno = propiedades.getProperty(propiedad, valDefecto);
        }catch (Exception ex){
            valRetorno = valDefecto;
        }
        return valRetorno;
    }
    public static String getAppProp(String propiedad){
        try{
            Properties propiedades = new Properties();
            propiedades.load(Propiedades.class.getClassLoader().getResourceAsStream("Propiedades/aplicacion.properties"));
            return propiedades.getProperty(propiedad);
        }catch (Exception ex){
            return null;
        }
    }

    public static String getBDProp(String propiedad, String valDefecto){
        String valRetorno = "";
        try{
            Properties propiedades = new Properties();
            propiedades.load(Propiedades.class.getClassLoader().getResourceAsStream("Propiedades/BD.properties"));
            valRetorno = propiedades.getProperty(propiedad, valDefecto);
        }catch (Exception ex){
            valRetorno = valDefecto;
        }
        return valRetorno;
    }
    public static String getBDProp(String propiedad){
        try{
            Properties propiedades = new Properties();
            propiedades.load(Propiedades.class.getClassLoader().getResourceAsStream("Propiedades/BD.properties"));
            return propiedades.getProperty(propiedad);
        }catch (Exception ex){
            return null;
        }
    }

    public static String getMsjProp(String propiedad, String valDefecto){
        String valRetorno = "";
        try{
            Properties propiedades = new Properties();
            propiedades.load(Propiedades.class.getClassLoader().getResourceAsStream("Propiedades/mensajes.properties"));
            valRetorno = propiedades.getProperty(propiedad, valDefecto);
        }catch (Exception ex){
            valRetorno = valDefecto;
        }
        return valRetorno;
    }

    public static String getMsjProp(String propiedad){
        try{
            Properties propiedades = new Properties();
            propiedades.load(Propiedades.class.getClassLoader().getResourceAsStream("Propiedades/mensajes.properties"));
            return propiedades.getProperty(propiedad);
        }catch (Exception ex){
            return null;
        }
    }
}

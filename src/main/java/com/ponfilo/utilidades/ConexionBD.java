package com.ponfilo.utilidades;

import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionBD {
    private Connection conexionSQL;

    private void conectarBD() {
        try {
            Class.forName("org.postgresql.Driver");
            conexionSQL = DriverManager.getConnection("jdbc:postgresql://" + Propiedades.getBDProp("bd.host") + ":" +
                    Propiedades.getBDProp("bd.puerto") + "/" + Propiedades.getBDProp("bd.basedatos"), Propiedades.getBDProp("bd.usuario"),
                    Propiedades.getBDProp("bd.clave"));
            LogsAplicacion.imprimirLogInf(this.getClass() + ".conectarBD()", "COMENZANDO CONEXIÓN A BASE DATOS", "");
        } catch (SQLException ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".conectarBD()", "SQLException conectando a BD", "", ex);
        } catch (ClassNotFoundException ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".conectarBD()", "ClassNotFoundException conectando a BD", "", ex);
        }
    }

    public void cerrarConexionBD(PreparedStatement query) {
        try {
            if (query != null && !query.isClosed()) {
                query.close();
            }
            if (conexionSQL != null && !conexionSQL.isClosed()) {
                conexionSQL.close();
            }
            LogsAplicacion.imprimirLogInf(this.getClass() + ".cerrarConexionBD()", "CERRANDO CONEXIÓN A BASE DATOS", "");
        } catch (SQLException ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".cerrarConexionBD()", "SQLException cerrando conexion a BD", "", ex);
        }
    }

    public boolean generarCommit() {
        boolean commitOK = false;
        try {
            if (conexionSQL != null && conexionSQL.isValid(Integer.parseInt(Propiedades.getBDProp("bd.timeout")))) {
                conexionSQL.commit();
                commitOK = true;
            }
        } catch (Exception ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".generarCommit()", "Exception al generar el commit en la BD", "", ex);
        }
        return commitOK;
    }

    public boolean generarRollBack() {
        boolean rollBackOK = false;
        try {
            if (conexionSQL != null && conexionSQL.isValid(Integer.parseInt(Propiedades.getBDProp("bd.timeout")))) {
                conexionSQL.rollback();
                rollBackOK = true;
            }
        } catch (Exception ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".generarRollBack()", "Exception al generar el RollBack en la BD", "", ex);
        }
        return rollBackOK;
    }

    public PreparedStatement prepararQuery(String sql) throws SQLException {
        PreparedStatement query = null;
        try {
            if (conexionSQL == null || !conexionSQL.isValid(Integer.parseInt(Propiedades.getBDProp("bd.timeout")))) {
                conectarBD();
            }
            if (conexionSQL != null && conexionSQL.isValid(Integer.parseInt(Propiedades.getBDProp("bd.timeout")))) {
                query = conexionSQL.prepareStatement(sql);
                LogsAplicacion.imprimirLogInf(this.getClass() + ".prepararQuery()", "PREPARANDO QUERY SQL PARA LA EJECUCION: " + query.toString(), "");
            }
        } catch (SQLException sqlEx) {
            if (query != null && !query.isClosed()) {
                query.close();
            }
            if (conexionSQL != null && !conexionSQL.isClosed()) {
                conexionSQL.close();
            }
            LogsAplicacion.imprimirLogError(this.getClass() + ".prepararQuery()", "SQLException preparando QUERY a ejecutar", "", sqlEx);
        } catch (NumberFormatException ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".prepararQuery()", "NumberFormatException preparando QUERY a ejecutar", "", ex);
        }
        return query;
    }

    public PreparedStatement prepararQuery(String sql, boolean autocomit) throws SQLException {
        PreparedStatement query = null;
        try {
            if (conexionSQL == null || !conexionSQL.isValid(Integer.parseInt(Propiedades.getBDProp("bd.timeout")))) {
                conectarBD();
            }
            if (conexionSQL != null && conexionSQL.isValid(Integer.parseInt(Propiedades.getBDProp("bd.timeout")))) {
                conexionSQL.setAutoCommit(autocomit);
                query = conexionSQL.prepareStatement(sql);
                LogsAplicacion.imprimirLogInf(this.getClass() + ".prepararQuery()", "PREPARANDO QUERY SQL PARA LA EJECUCION: " + query.toString(), "");
            }
        } catch (SQLException sqlEx) {
            if (query != null && !query.isClosed()) {
                query.close();
            }
            if (conexionSQL != null && !conexionSQL.isClosed()) {
                conexionSQL.close();
            }
            LogsAplicacion.imprimirLogError(this.getClass() + ".prepararQuery()", "SQLException preparando QUERY a ejecutar", "", sqlEx);
        } catch (NumberFormatException ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".prepararQuery()", "NumberFormatException preparando QUERY a ejecutar", "", ex);
        }
        return query;
    }

}

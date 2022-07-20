package com.ponfilo.utilidades;

import com.google.gson.Gson;
import com.ponfilo.modelos.sesion.Usuario;
import com.ponfilo.modelos.sesion.UsuarioConfiguracion;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.DatatypeConverter;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ValidarSesion extends ConexionBD {

    Gson GSON = new Gson();

    public ValidarSesion() {

    }

    public ValidarSesion(HttpServletRequest encabezados) {
        if (encabezados != null) {
            this.tokenSesion = encabezados.getHeader("-Token");
        }
    }

    public String GenerarToken(UsuarioConfiguracion usrConf) {
        String nuevoToken = null;
        Long milSegActuales = System.currentTimeMillis();
        Date fechaHoraActual = new Date(milSegActuales);
        Map<String, Object> CuerpoToken = new HashMap<String, Object>();

        if (usrConf != null) {
            CuerpoToken.put(NOMBRE_CAMPO_CODIGO, usrConf.getDatosUsuario().getUsrCodigo());
            CuerpoToken.put(NOMBRE_CAMPO_USUARIO, usrConf.getDatosUsuario().getUsrUsuario());
            CuerpoToken.put(NOMBRE_CAMPO_DOCUMENTO, usrConf.getDatosUsuario().getUsrDocumento());
        } else {
            return null;
        }

        SignatureAlgorithm AlgoritmoEncriptacion = SignatureAlgorithm.HS256;
        byte[] SecretBytes = null;
        Key LlaveInicio = null;
        try {
            SecretBytes = DatatypeConverter.parseBase64Binary(Propiedades.getAppProp("token.llaveencriptacion"));
            LlaveInicio = new SecretKeySpec(SecretBytes, AlgoritmoEncriptacion.getJcaName());
        } catch (Exception ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".GenerarToken()", "[SecretBytes, llaveInicio] - " + ex.getMessage(), "*", ex);
        }

        try {
            JwtBuilder builder = Jwts.builder()
                    .setId(usrConf.getDatosUsuario().getUsrCodigo())
                    .setIssuer(Propiedades.getAppProp("token.emisor"))
                    .setSubject(Propiedades.getAppProp("token.asunto"))
                    .setClaims(CuerpoToken)
                    .setIssuedAt(fechaHoraActual)
                    .setNotBefore(fechaHoraActual)
                    .signWith(
                            AlgoritmoEncriptacion,
                            LlaveInicio
                    );
            Long tiempoExpiracion = Long.parseLong(Propiedades.getAppProp("token.tiempovencimiento", "0"));
            if (tiempoExpiracion > 0) {
                tiempoExpiracion = (milSegActuales + tiempoExpiracion);
                builder.setExpiration(new Date(tiempoExpiracion));
            }
            nuevoToken = builder.compact();
        } catch (Exception ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".GenerarToken()", "Jwts.builder() - " + ex.getMessage(), "*", ex);
        }

        try {
            nuevoToken = Base64.getEncoder().encodeToString(nuevoToken.getBytes());
        } catch (Exception ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".GenerarToken()", "[Base64.getEncoder] - " + ex.getMessage(), "*", ex);
        }
        return nuevoToken;
    }

    public boolean ValidarToken() {
        boolean TokenValido = false;
        String TokenValidar = null;
        Map<String, Object> CuerpoToken = null;
        try {
            if (this.tokenSesion == null || this.tokenSesion.trim().isBlank()) {
                return TokenValido;
            }
            byte[] decodificarBytes = Base64.getDecoder().decode(this.tokenSesion);
            TokenValidar = new String(decodificarBytes);
        } catch (Exception ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".ValidarToken()", "[Base64.getEncoder] - " + ex.getMessage(), "*", ex);
        }
        try {
            CuerpoToken = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(Propiedades.getAppProp("token.llaveencriptacion")))
                    .parseClaimsJws(TokenValidar).getBody();
            TokenValido = true;
        } catch (ExpiredJwtException ex) {
            CuerpoToken = ex.getClaims();
            LogsAplicacion.imprimirLogError(this.getClass() + ".ValidarToken()", "[ExpiredJwtException] - " + ex.getMessage(), String.valueOf(CuerpoToken.get(NOMBRE_CAMPO_USUARIO)), ex);
        } catch (Exception ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".ValidarToken()", "[Exception] - " + ex.getMessage(), "*", ex);
        }
        if (CuerpoToken != null && TokenValido == true) {
            this.usuarioSesion = String.valueOf(CuerpoToken.get(NOMBRE_CAMPO_USUARIO));
            this.codigoSesion = String.valueOf(CuerpoToken.get(NOMBRE_CAMPO_CODIGO));
            this.documentoSesion = String.valueOf(CuerpoToken.get(NOMBRE_CAMPO_DOCUMENTO));
        }

        return TokenValido;
    }

    public String getTokenSesion() {
        return tokenSesion;
    }

    public void setTokenSesion(String tokenSesion) {
        this.tokenSesion = tokenSesion;
    }

    public String getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(String usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public Date getFechaSesion() {
        return fechaSesion;
    }

    public void setFechaSesion(Date fechaSesion) {
        this.fechaSesion = fechaSesion;
    }

    private String tokenSesion;
    private String usuarioSesion;

    private String codigoSesion;
    private String documentoSesion;
    private Date fechaSesion;

    private String NOMBRE_CAMPO_USUARIO = "usuarioToken";
    private String NOMBRE_CAMPO_CODIGO = "codigoToken";
    private String NOMBRE_CAMPO_DOCUMENTO = "documentoToken";
}

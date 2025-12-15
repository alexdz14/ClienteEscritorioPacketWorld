package clientepacketworld.conexion;

import clientepacketworld.pojo.RespuestaHTTP;
import clientepacketworld.utilidad.Constantes;
import clientepacketworld.utilidad.Utilidades;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConexionAPI {

    public static RespuestaHTTP peticionGET(String url) {
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlWS = new URL(url);
            HttpURLConnection conexion = (HttpURLConnection) urlWS.openConnection();
            conexion.setRequestMethod("GET");
            int codigo = conexion.getResponseCode();
            if (codigo == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(Utilidades.streamToString(conexion.getInputStream()));
            } else {
                if (conexion.getErrorStream() != null) {
                    respuesta.setContenido(Utilidades.streamToString(conexion.getErrorStream()));
                }
            }
            respuesta.setCodigo(codigo);
        } catch (MalformedURLException e) {
            respuesta.setCodigo(Constantes.ERROR_MALFORMED_URL);
            respuesta.setContenido(e.getMessage());
        } catch (IOException ex) {
            respuesta.setCodigo(Constantes.ERROR_PETICION);
            respuesta.setContenido(ex.getMessage());
        }
        return respuesta;
    }

    public static RespuestaHTTP peticionPOST(String url, String parametros, String contentType) {
        return peticionConBody(url, "POST", parametros, contentType);
    }

    public static RespuestaHTTP peticionPUT(String url, String parametros, String contentType) {
        return peticionConBody(url, "PUT", parametros, contentType);
    }

    public static RespuestaHTTP peticionDELETE(String url, String parametros, String contentType) {
        return peticionConBody(url, "DELETE", parametros, contentType);
    }

    private static RespuestaHTTP peticionConBody(String url, String metodo, String parametros, String contentType) {
        RespuestaHTTP respuesta = new RespuestaHTTP();
        try {
            URL urlWS = new URL(url);
            HttpURLConnection conexion = (HttpURLConnection) urlWS.openConnection();
            conexion.setRequestMethod(metodo);
            conexion.setRequestProperty("Content-Type", contentType);
            conexion.setDoOutput(true);
            OutputStream os = conexion.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            os.close();
            int codigo = conexion.getResponseCode();
            if (codigo == HttpURLConnection.HTTP_OK) {
                respuesta.setContenido(Utilidades.streamToString(conexion.getInputStream()));
            } else {
                if (conexion.getErrorStream() != null) {
                    respuesta.setContenido(Utilidades.streamToString(conexion.getErrorStream()));
                }
            }
            respuesta.setCodigo(codigo);
        } catch (Exception ex) {
            respuesta.setCodigo(Constantes.ERROR_PETICION);
            respuesta.setContenido(ex.getMessage());
        }
        return respuesta;
    }
}

package clientepacketworld.dominio;

import clientepacketworld.conexion.ConexionAPI;
import clientepacketworld.dto.Respuesta; // <--- Usamos Respuesta genérica, no RespuestaLogin
import clientepacketworld.pojo.RespuestaHTTP;
import clientepacketworld.utilidad.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

public class InicioSesionImp {

    public static Respuesta verificarSesion(String noPersonal, String password) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "colaborador/login";
        String parametros = "noPersonal=" + noPersonal + "&contrasenia=" + password;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionPOST(url, parametros, Constantes.CONTENT_FORM);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            try {
                Gson gson = new Gson();
                respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
            } catch (Exception e) {
                respuesta.setError(true);
                respuesta.setMensaje("Error al deserializar respuesta");
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error de conexión");
        }
        return respuesta;
    }
}

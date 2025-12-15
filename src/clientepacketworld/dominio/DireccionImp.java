package clientepacketworld.dominio;

import clientepacketworld.conexion.ConexionAPI;
import clientepacketworld.dto.RSDatosCodigoPostal;
import clientepacketworld.pojo.RespuestaHTTP;
import clientepacketworld.utilidad.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

public class DireccionImp {

    public static RSDatosCodigoPostal buscarPorCP(String cp) {
        RSDatosCodigoPostal respuestaCP = null;
        String url = Constantes.URL_WS + "direccion/datos/" + cp;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            try {
                respuestaCP = gson.fromJson(respuesta.getContenido(), RSDatosCodigoPostal.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return respuestaCP;
    }
}

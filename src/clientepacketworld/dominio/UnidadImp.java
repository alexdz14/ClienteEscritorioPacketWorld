package clientepacketworld.dominio;

import clientepacketworld.conexion.ConexionAPI;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.RespuestaHTTP;
import clientepacketworld.pojo.Unidad;
import clientepacketworld.utilidad.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

public class UnidadImp {

    public static List<Unidad> obtenerUnidades() {
        List<Unidad> lista = null;
        String url = Constantes.URL_WS + "unidad/getAll";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Unidad>>() {
            }.getType();
            lista = gson.fromJson(respuesta.getContenido(), tipoLista);
        }
        return lista;
    }

    public static Respuesta registrarUnidad(Unidad unidad) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "unidad/registrar";
        Gson gson = new Gson();
        String parametros = gson.toJson(unidad);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionPOST(url, parametros, Constantes.CONTENT_JSON);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al registrar unidad");
        }
        return respuesta;
    }

    public static Respuesta editarUnidad(Unidad unidad) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "unidad/editar";
        Gson gson = new Gson();
        String parametros = gson.toJson(unidad);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionPUT(url, parametros, Constantes.CONTENT_JSON);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al editar unidad");
        }
        return respuesta;
    }

    public static Respuesta darBajaUnidad(Integer idUnidad, String motivo) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "unidad/darBaja";

        Unidad unidadBaja = new Unidad();
        unidadBaja.setIdUnidad(idUnidad);
        unidadBaja.setMotivo(motivo);

        Gson gson = new Gson();
        String parametros = gson.toJson(unidadBaja);
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionPUT(url, parametros, Constantes.CONTENT_JSON);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al dar de baja la unidad");
        }
        return respuesta;
    }
}

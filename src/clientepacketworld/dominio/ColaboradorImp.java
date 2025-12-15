package clientepacketworld.dominio;

import clientepacketworld.conexion.ConexionAPI;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.Colaborador;
import clientepacketworld.pojo.RespuestaHTTP;
import clientepacketworld.utilidad.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

public class ColaboradorImp {

    public static Colaborador buscarColaborador(String noPersonal) {
        Colaborador colaborador = null;
        String url = Constantes.URL_WS + "colaborador/buscar/" + noPersonal;

        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Colaborador>>() {
            }.getType();
            List<Colaborador> lista = gson.fromJson(respuesta.getContenido(), tipoLista);

            if (lista != null && !lista.isEmpty()) {
                colaborador = lista.get(0);
            }
        }
        return colaborador;
    }

    public static List<Colaborador> obtenerColaboradores(String filtro) {
        List<Colaborador> lista = null;
        String url = Constantes.URL_WS + "colaborador/buscar/" + filtro;
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Colaborador>>() {
            }.getType();
            lista = gson.fromJson(respuesta.getContenido(), tipoLista);
        }
        return lista;
    }

    public static Respuesta eliminarColaborador(String noPersonal) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "colaborador/eliminar/" + noPersonal;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionDELETE(url, "", Constantes.CONTENT_JSON); // Parámetros vacíos

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al eliminar el colaborador.");
        }
        return respuesta;
    }

    public static Respuesta registrarColaborador(Colaborador colaborador) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "colaborador/registrar";

        Gson gson = new Gson();
        String parametros = gson.toJson(colaborador);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionPOST(url, parametros, Constantes.CONTENT_JSON);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al registrar el colaborador.");
        }
        return respuesta;
    }

    public static Respuesta editarColaborador(Colaborador colaborador) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "colaborador/editar";

        Gson gson = new Gson();
        String parametros = gson.toJson(colaborador);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionPUT(url, parametros, Constantes.CONTENT_JSON);

        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al editar el colaborador.");
        }
        return respuesta;
    }
}

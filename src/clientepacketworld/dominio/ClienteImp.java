package clientepacketworld.dominio;

import clientepacketworld.conexion.ConexionAPI;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.Cliente;
import clientepacketworld.pojo.RespuestaHTTP;
import clientepacketworld.utilidad.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

public class ClienteImp {

    public static List<Cliente> buscarClientes(String filtro) {
        List<Cliente> lista = null;
        String url = Constantes.URL_WS + "cliente/buscar/" + filtro;
        if (filtro.isEmpty()) {
            url = Constantes.URL_WS + "cliente/getAll";
        }

        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);

        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Cliente>>() {
            }.getType();
            lista = gson.fromJson(respuesta.getContenido(), tipoLista);
        }
        return lista;
    }

    public static Respuesta registrarCliente(Cliente cliente) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "cliente/registrar";
        Gson gson = new Gson();
        String parametros = gson.toJson(cliente);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionPOST(url, parametros, Constantes.CONTENT_JSON);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al registrar cliente");
        }
        return respuesta;
    }

    public static Respuesta editarCliente(Cliente cliente) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "cliente/editar";
        Gson gson = new Gson();
        String parametros = gson.toJson(cliente);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionPUT(url, parametros, Constantes.CONTENT_JSON);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al editar cliente");
        }
        return respuesta;
    }

    public static Respuesta eliminarCliente(Integer idCliente) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "cliente/eliminar/" + idCliente;

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionDELETE(url, "", Constantes.CONTENT_JSON);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al eliminar cliente");
        }
        return respuesta;
    }
}

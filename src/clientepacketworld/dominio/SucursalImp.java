package clientepacketworld.dominio;

import clientepacketworld.conexion.ConexionAPI;
import clientepacketworld.dto.Respuesta;
import clientepacketworld.pojo.RespuestaHTTP;
import clientepacketworld.pojo.Sucursal;
import clientepacketworld.utilidad.Constantes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.List;

public class SucursalImp {

    public static List<Sucursal> obtenerSucursales() {
        List<Sucursal> lista = null;
        String url = Constantes.URL_WS + "sucursal/getAll";
        RespuestaHTTP respuesta = ConexionAPI.peticionGET(url);
        if (respuesta.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Sucursal>>() {
            }.getType();
            lista = gson.fromJson(respuesta.getContenido(), tipoLista);
        }
        return lista;
    }

    public static Respuesta registrarSucursal(Sucursal sucursal) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "sucursal/registrar";
        Gson gson = new Gson();
        String parametros = gson.toJson(sucursal);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionPOST(url, parametros, Constantes.CONTENT_JSON);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al registrar sucursal");
        }
        return respuesta;
    }

    public static Respuesta editarSucursal(Sucursal sucursal) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "sucursal/editar";
        Gson gson = new Gson();
        String parametros = gson.toJson(sucursal);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionPUT(url, parametros, Constantes.CONTENT_JSON);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al editar sucursal");
        }
        return respuesta;
    }

    public static Respuesta eliminarSucursal(Integer idSucursal) {
        Respuesta respuesta = new Respuesta();
        String url = Constantes.URL_WS + "sucursal/eliminar/" + idSucursal;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionDELETE(url, "", Constantes.CONTENT_JSON);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error al eliminar sucursal");
        }
        return respuesta;
    }
}

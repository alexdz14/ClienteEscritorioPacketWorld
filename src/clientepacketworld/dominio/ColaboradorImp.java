package clientepacketworld.dominio;

import clientepacketworld.conexion.ConexionAPI;
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
}

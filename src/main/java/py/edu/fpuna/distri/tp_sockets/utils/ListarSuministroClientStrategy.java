package py.edu.fpuna.distri.tp_sockets.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import py.edu.fpuna.distri.tp_sockets.data.mappers.ListarSuministroDto;
import py.edu.fpuna.distri.tp_sockets.data.mappers.ListarSuministroResponse;
import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;

public class ListarSuministroClientStrategy implements TipoOperacionClientStrategy {
    UIConsole uiConsole = new UIConsole();
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    public String getDto(int tipoOperacion) throws IOException {
        ListarSuministroDto listarSuministroDto = new ListarSuministroDto(tipoOperacion);

        return listarSuministroDto.toJson();
    }

    @Override
    public void getRespuesta(String respuesta) throws IOException {
        ListarSuministroResponse response = ListarSuministroResponse.fromJson(respuesta);
        if (response.getData().size() == 0) {
            System.out.println("No se encontraron suministros");
        } else {
            System.out.println("Listado de suministros: ");
            for (Suministro suministro : response.getData()) {
                System.out.println(suministro);
            }
        }

    }
}

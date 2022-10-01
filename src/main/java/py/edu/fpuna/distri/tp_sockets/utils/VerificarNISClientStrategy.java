package py.edu.fpuna.distri.tp_sockets.utils;
import py.edu.fpuna.distri.tp_sockets.data.mappers.VerificarNISDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VerificarNISClientStrategy implements TipoOperacionClientStrategy{
    UIConsole uiConsole = new UIConsole();
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    public String getDto(int tipoOperacion) throws IOException {
        uiConsole.insertNis();
        String nis = inFromUser.readLine();


        VerificarNISDto verificarNisDto = new VerificarNISDto(tipoOperacion, nis);

        return verificarNisDto.toJson();
    }

    @Override
    public void getRespuesta(String respuesta) throws IOException {
        KResponse response= KResponse.fromJson(respuesta);
        System.out.println(response.mensaje);

    }
}


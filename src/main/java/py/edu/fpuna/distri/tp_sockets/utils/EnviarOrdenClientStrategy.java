package py.edu.fpuna.distri.tp_sockets.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import py.edu.fpuna.distri.tp_sockets.data.mappers.EnviarOrdenDto;

public class EnviarOrdenClientStrategy implements TipoOperacionClientStrategy {
    UIConsole uiConsole = new UIConsole();
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    public String getDto(int tipoOperacion) throws IOException {
        uiConsole.insertNis();
        String nis = inFromUser.readLine();

        EnviarOrdenDto enviarOrdenDto = new EnviarOrdenDto(tipoOperacion, nis);

        return enviarOrdenDto.toJson();
    }
}

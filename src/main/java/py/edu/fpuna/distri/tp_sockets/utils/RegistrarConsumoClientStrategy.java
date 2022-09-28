package py.edu.fpuna.distri.tp_sockets.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoDto;
import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoResponse;

public class RegistrarConsumoClientStrategy implements TipoOperacionClientStrategy {
    UIConsole uiConsole = new UIConsole();
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

    public String getDto(int tipoOperacion) throws IOException {
        uiConsole.insertNis();
        String nis = inFromUser.readLine();

        uiConsole.insertConsumo();
        String consumo = inFromUser.readLine();

        double parsedConsumo = Double.parseDouble(consumo);
        RegistrarConsumoDto registrarConsumoDto = new RegistrarConsumoDto(tipoOperacion, nis, parsedConsumo);

        return registrarConsumoDto.toJson();
    }

    @Override
    public void getRespuesta(String respuesta) throws IOException {
        RegistrarConsumoResponse response = RegistrarConsumoResponse.fromJson(respuesta);
        if (response.getData() == null) {
            System.out.println("No se pudo registrar el consumo, no existe el suministro");
        } else {
            System.out.println("Consumo registrado con exito");
        }

    }
}

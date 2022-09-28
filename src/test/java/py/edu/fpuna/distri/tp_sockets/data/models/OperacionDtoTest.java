package py.edu.fpuna.distri.tp_sockets.data.models;

import org.junit.Test;

import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoDto;

public class OperacionDtoTest {
    @Test
    public void convertirOperacionDtoAJson() {
        String jsonExpected = "{\"idOperacion\":1,\"nis\":\"123456789\"}";
        RegistrarConsumoDto registrarConsumoDto = new RegistrarConsumoDto(1, "123456789");
        String json = registrarConsumoDto.toJson();

        assert json.equals(jsonExpected);
    }

    @Test
    public void convertirJsonAOperacionDto() {
        String json = "{\"idOperacion\":1,\"nis\":\"123456789\"}";
        RegistrarConsumoDto registrarConsumoDto = RegistrarConsumoDto.fromJson(json);

        assert registrarConsumoDto.getIdOperacion() == 1;
        assert registrarConsumoDto.getNis().equals("123456789");
    }
}

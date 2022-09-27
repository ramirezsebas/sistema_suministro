package py.edu.fpuna.distri.tp_sockets.data.models;

import org.junit.Test;

public class OperacionDtoTest {
    @Test
    public void convertirOperacionDtoAJson() {
        String jsonExpected = "{\"idOperacion\":1,\"nis\":\"123456789\"}";
        OperacionDto operacionDto = new OperacionDto(1, "123456789");
        String json = operacionDto.toJson();

        assert json.equals(jsonExpected);
    }

    @Test
    public void convertirJsonAOperacionDto() {
        String json = "{\"idOperacion\":1,\"nis\":\"123456789\"}";
        OperacionDto operacionDto = OperacionDto.fromJson(json);

        assert operacionDto.getIdOperacion() == 1;
        assert operacionDto.getNis().equals("123456789");
    }
}

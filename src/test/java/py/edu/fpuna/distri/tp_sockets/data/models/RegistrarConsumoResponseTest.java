package py.edu.fpuna.distri.tp_sockets.data.models;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoResponse;

public class RegistrarConsumoResponseTest {

    @Test
    public void deberiaRetornarUnSuministroDeUnJson() {
        String userJson = "{\"mensaje\":\"ok\",\"estado\":1,\"tipoOperacion\":1,\"data\":{\"nis\":\"123456\",\"consumo\":12345.0,\"deuda\":1232.0}}";
        RegistrarConsumoResponse suministro = RegistrarConsumoResponse.fromJson(userJson);

        System.out.println(suministro.toString());

        assertTrue(suministro.getTipoOperacion() == 1);
        assertTrue(suministro.getEstado() == 1);
        assertTrue(suministro.getMensaje().equals("ok"));
    }

    // @Test
    // public void deberiaRetornarUnJsonDeUnSuministro() {
        
    //     String jsonResult = "{\"mensaje\":\"ok\",\"estado\":1,\"tipoOperacion\":1,\"data\":{\"nis\":\"123456\",\"consumo\":12345.0,\"deuda\":1232.0}}";
    //     RegistrarConsumoResponse suministroResponse = new RegistrarConsumoResponse("123456", 12345, 1232);
    //     SuministroModel suministro = new SuministroModel("ok", 1, 1, suministroResponse);


    //     String json = suministro.toJson();

    //     System.out.println(json);

    //     assertEquals(jsonResult, json);
    // }

}

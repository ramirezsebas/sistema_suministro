package py.edu.fpuna.distri.tp_sockets.data.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SuministroModelTest {

    @Test
    public void deberiaRetornarUnSuministroDeUnJson() {
        String userJson = "{\"mensaje\":\"ok\",\"estado\":1,\"idOperacion\":1,\"data\":{\"nis\":\"123456\",\"consumo\":12345.0,\"deuda\":1232.0}}";
        SuministroModel suministro = SuministroModel.fromJson(userJson);

        System.out.println(suministro.toString());

        assertTrue(suministro.getidOperacion() == 1);
        assertTrue(suministro.getEstado() == 1);
        assertTrue(suministro.getMensaje().equals("ok"));
    }

    @Test
    public void deberiaRetornarUnJsonDeUnSuministro() {
        
        String jsonResult = "{\"mensaje\":\"ok\",\"estado\":1,\"idOperacion\":1,\"data\":{\"nis\":\"123456\",\"consumo\":12345.0,\"deuda\":1232.0}}";
        SuministroResponseBuilder suministroResponse = new SuministroResponseBuilder("123456", 12345, 1232);
        SuministroModel suministro = new SuministroModel("ok", 1, 1, suministroResponse);


        String json = suministro.toJson();

        System.out.println(json);

        assertEquals(jsonResult, json);
    }

}

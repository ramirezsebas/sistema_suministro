package py.edu.fpuna.distri.tp_sockets.data.models;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SuministroModelTest {

    @Test
    public void deberiaRetornarUnSuministroDeUnJson() {
        String userJson = "{'idOperacion':1,'estado':1,'mensaje':'ok'}";
        SuministroModel suministro = SuministroModel.fromJson(userJson);

        System.out.println(suministro.toString());

        assertTrue(suministro.getidOperacion() == 1);
        assertTrue(suministro.getEstado() == 1);
        assertTrue(suministro.getMensaje().equals("ok"));
    }

    @Test
    public void deberiaRetornarUnJsonDeUnSuministro() {
        String jsonResult = "{\"mensaje\":\"ok\",\"estado\":1,\"idOperacion\":1}";
        SuministroModel suministro = new SuministroModel("ok", 1, 1);

        String json = suministro.toJson();

        System.out.println(json);

        assertTrue(json.equals(jsonResult));
    }

}

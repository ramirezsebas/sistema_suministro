package py.edu.fpuna.distri.tp_sockets.data.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OperacionDto {
    int idOperacion;
    String nis;

    public OperacionDto(int idOperacion, String nis) {
        this.idOperacion = idOperacion;
        this.nis = nis;
    }

    public static OperacionDto fromJson(String json) {
        Gson gson = new GsonBuilder()
        .setLenient()
        .create();
        return gson.fromJson(json, OperacionDto.class);
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
        .setLenient()
        .create();
        return gson.toJson(this);
    }

    public int getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

}

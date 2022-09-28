package py.edu.fpuna.distri.tp_sockets.data.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class SuministroResponse {
    private String nis;
    private double consumo;
    private double deuda;

    public SuministroResponse(String nis, double consumo, double deuda) {
        this.nis = nis;
        this.consumo = consumo;
        this.deuda = deuda;
    }

    public static SuministroResponse fromJson(String json) {
        return JsonUtil.fromJson(json, SuministroResponse.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    @Override
    public String toString() {
        return "SuministroResponse [nis=" + nis + ", consumo=" + consumo + ", deuda=" + deuda + "]";
    }
}

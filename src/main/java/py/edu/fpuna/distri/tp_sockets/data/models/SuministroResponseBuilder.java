package py.edu.fpuna.distri.tp_sockets.data.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class SuministroResponseBuilder {
    private String nis;
    private double consumo;
    private double deuda;

    public SuministroResponseBuilder() {
    }

    public SuministroResponseBuilder(String nis, double consumo, double deuda) {
        this.nis = nis;
        this.consumo = consumo;
        this.deuda = deuda;
    }

    public SuministroResponseBuilder withNis(String nis) {
        this.nis = nis;
        return this;
    }

    public SuministroResponseBuilder withConsumo(double consumo) {
        this.consumo = consumo;
        return this;
    }

    public SuministroResponseBuilder withDeuda(double deuda) {
        this.deuda = deuda;
        return this;
    }

    public static SuministroResponse fromJson(String json) {
        return JsonUtil.fromJson(json, SuministroResponse.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

    @Override
    public String toString() {
        return "SuministroResponseBuilder [nis=" + nis + ", consumo=" + consumo + ", deuda=" + deuda + "]";
    }
}

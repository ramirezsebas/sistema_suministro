package py.edu.fpuna.distri.tp_sockets.data.mappers;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class RegistrarConsumoDataResponse {
    private String nis;
    private double consumo;

    public RegistrarConsumoDataResponse(String nis, double consumo) {
        this.nis = nis;
        this.consumo = consumo;
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

    public static RegistrarConsumoDataResponse fromJson(String json) {
        return JsonUtil.fromJson(json, RegistrarConsumoDataResponse.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

}
package py.edu.fpuna.distri.tp_sockets.data.mappers;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class EnviarOrdenDataResponse {
    private String nis;
    private double deuda;

    public EnviarOrdenDataResponse(String nis, double deuda) {
        this.nis = nis;
        this.deuda = deuda;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    public static EnviarOrdenDataResponse fromJson(String json) {
        return JsonUtil.fromJson(json, EnviarOrdenDataResponse.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }
}

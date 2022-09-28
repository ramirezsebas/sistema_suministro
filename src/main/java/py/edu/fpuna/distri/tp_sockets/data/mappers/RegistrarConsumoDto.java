package py.edu.fpuna.distri.tp_sockets.data.mappers;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class RegistrarConsumoDto {
    int idOperacion;
    String nis;
    double consumo;

    public RegistrarConsumoDto(int idOperacion, String nis, double consumo) {
        this.idOperacion = idOperacion;
        this.nis = nis;
        this.consumo = consumo;
    }

    public static RegistrarConsumoDto fromJson(String json) {
        return JsonUtil.fromJson(json, RegistrarConsumoDto.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
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

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

}

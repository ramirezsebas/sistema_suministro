package py.edu.fpuna.distri.tp_sockets.data.mappers;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class RegistrarConsumoDto {
    int tipoOperacion;
    String nis;
    double consumo;

    public RegistrarConsumoDto(int tipoOperacion, String nis, double consumo) {
        this.tipoOperacion = tipoOperacion;
        this.nis = nis;
        this.consumo = consumo;
    }

    public static RegistrarConsumoDto fromJson(String json) {
        return JsonUtil.fromJson(json, RegistrarConsumoDto.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

    public int getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(int tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
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

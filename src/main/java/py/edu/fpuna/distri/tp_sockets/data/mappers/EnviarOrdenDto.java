package py.edu.fpuna.distri.tp_sockets.data.mappers;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class EnviarOrdenDto {
    int tipoOperacion;
    String nis;

    public EnviarOrdenDto(int tipoOperacion, String nis) {
        this.tipoOperacion = tipoOperacion;
        this.nis = nis;
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

    public static EnviarOrdenDto fromJson(String json) {
        return JsonUtil.fromJson(json, EnviarOrdenDto.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

}

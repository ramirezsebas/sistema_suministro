package py.edu.fpuna.distri.tp_sockets.data.mappers;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class VerificarNISDto {
    private String nis;
    private  int tipoOperacion;

    public int getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(int tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }



    public VerificarNISDto(int tipoOperacion, String nis) {
        this.tipoOperacion = tipoOperacion;
        this.nis = nis;
    }
    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public static VerificarNISDto fromJson(String json) {
        return JsonUtil.fromJson(json, VerificarNISDto.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }


}

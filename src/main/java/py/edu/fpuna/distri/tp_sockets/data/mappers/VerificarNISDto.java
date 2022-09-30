package py.edu.fpuna.distri.tp_sockets.data.mappers;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class VerificarNISDto {
    private String nis;
    private  int idOperacion;

    public int getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }



    public VerificarNISDto(int idOperacion, String nis) {
        this.idOperacion = idOperacion;
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

package py.edu.fpuna.distri.tp_sockets.data.mappers;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class ListarSuministroDto {
    private int idOperacion;

    public ListarSuministroDto(int idOperacion) {
        this.idOperacion = idOperacion;
    }

    public int getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }

    public static ListarSuministroDto fromJson(String json) {
        return JsonUtil.fromJson(json, ListarSuministroDto.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

}

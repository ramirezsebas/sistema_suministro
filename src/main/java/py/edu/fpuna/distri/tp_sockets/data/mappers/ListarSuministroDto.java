package py.edu.fpuna.distri.tp_sockets.data.mappers;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class ListarSuministroDto {
    private int tipoOperacion;

    public ListarSuministroDto(int tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public int getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(int tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public static ListarSuministroDto fromJson(String json) {
        return JsonUtil.fromJson(json, ListarSuministroDto.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

}

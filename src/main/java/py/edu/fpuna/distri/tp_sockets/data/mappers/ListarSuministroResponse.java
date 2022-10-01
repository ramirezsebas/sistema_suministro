package py.edu.fpuna.distri.tp_sockets.data.mappers;

import java.util.ArrayList;
import java.util.List;

import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;
import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class ListarSuministroResponse {
    private String mensaje;
    private int estado;
    private int tipoOperacion;
    private List<Suministro> data;

    public ListarSuministroResponse(String mensaje, int estado, int tipoOperacion) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.tipoOperacion = tipoOperacion;
        this.data = new ArrayList<Suministro>();
    }

    public ListarSuministroResponse(String mensaje, int estado, int tipoOperacion, List<Suministro> data) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.tipoOperacion = tipoOperacion;
        this.data = data;
    }

    public List<Suministro> getData() {
        return data;
    }

    public void setData(List<Suministro> data) {
        this.data = data;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(int tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public static ListarSuministroResponse fromJson(String json) {
        return JsonUtil.fromJson(json, ListarSuministroResponse.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

}

package py.edu.fpuna.distri.tp_sockets.data.mappers;

import java.util.ArrayList;
import java.util.List;

import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;
import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class ListarSuministroResponse {
    private String mensaje;
    private int estado;
    private int idOperacion;
    private List<Suministro> data;

    public ListarSuministroResponse(String mensaje, int estado, int idOperacion) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.idOperacion = idOperacion;
        this.data = new ArrayList<Suministro>();
    }

    public ListarSuministroResponse(String mensaje, int estado, int idOperacion, List<Suministro> data) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.idOperacion = idOperacion;
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

    public int getidOperacion() {
        return idOperacion;
    }

    public void setidOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }

    public static ListarSuministroResponse fromJson(String json) {
        return JsonUtil.fromJson(json, ListarSuministroResponse.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

}

package py.edu.fpuna.distri.tp_sockets.data.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class SuministroModel {
    private String mensaje;
    private int estado;
    private int idOperacion;
    private SuministroResponseBuilder data;

    public SuministroModel(String mensaje, int estado, int idOperacion) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.idOperacion = idOperacion;
    }

    public SuministroModel(String mensaje, int estado, int idOperacion, SuministroResponseBuilder data) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.idOperacion = idOperacion;
        this.data = data;
    }

    public static SuministroModel fromJson(String json) {
        return JsonUtil.fromJson(json, SuministroModel.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
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

    public SuministroResponseBuilder getData() {
        return data;
    }

    public void setData(SuministroResponseBuilder data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SuministroModel [mensaje=" + mensaje + ", estado=" + estado + ", idOperacion=" + idOperacion + ", data="
                + data.toJson() + "]";
    }

}

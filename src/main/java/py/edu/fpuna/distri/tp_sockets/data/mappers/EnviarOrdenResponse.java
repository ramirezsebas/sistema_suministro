package py.edu.fpuna.distri.tp_sockets.data.mappers;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class EnviarOrdenResponse {
    private String mensaje;
    private int estado;
    private int idOperacion;
    private EnviarOrdenDataResponse data;

    public EnviarOrdenResponse(String mensaje, int estado, int idOperacion) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.idOperacion = idOperacion;
    }

    public EnviarOrdenResponse(String mensaje, int estado, int idOperacion, EnviarOrdenDataResponse data) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.idOperacion = idOperacion;
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

    public EnviarOrdenDataResponse getData() {
        return data;
    }

    public void setData(EnviarOrdenDataResponse data) {
        this.data = data;
    }

    public static EnviarOrdenResponse fromJson(String json) {
        return JsonUtil.fromJson(json, EnviarOrdenResponse.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

}

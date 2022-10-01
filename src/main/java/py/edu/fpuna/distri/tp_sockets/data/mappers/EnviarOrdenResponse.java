package py.edu.fpuna.distri.tp_sockets.data.mappers;

import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class EnviarOrdenResponse {
    private String mensaje;
    private int estado;
    private int tipoOperacion;
    private EnviarOrdenDataResponse data;

    public EnviarOrdenResponse(String mensaje, int estado, int tipoOperacion) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.tipoOperacion = tipoOperacion;
    }

    public EnviarOrdenResponse(String mensaje, int estado, int tipoOperacion, EnviarOrdenDataResponse data) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.tipoOperacion = tipoOperacion;
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

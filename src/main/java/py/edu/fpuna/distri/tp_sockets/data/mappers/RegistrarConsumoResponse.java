package py.edu.fpuna.distri.tp_sockets.data.mappers;


import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class RegistrarConsumoResponse {
    private String mensaje;
    private int estado;
    private int tipoOperacion;
    private RegistrarConsumoDataResponse data;

    public RegistrarConsumoResponse(String mensaje, int estado, int tipoOperacion) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.tipoOperacion = tipoOperacion;
    }

    public RegistrarConsumoResponse(String mensaje, int estado, int tipoOperacion, RegistrarConsumoDataResponse data) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.tipoOperacion = tipoOperacion;
        this.data = data;
    }

    public RegistrarConsumoDataResponse getData() {
        return data;
    }

    public void setData(RegistrarConsumoDataResponse data) {
        this.data = data;
    }

    public static RegistrarConsumoResponse fromJson(String json) {
        return JsonUtil.fromJson(json, RegistrarConsumoResponse.class);
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

    public int getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(int tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

}

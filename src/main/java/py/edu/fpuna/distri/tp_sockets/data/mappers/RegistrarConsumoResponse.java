package py.edu.fpuna.distri.tp_sockets.data.mappers;


import py.edu.fpuna.distri.tp_sockets.utils.JsonUtil;

public class RegistrarConsumoResponse {
    private String mensaje;
    private int estado;
    private int idOperacion;
    private RegistrarConsumoDataResponse data;

    public RegistrarConsumoResponse(String mensaje, int estado, int idOperacion) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.idOperacion = idOperacion;
    }

    public RegistrarConsumoResponse(String mensaje, int estado, int idOperacion, RegistrarConsumoDataResponse data) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.idOperacion = idOperacion;
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

    public int getidOperacion() {
        return idOperacion;
    }

    public void setidOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }

}

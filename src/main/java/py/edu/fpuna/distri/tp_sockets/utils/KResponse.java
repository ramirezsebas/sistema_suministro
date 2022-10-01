package py.edu.fpuna.distri.tp_sockets.utils;

public class KResponse<T> {

    Integer estado;
    Integer tipoOperacion;
    String mensaje;
    T dato;

    public KResponse() {
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(Integer tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    @Override
    public String toString() {
        return "KResponse{" + "estado=" + estado + ", mensaje=" + mensaje + ", dato=" + dato + '}';
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

    public static KResponse fromJson(String json) {
        return JsonUtil.fromJson(json, KResponse.class);
    }

}
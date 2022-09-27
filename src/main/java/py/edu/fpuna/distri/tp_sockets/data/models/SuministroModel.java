package py.edu.fpuna.distri.tp_sockets.data.models;

import com.google.gson.Gson;

public class SuministroModel {
    private String mensaje;
    private int estado;
    private int idOperacion;

    public SuministroModel(String mensaje, int estado, int idOperacion) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.idOperacion = idOperacion;
    }

    public static SuministroModel fromJson(String json) {
        return new Gson().fromJson(json, SuministroModel.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
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

    @Override
    public String toString() {
        return "SuministroModel [mensaje=" + mensaje + ", estado=" + estado + ", idOperacion="
                + idOperacion + "]";
    }

}

package py.edu.fpuna.distri.tp_sockets.domain.entities;

enum Estado {
    ACTIVO,
    INACTIVO,
}

public class Suministro {
    private String nis;
    private double consumo;
    private double deuda;
    private Estado estado;

    public Suministro(String nis, double consumo, double deuda, Estado estado) {
        this.nis = nis;
        this.consumo = consumo;
        this.deuda = deuda;
        this.estado = estado;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Suministro [nis=" + nis + ", consumo=" + consumo + ", deuda=" + deuda + ", estado=" + estado + "]";
    }
}

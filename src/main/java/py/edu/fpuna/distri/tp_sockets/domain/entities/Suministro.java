package py.edu.fpuna.distri.tp_sockets.domain.entities;

public class Suministro {
    private String nis;
    private double consumo;
    private double deuda;
    private EstadoActual estado;

    public Suministro(String nis, double consumo, double deuda, EstadoActual estado) {
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

    public EstadoActual getEstado() {
        return estado;
    }

    public void setEstado(EstadoActual estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Suministro [nis=" + nis + ", consumo=" + consumo + ", deuda=" + deuda + ", estado=" + estado + "]";
    }
}

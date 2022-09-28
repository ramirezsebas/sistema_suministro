package py.edu.fpuna.distri.tp_sockets.utils;

import java.io.IOException;

public class ClientOperacion {
    private int tipoOperacion;

    public ClientOperacion(int tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getDto(TipoOperacionClientStrategy strategy) throws IOException {
        return strategy.getDto(this.tipoOperacion);
    }
}

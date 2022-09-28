package py.edu.fpuna.distri.tp_sockets.utils;

import java.io.IOException;

public interface TipoOperacionClientStrategy {
    public String getDto(int tipoOperacion) throws IOException;
    public void getRespuesta(String response) throws IOException;
}

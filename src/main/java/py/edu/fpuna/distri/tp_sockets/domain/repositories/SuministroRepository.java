package py.edu.fpuna.distri.tp_sockets.domain.repositories;


import java.util.List;

import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;

public interface SuministroRepository {
    public Suministro registrarConsumo(String nis);

    public boolean verificarConectividad(String nis);

    public Suministro enviarOrdenDesconexion(String nis);

    public Suministro enviarOrdenConexion(String nis);

    public List<Suministro> listarSuministrosActivos();

    public List<Suministro> listarSuministrosInactivos();

}

package py.edu.fpuna.distri.tp_sockets.domain.repositories;


import java.util.List;

import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;

public interface SuministroRepository {
    public Suministro registrarConsumo(String nis);

    public void verificarConectividad(String nis);

    public void enviarOrdenDesconexion(String nis);

    public void enviarOrdenConexion(String nis);

    public List<Suministro> listarSuministrosActivos();

    public List<Suministro> listarSuministrosInactivos();

}

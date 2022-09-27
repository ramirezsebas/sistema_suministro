package py.edu.fpuna.distri.tp_sockets.data.repositories;

import java.util.List;
import java.util.Map;

import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;
import py.edu.fpuna.distri.tp_sockets.domain.repositories.SuministroRepository;

public class MockSuministroRepository implements SuministroRepository {
    private final Map<String, Suministro> bdLocal;

    public MockSuministroRepository(Map<String, Suministro> bdLocal) {
        this.bdLocal = bdLocal;
    }

    private Suministro getSuministro(String nis) {
        return bdLocal.get(nis);
    }

    @Override
    public Suministro registrarConsumo(String nis) {
        Suministro suministro = getSuministro(nis);
        if (suministro == null) {
            return null;
        }
        suministro.setConsumo(suministro.getConsumo());
        return suministro;
    }

    @Override
    public void verificarConectividad(String nis) {
        // TODO Auto-generated method stub

    }

    @Override
    public void enviarOrdenDesconexion(String nis) {
        // TODO Auto-generated method stub

    }

    @Override
    public void enviarOrdenConexion(String nis) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Suministro> listarSuministrosActivos() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Suministro> listarSuministrosInactivos() {
        // TODO Auto-generated method stub
        return null;
    }

}

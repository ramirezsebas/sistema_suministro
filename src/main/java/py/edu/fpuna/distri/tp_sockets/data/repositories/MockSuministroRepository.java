package py.edu.fpuna.distri.tp_sockets.data.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import py.edu.fpuna.distri.tp_sockets.domain.entities.EstadoActual;
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
    public Suministro registrarConsumo(String nis,double consumo) {
        Suministro suministro = getSuministro(nis);
        if (suministro == null) {
            return null;
        }
        suministro.setConsumo(consumo);
        return suministro;
    }

    @Override
    public boolean verificarConectividad(String nis) {
        Suministro suministro = getSuministro(nis);
        if (suministro == null) {
            return false;
        }
        return true;
    }

    @Override
    public Suministro enviarOrdenDesconexion(String nis) {
        Suministro suministro = getSuministro(nis);

        if (suministro == null) {
            return null;
        }

        if (suministro.getDeuda() <= 0) {
            return null;
        }

        return suministro;

    }

    @Override
    public Suministro enviarOrdenConexion(String nis) {

        Suministro suministro = getSuministro(nis);

        if (suministro == null) {
            return null;
        }

        if (suministro.getDeuda() > 0) {
            return null;
        }

        return suministro;

    }

    @Override
    public List<Suministro> listarSuministrosInactivos() {
        List<Suministro> allSuministros = getAllSuministros();

        List<Suministro> suministrosInactivos = new ArrayList<>();

        for (Suministro suministro : allSuministros) {
            if (suministro.getEstado() == EstadoActual.INACTIVO) {
                suministrosInactivos.add(suministro);
            }
        }

        return suministrosInactivos;
    }

    @Override
    public List<Suministro> listarSuministrosActivos() {
        List<Suministro> allSuministros = getAllSuministros();

        List<Suministro> suministrosActivos = new ArrayList<>();

        for (Suministro suministro : allSuministros) {
            if (suministro.getEstado() == EstadoActual.ACTIVO) {
                suministrosActivos.add(suministro);
            }
        }

        return suministrosActivos;
    }

    private List<Suministro> getAllSuministros() {
        return bdLocal.values().stream().toList();
    }

}

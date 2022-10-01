package com.tarea_suministro.data.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tarea_suministro.domain.entities.Estado;
import com.tarea_suministro.domain.entities.Suministro;
import com.tarea_suministro.domain.repositories.SuministroRepository;

public class MockSuministroRepository implements SuministroRepository {
    private final Map<String, Suministro> bdLocal;

    public MockSuministroRepository(Map<String, Suministro> bdLocal) {
        this.bdLocal = bdLocal;
    }

    private Suministro getSuministro(String nis) {
        return bdLocal.get(nis);
    }

    @Override
    public Suministro registrarConsumo(String nis, double consumo) {
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

        suministro.setEstado(Estado.INACTIVO);

        return suministro;
    }

    @Override
    public Suministro enviarOrdenConexion(String nis) {

        Suministro suministro = getSuministro(nis);

        if (suministro == null) {
            return null;
        }

        suministro.setEstado(Estado.ACTIVO);

        return suministro;

    }

    @Override
    public List<Suministro> listarSuministrosInactivos() {
        List<Suministro> allSuministros = getAllSuministros();

        List<Suministro> suministrosInactivos = new ArrayList<>();

        for (Suministro suministro : allSuministros) {
            if (suministro.getEstado() == Estado.INACTIVO) {
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
            if (suministro.getEstado() == Estado.ACTIVO) {
                suministrosActivos.add(suministro);
            }
        }

        return suministrosActivos;
    }

    private List<Suministro> getAllSuministros() {
        return new ArrayList<>(bdLocal.values());
    }

}


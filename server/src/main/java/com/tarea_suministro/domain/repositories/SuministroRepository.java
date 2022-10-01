package com.tarea_suministro.domain.repositories;

import java.util.List;

import com.tarea_suministro.domain.entities.Suministro;

public interface SuministroRepository {
    public Suministro registrarConsumo(String nis, double consumo);

    public boolean verificarConectividad(String nis);

    public Suministro enviarOrdenDesconexion(String nis);

    public Suministro enviarOrdenConexion(String nis);

    public List<Suministro> listarSuministrosActivos();

    public List<Suministro> listarSuministrosInactivos();

}
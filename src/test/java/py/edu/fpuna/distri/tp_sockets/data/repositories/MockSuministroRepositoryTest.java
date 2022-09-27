package py.edu.fpuna.distri.tp_sockets.data.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;
import py.edu.fpuna.distri.tp_sockets.domain.entities.EstadoActual;

import py.edu.fpuna.distri.tp_sockets.domain.repositories.SuministroRepository;

public class MockSuministroRepositoryTest {

    @Test
    public void registrarConsumoDeberiaRetornarNullEnCasoNoExiste() {
        Map<String, Suministro> bdLocal = new HashMap<>();
        SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

        Suministro suministro = mockSuministroRepository.registrarConsumo("123456789");

        assertNull(suministro);
    }

    @Test
    public void registrarConsumoDeberiaRetornarSuministro() {
        Suministro suministroSeleccionado = new Suministro("123456789", "Juan Perez", 100, 100.0, EstadoActual.ACTIVO);
        List<Suministro> suministros = new ArrayList<>();
        suministros.add(suministroSeleccionado);
        suministros.add(new Suministro("987654323421", "Maria Lopez", 234, 200, EstadoActual.ACTIVO));
        suministros.add(new Suministro("15423456789", "Juan Perez", 54353, 100, EstadoActual.ACTIVO));
        suministros.add(new Suministro("98743534654321", "Maria Lopez", 345543, 200, EstadoActual.ACTIVO));

        Map<String, Suministro> bdLocal = new HashMap<>();
        for (Suministro suministro : suministros) {
            bdLocal.put(suministro.getNis(), suministro);
        }

        SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

        Suministro suministro = mockSuministroRepository.registrarConsumo("123456789");

        assertEquals(suministro, suministroSeleccionado);
    }

    @Test
    public void verificarConectividadDeberiaRetornarFalseEnCasoNoExiste() {
        Map<String, Suministro> bdLocal = new HashMap<>();
        SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

        boolean conectividad = mockSuministroRepository.verificarConectividad("123456789");

        assertEquals(conectividad, false);
    }

    @Test
    public void verificarConectividadDeberiaRetornarTrueEnCasoExiste() {
        Suministro suministroSeleccionado = new Suministro("123456789", "Juan Perez", 100, 100.0, EstadoActual.ACTIVO);
        List<Suministro> suministros = new ArrayList<>();
        suministros.add(suministroSeleccionado);
        suministros.add(new Suministro("987654323421", "Maria Lopez", 234, 200, EstadoActual.ACTIVO));
        suministros.add(new Suministro("15423456789", "Juan Perez", 54353, 100, EstadoActual.ACTIVO));
        suministros.add(new Suministro("98743534654321", "Maria Lopez", 345543, 200, EstadoActual.ACTIVO));

        Map<String, Suministro> bdLocal = new HashMap<>();
        for (Suministro suministro : suministros) {
            bdLocal.put(suministro.getNis(), suministro);
        }

        SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

        boolean conectividad = mockSuministroRepository.verificarConectividad("123456789");

        assertEquals(conectividad, true);
    }

    // @Test
    // public void enviarOrdenDesconexionDeberiaRetornarFalseEnCasoNoExiste() {
    // Map<String, Suministro> bdLocal = new HashMap<>();
    // SuministroRepository mockSuministroRepository = new
    // MockSuministroRepository(bdLocal);

    // boolean orden = mockSuministroRepository.enviarOrdenDesconexion("123456789");

    // assertEquals(orden, false);
    // }

    // @Test
    // public void enviarOrdenDesconexionDeberiaRetornarTrueEnCasoExiste() {
    // Suministro suministroSeleccionado = new Suministro("123456789", "Juan Perez",
    // 100, 100.0, EstadoActual.ACTIVO);
    // List<Suministro> suministros = new ArrayList<>();
    // suministros.add(suministroSeleccionado);
    // suministros.add(new Suministro("987654323421", "Maria Lopez", 234, 200,
    // EstadoActual.ACTIVO));
    // suministros.add(new Suministro("15423456789", "Juan Perez", 54353, 100,
    // EstadoActual.ACTIVO));
    // suministros.add(new Suministro("98743534654321", "Maria Lopez", 345543, 200,
    // EstadoActual.ACTIVO));

    // Map<String, Suministro> bdLocal = new HashMap<>();
    // for (Suministro suministro : suministros) {
    // bdLocal.put(suministro.getNis(), suministro);
    // }

    // SuministroRepository mockSuministroRepository = new
    // MockSuministroRepository(bdLocal);

    // boolean orden = mockSuministroRepository.enviarOrdenDesconexion("123456789");

    // assertEquals(orden, true);
    // }

    @Test
    public void listarSuministrosActivosDeberiaRetornarLista() {
        Suministro suministroSeleccionado = new Suministro("123456789", "Juan Perez", 100, 100.0,
                EstadoActual.INACTIVO);
        List<Suministro> suministros = new ArrayList<>();
        suministros.add(suministroSeleccionado);
        suministros.add(new Suministro("987654323421", "Maria Lopez", 234, 200, EstadoActual.ACTIVO));
        suministros.add(new Suministro("15423456789", "Juan Perez", 54353, 100, EstadoActual.INACTIVO));
        suministros.add(new Suministro("98743534654321", "Maria Lopez", 345543, 200, EstadoActual.INACTIVO));

        Map<String, Suministro> bdLocal = new HashMap<>();
        for (Suministro suministro : suministros) {
            bdLocal.put(suministro.getNis(), suministro);
        }

        List<Suministro> suministrosSeleccionados = new ArrayList<>();
        suministrosSeleccionados.add(new Suministro("987654323421", "Maria Lopez", 234, 200, EstadoActual.ACTIVO));

        SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

        List<Suministro> suministrosObtenidos = mockSuministroRepository.listarSuministrosActivos();

        assertTrue(suministrosSeleccionados.size() == suministrosObtenidos.size());

    }

}

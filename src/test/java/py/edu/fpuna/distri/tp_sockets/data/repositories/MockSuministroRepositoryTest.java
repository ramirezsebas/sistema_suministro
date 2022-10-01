// package py.edu.fpuna.distri.tp_sockets.data.repositories;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNotNull;
// import static org.junit.Assert.assertNull;
// import static org.junit.Assert.assertTrue;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.junit.Test;
// import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;
// import py.edu.fpuna.distri.tp_sockets.domain.entities.Estado;

// import py.edu.fpuna.distri.tp_sockets.domain.repositories.SuministroRepository;

// public class MockSuministroRepositoryTest {

//     @Test
//     public void registrarConsumoDeberiaRetornarNullEnCasoNoExiste() {
//         Map<String, Suministro> bdLocal = new HashMap<>();
//         SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

//         Suministro suministro = mockSuministroRepository.registrarConsumo("123456789", 121321);

//         assertNull(suministro);
//     }

//     @Test
//     public void registrarConsumoDeberiaRetornarSuministro() {
//         Suministro suministroSeleccionado = new Suministro("123456789", "Juan Perez", 100, Estado.ACTIVO);
//         List<Suministro> suministros = new ArrayList<>();
//         suministros.add(suministroSeleccionado);
//         suministros.add(new Suministro("987654323421", "Maria Lopez", 234, Estado.ACTIVO));
//         suministros.add(new Suministro("15423456789", "Juan Perez", 54353, Estado.ACTIVO));
//         suministros.add(new Suministro("98743534654321", "Maria Lopez", 345543, Estado.ACTIVO));

//         Map<String, Suministro> bdLocal = new HashMap<>();
//         for (Suministro suministro : suministros) {
//             bdLocal.put(suministro.getNis(), suministro);
//         }

//         SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

//         Suministro suministro = mockSuministroRepository.registrarConsumo("123456789", 12312);

//         assertEquals(suministro, suministroSeleccionado);
//     }

//     @Test
//     public void verificarConectividadDeberiaRetornarFalseEnCasoNoExiste() {
//         Map<String, Suministro> bdLocal = new HashMap<>();
//         SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

//         boolean conectividad = mockSuministroRepository.verificarConectividad("123456789");

//         assertEquals(conectividad, false);
//     }

//     @Test
//     public void verificarConectividadDeberiaRetornarTrueEnCasoExiste() {
//         Suministro suministroSeleccionado = new Suministro("123456789", "Juan Perez", 100, Estado.ACTIVO);
//         List<Suministro> suministros = new ArrayList<>();
//         suministros.add(suministroSeleccionado);
//         suministros.add(new Suministro("987654323421", "Maria Lopez", 234, Estado.ACTIVO));
//         suministros.add(new Suministro("15423456789", "Juan Perez", 54353, Estado.ACTIVO));
//         suministros.add(new Suministro("98743534654321", "Maria Lopez", 345543, Estado.ACTIVO));

//         Map<String, Suministro> bdLocal = new HashMap<>();
//         for (Suministro suministro : suministros) {
//             bdLocal.put(suministro.getNis(), suministro);
//         }

//         SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

//         boolean conectividad = mockSuministroRepository.verificarConectividad("123456789");

//         assertEquals(conectividad, true);
//     }

//     @Test
//     public void enviarOrdenDesconexionDeberiaRetornarNuloEnCasoNoExiste() {
//         Map<String, Suministro> bdLocal = new HashMap<>();
//         SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

//         Suministro orden = mockSuministroRepository.enviarOrdenDesconexion("123456789");

//         // Deuda should be null
//         assertNull(orden);
//     }

//     @Test
//     public void enviarOrdenDesconexionDeberiaRetornarSuministroEnCasoExiste() {
//         Suministro suministroSeleccionado = new Suministro("123456789", "Juan Perez", 100, Estado.ACTIVO);
//         List<Suministro> suministros = new ArrayList<>();
//         suministros.add(suministroSeleccionado);
//         suministros.add(new Suministro("987654323421", "Maria Lopez", 234, Estado.ACTIVO));
//         suministros.add(new Suministro("15423456789", "Juan Perez", 54353, Estado.ACTIVO));
//         suministros.add(new Suministro("98743534654321", "Maria Lopez", 345543, Estado.ACTIVO));
//         Map<String, Suministro> bdLocal = new HashMap<>();
//         for (Suministro suministro : suministros) {
//             bdLocal.put(suministro.getNis(), suministro);
//         }

//         SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

//         Suministro orden = mockSuministroRepository.enviarOrdenDesconexion("123456789");

//         assertNotNull("El suministro tiene desconexion", orden);
//     }

//     @Test
//     public void enviarOrdenDesconexionDeberiaRetornarNuloEnCasoExistePeroSinDeuda() {
//         Suministro suministroSeleccionado = new Suministro("123456789", "Juan Perez", 100, Estado.ACTIVO);
//         List<Suministro> suministros = new ArrayList<>();
//         suministros.add(suministroSeleccionado);
//         suministros.add(new Suministro("987654323421", "Maria Lopez", 234, Estado.ACTIVO));
//         suministros.add(new Suministro("15423456789", "Juan Perez", 54353, Estado.ACTIVO));
//         suministros.add(new Suministro("98743534654321", "Maria Lopez", 345543, Estado.ACTIVO));

//         Map<String, Suministro> bdLocal = new HashMap<>();
//         for (Suministro suministro : suministros) {
//             bdLocal.put(suministro.getNis(), suministro);
//         }

//         SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

//         Suministro orden = mockSuministroRepository.enviarOrdenDesconexion("123456789");

//         assertNull("El suministro tiene conexion", orden);
//     }

//     @Test
//     public void listarSuministrosActivosDeberiaRetornarLista() {
//         Suministro suministroSeleccionado = new Suministro("123456789", "Juan Perez", 100, Estado.ACTIVO);
//         List<Suministro> suministros = new ArrayList<>();
//         suministros.add(suministroSeleccionado);
//         suministros.add(new Suministro("987654323421", "Maria Lopez", 234, Estado.ACTIVO));
//         suministros.add(new Suministro("15423456789", "Juan Perez", 54353, Estado.ACTIVO));
//         suministros.add(new Suministro("98743534654321", "Maria Lopez", 345543, Estado.ACTIVO));
        
//         Map<String, Suministro> bdLocal = new HashMap<>();
        
//         for (Suministro suministro : suministros) {
//             bdLocal.put(suministro.getNis(), suministro);
//         }

//         List<Suministro> suministrosSeleccionados = new ArrayList<>();
//         suministrosSeleccionados.add(new Suministro("987654323421", "Maria Lopez", 234, Estado.ACTIVO));

//         SuministroRepository mockSuministroRepository = new MockSuministroRepository(bdLocal);

//         List<Suministro> suministrosObtenidos = mockSuministroRepository.listarSuministrosActivos();

//         assertTrue(suministrosSeleccionados.size() == suministrosObtenidos.size());

//     }

// }

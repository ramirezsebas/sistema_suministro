package py.edu.fpuna.distri.tp_sockets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import py.edu.fpuna.distri.tp_sockets.data.repositories.MockSuministroRepository;
import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;
import py.edu.fpuna.distri.tp_sockets.domain.repositories.SuministroRepository;

public class AppServer {
    public static void main(String[] args) {
        Map<String, Suministro> bdLocal = new HashMap<>();
        SuministroRepository suministroRepository = new MockSuministroRepository(bdLocal);

        // Recibo Tipo de Operacion y NIS del Cliente
        int tipoOperacion = 1;
        String nis = "123456789";
        switch (tipoOperacion) {
            // Registrar Consumo
            case 1:
                Suministro suministro = suministroRepository.registrarConsumo(nis);

                if (suministro == null) {
                    System.out.println("El NIS no existe");
                } else {
                    System.out.println("El consumo del suministro es: " + suministro.getConsumo());
                }

                break;

            // Verificar Conectividad
            case 2:
                boolean isConnected = suministroRepository.verificarConectividad(nis);
                System.out.println("El suministro esta conectado: " + isConnected);

                break;

            // Enviar Orden de Desconexion
            case 3:
                suministroRepository.enviarOrdenDesconexion(nis);
                break;

            // Enviar Orden de Conexion
            case 4:
                suministroRepository.enviarOrdenConexion(nis);
                break;

            // Listar Suministros Activos
            case 5:
                List<Suministro> suministros = suministroRepository.listarSuministrosActivos();
                for (Suministro suministro2 : suministros) {
                    System.out.println(suministro2);
                }
                break;

            // Listar Suministros Inactivos
            case 6:
                List<Suministro> suministrosInactivos = suministroRepository.listarSuministrosInactivos();
                for (Suministro suministro2 : suministrosInactivos) {
                    System.out.println(suministro2);
                }

                break;

            default:
                throw new IllegalArgumentException("Operacion no Valido: " + tipoOperacion);
        }

        // Recibo del cliente el NIS

    }
}

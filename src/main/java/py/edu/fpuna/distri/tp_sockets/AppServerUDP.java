package py.edu.fpuna.distri.tp_sockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import py.edu.fpuna.distri.tp_sockets.data.models.OperacionDto;
import py.edu.fpuna.distri.tp_sockets.data.models.SuministroModel;
import py.edu.fpuna.distri.tp_sockets.data.repositories.MockSuministroRepository;
import py.edu.fpuna.distri.tp_sockets.domain.entities.EstadoActual;
import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;
import py.edu.fpuna.distri.tp_sockets.domain.repositories.SuministroRepository;

public class AppServerUDP {

    private static Map<String, Suministro> initDB() {
        Map<String, Suministro> bdLocal = new HashMap<>();
        bdLocal.put("123456789", new Suministro("132423456789", "Juan Perez", 1234.5, 0, EstadoActual.ACTIVO));
        bdLocal.put("987654321", new Suministro("987634554321", "Maria Ramirez", 10000, 12345.6, EstadoActual.ACTIVO));
        bdLocal.put("123456789", new Suministro("1234567456389", "Juan Sosa", 1234.5, 12345.6, EstadoActual.ACTIVO));
        bdLocal.put("987654321", new Suministro("98765432561", "Mario Gomez", 1234.5, 12345.6, EstadoActual.ACTIVO));
        bdLocal.put("123456789", new Suministro("123456767589", "Juan Torres Brizuela", 12671, 0, EstadoActual.ACTIVO));
        bdLocal.put("987654321", new Suministro("98765432341", "Mariano Lopez", 1234.5, 0, EstadoActual.ACTIVO));
        bdLocal.put("123456789",
                new Suministro("12345678555559", "Enrique Gimenez", 1234.5, 12345.6, EstadoActual.ACTIVO));
        bdLocal.put("987654321", new Suministro("9876543266661", "Matias Lopez", 1234.5, 12345.6, EstadoActual.ACTIVO));
        bdLocal.put("123456789", new Suministro("123456787779", "Matias Perez", 1234.5, 12345.6, EstadoActual.ACTIVO));
        bdLocal.put("987654321",
                new Suministro("9876543286751", "Matias Pedroso", 1234.5, 12345.6, EstadoActual.ACTIVO));
        bdLocal.put("123456789", new Suministro("12345678564339", "Alejendro Sanz", 1234.5, 0, EstadoActual.ACTIVO));
        bdLocal.put("987654321", new Suministro("987654326541", "Enrique Iglesias", 1234.5, 0, EstadoActual.ACTIVO));
        bdLocal.put("123456789", new Suministro("12345678654549", "Arturo Suarez", 1234.5, 0, EstadoActual.ACTIVO));
        bdLocal.put("987654321",
                new Suministro("987654376544521", "Geronimo Torres", 1234.5, 12345.6, EstadoActual.INACTIVO));
        bdLocal.put("123456789",
                new Suministro("1234567875464569", "Juan Troche", 1234.5, 12345.6, EstadoActual.ACTIVO));
        bdLocal.put("987654321",
                new Suministro("987654326456451", "Maria Castillo", 1234.5, 12345.6, EstadoActual.INACTIVO));

        return bdLocal;
    }

    public static void main(String[] args) {
        Map<String, Suministro> bdLocal = AppServerUDP.initDB();
        SuministroRepository suministroRepository = new MockSuministroRepository(bdLocal);

        int puertoServidor = 9876;
        try {
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            while (true) {
                receiveData = new byte[1024];

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                System.out.println("Esperando a algun NIS... ");

                serverSocket.receive(receivePacket);
                String request = new String(receivePacket.getData()).trim();
                System.out.println("________________________________________________");
                System.out.println("Request: " + request);

                OperacionDto operacionDto = OperacionDto.fromJson(request);

                int tipoOperacion = operacionDto.getIdOperacion();
                String nis = operacionDto.getNis();

                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();

                switch (tipoOperacion) {
                    // Registrar Consumo
                    case 1:
                        Suministro suministro = suministroRepository.registrarConsumo(nis);

                        SuministroModel suministroModel = new SuministroModel("ok", 0, tipoOperacion);
                        sendData = suministroModel.toJson().getBytes();
                        if (suministro == null) {
                            System.out.println("El suministro no existe");
                        } else {
                            System.out.println("El consumo del suministro es: " +
                                    suministro.getConsumo());
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

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

                serverSocket.send(sendPacket);

            }

        } catch (SocketException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        // Recibo del cliente el NIS

    }
}

package py.edu.fpuna.distri.tp_sockets.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import py.edu.fpuna.distri.tp_sockets.data.mappers.EnviarOrdenDataResponse;
import py.edu.fpuna.distri.tp_sockets.data.mappers.EnviarOrdenResponse;
import py.edu.fpuna.distri.tp_sockets.data.mappers.ListarSuministroResponse;
import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoDataResponse;
import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoDto;
import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoResponse;
import py.edu.fpuna.distri.tp_sockets.data.repositories.MockSuministroRepository;
import py.edu.fpuna.distri.tp_sockets.domain.entities.EstadoActual;
import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;
import py.edu.fpuna.distri.tp_sockets.domain.repositories.SuministroRepository;

public class AppServerUDP {

    private static Map<String, Suministro> initDB() {
        Map<String, Suministro> bdLocal = new HashMap<>();
        bdLocal.put("123", new Suministro("123", "Juan Pereria", 1234.56, 0, EstadoActual.ACTIVO));
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
                new Suministro("999", "Geronimo Torres", 1234.5, 12345.6, EstadoActual.INACTIVO));
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

                System.out.println("Esperando peticion de algun NIS... ");

                serverSocket.receive(receivePacket);
                String request = new String(receivePacket.getData()).trim();
                RegistrarConsumoDto registrarConsumoDto = RegistrarConsumoDto.fromJson(request);

                System.out.println("________________________________________________");
                System.out.println("Request del NIS: " + registrarConsumoDto.getNis() + " " + request);
                System.out.println();

                int tipoOperacion = registrarConsumoDto.getIdOperacion();
                String nis = registrarConsumoDto.getNis();

                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                switch (tipoOperacion) {
                    // Registrar Consumo
                    case 1:
                        double consumo = registrarConsumoDto.getConsumo();
                        Suministro suministro = suministroRepository.registrarConsumo(nis, consumo);

                        if (suministro == null) {
                            System.out.println("El suministro no existe");
                            RegistrarConsumoResponse suministroModel = new RegistrarConsumoResponse("ok", 0,
                                    tipoOperacion);
                            sendData = suministroModel.toJson().getBytes();

                        } else {
                            RegistrarConsumoDataResponse registrarConsumoDataResponse = new RegistrarConsumoDataResponse(
                                    suministro.getNis(), suministro.getConsumo());
                            RegistrarConsumoResponse suministroModel = new RegistrarConsumoResponse("ok", 0,
                                    tipoOperacion,
                                    registrarConsumoDataResponse);

                            sendData = suministroModel.toJson().getBytes();
                        }

                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

                        serverSocket.send(sendPacket);

                        System.out.println("Se envio la respuesta al NIS:" + nis);
                        System.out.println("________________________________________________");
                        System.out.println();
                        System.out.println(new String(sendData).trim());

                        break;

                    // Verificar Conectividad
                    case 2:
                        boolean isConnected = suministroRepository.verificarConectividad(nis);
                        System.out.println("El suministro esta conectado: " + isConnected);

                        break;

                    // Enviar Orden de Desconexion
                    case 3:
                        Suministro suministro1 = suministroRepository.enviarOrdenDesconexion(nis);

                        if (suministro1 == null) {
                            System.out.println("El suministro no existe");
                            EnviarOrdenResponse suministroModel = new EnviarOrdenResponse("ok", 0, tipoOperacion);
                            sendData = suministroModel.toJson().getBytes();

                        } else {
                            EnviarOrdenDataResponse enviarOrdenDataResponse = new EnviarOrdenDataResponse(
                                    suministro1.getNis(), suministro1.getDeuda());

                            EnviarOrdenResponse suministroModel = new EnviarOrdenResponse("ok", 0, tipoOperacion,
                                    enviarOrdenDataResponse);

                            sendData = suministroModel.toJson().getBytes();
                        }
                        break;

                    // Enviar Orden de Conexion
                    case 4:
                        Suministro suministro2 = suministroRepository.enviarOrdenConexion(nis);

                        if (suministro2 == null) {
                            System.out.println("El suministro no existe");
                            EnviarOrdenResponse suministroModel = new EnviarOrdenResponse("ok", 0, tipoOperacion);
                            sendData = suministroModel.toJson().getBytes();

                        } else {
                            EnviarOrdenDataResponse enviarOrdenDataResponse = new EnviarOrdenDataResponse(
                                    suministro2.getNis(), suministro2.getDeuda());

                            EnviarOrdenResponse suministroModel = new EnviarOrdenResponse("ok", 0, tipoOperacion,
                                    enviarOrdenDataResponse);

                            sendData = suministroModel.toJson().getBytes();
                        }
                        break;

                    // Listar Suministros Activos
                    case 5:
                        List<Suministro> suministros = suministroRepository.listarSuministrosActivos();

                        ListarSuministroResponse listarSuministroResponseA = new ListarSuministroResponse("ok", 0,
                                tipoOperacion, suministros);

                        sendData = listarSuministroResponseA.toJson().getBytes();

                        System.out.println("Se envio la respuesta al NIS:" + nis);
                        System.out.println("________________________________________________");
                        System.out.println();
                        System.out.println(new String(sendData).trim());

                        break;

                    // Listar Suministros Inactivos
                    case 6:
                        List<Suministro> suministrosInactivos = suministroRepository.listarSuministrosInactivos();
                        ListarSuministroResponse listarSuministroResponseI = new ListarSuministroResponse("ok", 0,
                                tipoOperacion, suministrosInactivos);

                        sendData = listarSuministroResponseI.toJson().getBytes();

                        System.out.println("Se envio la respuesta al NIS:" + nis);
                        System.out.println("________________________________________________");
                        System.out.println();
                        System.out.println(new String(sendData).trim());

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

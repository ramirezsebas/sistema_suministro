package py.edu.fpuna.distri.tp_sockets.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import py.edu.fpuna.distri.tp_sockets.data.mappers.*;
import py.edu.fpuna.distri.tp_sockets.data.repositories.MockSuministroRepository;
import py.edu.fpuna.distri.tp_sockets.domain.entities.EstadoActual;
import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;
import py.edu.fpuna.distri.tp_sockets.domain.repositories.SuministroRepository;
import py.edu.fpuna.distri.tp_sockets.utils.KResponse;

public class AppServerUDP {
    private static Logger logger = Logger.getLogger(String.valueOf(AppServerUDP.class));
    private static FileHandler fh;

    static {
        try {
            fh = new FileHandler("mylog.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
        logger.addHandler(fh);
        KResponse response = new KResponse();
        try {
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
            logger.info("Servidor escuchando en puerto: " + puertoServidor);
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            while (true) {
                receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                logger.info("Esperando peticion de algun NIS... ");
                serverSocket.receive(receivePacket);

                String request = new String(receivePacket.getData()).trim();
                RegistrarConsumoDto registrarConsumoDto = RegistrarConsumoDto.fromJson(request);

                logger.info("________________________________________________");
                logger.info("Request del NIS: " + request);

                int tipoOperacion = registrarConsumoDto.getTipoOperacion();
                String nis = registrarConsumoDto.getNis();

                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();

                switch (tipoOperacion) {
                    // Registrar Consumo
                    case 1:
                        double consumo = registrarConsumoDto.getConsumo();
                        Suministro suministro = suministroRepository.registrarConsumo(nis, consumo);

                        if (suministro == null) {
                            logger.warning("El suministro no existe");
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

                        logger.info("Se envio la respuesta al NIS:" + nis);
                        logger.info("________________________________________________");
                        logger.info(new String(sendData).trim());

                        break;

                    // Verificar Conectividad
                    case 2:
                        boolean isConnected = suministroRepository.verificarConectividad(nis);
                        logger.info("El suministro esta conectado: " + isConnected); // Logger para el servidor

                        response.setEstado(0);
                        response.setMensaje("OK");
                        if (isConnected) {
                            response.setDato("El NIS: " + nis + " esta conectado");
                        }else{
                            response.setDato("El NIS: " + nis + " no esta conectado");
                        }
                        sendData = response.toJson().getBytes();

                        break;

                    // Enviar Orden de Desconexion
                    case 3:
                        Suministro suministro1 = suministroRepository.enviarOrdenDesconexion(nis);

                        if (suministro1 == null) {
                            logger.info("El suministro no existe");
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
                            logger.info("El suministro no existe");
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

                        logger.info("Se envio la respuesta al NIS:" + nis);
                        logger.info("________________________________________________");
                        logger.info(new String(sendData).trim());

                        break;

                    // Listar Suministros Inactivos
                    case 6:
                        List<Suministro> suministrosInactivos = suministroRepository.listarSuministrosInactivos();
                        ListarSuministroResponse listarSuministroResponseI = new ListarSuministroResponse("ok", 0,
                                tipoOperacion, suministrosInactivos);

                        sendData = listarSuministroResponseI.toJson().getBytes();

                        logger.info("Se envio la respuesta al NIS:" + nis);
                        logger.info("________________________________________________");
                        logger.info(new String(sendData).trim());

                        break;

                    default:
                        throw new IllegalArgumentException("Operacion no Valido: " + tipoOperacion);
                }

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

                serverSocket.send(sendPacket);

            }

        } catch (SocketException e) {
            logger.log(Level.WARNING, "ERROR", e);
            response.setEstado(10);
            response.setMensaje("ERROR AL OBTENER LOS DATOS");
            response.setMensaje(e.getMessage());
            response.setDato(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        // Recibo del cliente el NIS

    }
}

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
import py.edu.fpuna.distri.tp_sockets.domain.entities.Estado;
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
        bdLocal.put("1", new Suministro("1", "Juan Pereria", 1234.56, 0, Estado.ACTIVO));
        bdLocal.put("2", new Suministro("2", "Juan Perez", 1234.5, 0, Estado.ACTIVO));
        bdLocal.put("3", new Suministro("3", "Maria Ramirez", 10000, 12345.6, Estado.ACTIVO));
        bdLocal.put("4", new Suministro("4", "Juan Sosa", 1234.5, 12345.6, Estado.ACTIVO));
        bdLocal.put("5", new Suministro("5", "Mario Gomez", 1234.5, 12345.6, Estado.ACTIVO));
        bdLocal.put("6", new Suministro("6", "Juan Torres Brizuela", 12671, 0, Estado.ACTIVO));
        bdLocal.put("7", new Suministro("7", "Mariano Lopez", 1234.5, 0, Estado.ACTIVO));
        bdLocal.put("8",
                new Suministro("8", "Enrique Gimenez", 1234.5, 12345.6, Estado.ACTIVO));
        bdLocal.put("9", new Suministro("9", "Matias Lopez", 1234.5, 12345.6, Estado.ACTIVO));
        bdLocal.put("10", new Suministro("10", "Matias Perez", 1234.5, 12345.6, Estado.ACTIVO));
        bdLocal.put("11",
                new Suministro("11", "Matias Pedroso", 1234.5, 12345.6, Estado.ACTIVO));
        bdLocal.put("12", new Suministro("12", "Alejendro Sanz", 1234.5, 0, Estado.ACTIVO));
        bdLocal.put("13", new Suministro("13", "Enrique Iglesias", 1234.5, 0, Estado.ACTIVO));
        bdLocal.put("14", new Suministro("14", "Arturo Suarez", 1234.5, 0, Estado.ACTIVO));
        bdLocal.put("15",
                new Suministro("15", "Geronimo Torres", 1234.5, 12345.6, Estado.INACTIVO));
        bdLocal.put("16",
                new Suministro("16", "Juan Troche", 1234.5, 12345.6, Estado.ACTIVO));
        bdLocal.put("17",
                new Suministro("17", "Maria Castillo", 1234.5, 12345.6, Estado.INACTIVO));

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

                        response.setEstado(0);
                        response.setMensaje("OK");
                        response.setTipoOperacion(1);

                        if (suministro == null) {
                            logger.warning("El suministro no existe");
                            response.setDato("No se pudo registrar el consumo, no existe el suministro: " + nis);

                        } else {
                            RegistrarConsumoDataResponse registrarConsumoDataResponse = new RegistrarConsumoDataResponse(
                                    suministro.getNis(), suministro.getConsumo());
                            response.setDato(registrarConsumoDataResponse);
                        }
                        sendData = response.toJson().getBytes();

                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

                        serverSocket.send(sendPacket);

                        logger.info("Se envio la respuesta al NIS:" + nis);
                        logger.info("________________________________________________");
                        logger.info(new String(sendData).trim());

                        break;

                    case 2:
                        boolean isConnected = suministroRepository.verificarConectividad(nis);
                        logger.info("El suministro esta conectado: " + isConnected); // Logger para el servidor

                        response.setEstado(0);
                        response.setMensaje("OK");
                        response.setTipoOperacion(2);
                        if (isConnected) {
                            response.setDato("El NIS: " + nis + " esta conectado");
                        } else {
                            response.setDato("El NIS: " + nis + " no esta conectado");
                        }
                        sendData = response.toJson().getBytes();

                        logger.info("Se envio la respuesta al NIS:" + nis);
                        logger.info("________________________________________________");
                        logger.info(new String(sendData).trim());

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

                        logger.info("Se envio la respuesta al NIS:" + nis);
                        logger.info("________________________________________________");
                        logger.info(new String(sendData).trim());
                        break;

                    default:
                        logger.warning("Tipo de operacion no valida");
                        response.setEstado(1);
                        response.setMensaje("ERROR");
                        response.setTipoOperacion(tipoOperacion);
                        response.setDato("Tipo de operacion no valida");
                        sendData = response.toJson().getBytes();
                        break;
                }

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

                serverSocket.send(sendPacket);

            }

        } catch (SocketException e) {
            logger.log(Level.SEVERE, "ERROR", e);
            response.setEstado(10);
            response.setMensaje(e.getMessage());
            response.setDato(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "ERROR", e);
            response.setEstado(10);
            response.setMensaje(e.getMessage());
            response.setDato(e.getMessage());

            e.printStackTrace();
        }

        // Recibo del cliente el NIS

    }
}

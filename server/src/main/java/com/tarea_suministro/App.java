package com.tarea_suministro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tarea_suministro.data.mappers.BaseDto;
import com.tarea_suministro.data.mappers.BaseResponse;
import com.tarea_suministro.data.repositories.MockSuministroRepository;
import com.tarea_suministro.domain.entities.Estado;
import com.tarea_suministro.domain.entities.Suministro;
import com.tarea_suministro.domain.repositories.SuministroRepository;

/**
 * Hello world!
 *
 */
public class App {
    private static Logger logger = Logger.getLogger(String.valueOf(App.class));
    private static FileHandler fh;

    static {
        try {
            fh = new FileHandler("mylog.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            logger.log(Level.SEVERE, "Debe ingresar el protocolo");
            System.exit(-1);
        }

        String protocolo = args[0];

        // Check if protocolo is valid
        if (!protocolo.equals("tcp") && !protocolo.equals("udp")) {
            logger.log(Level.SEVERE, "Protocolo invalido");
            System.exit(-2);
        }

        Map<String, Suministro> bdLocal = App.initDB();
        SuministroRepository suministroRepository = new MockSuministroRepository(bdLocal);
        int puertoServidor = 9876;
        logger.addHandler(fh);

        if (protocolo.equals("tcp")) {
            App.iniciarServidorTCP(puertoServidor, suministroRepository);
        } else if (protocolo.equals("udp")) {
            App.iniciarServidorUDP(puertoServidor, suministroRepository);
        } else {
            logger.log(Level.SEVERE, "Protocolo no soportado");
        }

    }

    private static void iniciarServidorTCP(int puertoServidor, SuministroRepository suministroRepository) {
        Socket clientSocket = null;
        int tiempo_procesamiento_miliseg = 2000;

        BaseResponse kresponse = new BaseResponse();
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            ServerSocket serverSocket = new ServerSocket(puertoServidor);
            logger.info("Servidor escuchando en puerto: " + puertoServidor);

            while (true) {
                logger.info("Esperando peticion de algun NIS... ");
                clientSocket = serverSocket.accept();

                System.out
                        .println("Conexion establecida con el NIS de: "
                                + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort());

                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String request = in.readLine();
                String response;

                logger.info("________________________________________________");
                logger.info("Request del NIS: " + request);

                BaseDto dto = BaseDto.fromJson(request);

                int tipoOperacion = dto.getTipoOperacion();
                String nis = dto.getNis();

                switch (tipoOperacion) {
                    // Enviar Orden de Conexion
                    case 4:
                        Suministro suministro2 = suministroRepository.enviarOrdenConexion(nis);

                        kresponse.setEstado(0);
                        kresponse.setMensaje("OK");
                        kresponse.setTipoOperacion(3);
                        if (suministro2 == null) {
                            logger.info("El suministro no existe");
                            kresponse.setDato(
                                    "No se pudo enviar la orden de desconexion, no existe el suministro: " + nis);
                        } else {
                            kresponse.setDato(suministro2);
                        }

                        logger.info("Se envio la respuesta al NIS:" + nis);
                        logger.info("________________________________________________");
                        logger.info(new String(kresponse.toJson()).trim());
                        break;

                    // Listar Suministros Activos
                    case 5:
                        List<Suministro> suministros = suministroRepository.listarSuministrosActivos();

                        kresponse.setEstado(0);
                        kresponse.setMensaje("OK");
                        kresponse.setTipoOperacion(6);
                        kresponse.setDato(suministros);

                        logger.info("Se envio la respuesta al NIS:" + nis);
                        logger.info("________________________________________________");

                        logger.info(new String(kresponse.toJson()).trim());
                        break;

                    // Listar Suministros Inactivos
                    case 6:
                        List<Suministro> suministrosInactivos = suministroRepository.listarSuministrosInactivos();

                        kresponse.setEstado(0);
                        kresponse.setMensaje("OK");
                        kresponse.setTipoOperacion(6);
                        kresponse.setDato(suministrosInactivos);

                        logger.info("Se envio la respuesta al NIS:" + nis);
                        logger.info("________________________________________________");

                        logger.info(new String(kresponse.toJson()).trim());

                        break;

                    default:
                        logger.warning("Tipo de operacion no valida");
                        kresponse.setEstado(1);
                        kresponse.setMensaje("ERROR");
                        kresponse.setTipoOperacion(tipoOperacion);
                        kresponse.setDato("Tipo de operacion no valida");
                        break;
                }

                response = kresponse.toJson();

                TimeUnit.MILLISECONDS.sleep(tiempo_procesamiento_miliseg);

                out.println(response);

                clientSocket.close();

                logger.info("Cerrando conexion con el cliente");

            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "ERROR", e);
            kresponse.setEstado(10);
            kresponse.setMensaje(e.getMessage());
            kresponse.setDato(e.getMessage());
            String response = kresponse.toJson();
            out.println(response);
            e.printStackTrace();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "ERROR", e);
            kresponse.setEstado(10);
            kresponse.setMensaje(e.getMessage());
            kresponse.setDato(e.getMessage());
            String response = kresponse.toJson();
            out.println(response);
            e.printStackTrace();
        }

    }

    private static void iniciarServidorUDP(int puertoServidor, SuministroRepository suministroRepository) {
        BaseResponse response = new BaseResponse();
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        DatagramSocket serverSocket = null;
        InetAddress IPAddress = null;
        Integer port = null;
        DatagramPacket sendPacket = null;
        try {
            serverSocket = new DatagramSocket(puertoServidor);
            logger.info("Servidor escuchando en puerto: " + puertoServidor);

            while (true) {
                receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                logger.info("Esperando peticion de algun NIS... ");
                serverSocket.receive(receivePacket);

                String request = new String(receivePacket.getData()).trim();
                BaseDto baseDto = BaseDto.fromJson(request);

                logger.info("________________________________________________");
                logger.info("Request del NIS: " + request);

                int tipoOperacion = baseDto.getTipoOperacion();

                IPAddress = receivePacket.getAddress();
                port = receivePacket.getPort();

                String nis = baseDto.getNis();
                switch (tipoOperacion) {
                    // Registrar Consumo
                    case 1:

                        double consumo = baseDto.getConsumo();
                        Suministro suministro = suministroRepository.registrarConsumo(nis, consumo);

                        response.setEstado(0);
                        response.setMensaje("OK");
                        response.setTipoOperacion(1);

                        if (suministro == null) {
                            logger.warning("El suministro no existe");
                            response.setDato("No se pudo registrar el consumo, no existe el suministro: " + nis);

                        } else {
                            response.setDato(suministro);
                        }
                        sendData = response.toJson().getBytes();

                        sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

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
                        response.setEstado(0);
                        response.setMensaje("OK");
                        response.setTipoOperacion(3);
                        if (suministro1 == null) {
                            logger.info("El suministro no existe");
                            response.setDato(
                                    "No se pudo enviar la orden de desconexion, no existe el suministro: " + nis);
                        } else {
                            response.setDato(suministro1);
                        }
                        sendData = response.toJson().getBytes();

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

                sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

                serverSocket.send(sendPacket);

            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "ERROR", e);
            response.setEstado(10);
            response.setMensaje(e.getMessage());
            response.setDato(e.getMessage());
            String responsee = response.toJson();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

            e.printStackTrace();
        } 

    }

    private static Map<String, Suministro> initDB() {
        Map<String, Suministro> bdLocal = new HashMap<>();
        bdLocal.put("1", new Suministro("1", "Juan Pereria", 1234.56, Estado.ACTIVO));
        bdLocal.put("2", new Suministro("2", "Juan Perez", 1234.5, Estado.ACTIVO));
        bdLocal.put("3", new Suministro("3", "Maria Ramirez", 10000, Estado.ACTIVO));
        bdLocal.put("4", new Suministro("4", "Juan Sosa", 1234.5, Estado.ACTIVO));
        bdLocal.put("5", new Suministro("5", "Mario Gomez", 1234.5, Estado.ACTIVO));
        bdLocal.put("6", new Suministro("6", "Juan Torres Brizuela", 12671, Estado.ACTIVO));
        bdLocal.put("7", new Suministro("7", "Mariano Lopez", 1234.5, Estado.ACTIVO));
        bdLocal.put("8",
                new Suministro("8", "Enrique Gimenez", 1234.5, Estado.ACTIVO));
        bdLocal.put("9", new Suministro("9", "Matias Lopez", 1234.5, Estado.ACTIVO));
        bdLocal.put("10", new Suministro("10", "Matias Perez", 1234.5, Estado.ACTIVO));
        bdLocal.put("11",
                new Suministro("11", "Matias Pedroso", 1234.5, Estado.ACTIVO));
        bdLocal.put("12", new Suministro("12", "Alejendro Sanz", 1234.5, Estado.ACTIVO));
        bdLocal.put("13", new Suministro("13", "Enrique Iglesias", 1234.5, Estado.ACTIVO));
        bdLocal.put("14", new Suministro("14", "Arturo Suarez", 1234.5, Estado.ACTIVO));
        bdLocal.put("15",
                new Suministro("15", "Geronimo Torres", 1234.5, Estado.INACTIVO));
        bdLocal.put("16",
                new Suministro("16", "Juan Troche", 1234.5, Estado.ACTIVO));
        bdLocal.put("17",
                new Suministro("17", "Maria Castillo", 1234.5, Estado.INACTIVO));

        return bdLocal;
    }
}

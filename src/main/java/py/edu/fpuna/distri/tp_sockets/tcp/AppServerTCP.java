package py.edu.fpuna.distri.tp_sockets.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import py.edu.fpuna.distri.tp_sockets.data.mappers.EnviarOrdenDataResponse;
import py.edu.fpuna.distri.tp_sockets.data.mappers.EnviarOrdenResponse;
import py.edu.fpuna.distri.tp_sockets.data.mappers.ListarSuministroResponse;
import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoDataResponse;
import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoDto;
import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoResponse;
import py.edu.fpuna.distri.tp_sockets.data.repositories.MockSuministroRepository;
import py.edu.fpuna.distri.tp_sockets.domain.entities.Estado;
import py.edu.fpuna.distri.tp_sockets.domain.entities.Suministro;
import py.edu.fpuna.distri.tp_sockets.domain.repositories.SuministroRepository;
import py.edu.fpuna.distri.tp_sockets.utils.KResponse;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppServerTCP {

    private static Logger logger = Logger.getLogger(String.valueOf(AppServerTCP.class));
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

    public static void main(String[] args) {
        Map<String, Suministro> bdLocal = initDB();
        SuministroRepository suministroRepository = new MockSuministroRepository(bdLocal);
        final int puertoServidor = 9876;
        logger.addHandler(fh);
        KResponse kresponse = new KResponse();

        int tiempo_procesamiento_miliseg = 2000;
        if (args.length > 0) {
            tiempo_procesamiento_miliseg = Integer.parseInt(args[0]);
        }

        try {
            Socket clientSocket = null;
            ServerSocket serverSocket = new ServerSocket(puertoServidor);
            logger.info("Servidor escuchando en puerto: " + puertoServidor);

            while (true) {
                logger.info("Esperando peticion de algun NIS... ");
                clientSocket = serverSocket.accept();

                System.out
                        .println("Conexion establecida con el NIS de: "
                                + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort());

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String request = in.readLine();
                String response;

                logger.info("________________________________________________");
                logger.info("Request del NIS: " + request);

                RegistrarConsumoDto registrarConsumoDto = RegistrarConsumoDto.fromJson(request);

                String nis = registrarConsumoDto.getNis();

                int tipoOperacion = registrarConsumoDto.getTipoOperacion();

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
            e.printStackTrace();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "ERROR", e);
            kresponse.setEstado(10);
            kresponse.setMensaje(e.getMessage());
            kresponse.setDato(e.getMessage());
            e.printStackTrace();
        }

    }

}

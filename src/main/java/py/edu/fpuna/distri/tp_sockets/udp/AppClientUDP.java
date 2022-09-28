package py.edu.fpuna.distri.tp_sockets.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import py.edu.fpuna.distri.tp_sockets.data.mappers.ListarSuministroDto;
import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoDto;
import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoResponse;

public class AppClientUDP {
    public static void main(String[] args) {
        // Datos necesario
        String direccionServidor = "127.0.0.1";

        if (args.length > 0) {
            direccionServidor = args[0];
        }

        int puertoServidor = 9876;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(direccionServidor);
            System.out.println("Intentando conectar a = " + IPAddress + ":" + puertoServidor + " via UDP...");

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            System.out.println("1. Registrar Consumo");
            System.out.println("2. Verificar Conectividad");
            System.out.println("3. Enviar Orden de Desconexion");
            System.out.println("4. Enviar Orden de Desconexion");
            System.out.println("5. Listar Suministros Activos");
            System.out.println("5. Listar Suministros Inactivos");
            System.out.print("Ingrese el tipo de operacion (debe ser numérico entre 1 al 6): ");
            System.out.println();

            String tipoOperacion = inFromUser.readLine();
            int parseIdOperacion = Integer.parseInt(tipoOperacion);
            String jsonDto = null;

            if (parseIdOperacion == 1) {
                System.out.print("Ingrese su NIS: ");
                String nis = inFromUser.readLine();
                System.out.println();

                System.out.println("Ingrese su consumo: ");
                String consumo = inFromUser.readLine();
                System.out.println();

                double parsedConsumo = Double.parseDouble(consumo);
                RegistrarConsumoDto registrarConsumoDto = new RegistrarConsumoDto(parseIdOperacion, nis, parsedConsumo);

                jsonDto = registrarConsumoDto.toJson();
            } else if (parseIdOperacion == 5) {

                ListarSuministroDto listarSuministroDto = new ListarSuministroDto(parseIdOperacion);

                jsonDto = listarSuministroDto.toJson();

            } else if (parseIdOperacion == 6) {

                ListarSuministroDto listarSuministroDto = new ListarSuministroDto(parseIdOperacion);

                jsonDto = listarSuministroDto.toJson();

            }

            sendData = jsonDto.getBytes();

            System.out.println("Enviando datos al servidor = " + IPAddress + ":" + puertoServidor + " via UDP...");

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            System.out
                    .println("Esperando respuesta del servidor = " + IPAddress + ":" + puertoServidor + " via UDP...");

            clientSocket.setSoTimeout(10000);

            try {
                clientSocket.receive(receivePacket);

                String respuesta = new String(receivePacket.getData()).trim();

                System.out.println(
                        "Respuesta del servidor = " + IPAddress + ":" + puertoServidor + " via UDP... " + respuesta);

                if (parseIdOperacion == 1) {
                    RegistrarConsumoResponse response = RegistrarConsumoResponse.fromJson(respuesta);
                    if (response.getData() == null) {
                        System.out.println("No se pudo registrar el consumo, no existe el suministro");
                    } else {
                        System.out.println("Consumo registrado con exito");
                    }
                } else if (parseIdOperacion == 2) {
                    System.out.println("Verificacion de conectividad realizada con exito");
                } else if (parseIdOperacion == 3) {
                    System.out.println("Orden de desconexion enviada con exito");
                } else if (parseIdOperacion == 4) {
                    System.out.println("Orden de conexion enviada con exito");
                } else if (parseIdOperacion == 5) {
                    System.out.println("Listado de suministros activos");
                } else if (parseIdOperacion == 6) {
                    System.out.println("Listado de suministros inactivos");
                }
            } catch (SocketTimeoutException ste) {
                System.out.println("No se recibió respuesta del servidor = " + IPAddress + ":" + puertoServidor
                        + " via UDP...");
            }
            clientSocket.close();

        } catch (SocketException e) {

            e.printStackTrace();
        } catch (UnknownHostException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }

    }
}

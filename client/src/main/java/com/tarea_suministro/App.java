package com.tarea_suministro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.tarea_suministro.data.mappers.BaseDto;
import com.tarea_suministro.utils.UIConsole;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.exit(-1);
        }

        String protocolo = args[0];

        // Check if protocolo is valid
        if (!protocolo.equals("tcp") && !protocolo.equals("udp")) {
            System.exit(-2);
        }

        int puertoServidor = 9876;

    }

    private static void iniciarClienteUDP() {
        String direccionServidor = "127.0.0.1";

        int puertoServidor = 9876;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        UIConsole uiConsole = new UIConsole();

        try {
            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(direccionServidor);

            uiConsole.connecting(IPAddress, puertoServidor, "UDP");

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            uiConsole.insertOperation("UDP");

            int tipoOperacion = Integer.parseInt(inFromUser.readLine());

            BaseDto baseDto = new BaseDto();

            switch (tipoOperacion) {
                case 1:
                    uiConsole.insertNis();
                    String nis = inFromUser.readLine();
                    uiConsole.insertConsumo();
                    String consumo = inFromUser.readLine();
                    baseDto.setNis(nis);
                    baseDto.setTipoOperacion(tipoOperacion);
                    baseDto.setConsumo(Double.parseDouble(consumo));
                    break;
                case 2:
                    uiConsole.insertNis();
                    String niss = inFromUser.readLine();
                    baseDto.setNis(niss);
                    baseDto.setTipoOperacion(tipoOperacion);
                    break;
                case 3:
                    uiConsole.insertNis();
                    String nisss = inFromUser.readLine();
                    baseDto.setNis(nisss);
                    baseDto.setTipoOperacion(tipoOperacion);
                    break;
                default:
                    System.out.println("Operacion no valida");
                    System.exit(-1);
                    break;
            }

            sendData = baseDto.toJson().getBytes();

            uiConsole.sendInfo(IPAddress, puertoServidor, "UDP");

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, puertoServidor);

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            uiConsole.waitInfo(IPAddress, puertoServidor, "UDP");

            clientSocket.setSoTimeout(10000);

            try {
                clientSocket.receive(receivePacket);

                String respuesta = new String(receivePacket.getData()).trim();

                uiConsole.sendData(IPAddress, puertoServidor, "UDP", respuesta);

            } catch (SocketTimeoutException ste) {
                uiConsole.noResponse(IPAddress, puertoServidor, "UDP");
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

    private static void iniciarClienteTCP() {
        UIConsole uiConsole = new UIConsole();
        String direccionServidor = "127.0.0.1";

        int puertoServidor = 9876;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        try {
            Socket mySocket = new Socket(direccionServidor, puertoServidor);

            InetAddress IPAddress = InetAddress.getByName(direccionServidor);

            uiConsole.connecting(IPAddress, puertoServidor, "TCP");

            PrintWriter out = new PrintWriter(mySocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

            uiConsole.insertOperation("TCP");

            int tipoOperacion = Integer.parseInt(inFromUser.readLine());

            BaseDto baseDto = new BaseDto();

            switch (tipoOperacion) {
                case 4:
                    uiConsole.insertNis();
                    String nis = inFromUser.readLine();
                    baseDto.setNis(nis);
                    baseDto.setTipoOperacion(tipoOperacion);
                    break;
                case 5:
                    baseDto.setTipoOperacion(tipoOperacion);
                    break;
                case 6:
                    baseDto.setTipoOperacion(tipoOperacion);
                    break;
                default:
                    System.out.println("Operacion no valida");
                    break;
            }

            uiConsole.sendInfo(IPAddress, puertoServidor, "TCP");

            out.println(baseDto.toJson());

            uiConsole.waitInfo(IPAddress, puertoServidor, "TCP");

            String respuesta = in.readLine();

            uiConsole.sendData(IPAddress, puertoServidor, "TCP", respuesta);

            out.close();

            in.close();

            mySocket.close();

        } catch (UnknownHostException e) {
            System.err.println("Host desconocido");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error de I/O en la conexion al host");
            System.exit(1);
        }
    }
}

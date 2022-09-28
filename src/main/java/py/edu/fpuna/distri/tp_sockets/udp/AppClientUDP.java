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

import py.edu.fpuna.distri.tp_sockets.data.mappers.EnviarOrdenResponse;
import py.edu.fpuna.distri.tp_sockets.data.mappers.ListarSuministroResponse;
import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoResponse;
import py.edu.fpuna.distri.tp_sockets.utils.ClientOperacion;
import py.edu.fpuna.distri.tp_sockets.utils.EnviarOrdenClientStrategy;
import py.edu.fpuna.distri.tp_sockets.utils.ListarSuministroClientStrategy;
import py.edu.fpuna.distri.tp_sockets.utils.RegistrarConsumoClientStrategy;
import py.edu.fpuna.distri.tp_sockets.utils.TipoOperacionClientStrategy;
import py.edu.fpuna.distri.tp_sockets.utils.UIConsole;

public class AppClientUDP {
    public static void main(String[] args) {
        // Datos necesario
        String direccionServidor = "127.0.0.1";

        if (args.length > 0) {
            direccionServidor = args[0];
        }

        int puertoServidor = 9876;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        UIConsole uiConsole = new UIConsole();

        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(direccionServidor);
            uiConsole.connecting(IPAddress, puertoServidor, "UDP");

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            uiConsole.insertOperation();

            String tipoOperacion = inFromUser.readLine();
            int parseIdOperacion = Integer.parseInt(tipoOperacion);

            ClientOperacion clientOperacion = new ClientOperacion(parseIdOperacion);

            TipoOperacionClientStrategy tipoOperacionClientStrategy = getStrategy(parseIdOperacion);

            String jsonDto = clientOperacion.getDto(tipoOperacionClientStrategy);

            sendData = jsonDto.getBytes();

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

                clientOperacion.getRespuesta(tipoOperacionClientStrategy, respuesta);

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

    private static TipoOperacionClientStrategy getStrategy(int parseIdOperacion) throws IOException {

        TipoOperacionClientStrategy strategy = null;

        if (parseIdOperacion == 1) {
            strategy = new RegistrarConsumoClientStrategy();
        } else if (parseIdOperacion == 4) {
            strategy = new EnviarOrdenClientStrategy();
        } else if (parseIdOperacion == 5) {
            strategy = new ListarSuministroClientStrategy();
        } else if (parseIdOperacion == 6) {
            strategy = new ListarSuministroClientStrategy();
        }

        return strategy;
    }
}

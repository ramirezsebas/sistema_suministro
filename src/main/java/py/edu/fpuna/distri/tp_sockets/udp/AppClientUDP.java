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

            String jsonDto = getDto(parseIdOperacion);

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
                    EnviarOrdenResponse response = EnviarOrdenResponse.fromJson(respuesta);
                    if (response.getData() == null) {
                        System.out.println(
                                "No se pudo enviar la orden, no existe el suministro o el sumninistro no tiene desconexion");
                    } else {
                        System.out.println("Orden enviada con exito");
                    }
                } else if (parseIdOperacion == 4) {
                    EnviarOrdenResponse response = EnviarOrdenResponse.fromJson(respuesta);
                    if (response.getData() == null) {
                        System.out.println(
                                "No se pudo enviar la orden, no existe el suministro o el sumninistro no tiene conexion");
                    } else {
                        System.out.println("Orden enviada con exito");
                    }
                } else if (parseIdOperacion == 5) {
                    ListarSuministroResponse response = ListarSuministroResponse.fromJson(respuesta);
                    if (response.getData().size() == 0) {
                        System.out.println("No se encontraron suministros activos");
                    } else {
                        System.out.println("Listado de suministros activos");
                    }

                } else if (parseIdOperacion == 6) {
                    ListarSuministroResponse response = ListarSuministroResponse.fromJson(respuesta);
                    if (response.getData().size() == 0) {
                        System.out.println("No se encontraron suministros inactivos");
                    } else {
                        System.out.println("Listado de suministros inactivos");
                    }
                }
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

    private static String getDto(int parseIdOperacion) throws IOException {
        ClientOperacion clientOperacion = new ClientOperacion(parseIdOperacion);
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

        String jsonDto = clientOperacion.getDto(strategy);
        return jsonDto;
    }
}

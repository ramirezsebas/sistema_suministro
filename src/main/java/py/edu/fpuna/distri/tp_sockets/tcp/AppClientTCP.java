package py.edu.fpuna.distri.tp_sockets.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import py.edu.fpuna.distri.tp_sockets.utils.ClientOperacion;
import py.edu.fpuna.distri.tp_sockets.utils.EnviarOrdenClientStrategy;
import py.edu.fpuna.distri.tp_sockets.utils.ListarSuministroClientStrategy;
import py.edu.fpuna.distri.tp_sockets.utils.RegistrarConsumoClientStrategy;
import py.edu.fpuna.distri.tp_sockets.utils.TipoOperacionClientStrategy;
import py.edu.fpuna.distri.tp_sockets.utils.UIConsole;

public class AppClientTCP {
    public static void main(String[] args) {
        String direccionServidor = "127.0.0.1";

        if (args.length > 0) {
            direccionServidor = args[0];
        }

        int puertoServidor = 9876;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        UIConsole uiConsole = new UIConsole();

        try {
            Socket mySocket = new Socket(direccionServidor, puertoServidor);

            InetAddress IPAddress = InetAddress.getByName(direccionServidor);

            uiConsole.connecting(IPAddress, puertoServidor, "TCP");

            PrintWriter out = new PrintWriter(mySocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

            uiConsole.insertOperation();

            int tipoOperacion = Integer.parseInt(inFromUser.readLine());

            ClientOperacion clientOperacion = new ClientOperacion(tipoOperacion);

            TipoOperacionClientStrategy tipoOperacionClientStrategy = getStrategy(tipoOperacion);

            String jsonDto = clientOperacion.getDto(tipoOperacionClientStrategy);

            uiConsole.sendInfo(IPAddress, puertoServidor, "TCP");

            out.println(jsonDto);

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

    private static TipoOperacionClientStrategy getStrategy(int tipoOperacion) throws IOException {

        TipoOperacionClientStrategy strategy = null;

        if (tipoOperacion == 1) {
            strategy = new RegistrarConsumoClientStrategy();
        } else if (tipoOperacion == 4) {
            strategy = new EnviarOrdenClientStrategy();
        } else if (tipoOperacion == 5) {
            strategy = new ListarSuministroClientStrategy();
        } else if (tipoOperacion == 6) {
            strategy = new ListarSuministroClientStrategy();
        }

        return strategy;
    }
}

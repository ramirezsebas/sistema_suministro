package py.edu.fpuna.distri.tp_sockets.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        Socket unSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        UIConsole uiConsole = new UIConsole();

        try {
            unSocket = new Socket("127.0.0.1", 9876);
            // enviamos nosotros
            out = new PrintWriter(unSocket.getOutputStream(), true);

            // viene del servidor
            in = new BufferedReader(new InputStreamReader(unSocket.getInputStream()));

            uiConsole.insertOperation();

            String tipoOperacion = inFromUser.readLine();
            int parseIdOperacion = Integer.parseInt(tipoOperacion);

            String jsonDto = getDto(parseIdOperacion);

            // enviamos un mensaje al servidor
            out.println(jsonDto);

            // leemos la respuesta del servidor
            String respuesta = in.readLine();

            System.out.println("Respuesta del servidor: " + respuesta);

            // cerramos la conexion
            out.close();

            in.close();

            unSocket.close();

        } catch (UnknownHostException e) {
            System.err.println("Host desconocido");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error de I/O en la conexion al host");
            System.exit(1);
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

package py.edu.fpuna.distri.tp_sockets.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import py.edu.fpuna.distri.tp_sockets.data.mappers.EnviarOrdenDto;
import py.edu.fpuna.distri.tp_sockets.data.mappers.ListarSuministroDto;
import py.edu.fpuna.distri.tp_sockets.data.mappers.RegistrarConsumoDto;

public class AppClientTCP {
    public static void main(String[] args) {
        Socket unSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        try {
            unSocket = new Socket("127.0.0.1", 9876);
            // enviamos nosotros
            out = new PrintWriter(unSocket.getOutputStream(), true);

            // viene del servidor
            in = new BufferedReader(new InputStreamReader(unSocket.getInputStream()));

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

            } else if (parseIdOperacion == 4) {
                System.out.print("Ingrese su NIS: ");
                String nis = inFromUser.readLine();
                System.out.println();

                EnviarOrdenDto enviarOrdenDto = new EnviarOrdenDto(parseIdOperacion, nis);

                jsonDto = enviarOrdenDto.toJson();
            } else if (parseIdOperacion == 5) {

                ListarSuministroDto listarSuministroDto = new ListarSuministroDto(parseIdOperacion);

                jsonDto = listarSuministroDto.toJson();

            } else if (parseIdOperacion == 6) {

                ListarSuministroDto listarSuministroDto = new ListarSuministroDto(parseIdOperacion);

                jsonDto = listarSuministroDto.toJson();

            }

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
}

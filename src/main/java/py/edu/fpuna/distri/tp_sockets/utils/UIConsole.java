package py.edu.fpuna.distri.tp_sockets.utils;

import java.net.InetAddress;

public class UIConsole {
    public void insertOperation() {
        System.out.println("1. Registrar Consumo");
        System.out.println("2. Verificar Conectividad");
        System.out.println("3. Enviar Orden de Desconexion");
        System.out.println("4. Enviar Orden de Desconexion");
        System.out.println("5. Listar Suministros Activos");
        System.out.println("6. Listar Suministros Inactivos");
        System.out.print("Ingrese el tipo de operacion (debe ser numérico entre 1 al 6): ");
        System.out.println();
    }

    public void insertNis() {
        System.out.print("Ingrese su NIS: ");
        System.out.println();
    }

    public void insertConsumo() {
        System.out.println("Ingrese su consumo: ");
        System.out.println();
    }

    public void sendInfo(InetAddress IPAddress, int puertoServidor, String protocolo) {
        System.out.println("Enviando datos al servidor = " + IPAddress + ":" + puertoServidor + " via "
                + protocolo.toUpperCase() + "...");

    }

    public void sendData(InetAddress IPAddress, int puertoServidor, String protocolo, String respuesta) {
        System.out.println("Respuesta del servidor = " + IPAddress + ":" + puertoServidor + " via "
                + protocolo.toUpperCase() + "... " + respuesta);

    }

    public void waitInfo(InetAddress IPAddress, int puertoServidor, String protocolo) {
        System.out.println("Esperando respuesta del servidor = " + IPAddress + ":" + puertoServidor + " via "
                + protocolo.toUpperCase() + "...");

    }

    public void noResponse(InetAddress IPAddress, int puertoServidor, String protocolo) {
        System.out.println("No se recibió respuesta del servidor = " + IPAddress + ":" + puertoServidor + " via "
                + protocolo.toUpperCase() + "...");

    }

    public void connecting(InetAddress IPAddress, int puertoServidor, String protocolo) {
        System.out.println("Intentando conectar a = " + IPAddress + ":" + puertoServidor + " via "
                + protocolo.toUpperCase() + "...");
    }

}

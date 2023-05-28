package Practicas.Practica5;

import java.io.*;
import java.net.*;

class ServerTCP {
    public static String extract_text(String s) {
        String res = "";
        int salto = Character.getNumericValue(s.charAt(0));
        for (int i = 1; i < s.length(); i += salto + 1)
            res += s.charAt(i);
        return res;
    }

    public static void main(String[] args) throws IOException {
        // DATOS DEL SERVIDOR
        // * FIJO: Si se lee de línea de comando debe comentarse
        int port = 12345; // puerto del servidor
        // * VARIABLE: Si se lee de línea de comando debe descomentarse
        // int port = Integer.parseInt(args[0]);

        // SOCKETS
        ServerSocket server = null; // Pasivo (recepción de peticiones)
        try {
            server = new ServerSocket(port, 1);
        } catch (Exception e) {
            System.out.println("Error: could not listen to port " + port);
            System.exit(-1);
        }

        Socket client = null; // Activo (atención al cliente)

        // FLUJOS PARA EL ENVÍO Y RECEPCIÓN
        BufferedReader in = null;
        PrintWriter out = null;

        // * COMPLETAR: Crear e inicalizar el socket del servidor (socket pasivo)

        while (true) // Bucle de recepción de conexiones entrantes
        {
            System.out.println("STATUS: Waiting for clients");
            // * COMPLETAR: Esperar conexiones entrantes
            try {
                client = server.accept();
            } catch (IOException e) {
                System.out.println("Accept failed: " + port);
                System.exit(-1);
            }
            // * COMPLETAR: Una vez aceptada una conexion, inicializar flujos de
            // entrada/salida del socket conectado
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            } catch (Exception e) {
                System.err.println("Couldn't get I/O for " + client.getRemoteSocketAddress());
                System.exit(-1);
            }

            // getRemoteSocketAddress -> direccion IP del cliente
            System.out.println("STATUS: Client connected from: " + client.getRemoteSocketAddress());

            boolean salir = false;
            while (!salir) // Inicio bucle del servicio de un cliente
            {
                // * COMPLETAR: Recibir texto en line enviado por el cliente a través del flujo
                // de entrada del socket conectado

                try {
                    String line = in.readLine();

                    System.out.println("STATUS: Received from client " + line);

                    // * COMPLETAR: Comprueba si es fin de conexion - SUSTITUIR POR LA CADENA DE FIN
                    // enunciado
                    if (line.compareTo("FINISH") != 0) {
                        line = extract_text(line);

                        out.println(line);
                        System.out.println("STATUS: Sending to client " + line);

                        // * COMPLETAR: Enviar texto al cliente a traves del flujo de salida del socket
                        // conectado
                    } else { // El cliente quiere cerrar conexión
                        out.println("OK");
                        salir = true;
                    }
                } catch (IOException e) {
                    System.out.println("ERROR: recepcion / envio" + e.getMessage());
                    salir = true;
                }

            } // fin del servicio

            // * COMPLETAR: Cerrar flujos y socket
            client.close();

        } // fin del bucle
    } // fin del metodo
}

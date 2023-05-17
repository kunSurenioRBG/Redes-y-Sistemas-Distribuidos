
/**
 *
 * @author <tu nombre aqui>
 */
import java.io.*;
import java.net.*;

public class ClientTCP {

    public static void main(String[] args) throws IOException {
        // DATOS DEL SERVIDOR:
        // * FIJOS: coméntelos si los lee de la línea de comandos
         String serverName = "127.0.0.1"; // direccion local
         int serverPort = 12345;
        // * VARIABLES: descoméntelos si los lee de la línea de comandos
        //String serverName = args[0];
        //int serverPort = Integer.parseInt(args[1]);

        // SOCKET
        Socket serviceSocket = null;

        // FLUJOS PARA EL ENVÍO Y RECEPCIÓN
        PrintWriter out = null;
        BufferedReader in = null;

        // * COMPLETAR: Crear socket y conectar con servidor
        try {
            serviceSocket = new Socket(serverName, serverPort);
        } catch (Exception e) {
            System.out.println("Error: cannot connect to " + serverName + ":" + serverPort);
            System.exit(1);
        }
        // * COMPLETAR: Inicializar los flujos de entrada/salida del socket conectado en
        // las variables PrintWriter y BufferedReader
        try {
            out = new PrintWriter(serviceSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
        } catch (Exception e) {
            System.err.println("Couldn't get I/O for " + serverName);
            System.exit(1);
        }

        System.out.println("STATUS: Conectando al servidor ;)");

        // Obtener texto por teclado
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println("Introduzca un texto a enviar (para acabar introduzca un texto sin dígito inicial)");
        userInput = stdIn.readLine();

        // * COMPLETAR: Comprobar si el usuario ha iniciado el fin de la interacción
        while (Character.isDigit(userInput.charAt(0))) { // bucle del servicio
            // * COMPLETAR: Enviar texto en userInput al servidor a través del flujo de
            // salida del socket conectado
            out.println(userInput);

            System.out.println("STATUS: Enviando " + userInput);
            System.out.println("STATUS: Esperando la respuesta");
            // * COMPLETAR: Recibir texto enviado por el servidor a través del flujo de
            // entrada del socket conectado
            String line = null;
            line = in.readLine();
            System.out.println("Respuesta recibida " + line);

            // Leer texto de usuario por teclado
            System.out.println("Introduzca un texto a enviar (para acabar introduzca un texto sin dígito inicial)");
            userInput = stdIn.readLine();
        } // Fin del bucle de servicio en cliente

        // Salimos porque el cliente quiere terminar la interaccion, ha introducido TERMINAR
        // * COMPLETAR: Enviar FINISH al servidor para indicar el fin deL Servicio
        out.print("FINISH");
        // * COMPLETAR: Recibir el OK del Servidor
        String ok = in.readLine();
        System.out.println("STATUS: Closing...");
        // * COMPLETAR Cerrar flujos y socket
        out.close();
        in.close();
        stdIn.close();
        serviceSocket.close();
        System.out.println("Closed");

    }
}

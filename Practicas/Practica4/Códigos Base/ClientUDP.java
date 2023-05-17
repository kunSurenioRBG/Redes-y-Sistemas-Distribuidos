import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 *
 * @author Santiago Ponce Arrocha :P
 */

public class ClientUDP {
    public static void main(String[] args) throws IOException {
        // DATOS DEL SERVIDOR:
        //* FIJOS: coméntelos si los lee de la línea de comandos
        String serverName = "127.0.0.1"; //direccion local
        int serverPort = 54322;
        //* VARIABLES: descoméntelos si los lee de la línea de comandos
        //String serverName = args[0];
        //int serverPort = Integer.parseInt(args[1]);

        DatagramSocket clientSocket = new DatagramSocket(); // los puertos del cliente pueden variar

        // INICIALIZA ENTRADA POR TECLADO
        BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in) );
        String userInput;
        System.out.println("Introduzca un texto a enviar que empiece con dígito (sin dígito inicial para acabar): ");
        userInput = stdIn.readLine(); /*CADENA ALMACENADA EN userInput*/

        //* COMPLETAR: Comprobar si el usuario quiere terminar servicio
        while (Character.isDigit(userInput.charAt(0)))
        {
            //* COMPLETAR: Crear datagrama con la cadena escrito en el cuerpo
            DatagramPacket solicitud = new DatagramPacket(userInput.getBytes(), userInput.getBytes().length, InetAddress.getByName(serverName), serverPort);
            //* COMPLETAR: Enviar datagrama a traves del socket
            clientSocket.send(solicitud);
            System.out.println("STATUS: Waiting for the reply");

            //* COMPLETAR: Crear e inicializar un datagrama VACIO para recibir la respuesta de máximo 800 bytes
            byte [] buffer = new byte[800];
            DatagramPacket respuesta = new DatagramPacket(buffer, 800);
            //* COMPLETAR: Recibir datagrama de respuesta
            clientSocket.receive(respuesta);
            //* COMPLETAR: Extraer contenido del cuerpo del datagrama en variable line
            String line = new String(respuesta.getData(), respuesta.getOffset(), respuesta.getLength(), "UTF-8");
            //String line = new String(respuesta.getData(), respuesta.getOffset(), respuesta.getLength(), StandardCharsets.UTF_8);

            System.out.println("echo: " + line);
            System.out.println("Introduzca un texto a enviar que empiece con dígito (sin dígito inicial para acabar): ");
            userInput = stdIn.readLine();
        }

        System.out.println("STATUS: Closing client");

        //* COMPLETAR Cerrar socket cliente
        clientSocket.close();

        System.out.println("STATUS: closed");
    }
}

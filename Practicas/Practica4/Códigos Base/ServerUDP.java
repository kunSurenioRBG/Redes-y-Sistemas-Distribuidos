import java.io.IOException;
import java.net.*;

/**
 *
 * @author Santiago Ponce Arrocha :)
 */
public class ServerUDP {
    public static String extraerTexto(String texto) {
        String resultado = "";
        int salto = Character.getNumericValue(texto.charAt(0));

        for (int i = 1; i < texto.length(); i += salto + 1) {
            resultado += texto.charAt(i);
        }

        return resultado;
    }

    public static void main(String[] args) throws IOException { // IOException para tratar la excepcion de "receive"
        // DATOS DEL SERVIDOR
        // * FIJO: Si se lee de línea de comando debe comentarse
        int port = 54322; // puerto del servidor
        // * VARIABLE: Si se lee de línea de comando debe descomentarse
        // int port = Integer.parseInt(args[0]); // puerto del servidor

        try (// SOCKET
        DatagramSocket server = new DatagramSocket(port)) {
            // Funcion PRINCIPAL del servidor
            while (true) {
                // * COMPLETAR: Crear e inicializar un datagrama VACIO para recibir la respuesta
                // de máximo 800 bytes
                byte[] buffer = new byte[800];
                DatagramPacket recibido = new DatagramPacket(buffer, 800);
                // * COMPLETAR: Recibir datagrama
                server.receive(recibido);
                // * COMPLETAR: Obtener texto recibido
                String line = new String(recibido.getData(), recibido.getOffset(), recibido.getLength(), "UTF-8");

                // * COMPLETAR: Mostrar por pantalla la direccion socket (IP y puerto) del
                // cliente y su texto
                System.out.println("Direccion socket: " + recibido.getAddress() + ", Puerto: " + recibido.getPort());

                // Modificamos la linea en "line"
                line = extraerTexto(line);

                // * COMPLETAR: crear datagrama de respuesta
                DatagramPacket respuesta = new DatagramPacket(line.getBytes(), line.length(), recibido.getAddress(),
                        recibido.getPort());
                // * COMPLETAR: Enviar datagrama de respuesta
                server.send(respuesta);
            } // Fin del bucle del servicio
        }
    }
}

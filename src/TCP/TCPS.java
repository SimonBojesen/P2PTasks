package TCP;

import java.io.*;
import java.net.*;

public class TCPS {
    public static final int PORT = 6666;
    public static ServerSocket serverSocket = null; // Server gets found
    public static Socket openSocket = null;         // Server communicates with the client

    public static Socket configureServer() throws UnknownHostException, IOException
    {
        // get server's own IP address
        String serverIP = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Server ip: " + serverIP);

        // create a socket at the predefined port
        serverSocket = new ServerSocket(PORT);

        return openSocket;
    }

    public static void main(String[] args) throws IOException
    {
        try
        {
            openSocket = configureServer();
            int counter = 0;

            while(true){
                counter++;
                // Start listening and accepting requests on the serverSocket port, blocked until a request arrives
                openSocket = serverSocket.accept();
                System.out.println("Server accepts requests at: " + openSocket);

                System.out.println(" >> " + "Client No:" + counter + " started!");

                TCPServerClientThread sct = new TCPServerClientThread(openSocket, counter);
                sct.start();
            }


        }
        catch(Exception e)
        {
            System.out.println(" Connection fails: " + e);
        }
        finally
        {
            openSocket.close();
            System.out.println("Connection to client closed");

            serverSocket.close();
            System.out.println("Server port closed");
        }

    }
}

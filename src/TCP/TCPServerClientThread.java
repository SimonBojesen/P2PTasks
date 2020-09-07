package TCP;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPServerClientThread extends Thread{

    Socket serverClient;
    int clientNo;

    public TCPServerClientThread(Socket openSocket, int counter) {
        serverClient = openSocket;
        clientNo = counter;
    }

    public void run(){
        try{
            String request, response;

            // two I/O streams attached to the server socket:
            Scanner in;         // Scanner is the incoming stream (requests from a client)
            PrintWriter out;    // PrintWriter is the outcoming stream (the response of the server)
            in = new Scanner(serverClient.getInputStream());
            out = new PrintWriter(serverClient.getOutputStream(),true);
            // Parameter true ensures automatic flushing of the output buffer

            // Server keeps listening for request and reading data from the Client,
            // until the Client sends "stop" requests
            while(in.hasNextLine())
            {
                request = in.nextLine();

                if(request.equals("stop"))
                {
                    out.println("Good bye, client number " + clientNo);
                    System.out.println("Log: " + request + " client!" + clientNo + "!");
                    break;
                }
                else
                {
                    // server responses
                    response = new StringBuilder(request).reverse().toString();
                    out.println(response);
                    // Log response on the server's console, too
                    System.out.println("Log: " + response);
                }
            }
        } catch (Exception ex){
            System.out.println(ex);
        } finally {
            System.out.println("Client" + clientNo + " exit!! ");
            try {
                serverClient.close();
            } catch (Exception ex) {
                System.out.println("Failed to close Socket.");
            }
        }
    }
}

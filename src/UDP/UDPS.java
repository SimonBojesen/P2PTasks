package UDP;

import java.io.*;
import java.net.*;

public class UDPS {
    private static final int serverPort = 7777;

    // buffers for the messages
    public static String imgDirPath = "C:\\Users\\simon\\IdeaProjects\\whoisclient\\src\\UDP\\ServerImgs";
    private static byte[] dataIn = new byte[65507];
    private static byte[] dataOut = new byte[65507];

    // In UDP messages are encapsulated in packages and sent over sockets
    private static DatagramPacket requestPacket;
    private static DatagramPacket responsePacket;
    private static DatagramSocket serverSocket;

    public static void main(String[] args) throws Exception
    {
        byte[] dataIn;
        String messageOut;
        try
        {
            String serverIP = InetAddress.getLocalHost().getHostAddress();
            // Opens socket for accepting requests
            serverSocket = new DatagramSocket(serverPort);
            while(true)
            {
                System.out.println("Server " + serverIP + " running ...");
                dataIn = receiveRequest();
                messageOut = processRequest(dataIn);
                sendResponse(messageOut);
            }
        }
        catch(Exception e)
        {
            System.out.println(" Connection fails: " + e);
        }
        finally
        {
            serverSocket.close();
            System.out.println("Server port closed");
        }
    }

    public static byte[] receiveRequest() throws IOException
    {
        requestPacket = new DatagramPacket(dataIn, dataIn.length);
        serverSocket.receive(requestPacket);
        byte[] data = requestPacket.getData();
        System.out.println("Request received");
        return data;
    }

    public static String processRequest(byte[] data)
    {
        try{
            File dir = new File(imgDirPath);
            File[] files = dir.listFiles();
            int amount = files.length;
            ImgFunctionality imgF = new ImgFunctionality();
            imgF.ByteArrayToImg(data, imgDirPath, "output" + (amount+1) + ".jpg");
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        return "Image was successfully saved to the server";
    }

    public static void sendResponse(String message) throws IOException
    {
        InetAddress clientIP;
        int clientPort;

        clientIP = requestPacket.getAddress();
        clientPort = requestPacket.getPort();
        System.out.println("Client port: " + clientPort);
        System.out.println("Response: " + message);
        dataOut = message.getBytes();
        responsePacket = new DatagramPacket(dataOut, dataOut.length, clientIP, clientPort);
        serverSocket.send(responsePacket);
        System.out.println("Message sent back " + message);
    }
}

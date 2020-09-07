package UDP;

import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UDPC {
    // Client needs to know server identification, <IP:port>
    private static final int serverPort = 7777;

    // buffers for the messages
    public static String message;
    public static int fileNumber;
    public static String imgDirPath = "C:\\Users\\simon\\IdeaProjects\\whoisclient\\src\\UDP\\ClientImgs";
    private static byte[] dataIn = new byte[65507];
    private static byte[] dataOut = new byte[65507];

    // In UDP messages are encapsulated in packages and sent over sockets
    private static DatagramPacket requestPacket;
    private static DatagramPacket responsePacket;
    private static DatagramSocket clientSocket;

    public static void main(String[] args) throws IOException
    {
        clientSocket = new DatagramSocket();
        InetAddress serverIP = InetAddress.getByName(args[0]);
        System.out.println(serverIP);

        Scanner scan = new Scanner(System.in);
        try {
            // Sets up path.
            File dir = new File(imgDirPath);

            // instance of filter that removes files that not of a certain type
            // it also filters out files that are larger then the specified amount
            MyFileFilter mff = new MyFileFilter(".jpg", 65507);

            // Populates array with files using the filter created
            File[] files = dir.listFiles(mff);

            // List the filenames for the user to pick a file to send to server
            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getName() + " " + i);
            }

            System.out.println("Write the number after the image you want to send to connected server: ");

            try {
                fileNumber = scan.nextInt();
                sendRequest(serverIP, files[fileNumber]);
                receiveResponse();
            } catch(InputMismatchException e) {
                System.out.println("Not an integer, crashing down");
            }
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            clientSocket.close();
        }
    }

    public static void sendRequest(InetAddress serverIP, File image) throws IOException
    {
        ImgFunctionality imgF = new ImgFunctionality();
        //clientSocket = new DatagramSocket();
        dataOut = imgF.ImageToByteArray(image);
        requestPacket = new DatagramPacket(dataOut, dataOut.length, serverIP, serverPort);
        clientSocket.send(requestPacket);
    }

    public static void receiveResponse() throws IOException
    {
        //clientSocket = new DatagramSocket();
        responsePacket = new DatagramPacket(dataIn, dataIn.length);
        clientSocket.receive(responsePacket);
        String message = new String(responsePacket.getData(), 0, responsePacket.getLength());
        System.out.println("Response from Server: " + message);
    }
}

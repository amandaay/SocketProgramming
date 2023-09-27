// TCPServer.java reverse the string from Client
import java.net.*;
import java.io.*;

/**
 * This is a TCPServer class that expects using
 * javac TCPServer.java to compile
 * java TCPServer <PORT> to run
 * And exits after reversing and capitalizing one time.
 */
public class TCPServer {
    public static void main (String[] args) {
        if (args.length < 1) {
            System.out.println("TCP usage: java TCPServer <Port Number>");
            System.exit(1);
        }
        int ServerPort = Integer.parseInt(args[0]);
        try {
            System.out.println("Starting the server:");
            // Register service on port
            ServerSocket listenSocket = new ServerSocket(ServerPort);
            // Wait and accept a connection
            Socket clientSocket = listenSocket.accept();
            Connection c = new Connection(clientSocket);
            listenSocket.close();
        } catch (IOException e) {
            System.out.println("Listen: " + e.getMessage());
        }
    }
}
class Connection extends Thread {
    DataInputStream in ;
    DataOutputStream out;
    Socket clientSocket;
    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            // CreateI/O streams for communicating with the server.
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection: " + e.getMessage());
        }
    }
    public void run() {
        try { // an echo server
            String data = in.readUTF();
            // reverse and capitalize the string inputted from the client side
            String reverseCapitalize = ReversedString(data);
            // write it out to the client side
            out.writeUTF(reverseCapitalize);
        } catch (EOFException e) {
            System.out.println("EOF: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            try {
                System.out.println("Done reversing and capitalizing.");
                // exit client socket
                clientSocket.close();
            } catch (IOException e) {
                /* close failed */
            }
        }
    }

    private static String ReversedString(String data) {
        char [] charArr = data.toCharArray();
        StringBuilder ReversedStr = new StringBuilder();
        for (int i = charArr.length - 1; i > - 1; i--) {
            if (Character.isUpperCase(charArr[i])) {
                // if it's an upper case then lower it
                ReversedStr.append(Character.toLowerCase(charArr[i]));
            } else if (Character.isLowerCase(charArr[i])) {
                ReversedStr.append(Character.toUpperCase(charArr[i]));
            } else {
                // To account for white spaces
                ReversedStr.append(charArr[i]);
            }
        }
        return ReversedStr.toString();
    }
}
// TCPClient.java accepts an input to reverse a string
import java.net.*;
import java.io.*;

/**
 * This is a TCP Client class that expects using
 * javac TCPClient.java to compile
 * java TCPClient <IP> <PORT> to run
 * And Enter Text for reversal and capitalization.
 * Make sure the server is running
 */
public class TCPClient {
    public static void main (String[] args) {
        System.out.println("Starting the client:");
        if (args.length < 2) {
            System.out.println("TCP usage: java TCPClient <client> <Port Number>");
            System.exit(1);
        }
        // arguments supply message hostname and destination
        String ClientHostname = args[0];
        int ClientPort = Integer.parseInt(args[1]);
        Socket client = null;
        try {
            // Open your connection to a server
            client = new Socket(ClientHostname, ClientPort);
            // Get an input file handle from the socket and read the input
            // CreateI/O streams for communicating with the server.
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            System.out.println("Enter text to be reversed: ");
            String userInput = System.console().readLine();
            if (userInput.length() < 80) {
                // check if length is less than 80 per instructions
                out.writeUTF(userInput);
            } else {
                throw new ArithmeticException("Your input must have a length less than 80.");
            }
            System.out.println("Response from server: reverse " + in.readUTF());
        } catch (UnknownHostException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (client != null) {
                try {
                    // When done, just close the connection and exit
                    client.close();
                } catch (IOException e) {
                    /* close failed */
                }
            }
        }
    }

}
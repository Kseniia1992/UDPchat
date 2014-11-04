package udpchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP client sends message and receives response.
 * If client sends message 'bye' it stops
 * @author ksolodovnik
 */
public class UDPClient {
    private static DatagramSocket socket;
    
    public static void main(String[] args) throws IOException, InterruptedException{
        try {
            socket = new DatagramSocket();
            
            while (true) {
                /* buffer for outcoming data */
                byte[] outcoming_data = new byte[100];
                
                /* buffer for incoming data */
                byte[] incoming_data = new byte[100];
                
                int readBuf = System.in.read(outcoming_data);
                
                if (readBuf > 0) {
                    String message = new String(outcoming_data, 0, readBuf);
                    byte[] data = message.getBytes();
                    /* datagram for outcoming data */
                    DatagramPacket sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 8080);
                    socket.send(sendPacket);
                    System.out.println("Message sent from client");
                    
                    if(message.equals("bye\n"))
                        System.exit(0);
                    
                    /* datagram for incoming data */
                    DatagramPacket incomingPacket = new DatagramPacket(incoming_data, incoming_data.length);
                    socket.receive(incomingPacket);
                    String response = new String(incomingPacket.getData());
                    System.out.println("Response from server:" + response);
                    
                }
            }
        }finally {
            socket.close();
        }
    }
}

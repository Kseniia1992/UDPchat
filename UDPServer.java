package udpchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP Server receives data and sends response to the client.
 * If server receives message 'bye' it stops
 * @author ksolodovnik
 */
public class UDPServer {
    private static final int PORT = 8080;
    
    /* listening for the client message */
    private static DatagramSocket socket = null;
    
    public static void main(String[] args) throws IOException{
        try{
            socket = new DatagramSocket(PORT);
            
            while(true) {
                /* buffer for data */
                byte[] data = new byte[100];
                
                /*  datagram for incoming data */
                DatagramPacket in_packet = new DatagramPacket(data, data.length);
                socket.receive(in_packet);
                String message = new String(in_packet.getData());
                String str = message.substring(0,4);
                System.out.println("Received message: " + message);
                
                if(str.equals("bye\n"))
                    System.exit(0);
                
                InetAddress ip = in_packet.getAddress();
                int port = in_packet.getPort();
                String reply = "I've got your message";
                data = reply.getBytes();
                
                /* datagram for outcoming data */
                DatagramPacket out_packet = new DatagramPacket(data, data.length, ip, port);
                socket.send(out_packet);
            }
            
        }finally{
            socket.close();
        }
    }
}

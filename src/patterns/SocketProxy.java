package patterns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketProxy implements SocketInterface {

	// 1. Create a "wrapper" for a remote,
    // or expensive, or sensitive target
    private Socket socket;
    private ServerSocket serverSocket;
    private BufferedReader in;
    private PrintWriter out;

    public SocketProxy(String host, int port, boolean wait) {
        try {
            // 2. Encapsulate the complexity/overhead of the target in the wrapper
            if (wait) {
            	// Server
        		socket = new ServerSocket(port).accept();
            } else {
            	// Client
        		socket = new Socket(host, port);
            }
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public SocketProxy(int port, final ServerSocket mockServer) {
        try {

        	// Server
    		serverSocket = mockServer;
        	// Client
    		socket = mockServer.accept();

    		in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String readLine() {
        String str = null;
        try {
            str = in.readLine();
        } catch( IOException e ) {
            e.printStackTrace();
        }
        return str;
    }

    public void writeLine(String str) {
        // 4. The wrapper delegates to the target
        out.println(str);
    }

    public void dispose() {
        try {
            socket.close();
            if (serverSocket != null) {
            	serverSocket.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}

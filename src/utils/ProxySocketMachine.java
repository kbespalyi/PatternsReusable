package utils;

import java.util.Scanner;

import patterns.SocketInterface;
import patterns.SocketProxy;

final public class ProxySocketMachine {

	private SocketInterface socket;
	private boolean mode = false;
	
	public ProxySocketMachine(Boolean mode) {
		socket = new SocketProxy("127.0.0.1", mode ? 8081 : 8082, mode);
		this.mode = mode;
	}
	
	public void start() {
        String  str;
        boolean skip = true;
        while (true) {
            if (!mode && skip) {
                skip = !skip;
            } else {
                str = socket.readLine();
                System.out.println("Receive - " + str);
                if (str.equals(null)) {
                    break;
                }
            }
            System.out.print( "Send ---- " );
            str = new Scanner(System.in).nextLine();
            socket.writeLine( str );
            if (str.equals("quit")) {
            	stop();
                break;
            }
        }
	}

	public void stop() {
        socket.dispose();
	}
}

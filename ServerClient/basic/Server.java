import java.io.*; 
import java.net.*;
import java.util.Vector; 
  

class Server {
	public static int inPort = 9999;
	public static Vector<Client> clients = new Vector<Client>();
	
	public static void main(String[] args) throws Exception {
		new Server().createServer();
	}
	
	public void createServer() throws Exception {
	    ServerSocket server = new ServerSocket(inPort); 
        while (true) {
        	Socket socket = server.accept(); 
            Client c = new Client(socket);
            clients.add(c);
        }
	}
	
	public void sendtoall(String msg) {
		for(Client c : clients) 
			c.send(msg);
	}
	
	
	class Client extends Thread {
		Socket socket; 
		PrintWriter out = null; 
		BufferedReader in = null; 
  
		public Client(Socket socket) throws Exception { 
            this.socket = socket;
            out = new PrintWriter(socket.getOutputStream(), true); 
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            start();
		}
		
		public void send(String msg) {
			out.println(msg);
		}
  
        @Override
        public void run() {
        	String line; 
            try {
            	while(true) {
            		line = in.readLine();
            		System.out.println("("+ socket.getInetAddress()+ ") " + line); 
                	sendtoall(line);
            	}
            }
        	catch (IOException e) { } 
            finally {
            	try {
            		if (out != null)  
            			out.close(); 
            		if (in != null) {
            			in.close(); 
            			socket.close(); 
            		} 
            	}
            	catch (IOException e) { } 
            } 
        }
    
	}
    
} 

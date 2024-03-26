import java.io.*; 
import java.net.*; 
import java.util.*; 
  
 
class Client { 
	public static int inPort = 9999;
	public static String address ="localhost";
    
 
    public static void main(String[] args) { 
        try (Socket socket = new Socket(address, inPort)) {
        	PrintWriter out = new PrintWriter(socket.getOutputStream(), true); 
  
        	ChatHandler c = new ChatHandler(socket);
        	Thread myThread = new Thread(c);
        	myThread.start();
        	Scanner sc = new Scanner(System.in); 
            String line = null;
            while (!"exit".equalsIgnoreCase(line)) { 
            	line = sc.nextLine(); 
                out.println(line); 
                out.flush(); 
            } 
            sc.close();
        }
        catch (Exception e) {}
    }
        
}


class ChatHandler implements Runnable {
	private final Socket socket;; 
  
    public ChatHandler(Socket socket) { 
            this.socket = socket; 
    } 
  
    public void run() {
        BufferedReader in = null; 
        try {
        	in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
  
            String line; 
            while ((line = in.readLine()) != null) { 
            	System.out.println("("+ socket.getInetAddress()+ ") " + line); // ������ �����͸� line���� �о�´�
            } 
        } 
        catch (IOException e) { } 
        finally {
        	try {
                if (in != null) {
                	in.close(); 
                    socket.close(); 
                } 
            }
            catch (IOException e) { } 
        } 
    } 
} 

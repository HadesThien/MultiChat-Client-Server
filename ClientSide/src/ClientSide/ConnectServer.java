package ClientSide;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectServer extends Thread{
	//Properties
	private int port = 9999;
	private String networkAddress = "localhost";
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private MainWindow mainWindow;
	//Constructors 
	public ConnectServer(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		//Identify server location such as network address and port
		try {
			//Connect to Server 
			socket = new Socket(networkAddress,port);
			System.out.println("Connected: " + socket);	
			writer = new PrintWriter(this.socket.getOutputStream(), true);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override 
	public void run() {
		try {
			//Call some methods as task
			InputStream is = socket.getInputStream();
			this.reader = new BufferedReader(new InputStreamReader(is));
			receiveMessageFromServer();
			
		}catch (IOException e ) {
			e.printStackTrace();
		}finally {
			try {
				socket.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	//Methods
	public void receiveMessageFromServer() { // This methods is used for read a message from server
		try {
			//Create a read stream to receive a message from server 
			String line;
			while((line = reader.readLine()) != null) {
				System.out.println("\n"+line);
				System.out.println("\nReceived message from server!: " + line);
				mainWindow.updateChat("Server: "+ line);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void sendMessageToServer(String message) { // This method is used for send a message to server
		if(this.writer != null) {
			this.writer.println(message.toString());
			System.out.print("\nMessage sent to server: " + message.toString());
		}
	}

}

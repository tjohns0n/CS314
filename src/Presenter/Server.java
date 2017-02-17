package Presenter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


//Presenter side of MVP
//Server side of client-server for webpage

//TODO
//Integrate rest
//Provide listening loop
//Make listening loop into threadable run() method

public class Server{
	private int port; //Port number
	private ServerSocket srv; //Server Socket
	private Socket clin; //Client Socket
	private OutputStream out; //Client socket Output Stream
	private InputStream in; //Client socket Input Stream
	
	//Constructor with a specific port to try
	public Server(int setPort){
		port = setPort;
	}
	//Default Construct, finds own port
	public Server(){
		port = 0;
	}
	//Port number getter
	public int getPort(){
		return port;
	}
	//Initializes connection, sockets ,and streams
	public void initiate() throws IOException{
		srv = new ServerSocket(port);
		clin = srv.accept();
		in = clin.getInputStream();
		out = clin.getOutputStream();
		if(port == 0){
			port = srv.getLocalPort();
		}
	}
	//Sends Files to client socket output stream
	//TODO
	//Currently sending byte by byte, faster way surely possible
	//Can send array of bytes similar to current method, but would need to be buffered and handle that buffer
	public boolean sendFileToClient(File file) throws IOException{
		FileInputStream reader = new FileInputStream(file); //Reads file to be sent
		int bytbybyt = reader.read(); //Byte by Byte
		if (bytbybyt == -1 || !file.exists()){ //Check before sending anything
			//File empty
			reader.close();
			return false;
		}
		while(bytbybyt != -1){ //Loop byte by byte to be sent to client
			out.write(bytbybyt);
			bytbybyt = reader.read();
		}
		reader.close();	
		return true;
	}
	//Attempts to close all streams and sockets
	public void closeAll(){
		try {
			in.close();
		} catch (IOException e) {
			System.out.println("Unable to close client input stream");
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			System.out.println("Unable to close client output stream");
			e.printStackTrace();
		}
		try {
			clin.close();
		} catch (IOException e) {
			System.out.println("Unable to close client socket");
			e.printStackTrace();
		}
		try {
			srv.close();
		} catch (IOException e) {
			System.out.println("Unable to close server socket");
			e.printStackTrace();
		}
	}
	
}
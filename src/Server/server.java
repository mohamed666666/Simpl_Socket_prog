package Server;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.ServerSocket;
import java.util.Scanner;



public class server {
	static File credentials ;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
        int port;
        Scanner s=new Scanner(System.in);
        String servername;
        

        try {
        	System.out.println("Please enter the server name and port number.");
        	servername=s.nextLine();
        	port=s.nextInt();
        	ServerSocket Server=new ServerSocket(port);
        	
        	if (Files.isDirectory(Paths.get(servername))) {
        		System.out.println("this server is already running.");
        		}
        	else {
        		new File(servername).mkdirs();
            	credentials=createfile("/home/mohamed/eclipse-workspace/SMTP_projectV2/"+servername+"/credentials.txt");

        	}
        	
        	
        	System.out.println( servername+" server is booted up  on port : ["+port+"]");
            
        	
        	
        	
        	
            while(true) {
            	Socket Csocket=Server.accept();
            	
            	System.out.println("Client ["+Csocket+"] is connected now to server");
            	
            	ClientConnection connection=new ClientConnection(Csocket,servername,credentials);
            	connection.start();
            }
          
         
        }
        catch(IOException ie) {
            ie.printStackTrace();
        }
      

	}
	
	
	public static File createfile(String filename) {
		File file = new File(filename);
		  
		//Create the file
		try {
			if (file.createNewFile())
			{
			    System.out.println("File is created!");
			} else {
			    System.out.println("File already exists.");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;

}

}

package client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port ;
		String username;
		String password;
		String msg;
		String reply;

        try {
        	Scanner s=new Scanner(System.in);
        	System.out.println("Please enter server port number:");
        	port=s.nextInt();
            Socket client_socket=new Socket("127.0.0.1",port);
            DataOutputStream sender=new DataOutputStream(client_socket.getOutputStream());
            DataInputStream recv=new DataInputStream(client_socket.getInputStream());
            
        	reply= recv.readUTF();
        	System.out.println("Server Reply first :"+reply);

            while(true) {

            	System.out.println("Please choose ‘REGISTER or ‘LOGIN’ or ‘QUIT’.");
            	
                msg=s.nextLine();
                
                sender.writeUTF(msg);
                
                
                if (msg.equals("REGISTER")) {
                	System.out.println("Please enter an email and a password");
                	
                	username=s.nextLine();
                	password=s.nextLine();
                	
                	sender.writeUTF(username);
                	sender.writeUTF(password);
                	continue;
                }
                else if (msg.equals("LOGIN")) {
                	System.out.println("Please enter an email and a password");
                	
                	username=s.nextLine();
                	password=s.nextLine();
                	
                	sender.writeUTF(username);
                	sender.writeUTF(password);
                	
                	
                	String tmp=recv.readUTF();
                	System.out.println(tmp);
                	
                	
                
                	String how=recv.readUTF();
                	System.out.println("serever seend "+how);
                	
                	 how=recv.readUTF();
                	while(!how.equals("true")) {
                		how=recv.readUTF();
                	}
                	
                	if(how.equals("true")) {
                		System.out.println("Please choose ‘SEND’ or ‘QUIT’.");
                		msg=s.nextLine();
                		sender.writeUTF(msg);
                		if(msg.equals("SEND")) {
                			tmp=recv.readUTF();
                        	System.out.println(tmp);
                        	//get  to whom sending mail and ak server 
                			tmp=s.nextLine();
                			sender.writeUTF(tmp);
                			
                			tmp=recv.readUTF();
                			System.out.println(tmp);
                			
                			
                			System.out.println("Enter massege :");
                			msg=s.nextLine();
                			sender.writeUTF(msg);
                		
                	}}
                	
                	
                	
                	
                	
                }
                
                
               

                if(msg.equalsIgnoreCase("close")) {
                    break;
                }
            }
            client_socket.close();
            sender.close();
            s.close();
            recv.close();
        }catch(IOException ie) {
            ie.printStackTrace();
        }


	}

}

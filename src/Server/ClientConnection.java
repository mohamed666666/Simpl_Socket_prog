package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

public  class ClientConnection  extends Thread{
	Socket ClientSocket;
	String ServerName;
	String username;
	String password;
	File credentials;
	String MailRecv;
	File inbox;
	
	
	ClientConnection(Socket ClientSocket,String ServerName,File credentials){
		this.ClientSocket=ClientSocket;
		this.ServerName=ServerName;
		this.credentials=credentials;
	}
	public void run() {
		
		try {
			DataOutputStream sender=new DataOutputStream(ClientSocket.getOutputStream());
            DataInputStream recv=new DataInputStream(ClientSocket.getInputStream());
            Scanner s=new Scanner(System.in);
            while(true) {
                //String reply=recv.readUTF();
                //System.out.println("client ["+ClientSocket+"] massege :"+reply);
               
                sender.writeUTF("200 "+this.ServerName);

               
               
                String reply=recv.readUTF();
                
                if(reply.equalsIgnoreCase("LOGIN")) {
                	username=recv.readUTF();
                	password=recv.readUTF();
                	
                	//searching for the user in credentials  
                	String str ="empty";
                	Scanner txtscan = new Scanner(this.credentials);
                	
            		while(txtscan.hasNextLine()){
            		    str = txtscan.nextLine();
            		    
            		    if(str.indexOf(username)!= -1) {
            		    	
            		    	
            		    	if(str.contains(password)) {
            		    		
            		    		sender.writeUTF("250 HELLO "+username+"@"+ServerName);
                    			sender.writeUTF("true");
                    			
                    			String temp=recv.readUTF();
                    			if(temp.equalsIgnoreCase("SEND")) {
                    				sender.writeUTF("MAIL FROM :"+username+"\n250 sender OK \n RCPT TO");
                    				MailRecv=recv.readUTF();
                    				//home/mohamed/eclipse-workspace/SMTP_projectV2/gmail.com/mohamed
                    				
                    				sender.writeUTF("Server: 250"+MailRecv+"Recipient ok");
                    				String msg=recv.readUTF();
                    				String[] rcvservername=MailRecv.split("@");
                    				//saving massege to inbox file of sender
                    				
                    				writeTofile(inbox,"From: "+username+"\n"+msg+"\nTo: "+MailRecv);
                                	// save massege to recever inbox
                                	File recevInbox=new File("/home/mohamed/eclipse-workspace/SMTP_projectV2/"+rcvservername[1]+"/"+rcvservername[0]+"/inbox.txt");
                                	System.out.println(rcvservername[1]+"/"+rcvservername[0]);
                                	
                                	writeTofile(recevInbox,msg);
                                	
                                	
                    				break;
                    			}
                    			else if(temp.equalsIgnoreCase("QUIT")) {
                    				ClientSocket.close();
                                    break;
                    			}
                    		}else {
                    			sender.writeUTF("Incorrect password .");
                    		}
                    		
            		    	break;
            		    }   
            		}
            		txtscan.close();
            	
            		
                }
                
                
                
                else if(reply.equalsIgnoreCase("QUIT")) {
                	System.out.println("client ["+ClientSocket+"] Close Connection .");
                	ClientSocket.close();
                    break;
                }
                
                
                else if(reply.equalsIgnoreCase("REGISTER")) {
                	
                	//sender.writeUTF("Please enter an email and a password");
                	username=recv.readUTF();
                
                	password=recv.readUTF();
                	
                	new File("/home/mohamed/eclipse-workspace/SMTP_projectV2/"+ServerName+"/"+username).mkdirs();
                	this.inbox=createfile("/home/mohamed/eclipse-workspace/SMTP_projectV2/"+ServerName+"/"+username+"/inbox.txt");
                	FileWriter fr = new FileWriter(this.credentials, true);
                	
                	fr.write(username+"@"+ServerName+" | "+password+"\n");
                	fr.close();	
                }

            }
            
           
            sender.close();
            recv.close();
            s.close();
		}
		catch(Exception e) {
			System.out.println("ERorr with socket.");
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
		return file;}
	
	public static void writeTofile(File fil,String data) {
		FileWriter fr;
		try {
			fr = new FileWriter(fil, true);
			fr.write(data);
	    	fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	
	
	
}

package pubsubmq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Publisher {
	public static void main(String[] args) {
		 
		Socket socket = null;
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try{
			socket = new Socket("localhost", 143);
			
			isr = new InputStreamReader(socket.getInputStream());
			osw = new OutputStreamWriter(socket.getOutputStream());
			
			br = new BufferedReader(isr);
			bw = new BufferedWriter(osw);
			
			Scanner scanner = new Scanner(System.in);
			
			while (true) {
				System.out.println("1. Publisher"); 
				System.out.println("2. Exit"); 
				System.out.print("Choose an option: ");
				
				String choice = scanner.nextLine();  
				bw.write(choice);  
				bw.newLine();
				bw.flush();
				
				if(choice.equalsIgnoreCase("PUB")) { 
					System.out.print("Enter publisher ID: ");
	                String publisherID = scanner.nextLine();
	                
	                bw.write(publisherID);
	                bw.newLine(); 
	                bw.flush();
	                
	                System.out.println("Server: " + br.readLine());
	                
	                System.out.print("Enter message: ");
                    String payload = scanner.nextLine();
                    
                    bw.write(payload);
	                bw.newLine(); 
	                bw.flush();
	                
	                System.out.println("Select priority:");
                    System.out.println("1. High");
                    System.out.println("2. Medium");
                    System.out.println("3. Low");
                    System.out.print("Choose priority: ");
                    
                    int priorityChoice = scanner.nextInt(); 
    				scanner.nextLine();
    				
    				bw.write(Integer.toString(priorityChoice));  
					bw.newLine();
					bw.flush();
	                
	                System.out.println("Server: " + br.readLine()); 
	                 
				} 
				
				else if (choice.equalsIgnoreCase("EXIT")) {
					System.out.println("Exiting.");
					break;
				} 
				else {
					System.out.println("Invalid choice");
				}
				
				
			}
			
		}catch (IOException ex) {
			ex.printStackTrace();
		}finally {
			try {
				if (socket!= null) {
					socket.close();
				}
				if (isr!=null) {
					isr.close();
				}
				if (osw!=null) {
					osw.close();
				}
				if (br!=null) {
					br.close();
				}
				if (bw!=null) {
					bw.close();
				}
				}catch (IOException ex) {
					ex.printStackTrace();
			}
		}
	}
}

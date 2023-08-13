package pubsubmq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Subscriber {
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
			
			System.out.println("1. Subscriber");
			System.out.println("2. Exit"); 
			
			System.out.print("Choose an option: ");
			String choice = scanner.nextLine();  
			bw.write(choice);  
			bw.newLine();
			bw.flush();
			
			if(choice.equalsIgnoreCase("SUB")) {
				System.out.print("Enter subscriber ID: ");
                String subscriberID = scanner.nextLine();
                
                bw.write(subscriberID);
                bw.newLine(); 
                bw.flush();
                
                System.out.println("Server: " + br.readLine());
                
                int startIndex = Integer.parseInt(br.readLine());
                int endIndex = Integer.parseInt(br.readLine());
                
                for (int i = startIndex; i < endIndex; i++) {
                	System.out.println("Server: " + br.readLine());
                }
                 
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
package pubsubmq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map; 

public class Server {
	public static void main(String[] args) throws IOException {
		
		Socket socket = null;
		InputStreamReader isr = null;
		OutputStreamWriter osw = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		ServerSocket ss = null;
		
		Queue queue = new Queue();
        HashMap<String, Integer> c_Map = new HashMap<>();
        HashMaptoFile hmapfile = new HashMaptoFile(c_Map);
        String directoryPath = "C:\\Users\\70083358\\Desktop\\Java\\PubSubMQ\\src\\pubsubmq";
        String fileName = "hashmap.txt";
        
        File file = new File(directoryPath, fileName);
        if (file.exists()) {
        	hmapfile.readHashMapFromFile(fileName);
        } else {
            ;
        }
         
        final int max_qsize = 10000;
        final int payload_size = 256;
		
		ss = new ServerSocket(143);
		
		 while(true) { 
			try {
				socket = ss.accept();
				isr = new InputStreamReader(socket.getInputStream());
				osw = new OutputStreamWriter(socket.getOutputStream());
				
				br = new BufferedReader(isr);
				bw = new BufferedWriter(osw);
				
				while(true) {
					String choice = br.readLine();  
					if (choice.equalsIgnoreCase("PUB")) {
						 
						String publisherID = br.readLine();
						
						bw.write("Welcome Publisher " + publisherID);
						bw.newLine();
						bw.flush();
						
						String payload = br.readLine();
						int priorityChoice = Integer.parseInt(br.readLine());
						Messages.Priority priority = Messages.Priority.values()[priorityChoice - 1];
	                    Messages message = new Messages(payload, publisherID, priority);
	                    queue.enqueue(message); 
	                    
						System.out.println("Publisher ID: " + publisherID);
						System.out.println("Payload: " + payload); 
						System.out.println("Priority: " + priority); 
						
						bw.write("Message added to the queue.");
						bw.newLine();
						bw.flush();  
					}
					
					else if(choice.equalsIgnoreCase("SUB")) {
						String subscriberID = br.readLine();
						bw.write("Welcome Subscriber " + subscriberID);
						bw.newLine();
						bw.flush();
						
						int startIndex = c_Map.getOrDefault(subscriberID, 0);
	                    int endIndex = Math.min(startIndex + 100, queue.size());
	                    
	                    bw.write(Integer.toString(startIndex));  
	    				bw.newLine();
	    				bw.flush();
	    				
	    				bw.write(Integer.toString(endIndex));  
	    				bw.newLine();
	    				bw.flush();
	                    
	                    for (int i = startIndex; i < endIndex; i++) {
	                        Messages consumedMessage = queue.dequeue();
	                        bw.write("Subscriber " + subscriberID + " consumed message: " + consumedMessage.getPayload());
	                        bw.newLine();
							bw.flush();
	                    }
	                    
	                    c_Map.put(subscriberID, endIndex);
	                    
	                    hmapfile.writeHashMapToFile(directoryPath, fileName);
	                     
					}
					
					else if (choice.equalsIgnoreCase("EXIT")) {
						System.out.println("Exiting.");
						break; 
					}
					 
				}
			}catch (IOException ex) {
				ex.printStackTrace();
		} finally {
			socket.close();
			isr.close();
			osw.close();
			br.close();
			bw.close();
		}
	}  
	}
}





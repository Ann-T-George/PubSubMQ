package pubsubmq;
import java.io.*;
import java.util.*;

public class HashMaptoFile {
    private HashMap<String, Integer> map;
    
    public HashMaptoFile(HashMap<String, Integer> map) {
        this.map = map;
    }
    
    public void putValue(String key, Integer value) {
        map.put(key, value);
    }
    
    public Integer getValue(String key) {
        return map.get(key);
    }
    
    public String writeHashMapToFile(String directoryPath, String fileName) {
        String filePath = null;
        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory, fileName);
            filePath = file.getAbsolutePath();

            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    writer.println(entry.getKey() + ":" + entry.getValue());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }
    
    public void readHashMapFromFile(String fileName) throws FileNotFoundException {
        map.clear(); // Clear the existing map data
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    map.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


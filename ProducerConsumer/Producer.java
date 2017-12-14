import java.util.Random;
import java.io.FileInputStream;
import java.io.IOException;

public class Producer extends Thread {

	private String filename;
    private int maxProduce;
    private Buffer buff;
    private FileInputStream in;
    
    public Producer(String filename, int n, Buffer buff) {       
        this.filename = filename;
        maxProduce = n;
        this.buff = buff;
        in = null;
    }

    public void run() {
    	
        Random rand = new Random();         
        int produce = rand.nextInt(maxProduce) + 1;
        byte[] bytes = new byte[produce];
        
        try {
            in = new FileInputStream(filename);             
            while(in.available() != 0) {
        		in.read(bytes);
        		buff.put(bytes);
            	produce = rand.nextInt(maxProduce) + 1;
            	bytes = new byte[produce];          	
            }   
            buff.setReadDone();
            in.close();
        } catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}
    }
}

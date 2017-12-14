import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Consumer extends Thread {
	
    private String filename;
    private int maxConsume;
    private Buffer buff;
    private FileOutputStream out;
    
    public Consumer(String filename, int n, Buffer buff) {
        this.filename = filename;
        maxConsume = n;
        this.buff = buff;
        out = null;
    }

    public void run() {

        Random rand = new Random();         
        int consume = rand.nextInt(maxConsume) + 1;
        byte[] bytes;
        
        try {   
        	File file = new File(filename);
        	file.createNewFile();
            out = new FileOutputStream(filename);  
            while(!buff.getReadDone()) {
            	bytes = buff.get(consume);
            	out.write(bytes);
            	consume = rand.nextInt(maxConsume) + 1;
            }  
            int remain = buff.getLength();
            if(buff.getLength() > 0) {
            	bytes = buff.get(remain);
            	out.write(bytes);
            }
            out.close();      
        } catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}
    }
}

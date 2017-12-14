import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	// prompt and get user input
        System.out.println("Usage: cp SOURCE DEST MAX_BYTES_TO_COPY BUFF_SIZE");
        System.out.print(">> ");        
        Scanner in = new Scanner(System.in);
        String cmd = in.nextLine();
        in.close();
        
        String[] tokens = cmd.split("[ ]+");       
        String input;
        String output;
        int n;
        int m; 

        if(tokens[0].equals("cp") && tokens.length == 5) {
            input = tokens[1];
            output = tokens[2];
            n = Integer.parseInt(tokens[3]);
            m = Integer.parseInt(tokens[4]);            
        }
        else {
        	input = "input.doc";
            output = "output.doc";
            n = 5;
            m = 10;
        }
        
        Buffer buff = new Buffer(m);       
        Producer p = new Producer(input, n, buff);       
        Consumer c = new Consumer(output, n, buff);
        // start both threads
        p.start();
        c.start();
    }

}

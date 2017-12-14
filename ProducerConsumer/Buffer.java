import java.util.concurrent.Semaphore;

public class Buffer {

	private byte[] buff;
	private int length;
	private int size;
	private int indexIn;
	private int indexOut;
	private Semaphore mutex;
	private Semaphore full;
	private Semaphore empty;
	private boolean readDone;
	
	public Buffer(int m) {
		size = m;
		buff = new byte[size];
		length = 0;
		indexIn = 0;
		indexOut = 0;
		mutex = new Semaphore(1);
		full = new Semaphore(0);
		empty = new Semaphore(size);
		readDone = false;
	}
	
	public void put(byte[] in) throws InterruptedException {
		int permits = in.length;
		empty.acquire(permits);
		mutex.acquire();
		System.out.print("\n" + Thread.currentThread().getName()+" produce " + permits +" to   "+ length + " | bytes: ");
		for(int i = 0; i < in.length; i++) {
			buff[indexIn] = in[i];
			System.out.print(buff[indexIn] + " ");
			indexIn++;
			length++;
			if(indexIn == size) {
				indexIn = 0;
			}
		}
		mutex.release();
		full.release(permits);
	}

	public byte[] get(int n) throws InterruptedException {
		int permits = n;
		full.acquire(permits);
		mutex.acquire();	
		byte[] out = new byte[n];
		System.out.print("\n" + Thread.currentThread().getName()+" consume " + permits +" from "+ length + " | bytes: ");
		for(int i = 0; i < n; i++) {
			out[i] = buff[indexOut];
			System.out.print(buff[indexOut] + " ");
			indexOut++;
			length--;
			if(indexOut == size) {
				indexOut = 0;
			}
		}
		mutex.release();
		empty.release(permits);
		return out;		
	}
	
	public int getLength() {
		return length;
	}
	
	public int getSpace() {
		return size - length;
	}
	
	public void setReadDone() {
		readDone = true;
	}
	
	public boolean getReadDone() {
		return readDone;
	}
}

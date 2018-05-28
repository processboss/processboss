import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConsumidorDeMemoria {

	public static void main(String[] args) {
				
		List<String> lista = new ArrayList<String>();
		
		try {
			while (true) {
				lista.add(new Date().toString());
				
				long heapSize = Runtime.getRuntime().totalMemory();
				double limit = heapSize * 0.02;
				long freeMemory = Runtime.getRuntime().freeMemory();
				
				if(freeMemory < limit){ 
					System.gc();
					System.out.println("GC");
				}
				
			}
		} catch (OutOfMemoryError e) {
			System.out.println(e);
		}
		
		
	}
	
	
}

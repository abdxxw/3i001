import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test{

    public static void main(String[] args){

        final int NB_CLIENTS = 2;

    	ExecutorService es = Executors.newFixedThreadPool(5);
        ArrayBlockingQueue<Future<ReponseRequete>> ar = new  ArrayBlockingQueue<Future<ReponseRequete>> (5);
        Serveur s = new Serveur(es,ar);	
        Thread sT = new Thread(s);
        sT.start();	// lancer le thread serveur

        for(int i=0;i<NB_CLIENTS;i++){	// cree et lancer NB_CLIENTS Thread de clients
            Thread t = new Thread(new Client(i,s,5,ar));		
            t.start();
            try {
				t.join();	//pour gerer la terminaison
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            
        }
        sT.interrupt();	// on interrompt le Thread serveur a la fin d'executions des thread clients
        
        
        														
    }


}
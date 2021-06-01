public class Test{

    public static void main(String[] args){

        final int NB_CLIENTS = 5;
        
        Serveur s = new Serveur();	
        Thread sT = new Thread(s);
        sT.start();	// lancer le thread serveur

        for(int i=0;i<NB_CLIENTS;i++){	// cree et lancer NB_CLIENTS Thread de clients
            Thread t = new Thread(new Client(i,s,5));		
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
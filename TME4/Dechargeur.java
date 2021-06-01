
public class Dechargeur implements Runnable{
	private AleaStock S;
	private Chariot C;
	private int id;
	public static int cpt=0;
	private static final Object x = new Object(); // pour gerer la synchro des id
	
	//constructeur
	public Dechargeur(AleaStock s, Chariot c) {
		S = s;
		C = c;
		synchronized(x) {
			cpt++;
			id=cpt;
		}
	}

	//affichage
	public void trace(String m){
		System.out.println("Dechargeur "+id+" "+m);
		Thread.yield();
	}

	@Override
	public void run() {
		
			try{
				while(!S.isEmpty()) // tantque le srock n'est pas vide qui est equivalent a "on fait encore des chargement"
					C.removeAll(); // on vide le chariot
			}catch(InterruptedException e){ // traitement d'exception
			}
				
	}
	
	
	
	
}


public class Chargeur implements Runnable{
	
	private AleaStock S;
	private Chariot C;
	private int id;
	public static int cpt=0;
	private static final Object x = new Object(); // pour gerer la synchro des id
	
	//constructeur
	public Chargeur(AleaStock s, Chariot c) {
		S = s;
		C = c;
		synchronized(x) {
			cpt++;
			id=cpt;
		}
	}

	//affichage
	public void trace(String m){
		System.out.println("Chargeur "+id+" "+m);
		Thread.yield();
	}

	@Override
	public void run() {

		AleaObjet tmp;
		while(!S.isEmpty()) { // tantque le stock n'est pas vide 
				tmp = S.removeM(); // on supprime une marchandise 
				try {
					if(C.addM(tmp))	// on l'ajoute au chariot
						trace(" : "+tmp);	//affichage
					else
						S.addM(tmp); // reajouter la marchandise qu'on pouvez pas ajouter
				} catch (InterruptedException e) { // attrapper l'execption qui peut etre lever par addM
					System.out.println("Erreur wait");
				}
		}
		
	}
	
	
	
	
}

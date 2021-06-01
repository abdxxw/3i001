
public class Hangar {

	private boolean libre; // etat du hangar
	private int id;		//  identifiant
	public static int cpt=0;
	public static Object x= new Object();
	
	public Hangar() {
		synchronized(x) {
			cpt++;
			id=cpt;
		}
		libre=true;
	}

	public void entrer(int id) {		//pas besoin de synchronizer cette fonction car on donne toujour un id d'un hangar vide
			System.out.println("		Loco "+id+" est dans le Hangar "+this.id);
			libre = false;	// m-a-j de l'etat
	}
	
	
	public boolean getLibre() { // getter
		return libre;
	}

}


public class PoolHangars {
	private Hangar tab[];	// tableau de hangar

	public PoolHangars(int taille) { // constructeur
		tab=new Hangar[taille+1];	// on utilise taille+1 pour sauter la case 0 qui signifie le segment d'acceuil dans notre system
		for(int i=1;i<taille+1;i++) 
			tab[i]=new Hangar();
	
	}

	public int getHangar() {	// recherche d'une bonne position pour mettre un Loco

			for(int i=1;i<tab.length;i++) {
				if(tab[i].getLibre()){
					return i;
				}
			}
			return -1;
		
	}
	
	public Hangar getHangar(int i) {	// retourner le hangar a la postion i
		return tab[i];
	}


	public int getTaille(){
		return tab.length;
	}
}

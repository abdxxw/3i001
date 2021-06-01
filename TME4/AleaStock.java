
public class AleaStock {
	private AleaObjet tab[]; //tableau de marchandises
	private int nbM;	// nombre d'element courant 
	
	//constructeur
	public AleaStock(int taille) {
		tab = new AleaObjet[taille];
		nbM = 0;
	}

	//verifier si le stock est plein
	public boolean isFull() {
		return tab.length == nbM;
	}
	
	//verifier si le stock est vide
	public boolean isEmpty() {
		return 0 == nbM;
	}
	
	
	//ajouter une marchandise au stock
	
	public void addM(AleaObjet m){
		if(!isFull()) { // seulement s'il ya de la place
			tab[nbM] = m;
			nbM++;
		}
	}
	
	//enlever une marchandise du stock et la retourner 
	public AleaObjet removeM(){
		
		AleaObjet tmp = null;
			if(!isEmpty()) { // seulement si le stock contient des marchandises a enlever
				nbM--;
				tmp = tab[nbM];
				tab[nbM] = null;
			}
		return tmp;
	}
	
	
}

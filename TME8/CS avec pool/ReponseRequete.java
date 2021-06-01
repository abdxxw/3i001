import java.util.Random;

public class ReponseRequete {
	
	private Client c; //client emmeteur
	private int numReq;	//numero de requete
	private int x;	//valeur aleatoire
	private Random rand = new Random();	// generateur du num aleatoire
	
	
	public ReponseRequete(Client c, int numReq) { //const

		this.c = c;
		this.numReq = numReq;
		this.x = rand.nextInt();
	}
	
	public int getNumReq() { // getter
		return numReq;
	}
	
	public Client getClient() {
		return c;
	}
	public String toString() {
		return "pour le client "+c.getID()+" Reponse requete "+numReq+" : "+x;
	}



}

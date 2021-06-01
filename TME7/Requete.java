
public class Requete {
	
	private int type;	//type de requete
	private int numReq;	// numero requete
	private Client c;
	
	public Requete(int numReq, Client c) {

		this.numReq = numReq;
		if (numReq % 3 == 0)
			this.type = 2;
		else
			this.type = 1;
		this.c = c;
	}
	
	//getters

	public int getNumReq() {	
		return numReq;
	}

	public Client getClient() {
		return c;
	}

	public int getType() {
		return type;
	}

	

}

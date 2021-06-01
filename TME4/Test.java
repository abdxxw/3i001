
public class Test {
	public static void main(String[] args) {
		
		AleaStock S = new AleaStock(50);
		Chariot C = new Chariot(5000,10); 	// chariot avec capacité 10 , poids max 5000
		
		for(int i=0;i<50;i++)
			S.addM(new AleaObjet(800, 1200));	// ajouter 50 marchandises

		for(int i=0;i<5;i++) {	// lancer 5 chargeurs et 5 dechargeurs 
			new Thread(new Chargeur(S,C)).start();
			new Thread(new Dechargeur(S,C)).start();
		}
			


		
	}
}

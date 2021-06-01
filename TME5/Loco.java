
public class Loco implements Runnable {
	
	private int id;	// identifiant
	public static int cpt=0;	// compteur
	public static Object x=new Object();	// pour synchroniser l'attribution du id
	private SegAccueil sAccueil;
	private SegTournant sTournant;
	private PoolHangars pHangars;
	private static int actifLoco=0;	// compteurs des threads actifs
	private Thread end;	// le thread du segement tournant pour (gerer la terminaison)

	public Loco(SegAccueil sAccueil, SegTournant sTournant, PoolHangars pHangars,Thread end) { // constructeur
		synchronized(x) {
			cpt++;
			id=cpt;
			actifLoco++;
		}
		this.end = end;
		this.sAccueil = sAccueil;
		this.sTournant = sTournant;
		this.pHangars = pHangars;
		
	}
	
	public void run() {
		try {
		sAccueil.reserver();	// on reserve le segment d'acceuil 
		sTournant.appeler(0);	// on appelle le segement tournant a la postion 0 ( position du segement d'acceuil)
		sTournant.attendrePositionOK();	// on attents que la postion soit correcte 
		sTournant.entrer(id);	// entrer dans le segement tournant
		sAccueil.liberer();		//liberer le segement d'acceuil
		sTournant.attendrePositionOK();		//on attent que le segement tournant se deplace vers une position d'un Hangar vide
		pHangars.getHangar( sTournant.getPosition() ).entrer(id);	// Loco entre dans le Hangar  
		sTournant.sortir(id);	//et sort du segement tournant (le liberer)
		System.out.println("==================Loco "+id+" est terminé=========================");
		synchronized(x){

			actifLoco--;			// a la fin de chaque Thread on diminue le compteur pour gerer la terminaison
			if(actifLoco == 0){
					end.interrupt(); // si on a plus de Loco on arrete le thread SegTournant
			}
		}
		}
		catch (InterruptedException e) {
		System.out.println("Loco " + id + " interrompue (ne devrait pas arriver)");
		}
	}
		
}

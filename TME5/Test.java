public class Test{

    public static void main(String[] args){

        SegAccueil sA = new SegAccueil();	// un segment d'acceuil
        PoolHangars pH = new PoolHangars(10);	//PoolHangars avec 9 hangar + premiere position pour le segments d'acceuil
        SegTournant sT = new SegTournant(pH);	// un segement tournant

        Thread sTThread = new Thread(sT);		// thread du segement tournant
        sTThread.start();	// commancer le thread

        for(int i=1;i<pH.getTaille();i++)	//on lance taille-1 threads 
            new Thread(new Loco(sA,sT,pH,sTThread)).start();	// pour chaque thread loco on donne le thread du segement tournant
        														// pour gerer la terminaison
    }


}
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Client implements Runnable{

	private int id;	//identifiant client
	private Serveur s;	
	private Requete[] requetes;	// tab de requetes
	private boolean[] done; // un boolean pour chaque requete qui nous indique si elle est traiter ou pas encore
	private boolean[] sent; // un boolean pour chaque requete qui nous indique si elle est traiter ou pas encore
	public static final Object o = new Object();
	private int i=0;	// indice courant sur le tableau de requetes
	private ArrayBlockingQueue<Future<ReponseRequete>> ar;

	public Client(int id,Serveur s,int nbReq,ArrayBlockingQueue<Future<ReponseRequete>> ar) {
		this.id=id;
		this.s=s;
		requetes = new Requete[nbReq];	// nbReq est le nombre de requetes qu'un client peut envoyer
		done = new boolean[nbReq];
		sent = new boolean[nbReq];
		for(int j=0;j<requetes.length;j++){	// cree les requetes
			requetes[j] = new Requete(j+1, this);
		}
		this.ar = ar;
	}

	public int getID() {
		return id;
	}

	
	public boolean endIt() {	// pour verifier si on a bien traiter tous les requetes avant de terminer
		for(int j =0;j<done.length;j++)
			if (done[j] == false)
				return false;
		
		return true;
	}
	public void run() {
		
		try {
			while(!done[i]) {	// tant que on a pas encore traiter touts les requetes
				if(sent[i] == false ) {

					System.out.println("Client "+id+" soumet la requete "+(i+1));
					s.soumettre(requetes[i]);	// envoyer la requete au serveur
					System.out.println("	Client "+id+" Attends la reponse "+(i+1));
					sent[i] = true;
				}
				
					//attendreReponse();	// attendre la reponse
					for (Future<ReponseRequete> fr : ar) {
						ReponseRequete rq = fr.get();
						if (rq.getClient() == this) {
							done[rq.getNumReq()-1] = true;
							ar.remove(fr);
							i= (i+1) % requetes.length;
						}
							
							
					}
			}
		}catch (InterruptedException e) {
			System.out.println("Client interrompu");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}

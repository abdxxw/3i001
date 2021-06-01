import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Client implements Runnable{

	private int id;	//identifiant client
	private Serveur s;	
	private Requete[] requetes;	// tab de requetes
	private boolean[] done; // un boolean pour chaque requete qui nous indique si elle est traiter ou pas encore
	private ReentrantLock l = new ReentrantLock();
	private Condition reponse = l.newCondition();	// condition pour attendre la reponse du serveur 
	public static final Object o = new Object();
	private int i=0;	// indice courant sur le tableau de requetes

	public Client(int id,Serveur s,int nbReq) {
		this.id=id;
		this.s=s;
		requetes = new Requete[nbReq];	// nbReq est le nombre de requetes qu'un client peut envoyer
		done = new boolean[nbReq];
		for(int j=0;j<requetes.length;j++){	// cree les requetes
			requetes[j] = new Requete(j+1, this);
		}
	}

	public int getID() {
		return id;
	}
	synchronized void requeteServie(ReponseRequete r) {	
		l.lock();
		try{
			done[r.getNumReq()-1] = true;	// indiquer au client que la requete est traiter
			reponse.signalAll();	// et signaler les clients qui attends la reponse
		}finally {
			l.unlock();
		}
	}


	public void attendreReponse() throws InterruptedException {
		l.lock();
		try{
			
			while(!done[i]) {	// tantque la requete n'est pas encore traiter 	
				reponse.await();	// on attends une reponse
			}
			
		}finally {
			l.unlock();
		}
	}
	public boolean endIt() {	// pour verifier si on a bien traiter tous les requetes avant de terminer
		for(int j =0;j<done.length;j++)
			if (done[j] == false)
				return false;
		
		return true;
	}
	public void run() {
		
		try {

			while(!endIt()) {	// tant que on a pas encore traiter touts les requetes
				System.out.println("Client "+id+" soumet la requete "+i);
				s.soumettre(requetes[i]);	// envoyer la requete au serveur
				System.out.println("	Client "+id+" Attends la reponse "+i);
				attendreReponse();	// attendre la reponse
				
				
				synchronized(o) {	// pour proteger la resource critique i
					i= (i+1) % requetes.length;	
				}
			}
		}catch (InterruptedException e) {
			System.out.println("Client interrompu");
		}

		
	}

}

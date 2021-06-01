import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Serveur implements Runnable{

	private boolean full; //indiquer si le serveur est occuper ou non
	private boolean send;	//indiquer si le serveur peut recevoir une requete ou non (si le client peut envoyer une requete)
	private ReentrantLock l = new ReentrantLock();
	private Condition notLibre = l.newCondition(); // pour attendre que le serveur recoit une requete
	private Condition giveMe = l.newCondition(); 	//pour attendre que le serveur soit libre
	private Requete now;	// la requete courante
	
	public Serveur() {
		full=false;
		send=true;
		now = null;
	}
	
	
	 public void soumettre(Requete r) throws InterruptedException {
		 l.lock();
			try{
				while(!send) {	//tantque on ne peut pas envoyer une requets
					giveMe.await();	// on attends qu'on sera pret a recevoir
				}
				now = r;	// m-a-j de reqeute courante
				full=true;	// occuper a true
				notLibre.signalAll();	//on signale les threads qui attends une requete
			}finally {
				l.unlock();
			}
		
		
		
	}
	 
	 public void attendreRequete() throws InterruptedException {
		 l.lock();
			try{
				while(!full) {	// tant qu'on a pas de requete
					notLibre.await();	// on attends une requete
				}
				send=false;	//on ne peut pas recevoir une autre requete si on est occuper
			}finally {
				l.unlock();
			}
		
	 }
	 
	 public void traiterRequete() {
		 l.lock();
			try{
				ReponseRequete r = new ReponseRequete(now.getClient(),now.getNumReq());	// cree la reponse
				now.getClient().requeteServie(r);	// envoyer la reponse au client emetteur associé a la requete courante
				System.out.println(r); //afficher la reponse
				
				//le programme ne se terminera jamais si on garde la solution de la question 5 ( on aura une famine )
				//car les requete de type 2 boucle a l'infine d'ou le thread serveur ne progressera jamais
				//en utilisant des threads esclaves tous les thread clients et serveur se terminront bien 
				//et on aura seulement les thread des requets de type 2 qui ne se terminent pas
				new Thread(new Exec(now.getType())).start();	
				
				full=false;	// on est plus occuper
				send=true;	// on peut recevoir une nouvelle requete
				now=null;	// m-a-j la requete courante
				giveMe.signalAll();	// signaler les threads qui attends pour envoyer une requete
				
			}finally {
				l.unlock();
			}
		
	 }
	public void run() {
		
		try {
			while (true) {
				System.out.println("Serveur attends une requete");
				attendreRequete();
				
				System.out.println("Traitement en cours .....");
				traiterRequete();
				System.out.println(" =========================Next pleaseeee !!!!!!!!!!!!");
				
			}
		}catch (InterruptedException e) {
			System.out.println("Serveur interrompu");
		}
		
	}

}

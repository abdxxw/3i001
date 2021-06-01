import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Serveur implements Runnable{

	private boolean full; //indiquer si le serveur est occuper ou non
	private boolean send;	//indiquer si le serveur peut recevoir une requete ou non (si le client peut envoyer une requete)
	private ReentrantLock l = new ReentrantLock();
	private Condition notLibre = l.newCondition(); // pour attendre que le serveur recoit une requete
	private Condition giveMe = l.newCondition(); 	//pour attendre que le serveur soit libre
	private Requete now;	// la requete courante
	private ExecutorService es;
	private CompletionService<ReponseRequete> CS;
	private ArrayBlockingQueue<Future<ReponseRequete>> ar;
	
	public Serveur(ExecutorService es, ArrayBlockingQueue<Future<ReponseRequete>> ar) {
		full=false;
		send=true;
		now = null;
		this.ar=ar;
		this.es =es;
		CS = new ExecutorCompletionService<ReponseRequete>(es);
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

			
				CS.submit(new Exec(now));	
				// on utilise get avec tempo pour ne pas etre retarde
				Future<ReponseRequete> r = CS.take();
				ar.add(r);
				System.out.println(r); //afficher la reponse

				full=false;	// on est plus occuper
				send=true;	// on peut recevoir une nouvelle requete
				now=null;	// m-a-j la requete courante
				giveMe.signalAll();	// signaler les threads qui attends pour envoyer une requete
			}catch(InterruptedException e){
				
				System.out.println("gubhyiuoi");
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
			es.shutdown(); // on ajoute le shutdown a la fin pour traiter le cas d 'arret si il y a plus de thread
		}
		
	}

}

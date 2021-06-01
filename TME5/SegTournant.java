import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SegTournant implements Runnable{

	private int posNow; 	//position courante du segement tournant 
	private int posNext;	//position suivante du segement tournant
	private boolean libre;	//etat du segement tournant
	private boolean deplace;	// pour gerer les bonnes positionnement de deplacement
	private boolean appel;
	private PoolHangars pH;		
	private ReentrantLock l = new ReentrantLock();
	private Condition Call = l.newCondition();		//condition sur l'appel
	private Condition Move = l.newCondition();		//condition sur le deplacement
	private Condition Enter = l.newCondition();		//condition sur l'entrer
	private Condition Out = l.newCondition();		//condition sur la sortie
	
	
	public SegTournant(PoolHangars pH) {	// constructeur
		posNow = -1;	
		posNext = -1;
		libre = true;		//libre au debut
		deplace=false;		// on ne peut pas deplacer avant de confirmer la bonne position
		appel = false;		// on ne peut pas continuer avant d etre appeler
		this.pH = pH;

	}

	public void attendreAppel() throws InterruptedException { // attendre un appel de loco
		l.lock();
		try {
			System.out.println("	SegTournant Attent l'appel..");
		while(!appel)	// tantque je ne suis pas appele
			Call.await();	// j'attends qu'on m'appelle
		appel=false;	//apres chaque appel on remets a faux
		deplace=false;	//apres chaque appel on ne peut pas deplacer avant de verifier la bonne position
		}finally {
			l.unlock();
		}
	}
	
	public void appeler(int i) throws InterruptedException {	
		l.lock();
		try{
			while(i==0 && libre == false) // tantque le segement tournant est plein
				Out.await();								//et la prochaine position qu'on veut est le segement d'acceuil
															//on attends que le Loco sort du segement tournant
			posNext = i;	// m-a-j de la position suivante
			System.out.println("	SegTournant est appelé pour la position "+i);
			appel = true;	//on indique qu'on a bien fait l'appel
			Call.signalAll();	// on signal les threads qui attends l'appel
		}finally {
			l.unlock();
		}
	}

	
	public void attendrePositionOK() throws InterruptedException {	// attendre la verification du bonne position
		l.lock();
		try {
			System.out.println("	SegTournant Attent le Deplacement..");
		while(!deplace && posNow<1)	//tantque on a pas verifier la position et qu'on est dans le seg d'acceuil ou a la fin du sortie ( posNow=0 ou -1 )
			Move.await(); // on attend le deplacement
		deplace = false;
		}finally {
			l.unlock();
		}
	}

	public void seDeplacer() throws InterruptedException {
		l.lock();
		try{
			System.out.println("	Deplacement en cours....");
			Thread.sleep(1000);		// pour simuler l'attente d'apres l'enonce
			if(posNow==0 && libre == false)	// si on est dans le segement d'acceuil et un loco est dans le segement tournant
				posNext = pH.getHangar();		// on cherche une position d'un hangar vide 
			
			posNow = posNext;	//on se deplace ( si la condition d'avant n'est pas verifie on sera dans le cas d'appel 0. donc ok )
			deplace = true;		// on indique qu'on est bien deplacer
			Move.signalAll();	// on signal les threads qui attends le bon deplacement
			System.out.println("	Deplacement vers la postion "+posNow+" est fini ....");
				
		}finally {
			l.unlock();
		}
		
	}

	public void attendreEntree() throws InterruptedException {		//attendre l'entre dans le segement tournant
		l.lock();
		try {
		while(libre)	// tantque on est libre
			Enter.await();	//on attend l entree
		deplace=false;	// on ne peut pas deplacer avant verification.
		}finally {
			l.unlock();
		}
		
	}
	public void entrer(int id) throws InterruptedException{
		l.lock();
		try{
			while(!libre){		// traiter le cas ou on essaye d'entrer quand le segement est remplis
				attendreVide();		// on attends qu'il soit vide
			}

			System.out.println("Loco "+id+" est dans le Segment tournant");
			libre = false;		// m-a-j etat du segement
			Enter.signalAll();	// signaler les threads qui attends l'entree
		}finally {
			l.unlock();
		}
	}
	public void attendreVide() throws InterruptedException {	//attendre la liberation du segement tournant
		l.lock();
		try {
		while(!libre)	//tantque on est occupee
			Out.await(); 	//on attends que le loco sort
		posNow = -1;	// indiquer qu'on est libre a traiter un autre loco
		deplace = false;	//on ne peut pas deplacer avant verification
		appel = false;		// on doit attendre un appel
		}finally {
			l.unlock();
		}
		
	}

	public void sortir(int id) throws InterruptedException{
		l.lock();
		try{
			while(libre){		// traiter le cas ou on essaye de faire sortir un loco d'un segement vide
				attendreEntree();		//on attends qu'un loco entre 
			}
			System.out.println("Loco "+id+" est sorti du Segment tournant");
			libre = true;	//m-aj etat seg
			Out.signalAll();	//signaler les threads qui attends la sortie du loco

		}finally {
			l.unlock();
		}
	}
	public int getPosition() {	// getter
		return posNow;
	}

	
	public void run() {
		try {
		while (true) {		//pas un probleme car terminaison traiter avec compteurs des threads actifs
			attendreAppel();	//on attends un appel vers le segement d'acceuil
			seDeplacer();	// on se deplace vers lui
			attendreEntree();	// on attends qu'un lococ entre dans le segement tournant
			seDeplacer();	//on se deplace vers un hangar vide
			attendreVide();	// on attends que le loco entre dans le hangar ( liberation du segement tournant)
		}
		}
		catch (Exception e) {	// on capture l'excpetion qui est levee a cause de interrupt() qu'on a executer sur ce thread dans Loco
			System.out.println("Good Bye.....");	//terminaison du thread
		}
		}

	
}

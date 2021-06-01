import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class Chariot {
	private int poidsMax; // poids maximum
	private int nbMax;	// capacit� du chariot 
	private ArrayList<AleaObjet> list;	// ensemble des marchandises dans le chariot
	private int poidsNow;	// le poids actuel du chariot
	private boolean full;	// indique si le chariot ne peut pas recevoir des marchandise en plus (1) ou non (0)
	private Lock l = new ReentrantLock();	// le lock 
	private Condition canAdd = l.newCondition();	// condition sur le lock " pouvoir ajouter au chariot
	private Condition cantAdd = l.newCondition();	// condition sur le lock " impossible  d'ajouter au chariot
	
	//constructeur
	public Chariot(int poidsMax, int nbMax) {

		this.poidsMax = poidsMax;
		this.nbMax = nbMax;
		poidsNow = 0;
		list = new ArrayList<AleaObjet>();
		full = false; // on peut ajouter (au depart il est vide)
				
	}

	//verifier si le chariot est plein
	public boolean isFull() {
		return list.size() == nbMax;
	}
	

	//verifier si le chariot est vide
	public boolean isEmpty() {
		return 0 == list.size();
	}
	
	//ajout d'une marchandise dans le chariot 
	public boolean addM(AleaObjet m) throws InterruptedException{

		l.lock(); // proteger la section critique
		try{
			while(full){ // tantqu'on peut pas ajouter
				canAdd.await(); // on attend 
			}
			
			if(isFull()||(m.getPoids()+poidsNow > poidsMax)) { // sile chariot est remplis ou le poids restant ne suffit pas 
				System.out.println("On ne peut plus ajouter");
				full=true;	// on mise a jour le boolean
				cantAdd.signalAll();	// on signal au threads qui sont attends la condition cantAdd
				return false;
			}else {
				list.add(m);	// on met la marchandise dans le chariot
				poidsNow+=+m.getPoids(); // mise a jour du poids
				return true;
			}
		}finally{
			l.unlock(); // proteger la section critique
		}
	}
	

	// supprimer une marchandise
	public AleaObjet removeM(){
		
		AleaObjet tmp = null;
		
		if(!isEmpty()) { // si le chariot n'est pas vide 
			tmp = list.remove(list.size()-1); // on supprime
			poidsNow-=tmp.getPoids(); // on m-a-j le poids
		}
		
		return tmp; // return null si vide 
	}
	
	//supprimer tous les marchandise
	public void removeAll() throws InterruptedException{

		l.lock(); // protection de la section critique
		try{
			while(!full) {	// tantqu'on peut ajouter

				boolean b;
				b = cantAdd.await(1,TimeUnit.SECONDS); // on attend
				// on utilise await avec temporisateur pour gerer le cas de terminaison des dechargeurs
				if(!b) { // si une second est passée
					if(!full)	// si on ne peut encore pas ajouter 
						full=true; // on sort de la boucle 
				}
			}
			while(!isEmpty()) // tant que le chariot n'est pas vide 
				removeM(); // on supprime une marchandise
			System.out.println("Chariot Vide");
			full=false;	// le chariot est vide alors on peut ajouter
			canAdd.signalAll(); // signaler les threads qui attends la possibilité de l'ajout
		}finally{
			l.unlock(); // protection de la section critique
		}
		
			
		
	}
	
	
	
}

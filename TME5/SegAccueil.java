
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SegAccueil {
	private boolean libre; // indique l'etat du segment acceuil 
	private ReentrantLock l = new ReentrantLock(); 
	private Condition Free = l.newCondition();	
	
	public SegAccueil() {
		libre=true;
	}
	
	public void reserver() throws InterruptedException{
		l.lock(); // section critique
		try{
			while(!libre)	// tantque le segement n'est pas libre
				Free.await();	// on attents sa liberation
			libre=false;	// on le reserve
			System.out.println("SegAcceuil Reservé");
		}finally{
			l.unlock(); // fin section critique
		}
	}
	
	public void liberer() {
		
		l.lock(); // section critique
		try{
			libre=true;	// on le libere
			Free.signalAll();	// signaler les threads qui attends la liberation
			System.out.println("SegAcceuil Liberé");
		}finally{
			l.unlock(); // fin section critique
		}
	}
	
	public boolean getLibre() { // getter
		return libre;
	}
}

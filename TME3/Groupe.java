import java.util.ArrayList;

public class Groupe implements Runnable {
	private int nb;
	private int id;
	private Salle s;
	public static int cpt=0;
	private static final Object x = new Object();
	private ArrayList<Integer> reserved = new ArrayList<Integer>();
	
	public Groupe(int nb,Salle s) {
		this.nb = nb;
		this.s = s;
		synchronized (x) {
			cpt++;
			id=cpt;
		}
	}

	public int getNb() {
		return nb;
	}
	
	public ArrayList<Integer> getReserved() {
		return reserved;
	}

	public void addRes(int i,int j) {
		reserved.add(i);
		reserved.add(j);
	}
	

	public void deleteRes() {
		reserved.remove(0);
		reserved.remove(0);
	}

	private void trace(String m) {
		System.out.println(m);
		Thread.yield();
		
	}
	public void run() {

		if(s.reserver(this)) {
			trace("Reservation G"+id+" OK");
			
			if(s.annuler(this,nb)) {
				trace("Annulation G"+id+" OK");
				System.out.println(s);
			}
			else
				trace("Annulation G"+id+" ERROR");
		}
		else
			trace("Reservation G"+id+" ERROR");


		
		
		
	}
	
	
}

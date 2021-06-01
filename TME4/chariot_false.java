/*import java.util.ArrayList;

public class Chariot {
	private int poidsMax;
	private int nbMax;
	private ArrayList<AleaObjet> list;
	private int poidsNow;
	private final Object o;
	private boolean cantAdd;
	
	
	public Chariot(int poidsMax, int nbMax) {

		this.poidsMax = poidsMax;
		this.nbMax = nbMax;
		poidsNow = 0;
		list = new ArrayList<AleaObjet>();
		o= new Object();
		cantAdd = false;
				
	}
	
	public boolean isFull() {
		return list.size() == nbMax;
	}
	
	public boolean isEmpty() {
		return 0 == list.size();
	}
	
	public void addM(AleaObjet m) throws InterruptedException{
		synchronized(o) {
			if(isFull()||(m.getPoids()+poidsNow > poidsMax)){
				cantAdd = true;
				o.notifyAll();
			}

			while(cantAdd) {
				o.wait();
			}

		}
		list.add(m);
		poidsNow+=+m.getPoids();
	}
	

	public AleaObjet removeM(){
		AleaObjet tmp = null;
		if(!isEmpty()) {
			tmp = list.remove(list.size()-1);
			poidsNow-=tmp.getPoids();
		}
		return tmp;
	}
	

	public void removeAll() throws InterruptedException{
		synchronized(o) {
			while(!cantAdd)
				o.wait();
			while(!isEmpty())
				removeM();
			cantAdd = false;
			System.out.println("Chariot Vide");
			o.notifyAll();
			}
		
			
		
	}
	
	
	
}
*/
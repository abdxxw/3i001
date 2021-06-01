
public class Test {
	public static void main(String[] args) {
		
		Salle s = new Salle(10,10);
		
		Groupe g1 = new Groupe(20,s);
		Groupe g2 = new Groupe(30,s);
		Groupe g3 = new Groupe(80,s);

		Thread t1 = new Thread(g1);
		Thread t2 = new Thread(g2);
		Thread t3 = new Thread(g3);


		t1.start();
		t2.start();
		t3.start();
		
		try {
			t1.join();
			t2.join();
		    t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
}

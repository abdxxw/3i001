public class Salle {
	private boolean placesLibres[][];
	
	
	
	public Salle(int nbRangs, int nbPlacesParRang) {
		
		placesLibres = new boolean[nbRangs][nbPlacesParRang];
		for(int i=0;i<placesLibres.length;i++) {
			for(int j=0;j<placesLibres[0].length;j++) {
				placesLibres[i][j]=true;
			}
		}
	}
	
	
	
	@Override
	public String toString() {
		String out="";
		for(int i=0;i<placesLibres.length;i++) {
			for(int j=0;j<placesLibres[0].length;j++) {
				if(placesLibres[i][j])
					out+="O ";
				else
					out+="X ";
			}
			out+="\n";
		}
		return out;
	}



	synchronized public boolean capaciteOK(int n) {
		
		for(int i=0;i<placesLibres.length;i++) {
			for(int j=0;j<placesLibres[0].length;j++) {
				if(n<=0)
					return true;
				if(placesLibres[i][j])
					n--;
			}
		}
		return false;
		
	}
	
	synchronized public int nContiguesAuRangI (int n, int i) {
		int j=0,k=0;
		while(j+k<placesLibres[i].length) {
			if(placesLibres[i][j+k]) {
				k++;
			}else{
				j+=k+1;
				k=0;
			}
			
			if(k==n)
				return j;
			
		}
		return -1;
	}
	
	
	synchronized public boolean reserverContigues(Groupe g) {
		int j;
		for(int i=0;i<placesLibres.length;i++) {
			j=nContiguesAuRangI(g.getNb(),i);
			if(j != -1) {
				for(int k=j;k<j+g.getNb();k++) {
					placesLibres[i][k] = false;
					g.addRes(i,k);
				}
					

				return true;
			}
		}
		return false;
	}
	
	synchronized public boolean reserver(Groupe g) {
		int n=g.getNb();
		if(capaciteOK(g.getNb())) {
		
			if(reserverContigues(g)){
				System.out.println(this);
				return true;
			}
		
			for(int i=0;i<placesLibres.length;i++) {
				for(int j=0;j<placesLibres[0].length;j++) {
					if(n<=0){
						System.out.println(this);
						return true;
					}
					if(placesLibres[i][j]) {
						placesLibres[i][j]=false;
						g.addRes(i,j);
						n--;
					}
				}
				
			}
		}
		
		System.out.println(this);
		return false;
		
	}
	/* probleme expection concurentemodif
	synchronized public boolean annuler(Groupe g,int n) {

		Iterator<Integer> it = g.getReserved().iterator();
		int k=0;
		while ((it.hasNext())&&(k<2*n)) {
			placesLibres[it.next()][it.next()]= true;
			g.deleteRes(k);
			g.deleteRes(k+1);
			k+=2;
		}
		
		return (k>=2*n);
			
	}*/
	

	synchronized public boolean annuler(Groupe g,int n) {

		int j,k;
		if(g.getReserved().size() < 2*n)
			return false;
		
		for(int i=0;i<2*n;i+=2) {
			j=g.getReserved().get(0);
			k=g.getReserved().get(1);
			g.deleteRes();

			placesLibres[j][k]= true;
		}
		
		return true;
		
			
	}
	
	
	
}

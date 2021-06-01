public class Client implements Runnable{

    
	private int id;	// numero de client
	public static int cpt=0;
	private static final Object x = new Object(); // proteger le compteur 
    private Salon s;	
    private Barbier b;
	private boolean done;	//indique la fin du rasage
	private boolean come;	//indique l entre du client dans la salle du barbier pour commancer le rassage 
	private static int actifClients=0;	// compteurs des threads actifs
	private Thread end;	// le thread du barbier pour (gerer la terminaison)
	
	public Client(Salon s,Barbier b,Thread end) {	// constructeur
        this.s = s;
        this.b = b;
        done = false;	//le rasage n'est pas encore fini
        come = false;	//pas encore dans la salle de rasage
        this.end=end;	// le thread du barbier
		synchronized (x) {
			cpt++;
			id=cpt;
			actifClients++;	// compter les thread des clients actifs
		}
    }
    
    public synchronized boolean entrerSalon(){	// entrer dans salon si c'est possible
    	
        if (s.getSize() >= s.getNbMax()){	// si la taille de la file est au max
            System.out.println("    Client "+id+" : pas de place je rentre chez moi.");
            return false;	// on retourne faux
        }
        else{
            s.addC(this);	// on ajoute le client 
            System.out.println("    Client "+id+" : je rentre dans la salle d'attente.");

            return true;	// et retourner vrai
        }

    }

    public synchronized void getUp(){ // reveiller le thread de ce client
        this.notify();
    }

    public void setDone(){	
        done = true;
    }
    
    public void setCome(){
        come = true;
    }

    public synchronized void attendreBarbier() throws InterruptedException{	// attendre que le barbier nous invite a rentrer dans la salle de rasage

            System.out.println("    Client "+id+" : J'attend le barbier.");
            while(!come)
                wait();
       
    }


    public synchronized void attendreFinRassage() throws InterruptedException{ // attendre que le barbier nous indique que le rasage est fini

        System.out.println("    Client "+id+" : J'attend la fin de rasage.");
        while(!done)
            wait();
   
    }



    public synchronized void sortirSalon(){		// sortir de la salle d'attente et rentrer dans la salle de rasage
        s.removeC(this);
        System.out.println("    Client "+id+" : je sort de la salle d'attente.");
    }

    public int getID(){	// getter
        return id;
    }

    public void run(){
        try{
            if(entrerSalon()){		// si il ya de la place on rentre 
                b.callBarbier();	// et on notifie le babrbier qu'il ya des clients en attente
                attendreBarbier();	// on attent qu'il nous invite
                b.entrer();	// on entre dans la salle de rasage
                attendreFinRassage();	// attendre la fin du rasage
                b.goHome(id);	// sortir du sallon.
                
            }

            synchronized(x){

            	actifClients--;			// a la fin de chaque Thread on diminue le compteur pour gerer la terminaison
    			if(actifClients == 0)
    					end.interrupt(); // si on a plus de Client on arrete le thread Barbier
    			}
        }catch(InterruptedException e){
        	System.out.println("ne doit pas se passer.....");
        }
    }

    
}
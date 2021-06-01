import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Barbier implements Runnable{
    private Salon s;
    private Client clientNow;	// client qu'on est entrain de le raser
    private ReentrantLock l = new ReentrantLock();	
    private Condition clientExist = l.newCondition(); // condition s'il existe des clients dans la file d attente
    private Condition clientCome = l.newCondition();  // condition s'il existe un client dans la salle de rasage
    private Condition clientGo = l.newCondition();  // condition si le client est partie ou pas encore 

    public Barbier(Salon s){
        this.s=s;
        clientNow = null;	// pas de client au debut
    }

    public void attendreClient() throws InterruptedException{ // attendre qu'au moins un client soit dans la file d attente 
        l.lock();	// proteger la section critique
        try{
            System.out.println("Barbier : J'attend des clients.");
            while(s.getSize() < 1){
                clientExist.await();	// tantque il ya pas de client on attent qu'un client existe
            }
            s.getClient().setCome();	//on appel ce client
            s.getClient().getUp();	// et on notifie  son thread
            System.out.println("Barbier : Je suis pret.");
        }finally{
            l.unlock();	// fin section critique
        }

    }
    
    
    public void callBarbier(){ // indique au client qu'il existe des clients
        l.lock(); // proteger la section critique
        try{
            clientExist.signalAll();	// signaler au thread qui attendent des clients dans la file d'attente 
        }finally{
            l.unlock(); // fin section critique
        }
    }

    public void attendreEntrerClient() throws InterruptedException{	// attendre qu'un client rentre dans la salle de rasage 
        l.lock(); // proteger la section critique
        try{
            System.out.println("Barbier : J'attend que le client rentre.");
            while(clientNow == null)	// tantque pas de client
                clientCome.await();	// on attends qu'il vient


        }finally{
            l.unlock(); // fin section critique
        }
    }


    public void entrer(){   
        l.lock(); // proteger la section critique
        try{
            clientNow=s.getClient(); // on initialise le client courant
            System.out.println("        Client "+clientNow.getID()+" : je rentre chez le barbier.");
            s.removeC(clientNow);	// on le supprime de la liste d'attente
            clientCome.signalAll();	// on signal au thread qui attend qu'un client rentre pour le raser qu'il est la

        }finally{
            l.unlock(); // fin section critique
        }
    }
    
    public void raser() throws InterruptedException {
        l.lock(); // proteger la section critique
        try{

        		System.out.println("Barbier : Bienvenu client "+clientNow.getID()+" , Rasage en cours..........");
        		Thread.sleep(500);		// simulation de rasage par un sleep
        		clientNow.setDone(); // client a fini d'etre rase
           		clientNow.getUp();	// on notifie son thread
        }finally{
        	   l.unlock(); // fin section critique
       }
    }

    public void attendreVide() throws InterruptedException{	// attendre que le barbier se libere
        l.lock(); // proteger la section critique
        try{

            System.out.println("Barbier : J'attend que le client part.");
            while(clientNow != null)	// tantque il existe encore un client dans la salle 
                clientGo.await();	// on attends qu'il part 

          
            if(s.getSize() > 0)	{ //si il ya encore des clients dans la file d attente on les appelle
            	System.out.println("Barbier : AU SUIVANT !!!!!!!!!!!!!!!!!!");
            	s.getClient().getUp();
            }
        }finally{
            l.unlock(); // fin section critique
        }
    }
    
    public void goHome(int id){
        l.lock(); // proteger la section critique
        try{  
            System.out.println("        Client "+id+" : Merci pour le rasage. Bye====================================");
            clientNow=null;		// liberer le client courant
            clientGo.signalAll();	//signaler au thread qui attend que le client part
        }finally{
            l.unlock(); // fin section critique
        }
    }

    
    public void run(){
        try{
            while(true){	//infinimenet

                attendreClient(); // on attend qu'il soit en moins un client en attente	
                attendreEntrerClient();	//on attend qu'il rentre chez nous
                raser();	//on rase le client
                attendreVide();	//on attends qu'il part
            }
        }catch(InterruptedException e){
        	System.out.println("Je ferme le sallon good bye.....");	//terminaison du thread
        }
    }

}
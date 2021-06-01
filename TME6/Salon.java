import java.util.ArrayList;

public class Salon{

    private int nbMax;	// nombre maximum de place dans la salle d'attente
    private ArrayList<Client> salleAtt;	//file d'attente
    
    public Salon(int nbMax){ // constructeur
        this.nbMax=nbMax;
        salleAtt = new ArrayList<Client>();
    }

    public int getSize(){	// retourner le nombre courant des clients dans la file
        return salleAtt.size();
    }

    public synchronized void addC(Client c){	// ajouter un client a la fin de la liste
        salleAtt.add(c);
    }


    public synchronized void removeC(Client c){	// supprimer un client 
        salleAtt.remove(c);
    }

    public int getNbMax(){	// getter
        return nbMax;
    }

    public synchronized Client getClient(){	// on retourne le le client qui est dans la tete de la file (FIFO)
        return salleAtt.get(0);
    }
}
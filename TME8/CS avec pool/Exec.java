
import java.util.Random;

import java.util.concurrent.*;
//Thread esclave

public class Exec implements Callable<ReponseRequete>{
    private Random rand = new Random();
    private Requete now;

    public Exec(Requete r){
        now=r;
    }

    public ReponseRequete work() throws InterruptedException{	//traitement des requetes par type
        
		ReponseRequete r = new ReponseRequete(now.getClient(),now.getNumReq());	// cree la reponse
        if(now.getType()==1){
            System.out.println("type 1 : dormir ..../////////////////////////////////////////////////////");
            Thread.sleep(rand.nextInt(2000));	// dormir pour une valeur rand 2 sec max
        }else{
            System.out.println("type 2 : dormir infinieeeeeeeeeeeeeeeee/////////////////////////////////////////");
            Thread.sleep(rand.nextInt(1000));	// dormir pour une valeur rand 2 sec max
        }

        return r;
    }

    public ReponseRequete call(){
        try{
            return work();

        }catch(InterruptedException e){
            return null;
        }
    }
}
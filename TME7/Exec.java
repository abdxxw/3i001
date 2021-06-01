
import java.util.Random;

//Thread esclave

public class Exec implements Runnable{
    private int type;
	private Random rand = new Random();

    public Exec(int t){
        type=t;
    }

    public void work() throws InterruptedException{	//traitement des requetes par type
        if(type==1){
            System.out.println("type 1 : dormir ..../////////////////////////////////////////////////////");
            Thread.sleep(rand.nextInt(2000));	// dormir pour une valeur rand 2 sec max
        }else{
            System.out.println("type 2 : dormir infinieeeeeeeeeeeeeeeee/////////////////////////////////////////");
            while(true){	// traitement treeeeeeeeeeeeeeees long 

            }
        }
    }
    public void run(){
        try{
            work();

        }catch(InterruptedException e){
            
        }
    }
}
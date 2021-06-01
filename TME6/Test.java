public class Test{

    public static void main(String[] args){

        Salon s = new Salon(10);
        Barbier b = new Barbier(s);		
		
        Thread Tb = new Thread(b);
        Tb.start();	

        for(int i=0;i<20;i++){
            new Thread(new Client(s,b,Tb)).start();	
        }
        
        //for(int i=0;i<20;i++){
          //  tab[i].join();	
        //}
        //tb.interrupt();
        														
    }


}
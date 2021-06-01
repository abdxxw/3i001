import java.io.*;

public class TestProduitParallele {

public static void main(String[] args){
        try{
        MatriceEntiere m1 = new MatriceEntiere(new File("../lib/donnees_produit1"));
        MatriceEntiere m2 = new MatriceEntiere(new File("../lib/donnees_produit2"));

        MatriceEntiere m = new MatriceEntiere(m1.getNbLignes(),m2.getNbColonnes());
        
        for(int i=0;i<m.getNbLignes();i++){
            for(int j=0;j<m.getNbColonnes();j++){
                new Thread(new CalculElem(m,m1,m2,i,j)).start();
            }
        }

         System.out.println(m);

        }catch(FileNotFoundException f){
            System.out.println("WHHHHYYYYYYYYYYY");
        }
        
    }
    
}
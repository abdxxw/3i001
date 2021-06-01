import java.io.*;

public class test{

    public static void main(String[] args){
        try{
        MatriceEntiere m1 = new MatriceEntiere(new File("testfiles/donnees_produit1"));
        MatriceEntiere m2 = new MatriceEntiere(new File("testfiles/donnees_produit2"));
        MatriceEntiere m3 = new MatriceEntiere(new File("testfiles/donnees_somme1"));
        MatriceEntiere m4 = new MatriceEntiere(new File("testfiles/donnees_somme2"));

        System.out.println(m1.prodMat(m2));
        System.out.println(m3.sommeMat(m4));
        
        }catch(FileNotFoundException f){
            System.out.println("WHHHHYYYYYYYYYYY");
        }catch (TaillesNonConcordantesException t){
            System.out.println("CHANGE THE MAT");
        }
        
    }
}
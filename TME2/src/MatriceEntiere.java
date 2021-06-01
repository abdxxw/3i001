import java.io.*;
import java.util.Scanner; 

public class MatriceEntiere{

    private int x,y;
    private int[][] tab;


    public MatriceEntiere(int lignes, int colonnes){

        this.x=lignes;
        this.y=colonnes;
        this.tab = new int[this.x][this.y];

    }

    public MatriceEntiere(File fichier) throws FileNotFoundException{

        BufferedReader b = new BufferedReader(new FileReader(fichier));
        Scanner sc = new Scanner(b);
        this.x=sc.nextInt();
        this.y=sc.nextInt();
        this.tab = new int[this.x][this.y];
        for(int i=0;i<this.x;i++)
            for(int j=0;j<this.y;j++)
                this.tab[i][j]= sc.nextInt();
        
    }

    public int getElem(int i, int j){
        return this.tab[i][j];

    }

    public void setElem(int i, int j, int val){
        this.tab[i][j] = val;
    }

    public String toString(){
        String tmp= this.x+"\n"+this.y+"\n";
         for(int i=0;i<this.x;i++){

            for(int j=0;j<this.y;j++){
                tmp=tmp+this.tab[i][j]+" ";

            }
                tmp=tmp+"\n";
         }
        return tmp;
    }

    public void initZero (){

        for(int i=0;i<this.x;i++)
            for(int j=0;j<this.y;j++)
                this.tab[i][j]= 0;
    }

    public MatriceEntiere transpMat(){
        MatriceEntiere trans = new MatriceEntiere(this.y,this.x);

        for(int i=0;i<this.x;i++)
            for(int j=0;j<this.y;j++)
                trans.tab[j][i] = this.tab[i][j];

        return trans;
    }

    public MatriceEntiere sommeMat(MatriceEntiere m)throws TaillesNonConcordantesException{

        if((m.x != this.x)||(m.y != this.y)) throw new TaillesNonConcordantesException();
        MatriceEntiere tmp = new MatriceEntiere(this.x,this.y);
        tmp.initZero();

        for(int i=0;i<this.x;i++)
            for(int j=0;j<this.y;j++)
                tmp.tab[i][j] += this.tab[i][j]+m.tab[i][j];

        return tmp;
    }


    public MatriceEntiere prodSca(int k){
        MatriceEntiere m = new MatriceEntiere(this.x,this.y);

        for(int i=0;i<this.x;i++)
            for(int j=0;j<this.y;j++)
                m.tab[j][i] = this.tab[i][j]*k;

        return m;
    }

    public MatriceEntiere prodMat(MatriceEntiere m2) throws TaillesNonConcordantesException{

        if(m2.x != this.y) throw new TaillesNonConcordantesException();

        MatriceEntiere m = new MatriceEntiere(this.x,m2.y);
        m.initZero();
        
        for(int i=0;i<this.x;i++){
            for(int k=0;k<m2.y;k++){
                for(int j=0;j<this.y;j++){
                    m.tab[i][k]+=m2.tab[j][k]*this.tab[i][j];
                }
            }
            
        }
        return m;
    }

    public int getNbLignes(){
        return x;
    }

    public int getNbColonnes(){
        return y;
    }

    public static int produitLigneColonne(MatriceEntiere m1, int i, MatriceEntiere m2, int j) throws TaillesNonConcordantesException{
            if(m1.y != m2.x) throw new TaillesNonConcordantesException();
            int out=0;
            for(int k=0;k<m1.y;k++)
                out+=m1.getElem(i,k)*m2.getElem(k,j);

            return out;
    } 
}
import graphic.*;
import java.awt.Point;

public class test{

    public static void main (String[] args){

        // Question 1 ================================
        Window w = new Window(500,500,"Question 1");
        w.plotLine(new Point(100,100),new Point(100,400));
        w.plotLine(new Point(100,400),new Point(400,250));
        w.plotLine(new Point(400,250),new Point(100,100));
        

        // Question 2 ================================
        Window w2 = new Window(500,500,"Question 2");
        DessineLigne d1 = new DessineLigne(w2,100,100,100,400);
        DessineLigne d2 = new DessineLigne(w2,100,400,400,250);
        DessineLigne d3 = new DessineLigne(w2,400,250,100,100);
        
        /*d1.start();
        d2.start();
        d3.start();
*/
        // Question 3 ================================

        Window w3 = new Window(500,500,"Question 3");
        DessineLigne_bis d4 = new DessineLigne_bis(w3,100,100,100,400);
        DessineLigne_bis d5 = new DessineLigne_bis(w3,100,400,400,250);
        DessineLigne_bis d6 = new DessineLigne_bis(w3,400,250,100,100);
        
        Thread t1 = new Thread(d4);
        Thread t2 = new Thread(d5);
        Thread t3 = new Thread(d6);

        t1.start();
        t2.start();
        t3.start();
        }
}
import graphic.*;
import java.awt.Point;

public class DessineLigne extends Thread{
    int x1,y1,x2,y2;
    Window w;

    public DessineLigne(Window w,int a,int b,int c,int d){
        x1=a;
        y1=b;
        x2=c;
        y2=d;
        this.w=w;
    }
    public void run(){
        w.plotLine(new Point(x1,y1),new Point(x2,y2));
    }
}
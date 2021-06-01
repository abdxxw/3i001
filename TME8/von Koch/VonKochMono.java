import graphic.Window;
import java.awt.Point;
import java.util.concurrent.*;

public class VonKochMono {
	private final static double LG_MIN = 8.0;
	Window f;

	public VonKochMono (Window f, Point a, Point b, Point c) {
		this.f = f;
		ExecutorService e = Executors.newFixedThreadPool(10);

		e.execute(new Cote(f, b, a, e));
		e.execute(new Cote(f, a, c, e));
		e.execute(new Cote(f, c, b, e));


	}			
}

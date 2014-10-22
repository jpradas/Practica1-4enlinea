package practica1;

public class Main {
	private static java.util.Scanner in = new java.util.Scanner(System.in);
	private static Partida partida = new Partida();
	
	public static void main(String[] args) {
		Controlador c = new Controlador(partida,in);
		c.run();
		in.close();
	}

}

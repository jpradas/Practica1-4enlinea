package main;
import java.util.Scanner;
import logica.Partida;
import control.Controlador;

public class Main {
	private static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		Partida partida = new Partida();
		Controlador c = new Controlador(partida,in);
		c.run();
	}

}

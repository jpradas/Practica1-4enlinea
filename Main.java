package main;
import java.util.Scanner;

import logica.Partida;
import logica.ReglasJuegoConecta4;
import control.Controlador;
import control.TipoJuego;

public class Main {
	private static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		Partida partida = new Partida(new ReglasJuegoConecta4());
		Controlador c = new Controlador(partida,in, TipoJuego.CONECTA4);
		c.run();
	}

}

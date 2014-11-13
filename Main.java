package main;
import java.util.Scanner;
import logica.Partida;
import control.Controlador;


/** Primera practica de la asignatura Tecnologia de la Programacion del Grupo 2B
 * @author Emanuel Ramirez, Josue Pradas
 * @version Practica 1 TP , V1.0
 * Clase main que contiene la clase principal del programa, asi como la llamada inicial.
 */
public class Main {
	
	private static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		Partida partida = new Partida();
		Controlador c = new Controlador (partida,in);
		c.run();
	}

}

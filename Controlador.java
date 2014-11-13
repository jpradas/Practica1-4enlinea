package control;

import java.util.Scanner;
import logica.Ficha;
import logica.Partida;

public class Controlador {
	private Partida partida;
	private Scanner in;
	
	/**
	 * La constructora Controlador asigna a sus atributos la partida creada por Main y el lector Scanner.
	 * @param partida Es la partida creada por el Main la cual se pasa a controlador para que la ejecute
	 * @param in Es el parametro el cual recibe lo escrito por teclado.
	 */
	public Controlador(Partida partida, Scanner in){
		this.partida = partida;
		this.in = in;
	}
	
	/**
	 * El metodo run es el encargado de ejecutar la partida y ser de arbitro entre el usuario y la CPU, es el motor del juego.
	 */
	public void run(){
		this.partida.reset();
		String opcion = "";
		while(!opcion.equalsIgnoreCase("SALIR") && !this.partida.terminada()){
			System.out.println(this.partida.toString());
			System.out.println("Que quieres hacer? ");
			opcion = this.in.next();
			if(opcion.equalsIgnoreCase("PONER")){
				System.out.println("-- Introduce la columna: ");
				int col = this.in.nextInt() - 1;
				if(!this.partida.ejecutaMovimiento(col)){
					System.out.println("Movimiento incorrecto");
				}
				else{
					if(this.partida.terminada()){
						Ficha ganador = this.partida.getFichaGanador();
						if(ganador == Ficha.VACIA){
							System.out.println("Partida en tablas");
							opcion = "salir";
						}
						else{
							System.out.println("Gana la " + ganador);
							opcion = "salir";
						}
					}
				}
			}
			else if(opcion.equalsIgnoreCase("DESHACER")){
				if(!this.partida.desHacer()){
					System.out.println("Imposible Deshacer");
				}
			}
			else if(opcion.equalsIgnoreCase("REINICIAR")){
				this.partida.reset();
			}
		}
	}
	
}

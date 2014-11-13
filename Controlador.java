package control;
import java.util.Scanner;
import logica.Partida;
import logica.Ficha;


public class Controlador {
	private Partida partida;
	private Scanner in;
	
	/** Constructora para el objeto Controlador que lleva el control de la partida.
	 * @param partida instancia de partida creada.
	 * @param in parametro de tipo Scanner 
	 */
	public Controlador (Partida partida, Scanner in){
		this.partida = partida;
		this.in = in;
	}
	
	/**metodo que lleva el control de la partida, asi como los menus 
	 * para elegir opciones: 
	 * -Poner: coloca una ficha en la columna deseada
	 * -Deshacer: Deshace el ultimo movimiento mientras sea posible
	 * -Salir: Sale de la partida.
	 */
	public void run(){
		String opcion = "";
		while(!opcion.equalsIgnoreCase("SALIR") && !this.partida.partidaTerminada()){
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
					if(this.partida.partidaTerminada()){
						Ficha ganador = this.partida.getGanador();
						if(ganador == Ficha.VACIA){
							System.out.println("Partida en tablas");
							opcion = "salir";
						}
						else{
							System.out.println("Ganan la " + ganador);
							opcion = "salir";
						}
					}
				}
			}
			else if(opcion.equalsIgnoreCase("DESHACER")){
				if(!this.partida.undo()){
					System.out.println("Imposible Deshacer");
				}
			}
			else if(opcion.equalsIgnoreCase("REINICIAR")){
				this.partida.reset();
			}
		}
	}
}

package control;

import java.util.Scanner;

import comandos.Comando;
import comandos.ParserAyudaComandos;
import Excepciones.ErrorDeEjecucion;
import Factorias.FactoriaJuego;
import Jugadores.Jugador;
import logica.Ficha;
import movimientos.MovimientoComplica;
import movimientos.MovimientoConecta4;
import logica.Partida;
import reglas.ReglasJuegoComplica;
import reglas.ReglasJuegoConecta4;
import movimientos.Movimiento;

/**Clase que controla la ejecucion de la aplicacion
*
*/

public class Controlador {
	private Partida partida;
	private Scanner in;
	private Jugador jugador1;
	private Jugador jugador2;
	private FactoriaJuego factoria;
	private boolean salir = false;
	//private TipoJuego jugandoA;
	
	/** Constructora para el objeto Controlador que lleva el control de la partida.
	 * @param partida instancia de partida creada.
	 * @param in parametro de tipo Scanner 
	 * @param juego determina el tipo de juego, complica o conecta 4.
	 */
	public Controlador(FactoriaJuego f, Partida partida, Scanner in){
		this.factoria = f;
		this.partida = partida;
		this.in = in;
		this.jugador1 = this.factoria.creaJugadorHumano(this.in);
		this.jugador2 = this.factoria.creaJugadorHumano(this.in);
	}
	
	public void undo() throws ErrorDeEjecucion{
		this.partida.desHacer();
	}
	
	public void jugar(FactoriaJuego factoria){
		this.factoria = factoria;
		this.partida = new Partida(this.factoria.creaReglas());
		this.jugador1 = this.factoria.creaJugadorHumano(this.in);
		this.jugador2 = this.factoria.creaJugadorHumano(this.in);
	}
	
	public void reiniciar(){
		this.partida.reset(this.factoria.creaReglas());
		this.jugador1 = this.factoria.creaJugadorHumano(this.in);
		this.jugador2 = this.factoria.creaJugadorHumano(this.in);
	}
	
	public void ejecutarMovimiento(){
		// poner un get tablero en partida y un get turno para saber a que jugador le toca
		this.jugador1.obtenFilaColumna(tablero, color);
		this.factoria.creaMovimiento();
	}
	
	public void ponJugador(Ficha color, String tipoJugador){
		if(color == Ficha.BLANCA){
			if(tipoJugador.equalsIgnoreCase("ALEATORIO")){
				this.jugador1 = this.factoria.creaJugadorAleatorio();
			}
			else{
				this.jugador1 = this.factoria.creaJugadorHumano(this.in);
			}
		}
		else{
			if(tipoJugador.equalsIgnoreCase("ALEATORIO")){
				this.jugador2 = this.factoria.creaJugadorAleatorio();
			}
			else{
				this.jugador2 = this.factoria.creaJugadorHumano(this.in);
			}
		}
	}
	
	public void salir(){
		this.salir = true;
	}
	
	/**Metodo que lleva el control de la partida, asi como los menus 
	 * para elegir opciones: 
	 * -Jugar "c4" o "co" eliges el modo de juego y reinicia la partida al cambiar
	 * -Poner: coloca una ficha en la columna deseada
	 * -Deshacer: Deshace el ultimo movimiento mientras sea posible
	 * -Salir: Sale de la partida.
	 */
	public void run(){
		String orden;
		Comando comando;
		do {
			this.salir = false;
			System.out.println(this.partida.toString());
			System.out.println("Que quieres hacer? ");
			orden = this.in.nextLine();
			comando = ParserAyudaComandos.parsea(orden);
			if(comando != null){
				try{
					comando.execute(this);
				}
				catch(ErrorDeEjecucion e){
					System.err.println("Error de Ejecucion");
				}
			}
			else{
				System.err.println("Orden no entendida");
			}
		} while(!this.salir && !this.partida.terminada());
	}
		
		
		
		/*String opcion = "";
		while(!opcion.equalsIgnoreCase("SALIR") && !this.partida.terminada()){
			System.out.println(this.partida.toString());
			System.out.println("Que quieres hacer? ");
			opcion = null;
			opcion = this.in.nextLine();
			if(opcion.equalsIgnoreCase("PONER")){
				System.out.println("-- Introduce la columna: ");
				int col = this.in.nextInt() - 1;
				if(this.jugandoA == TipoJuego.CONECTA4){
					Movimiento mov = new MovimientoConecta4(col, this.partida.getJugador());
					if(!this.partida.ejecutaMovimiento(mov)){
						System.err.println("Movimiento incorrecto");
					}
					else{
						if(this.partida.terminada()){
							if(this.partida.getFichaGanador() == Ficha.VACIA){
								System.out.println("Partida en tablas");
								opcion = "salir";
							}
							else{
								System.out.println("Gana la " + this.partida.getFichaGanador());
								opcion = "salir";
							}
						}
					}
				}
				else if(this.jugandoA == TipoJuego.COMPLICA){
					Movimiento mov = new MovimientoComplica(col, this.partida.getJugador());
					if(!this.partida.ejecutaMovimiento(mov)){
						System.err.println("Movimiento incorrecto");
					}
					else{
						if(this.partida.terminada()){
								System.out.println("Gana la " + this.partida.getFichaGanador());
								opcion = "salir";
							}
						}
					}
				this.in.nextLine();
			}
			else if(opcion.equalsIgnoreCase("DESHACER")){
				if(!this.partida.desHacer()){
					System.err.println("Imposible Deshacer");
				}
			}
			else if(opcion.equalsIgnoreCase("REINICIAR")){
				if(this.jugandoA == TipoJuego.CONECTA4){
					this.partida.reset(new ReglasJuegoConecta4());
				}
				else{
					this.partida.reset(new ReglasJuegoComplica());
				}
			}
			else if(opcion.equalsIgnoreCase("JUGAR C4")){
				this.jugandoA = TipoJuego.CONECTA4;
				this.partida.reset(new ReglasJuegoConecta4());
			}
			else if(opcion.equalsIgnoreCase("JUGAR CO")){
				this.jugandoA = TipoJuego.COMPLICA;
				this.partida.reset(new ReglasJuegoComplica());
			}
		}
	}*/
}

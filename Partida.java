package logica;
import java.util.Stack;

import Excepciones.DatosIncorrectos;
import Excepciones.ErrorDeEjecucion;
import Excepciones.MovimientoInvalido;
import Factorias.FactoriaJuego;
import Jugadores.Jugador;
import reglas.ReglasJuego;
import movimientos.Movimiento;

/**Clase que representa el estado de una partida
 * 
 *
 */
public class Partida {
	private ReglasJuego reglas;
	private Tablero tablero;
	private Ficha turno;
	private boolean terminada = false;
	private Ficha ganador = Ficha.VACIA;
	private Stack<Movimiento> undoStack;		
	private int numUndo;
	private int contadorUndo; 
	public final static int GANAN = 4;
	

	/**
	 * La constructora Partida crea un tablero con un numero determinado de filas y columnas. 
	 * @param reglas determina que tipo de partida se va a jugar y su conjunto de reglas.
	 */

	
	public Partida(ReglasJuego reglas){
		this.reset(reglas);
	}
	
	/** devuelve el valor de la ficha ganadora
	 * @return ganador 
	 */

	public Ficha getFichaGanador(){
		return this.ganador;
	}
	
	public Tablero getTablero(){
		return this.tablero;
	}
	
	/**regresa true si esta terminada la partida 
	 * @return terminada
	 */
	public boolean terminada(){
		return this.terminada;
	}
	
	/** resetea el valor de todos los atributos de la partida 
	 * 
	 * @param reglas determina el tipo de partida que se inicia. 
	 */
	
	public void reset(ReglasJuego reglas){
		this.reglas = reglas;
		this.tablero = this.reglas.iniciaTablero();
		this.tablero.reset();
		this.turno = Ficha.BLANCA;
		this.numUndo = 0;
		//this.contadorUndo = 10;
	}
	
	
	/** metodo para deshacer un movimiento  si el numero de undos es valido. 
	 * 
	 * @return valido si el deshacer ha sido valido 
	 */
	public boolean desHacer() throws ErrorDeEjecucion{		
		boolean valido = true;
		if(this.numUndo <= 0 || this.contadorUndo <= 0){
			valido = false;
		}
		else{
			this.numUndo--;
			this.contadorUndo--;
			Movimiento mov = this.undoStack.peek();
			mov.undo(this.tablero);
			this.turno = mov.getJugador();
		}
		return valido;
	}
	
	/** metodo para ejectuar el moviemiento 
	 * 
	 * @param mov instancia del movimiento dependendiendo del tipo de juego
	 * @return valido si el movimiento ha sido valido
	 */
	
	public void ejecutaMovimiento(Movimiento mov) throws MovimientoInvalido{
		if(this.terminada){
			throw new MovimientoInvalido("Partida ya terminada");
		}
		Ficha color = mov.getJugador();
		if(color != this.turno){
			throw new MovimientoInvalido("Turno incorrecto");
		}
		mov.ejecutaMovimiento(this.tablero);
		this.undoStack.push(mov);
		if(this.reglas.hayGanador(mov.getColumna(), mov.getFila(), this.turno, this.tablero) != Ficha.VACIA){
			this.terminada = true;
			this.ganador = this.reglas.hayGanador(mov.getColumna(), mov.getFila(), this.turno, this.tablero);
		}
		this.turno = this.reglas.siguienteTurno(this.turno);
		this.numUndo++;
		if(this.contadorUndo < 10){
			this.contadorUndo++;
		}
		// codigo asociado al control de ganador y cambio de turno
		
		
		/*boolean valido = true;
		if(!mov.ejecutaMovimiento(this.tablero)){ // ahora el ejecuta es void no booleano
			valido = false;
		}
		else{
			if(this.reglas.hayGanador(mov.getColumna(), mov.getFila(), this.turno, this.tablero) != Ficha.VACIA){
					this.terminada = true;
					this.ganador = this.reglas.hayGanador(mov.getColumna(), mov.getFila(), this.turno, this.tablero);
			}
			this.turno = this.reglas.siguienteTurno(this.turno);
			this.undoStack.add(mov);
			this.numUndo++;
			if(this.contadorUndo < 10){
				this.contadorUndo++;
			}
		}
		return valido;*/
	}
	
	public Movimiento getMovimiento(FactoriaJuego factoria, Jugador jugador) throws DatosIncorrectos{
		try{
			return jugador.getMovimiento(factoria, this.tablero, this.turno);
		}
		catch (DatosIncorrectos e){
			throw e;
		}
	}
	
	/** metodo que regresa el turno actual del jugador
	 * 
	 * @return turno 
	 */
	public Ficha getJugador(){
		return this.turno;
	}
	
	
	public String toString(){
		String mensaje = this.tablero.toString();
		String nuevalinea = System.getProperty("line.separator");
		String ficha = "";
		if(this.turno == Ficha.BLANCA){
			ficha = "blancas";
		}
		else if(this.turno == Ficha.NEGRA){
			ficha = "negras";
		}
		return mensaje = mensaje + nuevalinea + "Juegan " + ficha;
	
	}
}

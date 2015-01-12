package logica;


public class Partida {
	private ReglasJuego reglas;
	private Tablero tablero;
	private Ficha turno;
	private boolean terminada = false;
	private Ficha ganador = Ficha.VACIA;
	private Movimiento[] undoStack = new Movimiento[100];		
	private int numUndo;
	private int contadorUndo; 
	public final static int GANAN = 4;
	
	public Partida(ReglasJuego reglas){
		this.reglas = reglas;
		this.tablero = this.reglas.getTablero();
		this.turno = Ficha.BLANCA;
		this.numUndo = 0;
		this.contadorUndo = 10;
	}
	
	public Ficha getFichaGanador(){
		return this.ganador;
	}
	
	public boolean terminada(){
		return this.terminada;
	}
	
	public void reset(ReglasJuego reglas){
		this.reglas = reglas;
		this.tablero = this.reglas.getTablero();
		this.tablero.reset();
		this.turno = Ficha.BLANCA;
		this.numUndo = 0;
		this.contadorUndo = 10;
	}
	
	public boolean desHacer(){		
		boolean valido = true;
		if(this.numUndo <= 0 || this.contadorUndo <= 0){
			valido = false;
		}
		else{
			this.numUndo--;
			this.contadorUndo--;
			Movimiento mov = this.undoStack[this.numUndo];
			mov.undo(this.tablero);
			this.turno = mov.getJugador();
		}
		return valido;
	}
	
	public boolean ejecutaMovimiento(Movimiento mov){
		boolean valido = true;
		if(!mov.ejecutaMovimiento(this.tablero)){
			valido = false;
		}
		else{
			if(this.reglas.hayGrupo(mov.getColumna(), mov.getFila())){
				if(this.reglas.getGanador() != Ficha.VACIA){
					this.terminada = true;
					this.ganador = this.reglas.getGanador();
				}
			}
			this.turno = this.reglas.siguienteTurno(this.turno);
			this.undoStack[this.numUndo]= mov;
			this.numUndo++;
			if(this.contadorUndo < 10){
				this.contadorUndo++;
			}
		}
		return valido;
	}
	
	/*public boolean ejecutaMovimiento(int col){
		boolean moValido = false;
		if(col < 0 || col >= this.columnas){
			moValido = false;
		}
		else{
			int altura = this.tablero.devolverAlturaDesocupada(col);
			if(altura <= this.filas && altura >= 0){
				this.tablero.setFicha(this.turno, col, altura);
				moValido = true;
				this.undoStack[numUndo] = col;
				numUndo++;
				if(numUndo == 42){
					this.terminada = true;
					this.ganador = Ficha.VACIA;
				}
				if(this.reglas.hayGrupo(col, altura)){
					this.terminada = true;
					this.ganador = this.turno;
				}
				this.turno = this.reglas.siguienteTurno(this.turno);
			}
			else{
				moValido = false;
			}
		}
		return moValido;
	}*/
	
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

package logica;


public class Partida {
	private ReglasJuego reglas;
	private Tablero tablero;
	private Ficha turno;
	private boolean terminada = false;
	private Ficha ganador = Ficha.VACIA;
	private int[] undoStack = new int [42];		
	private int numUndo = 0;
	private int filas;
	private int columnas;
	public final static int GANAN = 4;
	
	public Partida(ReglasJuego reglas){
		this.reglas = reglas;
		this.tablero = this.reglas.getTablero();
		this.filas = this.tablero.alto;
		this.columnas = this.tablero.ancho;
		this.turno = Ficha.BLANCA;
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
		this.filas = this.tablero.alto;
		this.columnas = this.tablero.ancho;
		this.turno = Ficha.BLANCA;
	}
	
	public boolean desHacer(){
		boolean valido = false;
		if(this.numUndo <= 0){
			valido = false;
		}
		else{
			int altura = this.tablero.devolverAlturaDesocupada(this.undoStack[this.numUndo - 1]) + 1;
			this.tablero.setFicha(Ficha.VACIA, this.undoStack[this.numUndo - 1] , altura);
			this.numUndo = this.numUndo - 1;
			valido = true;
		}
		this.turno = this.reglas.siguienteTurno(this.turno);
		return valido;
	}
	
	public boolean ejecutaMovimiento(Movimiento mov){
		boolean valido = true;
		if(!mov.ejecutaMovimiento(this.tablero)){
			valido = false;
		}
		else{
			if(this.reglas.hayGrupo(mov.getColumna(), mov.getFila())){
				this.terminada = true;
				this.ganador = this.turno;
			}
			this.turno = this.reglas.siguienteTurno(this.turno);
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

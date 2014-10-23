package logica;

public class Partida {
	private Tablero tablero;
	private Ficha turno = Ficha.BLANCA;
	private boolean terminada = false;
	private Ficha ganador = Ficha.VACIA;
	private int[] undoStack = new int [42];		
	private int numUndo = 0;
	private final static int FILAS = 6;
	private final static int COLUMNAS = 7;
	
	public Partida(){
		this.tablero = new Tablero(FILAS, COLUMNAS);
	}
	
	public Ficha getFichaGanador(){
		return this.ganador;
	}
	
	public boolean terminada(){
		return this.terminada;
	}
	
	public void reset(){
		this.tablero.reset();
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
		if(this.turno == Ficha.BLANCA){
			this.turno = Ficha.NEGRA;
		}
		else if(this.turno == Ficha.NEGRA){
			this.turno = Ficha.BLANCA;
		}
		return valido;
	}
	
	public boolean ejecutaMovimiento(int col){
		boolean moValido = false;
		if(col < 0 || col >= COLUMNAS){
			moValido = false;
		}
		else{
			int altura = this.tablero.devolverAlturaDesocupada(col);
			if(altura <= FILAS && altura >= 0){
				this.tablero.setFicha(this.turno, col, altura);
				moValido = true;
				this.undoStack[numUndo] = col;
				numUndo++;
				if(numUndo == 42){
					this.terminada = true;
					this.ganador = Ficha.VACIA;
				}
				if(this.tablero.hayGrupo()){
					this.terminada = true;
					this.ganador = this.turno;
				}
				if(this.terminada == false && moValido == true){
					if(this.turno == Ficha.BLANCA){
						this.turno = Ficha.NEGRA;
					}
					else if(this.turno == Ficha.NEGRA){
						this.turno = Ficha.BLANCA;
					}
				}
			}
			else{
				moValido = false;
			}
		}
		return moValido;
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

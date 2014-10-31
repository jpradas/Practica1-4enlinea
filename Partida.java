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
				if(hayGrupo()){
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
	
	private boolean hayGrupo(){
		boolean hayGrupo = false;
		int consecutivos = 0;
		for(int i = 0; i < FILAS; i++){
			for(int j = 0; j < COLUMNAS; j++){
				if((j+1 < COLUMNAS) && tablero.getFicha(i,j) == tablero.getFicha(i,j+1) && tablero.getFicha(i,j) != Ficha.VACIA){ 
	                consecutivos = 0;
					while((j+consecutivos < COLUMNAS) && tablero.getFicha(i, j) == tablero.getFicha(i, j+consecutivos)){ 
	                    consecutivos++; 
	                } 
	                if(consecutivos >= 4 && tablero.getFicha(i, j) == tablero.getFicha(i, j+1)){ 
	                    hayGrupo = true; 
	                } 
	            }
				else if((j+1 < COLUMNAS-3) && (i+1 < FILAS-2) && tablero.getFicha(i, j) == tablero.getFicha(i+1, j+1) && tablero.getFicha(i, j) != Ficha.VACIA){
					consecutivos = 0;
					while(consecutivos != 4 && tablero.getFicha(i, j) == tablero.getFicha(i+consecutivos, j+consecutivos)){
						consecutivos++;
					}
	                if(consecutivos >= 4){ 
	                    hayGrupo = true; 
	                }
				}
				else if((COLUMNAS-1-j > 2) && (i+1 < FILAS-2) && tablero.getFicha(i, COLUMNAS-1-j) == tablero.getFicha(i+1, COLUMNAS-1-j-1) && tablero.getFicha(i, COLUMNAS-1-j) != Ficha.VACIA){
					consecutivos = 0;
					while(consecutivos != 4 && tablero.getFicha(i, COLUMNAS-1-j) == tablero.getFicha(i+consecutivos, COLUMNAS-1-j-consecutivos)){
						consecutivos++;
					}
	                if(consecutivos >= 4){ 
	                    hayGrupo = true; 
	                }
				}
	            else if((i+1 < FILAS) && tablero.getFicha(i, j) == tablero.getFicha(i+1, j) && tablero.getFicha(i, j) != Ficha.VACIA){ 
	                consecutivos = 0; 
	                while((i+consecutivos < FILAS) && tablero.getFicha(i, j) == tablero.getFicha(i+consecutivos, j)){ 
	                    consecutivos++; 
	                }  
	                if(consecutivos >= 4 && tablero.getFicha(i, j) == tablero.getFicha(i+1, j)){ 
	                    hayGrupo = true; 
	                } 
	            } 
			}
		}
		return hayGrupo;
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

package practica1;

public class Tablero {
	private Ficha [][] tablero;
	private int ancho;  // COLUMNAS
	private int alto;   // FILAS
	
	public Tablero(int alto, int ancho){
		this.tablero = new Ficha [alto][ancho];
		this.ancho = ancho;
		this.alto = alto;
	}
	
	public void reset(){
		for (int i = 0; i < this.alto; i++){
			for (int j = 0; j < this.ancho; j++){
				this.tablero[i][j] = Ficha.VACIA;
			}
		}
	}
	
	public int devolverAlturaDesocupada(int col){
		int i = this.alto - 1;
		boolean encontrado = false;
		while(i < this.alto && i >= 0 && !encontrado){
			if (this.tablero[i][col] == Ficha.VACIA){
				encontrado = true;
			}
			else{
				i--;
			}
		}
		return i;
	}
	
	public int cuatroEnLinea(Ficha color, int col, int altura){
		int i = altura;
		while(i >= 0 && this.tablero[i][col] == color){
			i--;
		}
		return i;
	}
	
	public void setFicha(Ficha ficha, int col, int altura){
		this.tablero[altura][col] = ficha;
	}
	
	public String toString(){
		String mensaje = "";
		String nuevalinea = System.getProperty("line.separator");
		for(int i = 0; i < this.alto; i++){
			for(int j = 0; j < this.ancho; j++){
				if(this.tablero[i][j] == Ficha.VACIA){
					mensaje = mensaje + "O ";
				}
				else if(this.tablero[i][j] == Ficha.BLANCA){
					mensaje = mensaje + "B ";
				}
				else if(this.tablero[i][j] == Ficha.NEGRA){
					mensaje = mensaje + "N ";
				}
			}
			mensaje = mensaje + nuevalinea;
		}
		return mensaje;
	}
}

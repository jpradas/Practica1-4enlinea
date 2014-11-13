package logica;

public class Tablero {
	
	private int filas;
	private int columnas;
	private Ficha[][] tablero;
	private int contCompleto = 0;
	
	public Tablero (int y, int x){
		this.filas = y; 
		this.columnas = x;
		this.tablero = new Ficha[y][x];
		for (int i = 0; i < y; i++)
			for (int j = 0; j < x; j++)
				this.tablero[i][j] = Ficha.VACIA;
	}
	
	public void setCont(int cont){
		this.contCompleto = cont;
	}
	public int getCont (){
		return this.contCompleto;
	}
	
	public void setFicha(int y, int x,Ficha ficha){
		this.tablero[y][x] = ficha;
		this.contCompleto++;
	}
	
	public Ficha getFicha(int y, int x){
		return this.tablero[y][x];
	}
	
	public boolean completo(){
		boolean completo=false;
		int numFichas = this.filas*this.columnas;
		if (this.contCompleto >= numFichas){
			completo = true;
		}
		return completo;
	}
	
	public String toString(){
		String mensaje = "";
		String nuevalinea = System.getProperty("line.separator");
		for(int i = 0; i < this.filas; i++){
			for(int j = 0; j < this.columnas; j++){
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
	
	public int getAlturaVacia(int col){
		int alt = this.filas - 1;
		while (alt >= 0 && this.tablero[alt][col]!= Ficha.VACIA)
			alt--;
		return alt;	  
	}
}

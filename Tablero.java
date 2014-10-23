package logica;

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
	
	public void setFicha(Ficha ficha, int col, int altura){
		this.tablero[altura][col] = ficha;
	}
	
	public boolean hayGrupo(){
		boolean hayGrupo = false;
		int consecutivos = 0;
		for(int i = 0; i < this.alto; i++){
			for(int j = 0; j < this.ancho; j++){
				if((j+1 < this.ancho) && this.tablero[i][j] == this.tablero[i][j+1] && this.tablero[i][j] != Ficha.VACIA){ 
	                consecutivos = 0;
					while((j+consecutivos < this.ancho-1) && this.tablero[i][j] == this.tablero[i][j+consecutivos]){ 
	                    consecutivos++; 
	                } 
	                if(consecutivos >= 4 && this.tablero[i][j] == this.tablero[i][j+1]){ 
	                    hayGrupo = true; 
	                } 
	            } 
	            else if((i+1 < this.alto) && this.tablero[i][j] == this.tablero[i+1][j] && this.tablero[i][j] != Ficha.VACIA){ 
	                consecutivos = 0; 
	                while((i+consecutivos < this.alto) && this.tablero[i][j] == this.tablero[i+consecutivos][j]){ 
	                    consecutivos++; 
	                }  
	                if(consecutivos >= 4 && this.tablero[i][j] == this.tablero[i+1][j]){ 
	                    hayGrupo = true; 
	                } 
	            } 
			}
		}
		return hayGrupo;
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

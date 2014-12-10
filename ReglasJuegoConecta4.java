package logica;

public class ReglasJuegoConecta4 extends ReglasJuego{
	private Tablero tablero;
	private int columnas;
	private int filas;
	
	public ReglasJuegoConecta4(){
		this.filas = 7;
		this.columnas = 6;
		this.iniciaTablero(this.filas, this.columnas);
		
	}
	
	public void iniciaTablero(int filas, int columnas){
		this.tablero = new Tablero (filas, columnas);
		this.tablero.reset();
	}
	
	public Tablero getTablero(){
		return this.tablero;
	}

	public boolean hayGrupo(int x, int y){
		boolean encontrado = false;
		int i = 1, j, d,iguales; //filas, columnas, direccion y contador de igual respectivamente
		//Vertical
		iguales = 0;
			while (iguales <= Partida.GANAN-1 && this.v(x,y+i) != 0 && this.v(x,y)==this.v(x,y+i)){
				iguales++;				
				i++;
			}
		if (iguales >= Partida.GANAN-1)
			encontrado = true;		
		else  if (!encontrado) {		
		//Horizontal
			iguales = 0;
			for (d = -1; d <= 1; d+=2){
				j = 1;
				while (iguales <= Partida.GANAN-1 && this.v(x+j*d,y) != 0 && this.v(x,y) == this.v(x+j*d,y)){
					iguales++;
					j++;
				}
			}
		}
		if (iguales>=Partida.GANAN-1)
			encontrado = true;
		else if (!encontrado){
		//diagonal
			for (int dir = -1; dir <= 1; dir+=2){
				iguales = 0;
				for (d = -1; d <= 1; d +=2){
					j = 1;
					i = 1;
					while (iguales<=Partida.GANAN-1 && this.v(x+j*d, y+i*dir) !=0 && this.v(x,y) == this.v(x+j*d, y+i*dir)){
						iguales++;
						j++;
						i++;
					}
				}
			}
			if (iguales>=Partida.GANAN-1)
				encontrado = true;
			}
		return encontrado;	
	}
	
	/** Metodo para devolver un valor dependiendo de la ficha y su posicion
	 * @param y posicion de la ficha en el eje y
	 * @param x posicion de la ficha en el eje x 
	 * @return regresa el valor de la ficha en determinada posicion, 0 si es Vacia o se sale de los limites, 2 si es negra y 1 si es blanca
	 */
	
	private int v(int y, int x){ 
		int valor = 1; 
		if (y < 0 || x < 0 || y >= this.columnas || x >=this.filas || this.tablero.getFicha(y,x) == Ficha.VACIA)
			valor = 0; 
		else if (this.tablero.getFicha(y, x) == Ficha.NEGRA)
			valor = 2; 
		return valor;
	}
	
	/*public boolean hayGrupo(){
		boolean hayGrupo = false;
		int consecutivos = 0;
		for(int i = 0; i < this.filas; i++){
			for(int j = 0; j < this.columnas; j++){
				if((j+1 < this.columnas) && this.tablero.getFicha(i,j) == this.tablero.getFicha(i,j+1) && this.tablero.getFicha(i,j) != Ficha.VACIA){ 
	                consecutivos = 0;
					while((j+consecutivos < this.columnas) && this.tablero.getFicha(i, j) == this.tablero.getFicha(i, j+consecutivos)){ 
	                    consecutivos++; 
	                } 
	                if(consecutivos >= 4 && this.tablero.getFicha(i, j) == this.tablero.getFicha(i, j+1)){ 
	                    hayGrupo = true; 
	                } 
	            }
				else if((j+1 < this.columnas-3) && (i+1 < this.filas-2) && this.tablero.getFicha(i, j) == this.tablero.getFicha(i+1, j+1) && this.tablero.getFicha(i, j) != Ficha.VACIA){
					consecutivos = 0;
					while(consecutivos != 4 && this.tablero.getFicha(i, j) == this.tablero.getFicha(i+consecutivos, j+consecutivos)){
						consecutivos++;
					}
	                if(consecutivos >= 4){ 
	                    hayGrupo = true; 
	                }
				}
				else if((this.columnas-1-j > 2) && (i+1 < this.filas-2) && this.tablero.getFicha(i, this.columnas-1-j) == this.tablero.getFicha(i+1, this.columnas-1-j-1) && this.tablero.getFicha(i, this.columnas-1-j) != Ficha.VACIA){
					consecutivos = 0;
					while(consecutivos != 4 && this.tablero.getFicha(i, this.columnas-1-j) == this.tablero.getFicha(i+consecutivos, this.columnas-1-j-consecutivos)){
						consecutivos++;
					}
	                if(consecutivos >= 4){ 
	                    hayGrupo = true; 
	                }
				}
	            else if((i+1 < this.filas) && this.tablero.getFicha(i, j) == this.tablero.getFicha(i+1, j) && this.tablero.getFicha(i, j) != Ficha.VACIA){ 
	                consecutivos = 0; 
	                while((i+consecutivos < this.filas) && this.tablero.getFicha(i, j) == this.tablero.getFicha(i+consecutivos, j)){ 
	                    consecutivos++; 
	                }  
	                if(consecutivos >= 4 && this.tablero.getFicha(i, j) == this.tablero.getFicha(i+1, j)){ 
	                    hayGrupo = true; 
	                } 
	            } 
			}
		}
		return hayGrupo;
	}*/
}

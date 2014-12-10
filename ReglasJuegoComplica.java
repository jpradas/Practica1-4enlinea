package logica;

public class ReglasJuegoComplica extends ReglasJuego{
	private Tablero tab;
	private int columnas;
	private int filas;
	
	public ReglasJuegoComplica(){
		this.filas = 7;
		this.columnas = 4;
		this.iniciaTablero(this.filas, this.columnas);
	}
	
	public void iniciaTablero(int filas, int columnas){
		this.tab = new Tablero (filas, columnas);
		this.tab.reset();
	}
	
	public Tablero getTablero(){
		return this.tab;
	}

	
	public boolean hayGrupo(int x, int y){
		boolean encontrado = false;
		int suma=0;
		while(!encontrado && y+suma>0 && y+suma<this.filas){	
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
			suma++;
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
		if (y < 0 || x < 0 || y >= this.columnas || x >=this.filas || this.tab.getFicha(y,x) == Ficha.VACIA)
			valor = 0; 
		else if (this.tab.getFicha(y, x) == Ficha.NEGRA)
			valor = 2; 
		return valor;
	}

}

package logica;

/**Clase que almacena el estado de un tablero 
 * 
 *
 */
public class Tablero {
	
	private int filas;
	private int columnas;
	private Ficha[][] tablero;
	private int contCompleto = 0; //Contador para saber cuantas fichas han sido puestas
	
	/**
	 * La constructora Tablero implementa el tablero del juego con enumerados del tipo Ficha e inicializa los atributos privados alto y ancho al valor de la matriz creada.
	 * E inicializa la matriz a todas posiciones VACIA
	 * @param y	El parametro alto indica la altura que tendra el tablero.
	 * @param x	El parametro ancho indica la anchura que tendra el tablero.
	 */
	public Tablero (int y, int x){
		this.filas = y; 
		this.columnas = x;
		this.contCompleto=0;
		this.tablero = new Ficha[y][x];
		for (int i = 0; i < y; i++)
			for (int j = 0; j < x; j++)
				this.tablero[i][j] = Ficha.VACIA;
	}
	

	
	/** Coloca la ficha en la posicion (y,x)
	 * @param y posicion de la ficha en el eje y 
	 * @param x posicion de la ficha en el eje x 
	 * @param ficha ficha correspondiente al turno 
	 */
	public void setFicha(int y, int x,Ficha ficha){
		this.tablero[y][x] = ficha;
		if (ficha!=Ficha.VACIA) this.contCompleto++;
	}
	
	/** Devuelve la ficha en la posicion (y,x)
	 * @param y posicion de la ficha en el eje y 
	 * @param x posicion de la ficha en el eje x 
	 * @return Ficha en esa posicion 
	 */
	public Ficha getFicha(int y, int x){
		return this.tablero[y][x];
	}
	
	/** Devuelve verdadero si el tablero esta completo usando el contCompleto y el numero de fichas totales
	 * @return Verdadero o Falso si el tablero esta lleno
	 */
	public boolean completo(){
		boolean completo=false;
		int numFichas = this.filas*this.columnas;
		if (this.contCompleto >= numFichas){
			completo = true;
		}
		return completo;
	}
	
	/**
	 * El metodo toString devuelve la informacion de la clase tablero en forma de String.
	 */
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
	
	
	public void reset() {
		this.contCompleto=0;
		for (int i=0; i<this.filas;i++)
			for (int j = 0;j<this.columnas;j++)
				this.setFicha(i, j, Ficha.VACIA);
	}
}

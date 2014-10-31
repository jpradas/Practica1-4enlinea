package logica;


public class Tablero {
	
	/**
	 * @param tablero El atributo tablero crea una matriz tablero de fichas, por lo cual cada posición de la matriz tendrá un valor del enumerado Ficha.
	 * @param alto	El atributo alto nos indica el número de filas que tiene el tablero.
	 * @param ancho	El atributo ancho nos indica el número de columnas que tiene el tablero.
	 */
	
	private Ficha [][] tablero;
	private int ancho;  // COLUMNAS
	private int alto;   // FILAS
	
	/**
	 * La constructora Tablero crea el tablero con enumerados del tipo Ficha e inicializa los atributos privados alto y ancho al valor de la matriz creada.
	 * @param alto	El parámetro alto indica la altura que tendrá el tablero.
	 * @param ancho	El parámetro ancho indica la anchura que tendrá el tablero.
	 */
	
	public Tablero(int alto, int ancho){
		this.tablero = new Ficha [alto][ancho];
		this.ancho = ancho;
		this.alto = alto;
	}
	
	/**
	 * El método reset reinicia el tablero a su valor inicial, es decir, todas las casillas marcan la ficha VACIA.
	 */

	public void reset(){
		for (int i = 0; i < this.alto; i++){
			for (int j = 0; j < this.ancho; j++){
				this.tablero[i][j] = Ficha.VACIA;
			}
		}
	}
	
	/**
	 * El método getFicha devuelve una ficha del tablero en una posición indicada previamente por altura y columna.
	 * @param altura El parámetro altura le indica al método la fila en la que ha de recoger la ficha.
	 * @param columna El parámetro columna le indica al método la columna en la que ha de recoger la ficha
	 * @return	retorna una ficha
	 */
	
	public Ficha getFicha(int altura, int columna){
		return this.tablero[altura][columna];
	}
	
	/**
	 * El método devolver altura devuelve la ultima altura ocupada en la matriz, en caso de ser 0 la columna quiere decir que esta al completo.
	 * @param col	Indica la columna a mirar para revisar su altura.
	 * @return	retorna la ultima altura desocupada (en caso de haberla) y si no devuelve un 0.
	 */
	
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
	
	/**
	 * El método setFicha coloca una ficha en una determinada posición del tablero.
	 * @param ficha	Es la ficha que ha de colocar en el tablero.
	 * @param col	Columna en la cual ha de colocar la ficha.
	 * @param altura	Altura a la cual ha de colocar la ficha.
	 */
	
	public void setFicha(Ficha ficha, int col, int altura){
		this.tablero[altura][col] = ficha;
	}
	
	/**
	 * El método toString devuelve la informacion de la clase tablero en forma de String.
	 */
	
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

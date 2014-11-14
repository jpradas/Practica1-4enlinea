package logica;

public class Partida {
	private Ficha turno;
	private Tablero tablero;
	public boolean terminada = false;
	public static final int MAX_UNDO = 10;
	private Ficha ganador = Ficha.VACIA;
	private int[] undo = new int[MAX_UNDO+1];//se Usa el max_undo+1 para poder usar la funcion desplazar izquierda
	private int indexUndo = 0;
	private final static int FILAS = 6;
	private final static int COLUMNAS = 7;
	
	/**
	 * La constructora Partida crea un tablero con un numero determinado de filas y columnas. 
	 */
	
	public Partida(){
		this.tablero = new Tablero(FILAS,COLUMNAS);
		this.turno = Ficha.BLANCA;
	}

	/**si el tablero esta lleno o la partida esta terminada con ganador devuelve Verdadero
	 * @return el valor de terminada 
	 */
	public boolean partidaTerminada (){
		if (this.tablero.completo()) 
			this.terminada = true;
		return this.terminada;
	}
	
	
	/** devuelve el valor de la ficha ganadora
	 * @return ganador 
	 */
	public Ficha getGanador(){
		return this.ganador;
	}
	
	/**Reinicia la partida poniendo el tablero todas a VACIA, e inicializando el turno, contCompleto e indexUndo
	 * 
	 * 
	 */
	public void reset(){
		for (int i=0; i<FILAS;i++)
			for (int j = 0;j<COLUMNAS;j++)
				this.tablero.setFicha(i, j, Ficha.VACIA);
		this.turno = Ficha.BLANCA;
		this.tablero.setCont(0);
		this.indexUndo = 0;
	}
	
	/** Ejecuta el moviento en la columna introducida y actualiza los valores de la memoria para undos 
	 * @param c columna en la cual se realiza el movimiento
	 * @return Verdadero si se ha realizado el movimiento
	 */
	public boolean ejecutaMovimiento(int c){
		boolean mValido = false;
		if (c >= 0 && c < COLUMNAS){
			int altura = this.tablero.getAlturaVacia(c);
			if (altura < 0)
				mValido = false;
			else {
				mValido = true;
				this.tablero.setFicha(altura, c, this.turno);
				if (this.hayCuatroDesdeFicha(altura, c)){
					this.terminada = true;
					this.ganador = this.turno;
				}
				else {
					if (this.turno == Ficha.BLANCA)
						this.turno = Ficha.NEGRA;
					else if (this.turno == Ficha.NEGRA)
						this.turno = Ficha.BLANCA;
					this.undo[indexUndo] = c;
					indexUndo++;
					if (indexUndo > MAX_UNDO){
						desplazarUndoIzq();
						indexUndo--;
					}
				}
			}
		}
	return mValido;
	}
	
	/**Desplaza a la izquierda todos los movimientos guardados en la memoria undo 
	 * 
	 */
	public void desplazarUndoIzq(){
		for (int i = 0;i<indexUndo-1;i++)
			this.undo[i] = this.undo[i+1];	
	}
	
	/** deshace un moviento realizado cambiando la memoria undo
	 * @return Verdadero si se ha deshacido el moviento
	 */
	public boolean undo(){
		boolean valido = false;
		if(this.indexUndo <= 0){
			valido = false;
		}
		else{
			int altura = this.tablero.getAlturaVacia(this.undo[this.indexUndo - 1]) + 1;
			this.tablero.setFicha(altura,this.undo[this.indexUndo - 1] ,Ficha.VACIA);
			this.indexUndo = this.indexUndo - 1;
			valido = true;
			this.tablero.setCont(this.tablero.getCont()-1);
		}
		if(this.turno == Ficha.BLANCA){
			this.turno = Ficha.NEGRA;
		}
		else if(this.turno == Ficha.NEGRA){
			this.turno = Ficha.BLANCA;
		}
		return valido;
	}
	
	/** Metodo para devolver un valor dependiendo de la ficha y su posicion
	 * @param y posicion de la ficha en el eje y
	 * @param x posicion de la ficha en el eje x 
	 * @return regresa el valor de la ficha en determinada posicion, 0 si es Vacia o se sale de los limites, 2 si es negra y 1 si es blanca
	 */
	public int v(int y, int x){ 
		int valor = 1; 
		if (y < 0 || x < 0 || y >= FILAS || x >=COLUMNAS || this.tablero.getFicha(y,x) ==Ficha.VACIA)
			valor = 0; 
		else if (this.tablero.getFicha(y, x) == Ficha.NEGRA)
			valor = 2; 
		else{}
		return valor;
	}
	
	/**Comprueba si desde determinada posicion hay fichas iguales en Vertical, Horizontal y Diagonal
	 * @param y posicion de la ficha en el eje y
	 * @param x x posicion de la ficha en el eje x 
	 * @return Verdadero si hay un grupo de fichas iguales igual o mayor que 4
	 */
	public boolean hayCuatroDesdeFicha(int y, int x){
		
		boolean encontrado = false;
		int i = 1, j, d,iguales; //filas, columnas, direccion y contador de igual respectivamente
		//Vertical
		iguales = 0;
			while (iguales <= 3 && v(y+i,x) != 0 && v(y,x)==v(y+i,x)){
				iguales++;				
				i++;
			}
		if (iguales >= 3)
			encontrado = true;		
		else  if (!encontrado) {		
		//Horizontal
			iguales = 0;
			for (d = -1; d <= 1; d+=2){
				j = 1;
				while (iguales <= 3 && v(y,x+j*d) != 0 && v(y,x) == v(y,x+j*d)){
					iguales++;
					j++;
				}
			}
		}
		if (iguales>=3)
			encontrado = true;
		else if (!encontrado){
		//diagonal
			for (int dir = -1; dir <= 1; dir+=2){
				iguales = 0;
				for (d = -1; d <= 1; d +=2){
					j = 1;
					i = 1;
					while (iguales<=3 && v(y+i*dir,x+j*d) !=0 && v(y,x) == v(y+i*dir,x+j*d)){
						iguales++;
						j++;
						i++;
					}
				}
			}
			if (iguales>=3)
				encontrado = true;
			}
		return encontrado;	
	}
	/**
	 * El metodo toString devuelve la informacion de la clase partida en forma de String.
	 */
	public String toString(){
		String mensaje = this.tablero.toString();
		String nuevalinea = System.getProperty("line.separator");
		String ficha = "";
		if(this.turno == Ficha.BLANCA){
			ficha = "Blancas";
		}
		else if(this.turno == Ficha.NEGRA){
			ficha = "Negras";
		}
		return mensaje = mensaje + nuevalinea + "Juegan " + ficha;
	
	}
}

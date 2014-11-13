package logica;

public class Partida {
	private Ficha turno;
	private Tablero tablero;
	public boolean terminada = false;
	public static final int MAX_UNDO = 10;
	private Ficha ganador = Ficha.VACIA;
	private int[] undo = new int[Partida.FILAS*Partida.COLUMNAS];
	private int indexUndo = 0;
	private final static int FILAS = 6;
	private final static int COLUMNAS = 7;
	
	
	public Partida(){
		this.tablero = new Tablero(FILAS,COLUMNAS);
		this.turno = Ficha.BLANCA;
	}

	public boolean partidaTerminada (){
		if (this.tablero.completo()) 
			this.terminada = true;
		return this.terminada;
	}
	
	
	public Ficha getGanador(){
		return this.ganador;
	}
	
	public void reset(){
		for (int i=0; i<FILAS;i++)
			for (int j = 0;j<COLUMNAS;j++)
				this.tablero.setFicha(i, j, Ficha.VACIA);
		this.turno = Ficha.BLANCA;
	}
	
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
					if (indexUndo >= MAX_UNDO){
						desplazarUndoIzq();
						indexUndo--;
					}
				}
			}
		}
	return mValido;
	}
	
	public void desplazarUndoIzq(){
		for (int i = 0;i >=indexUndo;i++)
			this.undo[i] = this.undo[i+1];	
	}
	
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
	
	public int v(int y, int x){ //metodo para devolver valor dependiendo de la ficha en la posicion o fuera de tablero
		int valor = 1; 
		if (y < 0 || x < 0 || y >= FILAS || x >=COLUMNAS || this.tablero.getFicha(y,x) ==Ficha.VACIA)
			valor = 0; 
		else if (this.tablero.getFicha(y, x) == Ficha.NEGRA)
			valor = 2; 
		else{}
		return valor;
	}
	
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

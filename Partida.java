package logica;

public class Partida {
	private Ficha turno;
	private Tablero tablero;
	public boolean terminada = false;
	public static final int MAX_UNDO = 10;
	private Ficha ganador = Ficha.VACIA;
	private int[] undo;
	private int indexUndo;
	private final static int FILAS = 6;
	private final static int COLUMNAS = 7;
	
	
	public Partida(){
		this.tablero = new Tablero(FILAS,COLUMNAS);
	}

	public boolean partidaTerminada (){
		if (/*hayCuatro() || */this.tablero.completo())
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
			this.tablero.setFicha(altura, c, this.turno);
			mValido = true;
			if (this.turno == Ficha.BLANCA)
				this.turno = Ficha.NEGRA;
			else if (this.turno == Ficha.NEGRA)
				this.turno = Ficha.BLANCA;
			this.undo[indexUndo] = c;
			indexUndo++;
		}
		else {
			mValido = false;
		}
		return mValido;
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
	
	/*public boolean hayCuatro(){
		boolean encontrado = false;
		int limiteH = COLUMNAS - 4;
		int limiteV = FILAS - 4;
		int i = 0, j = 0;
		for (j = 0; j>=limiteH;j++)
			if (!encontrado && this.tablero.getFicha(i, j) == this.tablero.getFicha(i+1, j)&& this.tablero.getFicha(i, j) == this.tablero.getFicha(i+2, j)&& this.tablero.getFicha(i, j) == this.tablero.getFicha(i+3, j))
				encontrado = true;
		for (i = 0; i>=limiteV;i++)
			if (!encontrado && this.tablero.getFicha(i, j) == this.tablero.getFicha(i, j+1)&& this.tablero.getFicha(i, j) == this.tablero.getFicha(i, j+2)&& this.tablero.getFicha(i, j) == this.tablero.getFicha(i, j+3))
				encontrado = true;
		//falta diagonal
		this.ganador = this.turno;
		return encontrado;				
	}*/

	
	public int v(int y, int x){ //metodo para devolver valor dependiendo de la ficha en la posicion o fuera de tablero
		int valor = 1; 
		if (y < 0 || x < 0 || y >= COLUMNAS || x >=FILAS || this.tablero.getFicha(y,x) ==Ficha.VACIA)
			valor = 0; 
		else if (this.tablero.getFicha(y, x) == Ficha.NEGRA)
			valor = 2;
		else{}
		return valor;
	}
	
	public boolean hayCuatroDesdeFicha(int y, int x){
		
		boolean encontrado = false;
		int i = 0, j = 0, d = -1,c=0; //filas, columnas, direccion y contador de igual respectivamente
		//vertical
		for (d = -1; d == 1; d = d+2){
			i = 0;
			while (c <= 3 && v(y+i*d,x) != 0){
				if (v(y,x)==v(y+i*d,x)){
					c++;				
				}
				else {}
				i++;
			}
		}
		if (c >= 3)
			encontrado = true;		
		else  if (!encontrado) {		
		//Horizontal
			c = 0;
			for (d = -1; d == 1; d = d+2){
				j = 0;
				while (c <= 3 && v(y,x+j*d) != 0){
					if (v(y,x)==v(y,x+j*d)){
						c++;
					}
					else{}
					j++;
				}
			}
		}
		if (c>=3)
			encontrado = true;
		else if (!encontrado){
		//diagonal
			int dir = -1;
			for (dir = -1; dir == 1; dir = dir + 2)
				c = 0;
				for (d = -1; d == 1; d = d+2){
					j = 0;
					while (c<=3 && v(y+i*dir,x+j*d) !=0 ){
						if (v(y,x) == v(y+i*dir,x+j*d))
							c++;
						else{}
						j++;
						i++;
					}
				}		
			}
		return encontrado;	
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

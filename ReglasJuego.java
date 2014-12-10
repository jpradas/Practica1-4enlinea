package logica;

public abstract class ReglasJuego {
	
	abstract public void iniciaTablero(int columnas, int filas);
	
	public Ficha jugadorInicial(){
		return Ficha.BLANCA;
	}
	abstract public Tablero getTablero();
	
	public Ficha siguienteTurno(Ficha color){
		if(color == Ficha.NEGRA){
			color = Ficha.BLANCA;
		}
		else{
			color = Ficha.NEGRA;
		}
		return color;
	}
	
	abstract public boolean hayGrupo(int x, int y);
}

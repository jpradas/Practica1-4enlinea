package logica;

public abstract class Movimiento {
	protected int columna;
	protected Ficha turno;
	protected int fila;
	
	public Movimiento(int columna, Ficha turno){
		this.columna = columna;
		this.turno = turno;
	}
	
	public Ficha getJugador(){
		return this.turno;
	}
	
	public int getColumna(){
		return this.columna;
	}
	
	public int getFila(){
		return this.fila;
	}
	
	public abstract boolean ejecutaMovimiento(Tablero t);
	public abstract void undo(Tablero t);
}

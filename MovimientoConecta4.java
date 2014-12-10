package logica;

public class MovimientoConecta4 extends Movimiento{

	public MovimientoConecta4(int columna, Ficha turno){
		super(columna, turno);
	}

	public boolean ejecutaMovimiento(Tablero t){
		boolean moValido = false;
		if(this.columna < 0 || this.columna >= t.ancho){
			moValido = false;
		}
		else{
			this.fila = t.devolverAlturaDesocupada(this.columna);
			if(this.fila <= t.alto && this.fila >= 0){
				t.setFicha(this.turno, this.columna, this.fila);
				moValido = true;
			}
			else{
				moValido = false;
			}
		}
		return moValido;
	}

	public void undo(Tablero t){
		
	}

}

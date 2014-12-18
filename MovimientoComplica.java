package logica;

public class MovimientoComplica extends Movimiento{

	public MovimientoComplica(int columna, Ficha turno){
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
			else if (this.fila == -1){
				for(int i = t.alto - 2; i >= 0; i--){
					Ficha aux = t.getFicha(this.columna, i);
					t.setFicha(aux, this.columna, i+1);
				}
				this.fila = 0;
				t.setFicha(this.turno, this.columna, this.fila);
				moValido = true;
			}
		}
		return moValido;
	}

	public void undo(Tablero t){
		t.setFicha(Ficha.VACIA, this.columna, this.fila);
	}
	
}

package movimientos;

import logica.Ficha;
import logica.Tablero;

public class MovimientoComplica extends Movimiento{
	
	private Ficha fichaDesplazada;

	public MovimientoComplica(int columna, Ficha turno){
		
		super(columna, turno);
		this.fichaDesplazada=Ficha.VACIA;
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
				this.fichaDesplazada = t.getFicha(this.columna,t.alto-1);
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
		
		desplazarArriba(t,this.columna);
		t.setFicha(this.fichaDesplazada, this.columna, t.alto);
	}
	
	private void desplazarArriba(Tablero t,int col){
		for (int i = 1; i >= t.alto-1;i++){
			t.setFicha(t.getFicha(i, col), col, i-1);			
		}
	}
	
}


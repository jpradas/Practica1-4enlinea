package control;

import java.util.Scanner;

import logica.Ficha;
import logica.MovimientoComplica;
import logica.MovimientoConecta4;
import logica.Partida;
import logica.ReglasJuegoComplica;
import logica.ReglasJuegoConecta4;
import logica.Movimiento;

public class Controlador {
	private Partida partida;
	private Scanner in;
	private TipoJuego jugandoA;
	
	public Controlador(Partida partida, Scanner in, TipoJuego juego){
		this.partida = partida;
		this.in = in;
		this.jugandoA = juego;
	}
	
	public void run(){
		//this.partida.reset(new ReglasJuegoConecta4());
		String opcion = "";
		while(!opcion.equalsIgnoreCase("SALIR") && !this.partida.terminada()){
			/*for(int i = 0; i < 20; i++){
				System.out.println(System.getProperty("line.separator"));
			}*/
			System.out.println(this.partida.toString());
			System.out.println("Que quieres hacer? ");
			opcion = null;
			opcion = this.in.nextLine();
			if(opcion.equalsIgnoreCase("PONER")){
				System.out.println("-- Introduce la columna: ");
				int col = this.in.nextInt() - 1;
				if(this.jugandoA == TipoJuego.CONECTA4){
					Movimiento mov = new MovimientoConecta4(col, this.partida.getJugador());
					if(!this.partida.ejecutaMovimiento(mov)){
						System.err.println("Movimiento incorrecto");
					}
					else{
						if(this.partida.terminada()){
							if(this.partida.getFichaGanador() == Ficha.VACIA){
								System.out.println("Partida en tablas");
								opcion = "salir";
							}
							else{
								System.out.println("Gana la " + this.partida.getFichaGanador());
								opcion = "salir";
							}
						}
					}
				}
				else if(this.jugandoA == TipoJuego.COMPLICA){
					Movimiento mov = new MovimientoComplica(col, this.partida.getJugador());
					if(!this.partida.ejecutaMovimiento(mov)){
						System.err.println("Movimiento incorrecto");
					}
					else{
						if(this.partida.terminada()){
								System.out.println("Gana la " + this.partida.getFichaGanador());
								opcion = "salir";
							}
						}
					}
				/*if(!this.partida.ejecutaMovimiento(col)){
					System.out.println("Movimiento incorrecto");
				}
				else{
					if(this.partida.terminada()){
						Ficha ganador = this.partida.getFichaGanador();
						if(ganador == Ficha.VACIA){
							System.out.println("Partida en tablas");
							opcion = "salir";
						}
						else{
							System.out.println("Gana la " + ganador);
							opcion = "salir";
						}
					}
				}*/
			}
			else if(opcion.equalsIgnoreCase("DESHACER")){
				if(!this.partida.desHacer()){
					System.out.println("Imposible Deshacer");
				}
			}
			else if(opcion.equalsIgnoreCase("REINICIAR")){
				if(this.jugandoA == TipoJuego.CONECTA4){
					this.partida.reset(new ReglasJuegoConecta4());
				}
				else{
					this.partida.reset(new ReglasJuegoComplica());
				}
			}
			else if(opcion.equalsIgnoreCase("JUGAR C4")){
				this.jugandoA = TipoJuego.CONECTA4;
				this.partida.reset(new ReglasJuegoConecta4());
			}
			else if(opcion.equalsIgnoreCase("JUGAR CO")){
				this.jugandoA = TipoJuego.COMPLICA;
				this.partida.reset(new ReglasJuegoComplica());
			}
		}
	}
}

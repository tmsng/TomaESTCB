package tomaest.maquina;

/** Classe que representa uma toma parcial, isto é, qual o dispensador
 * e número de comprimidos a usar desse dispensador. A hora a que os comprimidos
 * devem ser tomados é dada pela classe Toma 
 */
public class TomaParcial {

	private Dispensador dispensador;
	private int quant;
	
	public TomaParcial(Dispensador d, int q){
		this.dispensador = d;
		this.quant = q;
	}
	
	public Dispensador getDispensador(){
		return dispensador;
	}
	
	public int getQuant(){
		return quant;
	}
	
	@Override
	public String toString() {
		return "Dispensador[ " + dispensador + "] - " + "quantidade: "+ quant + "\n";
	}
}

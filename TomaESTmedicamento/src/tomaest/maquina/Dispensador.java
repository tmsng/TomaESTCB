package tomaest.maquina;

//import java.util.List;

/** 
 * Classe que representa um dos dispensadores da m�quina.
 * Cada dispensador tem um medicamento e v�rios comprimidos (ou c�psulas)
 * desse medicamento
 */
public class Dispensador {

	private String nomeMedicamento;
	private int quantidadeTotal;
	
	
	/** adiciona numa certa quantidade de comprimidos ao dispensador
	 * @param quant quantidade a adicionar ao dispensador
	 */
	public Dispensador(String nomeMedicamento, int quantidadeTotal) {
		setquantidadeTotal(quantidadeTotal);
		setNomeMedicamento(nomeMedicamento);
	}


	public String getNomeMedicamento() {
		return nomeMedicamento;
	}

	
	public void setNomeMedicamento(String nomeMedicamento) {
		this.nomeMedicamento = nomeMedicamento;
	}


	public int getquantidadeTotal() {
		return quantidadeTotal;
	}

	
	public void setquantidadeTotal(int quantidadeTotal) {		
		this.quantidadeTotal = quantidadeTotal;
	}


	public int addQuantidade(int quant) {
		return quantidadeTotal += quant;
	}

	
	/** despeja, na gaveta de sa�da, a quantidade solicitada de comprimidos.
	 * @param quant quantidade de comprimidos a despejar
	 * @return a quantidade realmente despejada, pois pode n�o haver quantidade
	 *  suficiente para satisfazer o pedido
	 */
	public int despeja( int quant ) {
		quantidadeTotal=quantidadeTotal-quant;
		return quantidadeTotal;
	}
	

	/** indica se este dispensador est� a ser usado ou se est� livre
	 * @return true, se est� a ser usado, false caso esteja livre
	 */
	public boolean estaUsado( ) {
		if(quantidadeTotal /*- quantidade parcial */ >0) {
			return true; //Vai haver confus�o entre estar a ser usado ou ter esgotado o stock!

		}else
		return false;
	}


	/** Indica se o dispensador tem comprimidos suficientes para
	 * satisfazer todas as tomas da lista
	 * @param tomas lista de tomas que � necess�rio satisfazer
	 * @return true, se tem comprimidos suficientes, false caso contr�rio
	 */
//	public boolean consegueResponder( List<Toma> tomas/*int quant*/) {
//		for (Toma toma : tomas){
//			//toma.getTomaParte()
//		}
//		
//		return true;
//	}
	
	
	@Override
	public String toString() {
		return "Nome do Medicamento: " + nomeMedicamento + " - Quantidade: " + quantidadeTotal;
	}
}
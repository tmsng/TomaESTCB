package tomaest.maquina;

import java.util.List;

/** 
 * Classe que representa um dos dispensadores da máquina.
 * Cada dispensador tem um medicamento e vários comprimidos (ou cápsulas)
 * desse medicamento
 */
public class Dispensador {

	/** adiciona numa certa quantidade de comprimidos ao dispensador
	 * @param quant quantidade a adicionar ao dispensador
	 */
	public void addQuantidade(int quant) {
	}
	
	/** despeja, na gaveta de saída, a quantidade solicitada de comprimidos.
	 * @param quant quantidade de comprimidos a despejar
	 * @return a quantidade realmente despejada, pois pode não haver quantidade
	 *  suficiente para satisfazer o pedido
	 */
	public int despeja( int quant ) {
		return 0;
	}
	
	/** indica se este dispensador está a ser usado ou se está livre
	 * @return true, se está a ser usado, false caso esteja livre
	 */
	public boolean estaUsado( ) {
		return false;
	}
	
	/** Indica se o dispensador tem comprimidos suficientes para
	 * satisfazer todas as tomas da lista
	 * @param tomas lista de tomas que é necessário satisfazer
	 * @return true, se tem comprimidos suficientes, false caso contrário
	 */
	public boolean consegueResponder( List<Toma> tomas ) {
		return true;
	}
}

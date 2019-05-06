package tomaest.maquina;

import java.util.List;

/** 
 * Classe que representa um dos dispensadores da m�quina.
 * Cada dispensador tem um medicamento e v�rios comprimidos (ou c�psulas)
 * desse medicamento
 */
public class Dispensador {

	/** adiciona numa certa quantidade de comprimidos ao dispensador
	 * @param quant quantidade a adicionar ao dispensador
	 */
	public void addQuantidade(int quant) {
	}
	
	/** despeja, na gaveta de sa�da, a quantidade solicitada de comprimidos.
	 * @param quant quantidade de comprimidos a despejar
	 * @return a quantidade realmente despejada, pois pode n�o haver quantidade
	 *  suficiente para satisfazer o pedido
	 */
	public int despeja( int quant ) {
		return 0;
	}
	
	/** indica se este dispensador est� a ser usado ou se est� livre
	 * @return true, se est� a ser usado, false caso esteja livre
	 */
	public boolean estaUsado( ) {
		return false;
	}
	
	/** Indica se o dispensador tem comprimidos suficientes para
	 * satisfazer todas as tomas da lista
	 * @param tomas lista de tomas que � necess�rio satisfazer
	 * @return true, se tem comprimidos suficientes, false caso contr�rio
	 */
	public boolean consegueResponder( List<Toma> tomas ) {
		return true;
	}
}

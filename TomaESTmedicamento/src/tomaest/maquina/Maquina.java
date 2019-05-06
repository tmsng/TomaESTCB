package tomaest.maquina;

import java.time.LocalDateTime;
import java.util.List;

import tomaest.hardware.Hardware;

/** Classe que representa o software de controlo da m�quina dispensadora.
 * Esta classe deve fornecer m�todos para a comunica��o com a PhoneApp e
 * deve ela pr�pria comunicar com o hardware.
 */
public class Maquina {
	
	private Hardware hardware; // hardware da m�quina que vai controlar

	// deve ter uma lista de dispensadores e outra de tomas

	/** cria uma m�quina com o respetivo hardware 
	 * @param hardware hardware da m�quina que vai controlar
	 */
	public Maquina(Hardware hardware) {
		this.hardware = hardware;
		
		// configurar os restantes elementos da m�quina
		
		hardware.setMaquina( this );
	}

	/** m�todo que � chamado pelo temporizador do hardware
	 * sempre que se passam 20 segundos
	 */
	public void processar() {

	}
	
	/** m�todo que � chamado pelo hardware quando o utente
	 *  removeu os comprimidos da gaveta
	 */
	public void gavetaVazia() {
		// deve fechar a gaveta
	}


	/** m�todo que vai verificar se h� tomas que t�m
	 * de ser ministradas neste momento
	 */
	private void checkTomas() {

	}	

	/** Indica quantos dispensadores horizontais existem
	 * @return n�mero de dispensadores horizontais
	 */
	public int getDispensadoresHorizontais() {
		return 4;
	}
	
	/** Indica quantos dispensadores verticais existem
	 * @return n�mero de dispensadores verticais
	 */
	public int getDispensadoresVerticais() {
		return 4;
	}
	
	/** Abre o dispensador indicado
	 * @param disp n�mero do dispensador a abrir
	 */
	public void abrirDispensador( int disp ) {
	}
	
	/** indica se o diepensador est� livre ou em uso
	 * @param dispNum n�mero do dispensador
	 * @return true, se o dispensador est� livre, false caso esteja em uso
	 */
	public boolean estaLivre( int dispNum ) {
		return false;
	}

	/** indica qual o medicamento que est� associado ao dispensador
	 * @param dispNum  n�mero do dispensador
	 * @return nome do medicamento associado ao dispensador,
	 * null, caso n�o haja medicamento associado
	 */
	public String getMedicamento( int dispNum ) {
		return "MEDICAMENTO???";
	}

	/** indica quantos comprimidos ainda tem no dispensador
	 * @param dispNum n�mero do dispensador
	 * @return quantos comprimidos ainda tem no dispensador
	 */
	public int getQuantidade( int dispNum ) {
		return 0;
	}
	
	/** Configura um dispensador, indicando qual o medicamento a ele associado
	 * e respetiva quantidade de comprimidos
	 * 
	 * @param dispNum n�mero do dispensador
	 * @param medicamento nome do medicamento
	 * @param quant quantidade de comprimidos
	 */
	public void configurarDispensador( int dispNum, String medicamento, int quant ) {
	}

	/** carrega o dispensador com a quantidade de medicamentos indicada
	 * @param dispNum n�mero do dispensador
	 * @param quant quantidade de comprimidos a adicionar
	 */
	public void carregarDispensador(int dispNum, int quant) {
	}

	/** Adiciona uma toma � lista de tomas. Se a toma a adicionar tiver a mesma data
	 * que uma toma j� existente, a nova deve ser associada � j� existente.
	 * As tomas devem estar ordenadas por ordem crescente da data de toma.
	 * 
	 * @param quando data a que a toma deve ser efetuada
	 * @param dispNum qual o dispensador a usar
	 * @param quant quantos comprimidos a usar
	 */
	public void addToma(LocalDateTime quando, int dispNum, int quant ) {
	}
	
	/** devolve uma lista com as tomas que devem ser realizadas at� � data indicada
	 * @param fim data de fim da pesquisa
	 * @return uma lista com as tomas que devem ser realizadas at� � data indicada
	 */
	public List<Toma> tomasAte( LocalDateTime fim ){
		return null;
	}
}

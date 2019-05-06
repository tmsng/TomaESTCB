package tomaest.maquina;

import java.time.LocalDateTime;
import java.util.List;

import tomaest.hardware.Hardware;

/** Classe que representa o software de controlo da máquina dispensadora.
 * Esta classe deve fornecer métodos para a comunicação com a PhoneApp e
 * deve ela própria comunicar com o hardware.
 */
public class Maquina {
	
	private Hardware hardware; // hardware da máquina que vai controlar

	// deve ter uma lista de dispensadores e outra de tomas

	/** cria uma máquina com o respetivo hardware 
	 * @param hardware hardware da máquina que vai controlar
	 */
	public Maquina(Hardware hardware) {
		this.hardware = hardware;
		
		// configurar os restantes elementos da máquina
		
		hardware.setMaquina( this );
	}

	/** método que é chamado pelo temporizador do hardware
	 * sempre que se passam 20 segundos
	 */
	public void processar() {

	}
	
	/** método que é chamado pelo hardware quando o utente
	 *  removeu os comprimidos da gaveta
	 */
	public void gavetaVazia() {
		// deve fechar a gaveta
	}


	/** método que vai verificar se há tomas que têm
	 * de ser ministradas neste momento
	 */
	private void checkTomas() {

	}	

	/** Indica quantos dispensadores horizontais existem
	 * @return número de dispensadores horizontais
	 */
	public int getDispensadoresHorizontais() {
		return 4;
	}
	
	/** Indica quantos dispensadores verticais existem
	 * @return número de dispensadores verticais
	 */
	public int getDispensadoresVerticais() {
		return 4;
	}
	
	/** Abre o dispensador indicado
	 * @param disp número do dispensador a abrir
	 */
	public void abrirDispensador( int disp ) {
	}
	
	/** indica se o diepensador está livre ou em uso
	 * @param dispNum número do dispensador
	 * @return true, se o dispensador está livre, false caso esteja em uso
	 */
	public boolean estaLivre( int dispNum ) {
		return false;
	}

	/** indica qual o medicamento que está associado ao dispensador
	 * @param dispNum  número do dispensador
	 * @return nome do medicamento associado ao dispensador,
	 * null, caso não haja medicamento associado
	 */
	public String getMedicamento( int dispNum ) {
		return "MEDICAMENTO???";
	}

	/** indica quantos comprimidos ainda tem no dispensador
	 * @param dispNum número do dispensador
	 * @return quantos comprimidos ainda tem no dispensador
	 */
	public int getQuantidade( int dispNum ) {
		return 0;
	}
	
	/** Configura um dispensador, indicando qual o medicamento a ele associado
	 * e respetiva quantidade de comprimidos
	 * 
	 * @param dispNum número do dispensador
	 * @param medicamento nome do medicamento
	 * @param quant quantidade de comprimidos
	 */
	public void configurarDispensador( int dispNum, String medicamento, int quant ) {
	}

	/** carrega o dispensador com a quantidade de medicamentos indicada
	 * @param dispNum número do dispensador
	 * @param quant quantidade de comprimidos a adicionar
	 */
	public void carregarDispensador(int dispNum, int quant) {
	}

	/** Adiciona uma toma à lista de tomas. Se a toma a adicionar tiver a mesma data
	 * que uma toma já existente, a nova deve ser associada à já existente.
	 * As tomas devem estar ordenadas por ordem crescente da data de toma.
	 * 
	 * @param quando data a que a toma deve ser efetuada
	 * @param dispNum qual o dispensador a usar
	 * @param quant quantos comprimidos a usar
	 */
	public void addToma(LocalDateTime quando, int dispNum, int quant ) {
	}
	
	/** devolve uma lista com as tomas que devem ser realizadas até à data indicada
	 * @param fim data de fim da pesquisa
	 * @return uma lista com as tomas que devem ser realizadas até à data indicada
	 */
	public List<Toma> tomasAte( LocalDateTime fim ){
		return null;
	}
}

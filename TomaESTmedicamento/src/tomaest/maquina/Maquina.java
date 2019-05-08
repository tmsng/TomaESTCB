package tomaest.maquina;

import java.awt.MultipleGradientPaint.ColorSpaceType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//CHANGE_START
import tomaest.hardware.Hardware;
import tomaest.maquina.Dispensador;
import tomaest.hardware.CorLedTampa;
//CHANGE_END

/** Classe que representa o software de controlo da máquina listaDispensadora.
 * Esta classe deve fornecer métodos para a comunicação com a PhoneApp e
 * deve ela própria comunicar com o hardware.
 */
public class Maquina {
	
	private Hardware hardware; // hardware da máquina que vai controlar
	
	//CHANGE_START
	private final int SETE_DIAS = 7;
	private final int TRES_DIAS = 3;
	private int controlo = 0;
	private boolean x = false;

	// deve ter uma lista de listaDispensadores e outra de listaTomas
	private ArrayList<Dispensador> listaDispensador = new ArrayList<Dispensador>();
	private ArrayList<Toma> listaToma = new ArrayList<Toma>();
	//CHANGE_END
	
	
	/** cria uma máquina com o respetivo hardware 
	 * @param hardware hardware da máquina que vai controlar
	 */
	public Maquina(Hardware hardware) {
		this.hardware = hardware;
		
		// configurar os restantes elementos da máquina
		
		
		hardware.setMaquina( this );
	}
	
	//CHANGE_START
	public void dispensadoresIniciais() {
		addDispensador( new Dispensador("ProgJa", 5) );		
		addDispensador( new Dispensador("JavaPill", 20) );
		addDispensador( new Dispensador("InstantPOO", 5) );
		addDispensador( new Dispensador("CodeNight", 25) );		
		
		//System.out.println(getDispensador(0));
		
		//System.out.println("listaDispensador: " + listaDispensador + "\n\n");
	}
	//CHANGE_END
	
	
	//CHANGE_START
	public void tomasIniciais(LocalDateTime inicio) {
		Toma t1 = new Toma(inicio.plusMinutes(1));
		t1.addPartes( new TomaParcial(getDispensador(0) , 2) );
		t1.addPartes( new TomaParcial(getDispensador(1) , 1) );
		addToma(t1);
		
		//System.out.println("Toma 1:\n" + t1);

		Toma t2 = new Toma(inicio.plusMinutes(10));
		t2.addPartes( new TomaParcial(getDispensador(2), 1) );		
		t2.addPartes( new TomaParcial(getDispensador(3), 1) );
		addToma(t2);
		
		//System.out.println("Toma 2:\n" + t2);		
		
		//System.out.println("listaToma:\n" + listaToma);
		verificarLed(listaToma); // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** Verifica todas as tomas e associa cada
	 * uma delas a uma cor de Led correspondente
	 * @param t ArrayList(Toma)
	 */
	public void verificarLed(ArrayList<Toma> t){ // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		//System.out.println(t);
		//System.out.println(d);
		int disp = 0;
		
		for (Toma listaToma : t) {
			List<TomaParcial> tp = listaToma.getPartes();

			for (TomaParcial tomaP : tp) {
				System.out.println(tomaP.getDispensador().getNomeMedicamento());
				int dias = 0;
								
				int dispQuant = tomaP.getDispensador().getquantidadeTotal();
				System.out.println("Disp_quant: " + dispQuant);
				int quant = tomaP.getQuant();
				System.out.println("Toma_quant: " + quant);
				
				for(int x = 0 ; x <= (SETE_DIAS) ; x++){
					if(dispQuant - quant >= 0){
						dias++;
						dispQuant -= quant;
					}
				}
				System.out.println(dias);
				
				if(dias < TRES_DIAS ){
					mudarLed(disp, CorLedTampa.RED);
					System.out.println("RED");
				}
				
				if(dias > TRES_DIAS && dias < SETE_DIAS){
					mudarLed(disp, CorLedTampa.YELLOW);
					System.out.println("YELLOW");
				}
				
				if(dias > SETE_DIAS ){
					mudarLed(disp, CorLedTampa.GREEN);
					System.out.println("GREEN");
				}
				
				System.out.println(disp);
				System.out.println("----------------------------------");
				disp++;
			}
		}
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** método que é chamado pelo temporizador do hardware
	 * sempre que se passam 20 segundos
	 */
	public void processar() { // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		if(x){
			if(controlo < 15){
				switch(controlo){
					case 0: // 0min
						this.hardware.tocaAlarme();
						break;
					case 3: // 1min
						this.hardware.tocaAlarme();
						break;
					case 6: // 2min
						this.hardware.tocaAlarme();
						break;
					case 9: // 3min
						this.hardware.tocaAlarme();
						break;
					case 12: // 4min
						this.hardware.tocaAlarme();
						break;
				}
				controlo++;
			}
			this.hardware.tocaAlarme(); // 5min
		}
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** método que é chamado pelo hardware quando o utente
	 *  removeu os comprimidos da gaveta
	 */
	public void gavetaVazia() { // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		// deve fechar a gaveta
		x = false;
		controlo = 0;
		this.hardware.fecharGaveta();
	}
	//CHANGE_END
	

	/** método que vai verificar se há listaTomas que têm
	 * de ser ministradas neste momento
	 */
	private void checkTomas() {
			
	}	

	
	//CHANGE_START
	/** Indica quantos listaDispensadores horizontais existem
	 * @return número de listaDispensadores horizontais
	 */
	public int getDispensadoresHorizontais() {
		return hardware.getHorizontais();
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** Indica quantos listaDispensadores verticais existem
	 * @return número de listaDispensadores verticais
	 */
	public int getDispensadoresVerticais() {
		return hardware.getVerticais();
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** Abre o listaDispensador indicado
	 * @param disp número do listaDispensador a abrir
	 */
	public void abrirDispensador( int disp ) {
		hardware.abrirTampa(disp);
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** Fecha o listaDispensador indicado
	 * @param disp número do listaDispensador a fechar
	 */
	public void fecharDispensador( int disp ) { // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		hardware.fecharTampa(disp);
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** indica se o dispensador está livre ou em uso
	 * @param dispNum número do listaDispensador
	 * @return true, se o listaDispensador está livre, false caso esteja em uso
	 */
	public boolean estaLivre( int dispNum ) {
		if(dispNum < listaDispensador.size()){
			//System.out.println(getDispensador(dispNum).estaUsado());
			if(getDispensador(dispNum).estaUsado())
				return false;
		}
		return true;
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** indica qual o medicamento que está associado ao listaDispensador
	 * @param dispNum  número do listaDispensador
	 * @return nome do medicamento associado ao listaDispensador,
	 * null, caso não haja medicamento associado
	 */
	public String getMedicamento( int dispNum ){
		if(dispNum < listaDispensador.size()) {
			return getDispensador(dispNum).getNomeMedicamento();
		}
		return "";
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** indica quantos comprimidos ainda tem no listaDispensador
	 * @param dispNum número do listaDispensador
	 * @return quantos comprimidos ainda tem no listaDispensador
	 */
	public int getQuantidade( int dispNum ) {
		if(dispNum < listaDispensador.size()) {
			return getDispensador(dispNum).getquantidadeTotal();
		}
		return 0;
	}
	//CHANGE_END
	
	
	/** Configura um listaDispensador, indicando qual o medicamento a ele associado
	 * e respetiva quantidade de comprimidos
	 * 
	 * @param dispNum número do listaDispensador
	 * @param medicamento nome do medicamento
	 * @param quant quantidade de comprimidos
	 */
	public void configurarDispensador( int dispNum, String medicamento, int quant ) {
		
	}

	
	/** carrega o listaDispensador com a quantidade de medicamentos indicada
	 * @param dispNum número do listaDispensador
	 * @param quant quantidade de comprimidos a adicionar
	 */
	public void carregarDispensador(int dispNum, int quant) {

	}

	
	/** Adiciona uma listaToma à lista de listaTomas. Se a listaToma a adicionar tiver a mesma data
	 * que uma listaToma já existente, a nova deve ser associada à já existente.
	 * As listaTomas devem estar ordenadas por ordem crescente da data de listaToma.
	 * 
	 * @param quando data a que a listaToma deve ser efetuada
	 * @param dispNum qual o listaDispensador a usar
	 * @param quant quantos comprimidos a usar
	 */
	public void addToma(LocalDateTime quando, int dispNum, int quant ) {
		
	}
	
	
	/** devolve uma lista com as listaTomas que devem ser realizadas até à data indicada
	 * @param fim data de fim da pesquisa
	 * @return uma lista com as listaTomas que devem ser realizadas até à data indicada
	 */
	public List<Toma> listaTomasAte( LocalDateTime fim ){
		return null;
	}

	
	//CHANGE_START
	public void addDispensador(Dispensador d){
		listaDispensador.add(d);
	}
	//CHANGE_END
	
	
	//CHANGE_START
	public Dispensador getDispensador(int idx){
		return listaDispensador.get(idx);
	}
	//CHANGE_END
	
	
	//CHANGE_START
	public void addToma(Toma t) {
		listaToma.add(t);
	}
	//CHANGE_END
	
	//CHANGE_START
	public void mudarLed(int disp, CorLedTampa c){ // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		hardware.turnLed(disp, c);
	}
	//CHANGE_END
}

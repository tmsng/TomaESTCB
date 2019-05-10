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
	public static final int SETE_DIAS = 7;
	public static final int TRES_DIAS = 3;
	
	private boolean temToma = false;
	private int controlo = 0;

	// deve ter uma lista de listaDispensadores e outra de listaTomas
	private ArrayList<Dispensador> listaDispensador = new ArrayList<Dispensador>();
	
	private ArrayList<Toma> listaToma = new ArrayList<Toma>();
	//CHANGE_END
	
	
	//CHANGE_START
	/** cria uma máquina com o respetivo hardware 
	 * @param hardware hardware da máquina que vai controlar
	 */
	public Maquina(Hardware hardware) {
		this.hardware = hardware;
		
		// configurar os restantes elementos da máquina
		
		int disp_H = getDispensadoresHorizontais();
		int disp_V = getDispensadoresVerticais();
		int listaDispSize = disp_H * disp_V;
		
		while (listaDispensador.size() < listaDispSize){
		     listaDispensador.add(new Dispensador());
		}
			
		hardware.setMaquina( this );
	}
	//CHANGE_END
	
	
	//CHANGE_START
	public void ligaLed(){
		verificarLed(listaToma, listaDispensador);
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** Verifica todas as tomas e associa cada
	 * uma delas a uma cor de Led correspondente
	 * @param t ArrayList(Toma)
	 * @param d ArrayList(Dispensador)
	 */
	public void verificarLed(ArrayList<Toma> t, ArrayList<Dispensador> d){
		//System.out.println(t);
		//System.out.println(d);
		
		System.out.println("\n============================\n");
		
		int disp = 0;
		
		for (Dispensador dispensador : d) {
			
			for (Toma listaToma : t) {
				List<TomaParcial> tp = listaToma.getPartes();

				for (TomaParcial tomaP : tp) {
					Dispensador idxOf = tomaP.getDispensador();
			
					if(idxOf == dispensador) {
						System.out.println(dispensador.getNomeMedicamento());
						
						int dias = 0;
						
						int dispQuant = dispensador.getquantidadeTotal();
						System.out.println("Quant_Disp: " + dispQuant);
						
						int quant = tomaP.getQuant();
						System.out.println("Quant_TomaP: " + quant);
						
						for(int x = 0 ; x <= (SETE_DIAS) ; x++){
							if(dispQuant - quant >= 0){
								dias++;
								dispQuant -= quant;
							}
						}
						
						System.out.println("Dias_Uso: " + dias);
						
						if(dias < TRES_DIAS ){
							mudarLed(disp, CorLedTampa.RED);
							System.out.println("RED");
						}
					
						if(dias >= TRES_DIAS && dias < SETE_DIAS){
							mudarLed(disp, CorLedTampa.YELLOW);
							System.out.println("YELLOW");
						}
						
						if(dias >= SETE_DIAS ){
							mudarLed(disp, CorLedTampa.GREEN);
							System.out.println("GREEN");
						}	
						System.out.println("----------------------");
					}
				}
			}
			disp++;
		}
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** método que é chamado pelo temporizador do hardware
	 * sempre que se passam 20 segundos
	 */
	public void processar() {
		System.out.println("Checking Tomas");
		checkTomas();
		System.out.println("Toma? " + temToma);
		if(temToma){
			this.hardware.abrirGaveta();
			System.out.println("Tomar! Alerta " + controlo);
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
			}else{
				this.hardware.tocaAlarme(); // 5min
			}
		}
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** método que é chamado pelo hardware quando o utente
	 *  removeu os comprimidos da gaveta
	 */
	public void gavetaVazia() { //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		// deve fechar a gaveta
		controlo = 0;
		temToma = false;
		this.hardware.fecharGaveta();
	}
	//CHANGE_END
	

	/** método que vai verificar se há listaTomas que têm
	 * de ser ministradas neste momento
	 */
	private void checkTomas() {
		LocalDateTime now = LocalDateTime.now();
		
		for (Toma toma : listaToma) {
			System.out.println(now + " | " + toma.getQuando());
			if(now.getMinute() >= toma.getQuando().getMinute()){
				temToma = true;
			}
		}
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
	public void fecharDispensador( int disp ) {
		hardware.fecharTampa(disp);
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** indica se o dispensador está livre ou em uso
	 * @param dispNum número do listaDispensador
	 * @return true, se o listaDispensador está livre, false caso esteja em uso
	 */
	public boolean estaLivre( int dispNum ) {
		//System.out.println(getDispensador(dispNum).estaUsado());
		if(getDispensador(dispNum).estaUsado())
			return false;
		
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
		return getDispensador(dispNum).getNomeMedicamento();
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** indica quantos comprimidos ainda tem no listaDispensador
	 * @param dispNum número do listaDispensador
	 * @return quantos comprimidos ainda tem no listaDispensador
	 */
	public int getQuantidade( int dispNum ) {
		return getDispensador(dispNum).getquantidadeTotal();
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** Configura um listaDispensador, indicando qual o medicamento a ele associado
	 * e respetiva quantidade de comprimidos
	 * 
	 * @param dispNum número do listaDispensador
	 * @param medicamento nome do medicamento
	 * @param quant quantidade de comprimidos
	 */
	public void configurarDispensador( int dispNum, String medicamento, int quant ) {
		Dispensador d = new Dispensador(medicamento, quant);
		listaDispensador.set(dispNum, d);
		mudarLed(dispNum, CorLedTampa.GREEN);
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** carrega o listaDispensador com a quantidade de medicamentos indicada
	 * @param dispNum número do listaDispensador
	 * @param quant quantidade de comprimidos a adicionar
	 */
	public void carregarDispensador(int dispNum, int quant) {
		getDispensador(dispNum).addQuantidade(quant);
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** Adiciona uma listaToma à lista de listaTomas. Se a listaToma a adicionar tiver a mesma data
	 * que uma listaToma já existente, a nova deve ser associada à já existente.
	 * As listaTomas devem estar ordenadas por ordem crescente da data de listaToma.
	 * 
	 * @param quando data a que a listaToma deve ser efetuada
	 * @param dispNum qual o listaDispensador a usar
	 * @param quant quantos comprimidos a usar
	 */
	public void addToma(LocalDateTime quando, int dispNum, int quant ) {
		Toma t = new Toma(quando);
		
		boolean tempoDif = true;
		
		//System.out.println("Data enviada: " + quando);
		
		for (int i = 0; i < listaToma.size(); i++) {
			//System.out.println("Data listada: " + listaToma.get(i).getQuando());
			//System.out.println("Data igual? " + (listaToma.get(i).getQuando() == quando));
			
			
			if(listaToma.get(i).getQuando() == quando) {
				tempoDif = false;
				
				for (int j = 0 ; j < listaToma.get(i).getPartes().size() ; j++) {
					TomaParcial tP = listaToma.get(i).getPartesIdx(j);
					
					t.addPartes(new TomaParcial( tP.getDispensador() , tP.getQuant()));
				}
				
				t.addPartes(new TomaParcial( getDispensador(dispNum) , quant));
	
				//System.out.println("Partes_size: " + listaToma.get(i).getPartes().size() );
				
				listaToma.remove(i);
			}
		}
		
		if(tempoDif)
			t.addPartes(new TomaParcial( getDispensador(dispNum) , quant));
		
		
		listaToma.add(t);
		
		tempoDif = true;
		
		//System.out.println("Toma_size: " + listaToma.size());
		//System.out.println(listaToma);
	}
	//CHANGE_END
	
	
	/** devolve uma lista com as listaTomas que devem ser realizadas até à data indicada
	 * @param fim data de fim da pesquisa
	 * @return uma lista com as listaTomas que devem ser realizadas até à data indicada
	 */
	public List<Toma> listaTomasAte( LocalDateTime fim ){
		return null;
	}
	
	
	//CHANGE_START
	public Dispensador getDispensador(int idx){
		return listaDispensador.get(idx);
	}
	//CHANGE_END
	
	
	//CHANGE_START
	public void mudarLed(int disp, CorLedTampa c){
		hardware.turnLed(disp, c);
	}
	//CHANGE_END
}

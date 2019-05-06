package tomaest.arranque;

import java.time.LocalDateTime;

import tomaest.app.PhoneApp;
import tomaest.hardware.Hardware;
import tomaest.maquina.Maquina;

/**
 * Classe que inicializa o sistema, criando as condições necessárias
 * para um teste eficaz do mesmo
 */
public class Arranque {

	public static void main(String[] args) {
		// ñº de dispensadores é aleatório, mas no mínimo é 4x2
		int nHoriz = 4 + (int)(Math.random()*100) % 7;
		int nVert  = 2 + (int)(Math.random()*100) % 3;

		// criar os 3 componentes
		Hardware h = new Hardware( nHoriz, nVert );
		Maquina maq = new Maquina( h );
		PhoneApp phone = new PhoneApp( );

		// preparar as condições inicias de teste
		prepararDispensadoresIniciais( maq );
		prepararTomasIniciais( maq );
		
		// onde colocar as janelas das aplicações
		h.getJanela().setLocation( 20, 20 );
		h.getJanela().setVisible(true );

		phone.setLocation( h.getJanela().getWidth() + 30, 20 );
		phone.setVisible( true );
		phone.menuPrincipal();
	}

	/** Configura os dispensadores com as condições iniciais indicadas no enunciado
	 * @param maq máquina a configurar
	 */
	private static void prepararDispensadoresIniciais(Maquina maq) {
		// TODO falta fazer este método
		// falta fazer este método
		// falta fazer este método
		// falta fazer este método
	}

	/** Configura as tomas que já devem estar criadas aquando do arranque do sistema,
	 * para assim se terem condiçoes de teste, sem estar sempre a criar tomas à mão.
	 * Ver as tomas a criar no enunciado.
	 * @param maq máquina a configurar
	 */
	private static void prepararTomasIniciais(Maquina maq) {
		// ver a hora atual e colocar os segundos a zero
		LocalDateTime inicio = LocalDateTime.now();  
		inicio = inicio.minusSeconds( inicio.getSecond() );

		// TODO falta fazer o resto deste método
		// falta fazer o resto deste método
		// falta fazer o resto deste método
		// falta fazer o resto deste método
	}
}

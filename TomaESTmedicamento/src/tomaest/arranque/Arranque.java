package tomaest.arranque;

import java.time.LocalDateTime;

import tomaest.app.PhoneApp;
import tomaest.hardware.Hardware;
import tomaest.maquina.Maquina;

/**
 * Classe que inicializa o sistema, criando as condi��es necess�rias
 * para um teste eficaz do mesmo
 */
public class Arranque {

	public static void main(String[] args) {
		// � de dispensadores � aleat�rio, mas no m�nimo � 4x2
		
		//CHANGE_START
		int nHoriz = 4; //+ (int)(Math.random()*100) % 7;
		int nVert  = 2; //+ (int)(Math.random()*100) % 3;
		//CHANGE_END
		
		// criar os 3 componentes
		Hardware h = new Hardware( nHoriz, nVert );
		Maquina maq = new Maquina( h );
		//CHANGE_START
		PhoneApp phone = new PhoneApp( maq );
		//CHANGE_END
		
		// preparar as condi��es inicias de teste
		prepararDispensadoresIniciais( maq );
		prepararTomasIniciais( maq );
		
		// onde colocar as janelas das aplica��es
		h.getJanela().setLocation( 20, 20 );
		h.getJanela().setVisible(true );

		phone.setLocation( h.getJanela().getWidth() + 30, 20 );
		phone.setVisible( true );
		//CHANGE_START
		phone.menuPrincipal(maq);
		//CHANGE_END
		
	}

	/** Configura os dispensadores com as condi��es iniciais indicadas no enunciado
	 * @param maq m�quina a configurar
	 */
	private static void prepararDispensadoresIniciais(Maquina maq) {
		// TODO falta fazer este m�todo ----------------------------------------------------------------------------------------------------------------------
		//CHANGE_START
		maq.dispensadoresIniciais();
		//CHANGE_END
	}

	/** Configura as tomas que j� devem estar criadas aquando do arranque do sistema,
	 * para assim se terem condi�oes de teste, sem estar sempre a criar tomas � m�o.
	 * Ver as tomas a criar no enunciado.
	 * @param maq m�quina a configurar
	 */
	private static void prepararTomasIniciais(Maquina maq) {
		// ver a hora atual e colocar os segundos a zero
		LocalDateTime inicio = LocalDateTime.now();  
		inicio = inicio.minusSeconds( inicio.getSecond() );
		// TODO falta fazer o resto deste m�todo --------------------------------------------------------------------------------------------------------------
		//CHANGE_START
		maq.tomasIniciais(inicio);
		//CHANGE_END
	}
}

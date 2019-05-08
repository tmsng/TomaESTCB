package tomaest.app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

//CHANGE_START
import tomaest.maquina.Maquina;
//CHANGE_END

/** Classe que representa a aplicação de telemóvel. 
 * É a única responsável pela interação com o utilizador.
 * SÓ ESTA CLASSE É QUE DEVE FAZER INTERAÇÕES COM O UTILIZADOR.
 * Para simplificar o desenvolvimento não devem ser verificadas
 * condições de erro aquando da interação com o utilizador. Esta classe
 * NÃO deve interagir com o hardware da máquina, apenas com a classe Maquina.
  */
public class PhoneApp extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// elementos para a interface com o utilizador
	private int resposta;
	private Image fundo;
	private JPanel painelApp;
	private Rectangle screenRect;

	// cores para as interações
	private static final Color corFundo = new Color( 150, 150, 200 );
	private static final Color corTexto = new Color( 250, 250, 250 );
	private static final Color corCaixa = new Color( 200, 200, 200 );

	//CHANGE_START
	private Maquina maq;
	private int h;
	private int v;
	//CHANGE_END
	
	/** cria a aplicação indicando qual a máquina com que vai comunicar
	 * @param maquina máquina que vai ser configurada pela aplicação
	 */
	public PhoneApp( Maquina maq ) {
		//setSize(284,590);
		setTitle( "Phone App");	
		setDefaultCloseOperation( EXIT_ON_CLOSE );
	
		// ler a imagem de fundo
		try {
			fundo = ImageIO.read( new File("data//fundo2.gif") );
		} catch (IOException e) {
			System.out.println("Não consegui carregar a imagem de fundo");
		}

		setContentPane( new PainelCentral( fundo ) );

		pack();
		setResizable( false );
		
		//CHANGE_START
		this.h = maq.getDispensadoresHorizontais();
		this.v = maq.getDispensadoresVerticais();
		//CHANGE_END
	}
	
	//CHANGE_START
	/** apresenta o menu principal da aplicação
	 */
	public void menuPrincipal(Maquina maq) {
		String opcoes[] = {"Programar Toma", "Carregar Dispensador", "Configurar Dispensador", "Sair"};
		do {
			int opcao = showMenu( "Menu principal", opcoes );
			//System.out.println( "Menu opcao: " + opcao );
			switch( opcao ) {
			case 0: programarToma(maq); break;
			case 1: carregarDispensador(maq); break;
			case 2: configurarDispensador(maq); break;
			case 3: System.exit( 0 );
			}
		} while( true );
	}
	//CHANGE_END
	
	
	//CHANGE_START
	/** Verificar se a String é integer
	 * @param s String a avaliar
	 */
	public boolean verificarStringInt(String s){ // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	    try{
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex){
	        return false;
	    }
	}
	//CHANGE_END
	
	
	/** programa uma toma: pede a data, o(s) dispensador(es) e
	 * a quantidade a usar de cada dispensador(es)
	 */
	private void programarToma(Maquina maq) {
		// TODO tem de saber quantos dispensadores colocar
		//CHANGE_START
		int nHoriz = h;
		int nVert = v;
		//CHANGE_END
		
		LocalDateTime quando = escolherData();
		
		while( true ) { 
			int dispNum = escolherDispensador( nHoriz, nVert, false, maq);
			if( dispNum == -1 )
				return;
		
			// TODO qual o nome do medicamento??
			//CHANGE_START
			String nomeMedicamento = maq.getMedicamento(dispNum);
			//CHANGE_END
			
			String quantStr = pedirValor( "Quantos " + nomeMedicamento+ "?" );
			
			//CHANGE_START
			int quant = verificarStringInt(quantStr) ? Integer.parseInt( quantStr ) : 1; // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
			//System.out.println(quant);
			//CHANGE_END
			
			// TODO tem de registar a toma
			
		}
	}
	

	/** pede os dados para carregar o dispensador: qual deles e a quantidade a adicionar
	 */
	private void carregarDispensador(Maquina maq) {
		// TODO tem de saber quantos dispensadores colocar
		//CHANGE_START
		int nHoriz = h;
		int nVert = v;
		//CHANGE_END
		
		int dispNum = escolherDispensador( nHoriz, nVert, false, maq );
		if( dispNum == -1 )
			return;
		
		// TODO abrir a tampa do dispensador correto
		//CHANGE_START
		maq.abrirDispensador(dispNum);
		//CHANGE_END
		
		// TODO qual o nome do medicamento
		//CHANGE_START
		String nomeMedicamento = maq.getMedicamento(dispNum);
		//CHANGE_END
		
		String quantStr = pedirValor( "Quantos " + nomeMedicamento+ "?" );
		
		//CHANGE_START
		int quant = verificarStringInt(quantStr) ? Integer.parseInt( quantStr ) : 0; // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		//System.out.println(quant);
		
		maq.fecharDispensador(dispNum); // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		//CHANGE_END
		
		// TODO tem de carregar o dispensador
		
	}
	

	/** pede os dados de configuração de um dispensador:
	 * qual o dispensador, qual o nome do medicamento e a quantidade de comprimidos
	 */
	private void configurarDispensador(Maquina maq) {
		// TODO tem de saber quantos dispensadores colocar
		//CHANGE_START
		int nHoriz = h;
		int nVert = v;
		//CHANGE_END
		
		int disp = escolherDispensador( nHoriz, nVert, true, maq );
		if( disp == -1 )
			return;
		
		// TODO abrir a tampa do dispensador correto
		//CHANGE_START
		maq.abrirDispensador(disp);
		//CHANGE_END
		
		String medicamento = pedirValor( "Qual o medicamento?" );
		String quantStr = pedirValor( "Quantos " + medicamento+ "?" );
		
		//CHANGE_START
		int quant = verificarStringInt(quantStr) ? Integer.parseInt( quantStr ) : 1; // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		//System.out.println(quant);
		
		maq.fecharDispensador(disp); // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		//CHANGE_END
		
		// TODO tem de configurar o dispensador
		
	}
	
	
	/** Apresenta todos os dispensadores da máquina, sendo que alguns ficam ativos e outros desativos.
	 * Os que ficam ativos são os que obedecem ai critério de estarem ou não livres
	 * @param nHoriz quantos dispensadores há na horizontal
	 * @param nVert quantos dispensadores há na vertical
	 * @param livre é para apresentar os livres (true) ou ocupados (false)?
	 * @return qual o número do dispensador escolhido
	 */
	private int escolherDispensador( int nHoriz, int nVert, boolean livre , Maquina m) {
		JPanel principal = createInterfacePanel();
		int topo = screenRect.y;
		topo = meterTitulo("Escolha o dispensador", principal, topo);
		
		int left = 20+screenRect.x + (screenRect.width - nHoriz *35 ) / 2;
		topo += 35 * (nVert-1);
				
		for( int y=0; y < nVert; y++ ) { 
			for( int x=0; x < nHoriz; x++ ){
				final int btNum = y*nHoriz + x;
				JButton bt = new JButton( "" + btNum );
				bt.setMargin( new Insets(0,0,0,0) );
				bt.setAlignmentX( Component.CENTER_ALIGNMENT );
				bt.setBounds( left + x*34, topo, 30, 30 );
				bt.setBackground( corCaixa );
				principal.add( bt );
				
				//CHANGE_START
				//System.out.println("Disp > " + btNum);
				//CHANGE_END
				
				// TODO tem de saber se o dispensador está usado ou livre <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				//CHANGE_START
				boolean estaLivre = m.estaLivre(btNum);
				//CHANGE_END
				
				//CHANGE_START
				if( !estaLivre ) {
					// TODO qual o nome do medicamento??
					String medicamento = m.getMedicamento(btNum);
					//System.out.println("Texto: " + medicamento);
					
					// TODO qual a quantidade existente?
					int quant = m.getQuantidade(btNum);
					//System.out.println("Quant: " + quant);
					bt.setToolTipText( medicamento + "(" + quant + ")" );
				}
				//CHANGE_END
				
				// TODO tem de saber se o dispensador está usado ou livre <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				if( estaLivre == livre ) {
					bt.addActionListener( new ActionListener() {
						public void actionPerformed(ActionEvent e) {						
							setResposta( btNum );
						}
					});
				}
				else
					bt.setEnabled( false );
			}
			topo -= 35;
		}
		
		// criar o botão de voltar
		JButton btBack = new JButton( "<" );
		btBack.setMargin( new Insets(0,0,0,0) );
		btBack.setAlignmentX( Component.CENTER_ALIGNMENT );
		btBack.setBounds( 4, 50, 30, 30 );
		btBack.setBackground( corCaixa );
		btBack.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {						
				setResposta( -1 );
			}
		});
		principal.add( btBack );
		
		setInterface(principal);
		return esperaResposta( );
	}
	
	
	/** apresenta uma lista de datas para o utilizador escolher uma delas
	 * @return a data escolhida pelo utilizador
	 */
	private LocalDateTime escolherData() {
		JPanel principal = createInterfacePanel();
		int topo = screenRect.y;
		
		/** classe interna para apresentar a data no formato desejado e 
		 * não no formato dado pela classe LocalDateTime. Necessária
		 * porque a classe JList usa o toString para apresentar a informação
		 * na lista. Isto podia ser feito de forma mais simples com a herança,
		 * mas esta ainda não foi dada nas aulas.
		 */
		class DataFormatada {
			LocalDateTime data;

			public DataFormatada(LocalDateTime data) {
				super();
				this.data = data;
			}

			public LocalDateTime getData() {
				return data;
			}

			public String toString() {
				return String.format( "%02d/%02d às %02d:%02d", data.getDayOfMonth(), data.getMonthValue(), data.getHour(), data.getMinute() );
			}
		}
		
		topo = meterTitulo("Quando tomar?", principal, topo);
		LocalDateTime inicio = LocalDateTime.now();
		// criar a lista de datas
		DataFormatada []escolhas = { new DataFormatada( inicio.plusMinutes( 2 ) ),
									 new DataFormatada( inicio.plusMinutes( 4 ) ),
									 new DataFormatada( inicio.plusMinutes( 12 ) ),
									 new DataFormatada( inicio.plusHours( 1 ) ),
									 new DataFormatada( inicio.plusHours( 10 ) ),
									 new DataFormatada( inicio.plusDays( 1 ) ),
									 new DataFormatada( inicio.plusDays( 2 ) ),
									 new DataFormatada( inicio.plusDays( 3 ) ),
									 new DataFormatada( inicio.plusDays( 5 ) ),
									 new DataFormatada( inicio.plusDays( 7 ) )
				                     };
		JList<DataFormatada> listaData = new JList<DataFormatada>( escolhas );
		listaData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaData.setLayoutOrientation(JList.VERTICAL);
		listaData.setVisibleRowCount( -1 );
		listaData.setSelectedIndex( 0 );
		listaData.setBounds( 10, topo, screenRect.width - 20, 80);
		JScrollPane sp = new JScrollPane( listaData );
		sp.setBounds( 10, topo, screenRect.width - 20, 80);
		sp.setPreferredSize( new Dimension(screenRect.width - 20, 80) );
		principal.add( sp );
		
		JButton btOk = criarBotaoOk(topo+60);
		principal.add( btOk );
		
		setInterface(principal);
		principal.getRootPane().setDefaultButton( btOk );
		
		esperaResposta( );
		return listaData.getSelectedValue().getData();
	}

	
	/** apresenta um pergunta ao utilziador para que este introduza um valor
	 * @param titulo pergunta a colocar
	 * @return retorna o valor pedido (será sempre uma string, a qual
	 * pode depois ser convertida para o tipod e dados correto)
	 */
	private String pedirValor(String titulo) {
		JPanel principal = createInterfacePanel();
		int topo = screenRect.y;
		topo = meterTitulo(titulo, principal, topo);
		
		JTextField quant = new JTextField( "" );
		quant.setBounds( screenRect.x + 20, topo, screenRect.width-30, 40 );
		principal.add( quant );
		
		JButton btOk = criarBotaoOk(topo);
		principal.add( btOk );
		
		setInterface(principal);
		quant.requestFocusInWindow();
		principal.getRootPane().setDefaultButton( btOk );

		esperaResposta( );
		return quant.getText();
	}

	
	/** cria um botão que representa o ok nas janeals de pedir dados 
	 * @param topo coordenada y onde colocar o botão
	 * @return o botão criado
	 */
	private JButton criarBotaoOk(int topo) {
		JButton btOk = new JButton("Ok");
		btOk.setBounds( screenRect.x + 50, topo+50, screenRect.width-80, 30 );
		btOk.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {						
				setResposta( 0 );
			}
		});
		return btOk;
	}


	// A PARTIR DESTE PONTO O CÓDIGO NÃO DEVE SER ALTERADO
	// A PARTIR DESTE PONTO O CÓDIGO NÃO DEVE SER ALTERADO
	// A PARTIR DESTE PONTO O CÓDIGO NÃO DEVE SER ALTERADO
	// A PARTIR DESTE PONTO O CÓDIGO NÃO DEVE SER ALTERADO
	// A PARTIR DESTE PONTO O CÓDIGO NÃO DEVE SER ALTERADO
	
	
	/** cria uma painel de interface que serve para colocar os restantes elementos de interação
	 * @return o painel criado
	 */
	private JPanel createInterfacePanel() {
		JPanel principal = new JPanel( null );
		principal.setBounds( screenRect );
		principal.setPreferredSize(new Dimension( screenRect.width, screenRect.height) );
		principal.setBackground( Color.PINK );
		principal.setOpaque( false );
		return principal;
	}
	
	/** Apresenta um menu de interação!
	 * @param titulo título do menu
	 * @param opcoes as opções para o menu
	 * @return a opção escolhida
	 */
	private int showMenu( String titulo, String []opcoes ) {
		JPanel principal = createInterfacePanel();
		int topo = screenRect.y;

		if( titulo != null ) {
			topo = meterTitulo(titulo, principal, topo);
		}

		for( int i=0; i < opcoes.length; i++ ) {
			String opcao = opcoes[i];
			JButton bt = new JButton(opcao);
			bt.setAlignmentX( Component.CENTER_ALIGNMENT );
			bt.setBounds( screenRect.x+20, topo, screenRect.width-25, 30 );
			bt.setBackground( corCaixa );
			principal.add( bt );
			topo += 30;
			final int sel = i;
			bt.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setResposta( sel );
				}
			});
		}
		principal.setAlignmentY( Component.CENTER_ALIGNMENT );
		setInterface(principal);
		return esperaResposta( );
	}

	/** escreve um titulo no painel de interação
	 * @param titulo o título a colocar 
	 * @param principal paindel de interação onde colocar o título
	 * @param topo coordenada y onde colcoar o título
	 * @return coordenada imediatamente abaixo do titulo
	 */
	private int meterTitulo(String titulo, JPanel principal, int topo) {
		JLabel tituloLbl = new JLabel(titulo);
		tituloLbl.setFont( new Font("Arial", Font.BOLD, 20) );
		tituloLbl.setForeground( corTexto );
		tituloLbl.setHorizontalAlignment( JLabel.CENTER );
		tituloLbl.setBounds( screenRect.x+20, topo, screenRect.width-25, 20 );
		tituloLbl.setOpaque( false );
		topo += 30;
		principal.add( tituloLbl );
		return topo;
	}

	/** método que faz o sistema ficar à espera de uma resposta
	 * @return a resposta dada pelo utilizador
	 */
	private synchronized int esperaResposta( ) {
		try {
			wait();
		} catch (InterruptedException e1) {	}
		return resposta;
	}
	
	/** define a resposta a dar a um pedido e termina a espera pela resposta
	 * @param sel a resposta a dar a um pedido
	 */
	private synchronized void setResposta(int sel) {
		resposta = sel;
		notify();
	}

	/** define a nova interface a ser usada na aplicação móvel
	 * @param principal a nova interface a ser usada 
	 */
	private void setInterface(JPanel principal) {
		while( painelApp.getComponentCount() >= 1)
			painelApp.remove(0);
		painelApp.add( principal );
		validate();
	}

	/** classe auxiliar que será usada para criar o painel central de interção
	 * da aplicação. 
	 * @author F. Sérgio Barbosa
	 */
	private class PainelCentral extends JPanel {
		Image fundo;

		PainelCentral( Image fundo ){
			this.fundo = fundo;
			Dimension dim = new Dimension( fundo.getWidth(null)+10, fundo.getHeight(null)+10);
			setMaximumSize( dim );
			setPreferredSize( dim );
			setMinimumSize( dim );
			setLayout( null );

			/*
			painelApp = new JPanel();
			painelApp.setBounds( 8, 20, dim.width-16, dim.height-40 );
			painelApp.setBackground( corFundo );
			add( painelApp );

			Rectangle r = painelApp.getBounds();
			screenRect = new Rectangle( 4, 4, r.width-12, r.height-20 );
			*/
			painelApp = new JPanel();
			painelApp.setBounds( 20, 8, dim.width-40, dim.height-16 );
			painelApp.setPreferredSize( new Dimension(dim.height-16, dim.width-40 ) );
			painelApp.setBackground( corFundo );
			add( painelApp );

			Rectangle r = painelApp.getBounds();
			screenRect = new Rectangle( 4, 4, r.width-15, r.height-12 );
		}

		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage( fundo, 5, 5, null);	// desenhar a imagem do telemóvel
		}
	}
}

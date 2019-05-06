package tomaest.hardware;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import tomaest.maquina.Maquina;

/** Classe que simula o hardware da máquina. 
 * 
 * @author F. Sérgio Barbosa
  */
//ESTA CLASSE NÃO PODE SER ALTERADA
//ESTA CLASSE NÃO PODE SER ALTERADA
//ESTA CLASSE NÃO PODE SER ALTERADA
//ESTA CLASSE NÃO PODE SER ALTERADA
//ESTA CLASSE NÃO PODE SER ALTERADA
public class Hardware {

	private Maquina maquina;  // software que controla a máquina
	
	// variáveis para guardar os vários estados da máquina 
	private boolean gavetaEstaAberta, gavetaEstaVazia;
	private int nVert, nHoriz;
	private boolean tampasOpen[][];
	
	private Clip alarme; // som do alarme
	private HardwareJanela janela; // janela que apresenta a máquina

	/** cria o hardware indicando quantos dispensadores tem na horizontal e na vertical
	 * @param nHoriz quantos dispensadores tem na horizontal 
	 * @param nVert quantos dispensadores tem na vertical 
	 */
	public Hardware(int nHoriz, int nVert) {
		this.nHoriz = nHoriz;
		this.nVert = nVert;
		tampasOpen = new boolean[ nHoriz][nVert ];    // cria as tampas, e todas fechadas
		janela = new HardwareJanela( nHoriz, nVert ); // cria a janela
		setupTimer();
		setupAlarme();
	}
	
	/** toca o alarme
	 */
	public void tocaAlarme() {
		alarme.setFramePosition( 0 );
        alarme.start();
	}

	/** abre a tapa de um dispensador
	 * @param t número do dispensador de que se pretende abrir a tampa
	 */
	public void abrirTampa( int t ) {
		tampasOpen[t%nHoriz][t/nHoriz] = true;
		janela.fundo.repaint();
	}

	/** fecha a tapa de um dispensador
	 * @param t número do dispensador de que se pretende fechar a tampa
	 */
	public void fecharTampa( int t ) {
		tampasOpen[t%nHoriz][t/nHoriz] = false;
		janela.fundo.repaint();
	}

	/** define a cor a ssociar a um dos leds
	 * @param t dispensador para o qual se quer mudar o led
	 * @param led cor do led a usar
	 */
	public void turnLed( int t, CorLedTampa led ) {
		janela.tampas[t%nHoriz][t/nHoriz] = janela.coresTampas[ led.ordinal() ];
		janela.fundo.repaint();
	}

	/** retorna o número de dispensadores que tem na horizontal
	 * @return o número de dispensadores que tem na horizontal
	 */
	public int getHorizontais() {
		return nHoriz;
	}

	/** o número de dispensadores que tem na vertical
	 * @return o número de dispensadores que tem na vertical
	 */
	public int getVerticais() {
		return nVert;
	}

	/** fecha a gaveta dos medicamentos
	 */
	public void fecharGaveta() {
		gavetaEstaAberta = false;
		gavetaEstaVazia = true;
		janela.fundo.repaint();
	}

	/** abre a gaveta dos medicamentos
	 */
	public void abrirGaveta() {
		gavetaEstaAberta = true;
		gavetaEstaVazia = false;
		janela.fundo.repaint();
	}

	/** define qual o software associado a este hardware
	 * @param maquina o software associado a este hardware
	 */
	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}
	
	/** devolve a janela de simulação
	 * @return  a janela de simulação
	 */
	public JFrame getJanela() {
		return janela;
	}

	/** cria um temporizaodr, para que este informe a aplicação
	 * da passagem do tempo a cada 20 segundos
	 */
	private void setupTimer() {
		Timer t = new Timer( 20*1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( maquina != null )
					maquina.processar();
				janela.fundo.repaint();
			}
		});
		t.start();
	}
	
	/** lê a informação do ficheiro de som e inicializa o alarme
	 */
	private void setupAlarme() {
	     try {
	         // Open an audio input stream.           
	         File soundFile = new File("data//alarme.wav"); 
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
	         // Get a sound clip resource.
	         alarme = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         alarme.open(audioIn);	        
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	}

	/** janela que mostra a simulação da máquina
	 * @author F. Sérgio Barbosa
	 */
	private class HardwareJanela extends JFrame { 

		private static final long serialVersionUID = 1L;
		
		// comprimento e altura da janela
		private static final int COMP = 600; 
		private static final int ALT = 600;

		private Image maquinaImg;
		private Image tampaAberta;
		private Image gavetaFechada, gavetaVazia, gavetaCheia;
		private Image tampas[][];
		private Image coresTampas[] = new Image[ CorLedTampa.values().length ];
		private SimpleDateFormat formatHoras = new SimpleDateFormat ("HH:mm"); 
		private Font horasFont = new Font("Roman", Font.BOLD, 24 );
		private JPanel fundo;

		public HardwareJanela(int nHoriz, int nVert) throws HeadlessException {
			setTitle( "TomaEST: Máquina");
			tampasOpen = new boolean[ nHoriz][nVert ];
			maquinaImg = prepararImagemMaquina( nHoriz, nVert );
			setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

			// vamos tentar uma fonte digital para as horas
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			try {
				ge.registerFont( Font.createFont(Font.TRUETYPE_FONT, new File("data\\DS-DIGIB.TTF") ) ); 
				horasFont = new Font("DS-Digital",Font.BOLD, 30);
			} catch (FontFormatException e1) {
			} catch (IOException e1) {
			}

			setContentPane( setupFundo() );
			pack();
		}

		private JPanel setupFundo() {
			fundo = new JPanel() {
				private static final long serialVersionUID = -4996689351357375264L;

				public void paint(Graphics g) {
					super.paint(g);
					g.drawImage( maquinaImg, 0, 0, null );

					paintTampas( g );
					paintTempo((Graphics2D)g);
					paintGaveta( g );
				}			
			};		
			fundo.setPreferredSize( new Dimension(COMP, ALT) );
			fundo.addMouseListener( new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if( gavetaEstaAberta ) {
						gavetaEstaVazia = true;
						fundo.repaint();
						maquina.gavetaVazia();
					}
				}
			});
			return fundo;
		}

		private Image prepararImagemMaquina( int nHoriz, int nVert ) {
			try {
				Image direita = ImageIO.read( new File("data//direita.png" ) );
				Image lateralDireita = ImageIO.read( new File("data//lateral_direita.png" ) );
				Image fimLateralDireita = ImageIO.read( new File("data//fim_lateral_direita.png" ) );
				Image esquerda = ImageIO.read( new File("data//esquerda.png" ) );
				Image lateralEsquerda = ImageIO.read( new File("data//lateral_esquerda.png" ) );
				Image fimLateralEsquerda = ImageIO.read( new File("data//fim_lateral_esquerda.png" ) );
				Image meio = ImageIO.read( new File("data//meio.png" ) );
				Image traseira = ImageIO.read( new File("data//traseira.png" ) );
				gavetaFechada = ImageIO.read( new File("data//gaveta_fechada.png" ) );
				gavetaVazia = ImageIO.read( new File("data//gaveta_vazia.png" ) );
				gavetaCheia = ImageIO.read( new File("data//gaveta_comprimidos.png" ) );
				//tampaFechada = ImageIO.read( new File("data//tampa.png" ) );

				coresTampas[ CorLedTampa.RED.ordinal() ] = ImageIO.read( new File("data//tampa_red.png" ) );
				coresTampas[ CorLedTampa.YELLOW.ordinal() ] = ImageIO.read( new File("data//tampa_yellow.png" ) );
				coresTampas[ CorLedTampa.GREEN.ordinal() ] = ImageIO.read( new File("data//tampa_green.png" ) );
				coresTampas[ CorLedTampa.OFF.ordinal() ] = ImageIO.read( new File("data//tampa_apagada.png" ) );
				tampaAberta  = ImageIO.read( new File("data//tampa_aberta.png" ) );
				tampas = new Image[nHoriz][nVert];
				for( int i=0; i < nHoriz; i++)
					for( int j=0; j < nVert; j++ )
						tampas[i][j] = coresTampas[ CorLedTampa.OFF.ordinal() ];

				BufferedImage maq = new BufferedImage(COMP, ALT, BufferedImage.TYPE_4BYTE_ABGR );

				Graphics2D g = maq.createGraphics();

				int left = 30;
				int fundo = ALT - 220;

				// desenhar a esquerda
				g.drawImage( esquerda, left, fundo - esquerda.getHeight( null ), null);
				int desloca = esquerda.getWidth(null);

				// desenhar os meios
				int desce = 0;
				for( int i=1; i < nHoriz; i++) {
					desce += 18;
					g.drawImage( meio, left + desloca, fundo + desce - meio.getHeight( null ), null);
					desloca += meio.getWidth(null);
				}

				// desenhar a direita
				desce += 42;
				g.drawImage( direita, left + desloca, fundo + desce - direita.getHeight( null ), null);			

				// desenhar a lateral direita
				desloca += 1; //direita.getWidth( null ) ;
				desce -= -9;
				for( int i=1; i < nVert; i++ ) {
					desloca += 31;
					desce -= 18;
					g.drawImage( lateralDireita, left + desloca, fundo + desce - lateralDireita.getHeight( null ), null);
				}

				// desenhar fim lateral direita
				desce -= 18;
				desloca += lateralDireita.getWidth( null )-59;
				g.drawImage( fimLateralDireita, left + desloca, fundo + desce - fimLateralDireita.getHeight( null ), null);

				// desenhar a lateral esquerda
				int topo = fundo - esquerda.getHeight(null); 
				desloca = esquerda.getWidth( null ) - 9;

				int sobe = 5;
				for( int i=0; i < nVert; i++ ) {
					g.drawImage( lateralEsquerda, left + desloca, topo + sobe - lateralEsquerda.getHeight( null ), null);
					desloca += lateralEsquerda.getWidth( null )-7;
					sobe -= lateralEsquerda.getHeight( null )-5;
				}

				g.drawImage( fimLateralEsquerda, left + desloca, topo + sobe - fimLateralEsquerda.getHeight( null ), null);

				// desenhar a traseira
				desloca += fimLateralEsquerda.getWidth(null) - 4;
				sobe += fimLateralEsquerda.getHeight( null ) + 12;

				for( int i=0; i < nHoriz; i++ ) {
					g.drawImage( traseira, left + desloca, topo + sobe - traseira.getHeight( null ), null);
					desloca += traseira.getWidth(null)-3;
					sobe += traseira.getHeight( null )-2;
				}

				return maq;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Falha na leitura dos ficheiros de imagens" , "Erro", JOptionPane.ERROR_MESSAGE );
				System.exit( 0 );
			}

			return null;
		}


		private void paintTampas( Graphics g ) {
			int left = 30;
			int fundo = ALT - 220;

			// desenhar tampas
			int desloca = 0;
			int sobe = -210;//-256/2;

			desloca += 53;
			//sobe += 23;

			int meiaAltura = tampaAberta.getHeight(null) / 4;
			int meioComp = tampaAberta.getWidth(null) / 2;

			for( int j=nVert-1; j >= 0; j-- ) {
				int d = desloca + (1 + meioComp) * j;
				int s = sobe - (-7+meiaAltura) * j;
				for( int i=0; i< nHoriz; i++) {
					if( tampasOpen[i][j] )
						g.drawImage(tampaAberta, left + d, fundo + s - tampaAberta.getHeight(null), null );
					else {
						g.drawImage(tampas[i][j], left + d, fundo + s - tampas[i][j].getHeight(null), null );
					}
					//s += meiaAltura + 1;
					s += meiaAltura -7;
					d += meioComp + 1;
				}
			}
		}

		private void paintGaveta(Graphics g) {
			if( gavetaEstaAberta && !gavetaEstaVazia )
				g.drawImage( gavetaCheia, 25, 325, null );
			else if( gavetaEstaAberta && gavetaEstaVazia )
				g.drawImage( gavetaVazia, 25, 325, null );
			else
				g.drawImage( gavetaFechada, 25, 325, null );
		}

		private void paintTempo(Graphics2D g) {
			AffineTransform old = g.getTransform();

			int x = 120, y = 200;
			Date agora = new Date( );
			g.setFont( horasFont );
			g.scale(0.866025, 1);
			g.translate(x,y);
			//g.shear(-0.866025, 0.5);
			g.shear(0, 0.5);
			g.translate(-x,-y);
			g.drawString( formatHoras.format( agora ), x, y);

			g.setTransform( old );
		}
	}
}

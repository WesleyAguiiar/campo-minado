package br.com.aguiar.cm.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import br.com.aguiar.cm.modelo.Campo;
import br.com.aguiar.cm.modelo.CampoEvento;
import br.com.aguiar.cm.modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador, MouseListener {
	
	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color TEXTO_VERDE = new Color(0, 100, 0);
	
	ImageIcon bandeira = new ImageIcon("E:\\Projetos Java\\campo-minado-swing\\bandeira.png");
	ImageIcon bomba = new ImageIcon("E:\\Projetos Java\\campo-minado-swing\\bomba.png");
	JLabel label = new JLabel(bandeira);
	
	private Campo campo;
	
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBorder(BorderFactory.createBevelBorder(0));
		setBackground(BG_PADRAO);
		
		addMouseListener(this);
		campo.registrarObservador(this);
	}
	
	public void eventoOcorreu(Campo c, CampoEvento e) {
		switch(e) {
			case ABRIR:
				aplicarEstiloAbrir();
				break;
			case MARCAR:
				aplicarEstiloMarcar();
				break;
			case DESMARCAR:
				aplicarEstiloDesmarcar();
				break;
			case EXPLODIR:
				aplicarEstiloExplodir();
				break;
			default:
				aplicarEstiloPadrao();
		}
		
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
		});
	}


	private void aplicarEstiloPadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
		setIcon(null);
	}

	private void aplicarEstiloExplodir() {
		setForeground(Color.WHITE);
		setIcon(bomba);
	}

	private void aplicarEstiloDesmarcar() {
		setIcon(null);
	}
	
	private void aplicarEstiloMarcar() {
		setForeground(Color.BLACK);
		setIcon(bandeira);
	}

	private void aplicarEstiloAbrir() {
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if(campo.isMinado()) {
			setIcon(bomba);
			return;
		}
		
		setBackground(BG_PADRAO);
		
		switch (campo.minasNaVizinhanca()){
			case 1:
				setForeground(TEXTO_VERDE);
				break;
			case 2:
				setForeground(Color.BLUE);
				break;
			case 3:
				setForeground(Color.YELLOW);
				break;
			case 4:
			case 5:
			case 6:
				setForeground(Color.RED);
				break;
			default:
				setForeground(Color.PINK);
		}
		
		String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + "" : "";
		setText(valor);
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			campo.abrir();
		} else {
			campo.alternarMarcacao();
		}
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}

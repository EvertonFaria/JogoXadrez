package xadrez;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		configuracaoInicial();
	}
	
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	private void configuracaoInicial() {
		tabuleiro.colocaPeca(new Torre(tabuleiro, Cor.PRETA), new Posicao(0,0));
		tabuleiro.colocaPeca(new Rei(tabuleiro, Cor.PRETA), new Posicao(0,3));
		tabuleiro.colocaPeca(new Torre(tabuleiro, Cor.PRETA), new Posicao(0,7));
		
		tabuleiro.colocaPeca(new Torre(tabuleiro, Cor.BRANCA), new Posicao(7,0));
		tabuleiro.colocaPeca(new Rei(tabuleiro, Cor.BRANCA), new Posicao(7,3));
		tabuleiro.colocaPeca(new Torre(tabuleiro, Cor.BRANCA), new Posicao(7,7));
	}
}

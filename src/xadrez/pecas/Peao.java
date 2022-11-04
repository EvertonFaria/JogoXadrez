package xadrez.pecas;

import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "P";
	}	
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mtz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		if (getCor() == Cor.BRANCA) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
				mtz[p.getLinha()][p.getColuna()] = true;
			}
			
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());			
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temPeca(p2) && getContagemMovimentos() == 0) {
				mtz[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && temPecaAdversaria(p)) {
				mtz[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && temPecaAdversaria(p)) {
				mtz[p.getLinha()][p.getColuna()] = true;
			}			
		} else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
				mtz[p.getLinha()][p.getColuna()] = true;
			}
			
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());			
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temPeca(p2) && getContagemMovimentos() == 0) {
				mtz[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && temPecaAdversaria(p)) {
				mtz[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && temPecaAdversaria(p)) {
				mtz[p.getLinha()][p.getColuna()] = true;
			}
		}
		
		return mtz;
	}

}

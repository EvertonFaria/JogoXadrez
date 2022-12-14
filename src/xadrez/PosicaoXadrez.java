package xadrez;

import jogotabuleiro.Posicao;

public class PosicaoXadrez {

	private char coluna;
	private int linha;
	public PosicaoXadrez(char coluna, int linha) {
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new ExcessoesXadrez("Erro ao instanciar as posi??es do tabuleiro! Os valores v?lidos v?o de a1 at? h8.");
		}
		
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}
	
	protected Posicao posicionar() {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	protected static PosicaoXadrez origem (Posicao posicao) {
		return new PosicaoXadrez((char)('a' - posicao.getColuna()), 8 - posicao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}

package jogotabuleiro;

public abstract class Peca {

	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}
	
	public abstract boolean[][] movimentosPossiveis();
	
	public boolean movimentoPossivel(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public boolean existeMovimentoPossivel() {
		boolean[][] mtz = movimentosPossiveis();
		
		for (int i=0; i<mtz.length; i++) {
			for (int j=0; j<mtz.length; j++) {
				if (mtz[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}

package jogotabuleiro;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new ExcessoesTabuleiro("Erro ao criar o tabuleiro: deve haver ao menos uma linha e uma coluna!");
		}
		
		this.linhas = linhas;
		this.colunas = colunas;
		
		pecas = new Peca[linhas][colunas];
		
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peca peca(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna)) {
			throw new ExcessoesTabuleiro("Posição informada não existe!");
		}
		return pecas[linha][coluna];
	}
	
	public Peca peca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new ExcessoesTabuleiro("Posição informada não existe!");
		}
		
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void posicionarPeca(Peca peca, Posicao posicao) {
		if (temPeca(posicao)) {
			throw new ExcessoesTabuleiro("Já existe uma peça na posição informada! Posição: " + posicao);
		}
		
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean temPeca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new ExcessoesTabuleiro("Posição informada não existe!");
		}
		
		return peca(posicao) != null;
	}
}

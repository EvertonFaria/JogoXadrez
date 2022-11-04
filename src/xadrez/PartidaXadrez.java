package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogotabuleiro.Peca;
import jogotabuleiro.Posicao;
import jogotabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cor jogadorAtual;	
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCA;
		check = false;
		checkMate = false;
		configuracaoInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.posicionar();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaXadrez realizaMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.posicionar();
		Posicao destino = posicaoDestino.posicionar();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		
		if (testarCheck(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new ExcessoesXadrez("Você não pode se por em check!");
		}
		
		check = (testarCheck(oponente(jogadorAtual))) ? true : false;
		
		if (testarCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {		
			proximoTurno();
		}
		
		return (PecaXadrez)pecaCapturada;
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(origem);
		p.incrementarContagemMovimentos();		
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.posicionarPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removerPeca(destino);
		p.decrementarContagemMovimentos();
		tabuleiro.posicionarPeca(p, origem);
		
		if (pecaCapturada != null) {
			tabuleiro.posicionarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temPeca(posicao)) {
			throw new ExcessoesXadrez("Não há peça na posição de origem informada!");
		}
		
		if (jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new ExcessoesXadrez("A peça escolhida não é a sua!");
		}
		
		if (!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new ExcessoesXadrez("Não existem posições possiveis para mover a peça escolhida!");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new ExcessoesXadrez("Não existem posições possiveis para mover a peça escolhida!");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private PecaXadrez rei(Cor cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		throw new IllegalStateException("Não há rei " + cor + "no tabuleiro");
	}
	
	private boolean testarCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().posicionar();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasOponentes) {
			boolean[][] mtz = p.movimentosPossiveis();
			
			if (mtz[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testarCheckMate(Cor cor) {
		if (!testarCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mtz = p.movimentosPossiveis();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (mtz[i][j]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().posicionar();
						Posicao destino = new Posicao(i, j);
						
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testarCheck = testarCheck(cor);
						
						desfazerMovimento(origem, destino, pecaCapturada);
						
						if (!testarCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void posicionarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.posicionarPeca(peca, new PosicaoXadrez(coluna, linha).posicionar());
		pecasNoTabuleiro.add(peca);
	}
	
	private void configuracaoInicial() {
		posicionarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCA));
		
		posicionarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCA));
		posicionarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCA));
		
		posicionarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETA));
		
		posicionarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETA));
		posicionarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETA));		
	}
}

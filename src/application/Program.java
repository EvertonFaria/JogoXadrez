package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ExcessoesXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while (true) {
			try {
				UI.limparTela();
				UI.imprimePartida(partidaXadrez);
				
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.LerPosicaoXadrex(sc);
				
				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
				UI.limparTela();
				UI.imprimeTabuleiro(partidaXadrez.getPecas(), movimentosPossiveis);
								
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez detino = UI.LerPosicaoXadrex(sc);
				
				PecaXadrez pecaCapturada = partidaXadrez.realizaMovimentoXadrez(origem, detino);
			} catch (ExcessoesXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

	}

}

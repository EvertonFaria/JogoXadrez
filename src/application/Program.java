package application;

import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while (true) {
			UI.imprimeTabuleiro(partidaXadrez.getPecas());
			
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = UI.LerPosicaoXadrex(sc);
			
			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez detino = UI.LerPosicaoXadrex(sc);
			
			PecaXadrez pecaCapturada = partidaXadrez.realizaMovimentoXadrez(origem, detino);
		}

	}

}

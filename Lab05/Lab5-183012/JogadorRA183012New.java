
import java.util.ArrayList;
import java.util.Random;

public class JogadorRA183012New extends Jogador{

	private ArrayList<CartaLacaio> lacaios;
	private ArrayList<CartaLacaio> lacaiosOponente;
		
		/**
		  * O método construtor do JogadorAleatorio.
		  * 
		  * @param maoInicial Contém a mão inicial do jogador. Deve conter o número de cartas correto dependendo se esta classe Jogador que está sendo construída é o primeiro ou o segundo jogador da partida. 
		  * @param primeiro   Informa se esta classe Jogador que está sendo construída é o primeiro jogador a iniciar nesta jogada (true) ou se é o segundo jogador (false).
		  */
		public JogadorRA183012New(ArrayList<Carta> maoInicial, boolean primeiro){
			primeiroJogador = primeiro;
			
			mao = maoInicial;
			lacaios = new ArrayList<CartaLacaio>();
			lacaiosOponente = new ArrayList<CartaLacaio>();
			
			// Mensagens de depuração:
			System.out.println("*Classe JogadorRA183012* Sou o " + (primeiro?"primeiro":"segundo") + " jogador (classe: JogadorAleatorio)");
			System.out.println("Mao inicial:");
			for(int i = 0; i < mao.size(); i++)
				System.out.println("ID " + mao.get(i).getID() + ": " + mao.get(i));
		}
		
		/**
		  * Um método que processa o turno de cada jogador. Este método deve retornar as jogadas do Jogador decididas para o turno atual (ArrayList de Jogada).
		  * 
		  * @param mesa   O "estado do jogo" imediatamente antes do início do turno corrente. Este objeto de mesa contém todas as informações 'públicas' do jogo (lacaios vivos e suas vidas, vida dos heróis, etc).
		  * @param cartaComprada   A carta que o Jogador recebeu neste turno (comprada do Baralho). Obs: pode ser null se o Baralho estiver vazio ou o Jogador possuir mais de 10 cartas na mão.
		  * @param jogadasOponente   Um ArrayList de Jogada que foram os movimentos utilizados pelo oponente no último turno, em ordem.
		  * @return            um ArrayList com as Jogadas decididas
		  */
		public ArrayList<Jogada> processarTurno (Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente){
			//System.out.println("Declarando minhaMana e minhaVida");
			int minhaMana, minhaVida;
			if(cartaComprada != null){
				//System.out.println("CARTA COMPRADA NAO E NULA");
				mao.add(cartaComprada);
			}
			if(primeiroJogador){
				//System.out.println("PRIMEIRO JOGADOR CUZAO");
				minhaMana = mesa.getManaJog1();
				minhaVida = mesa.getVidaHeroi1();
				lacaios = mesa.getLacaiosJog1();
				lacaiosOponente = mesa.getLacaiosJog2();
				//System.out.println("--------------------------------- Começo de turno pro jogador1");
			}
			else{
				//System.out.println("SEGUNDO JOGADOR CUZAO");
				minhaMana = mesa.getManaJog2();
				minhaVida = mesa.getVidaHeroi2();
				lacaios = mesa.getLacaiosJog2();
				lacaiosOponente = mesa.getLacaiosJog1();
				//System.out.println("--------------------------------- Começo de turno pro jogador2");
			}
			//System.out.println("MINHASJOGADAS AQUI CUZAO:");
			ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();

			for(int i = 0; i < mao.size(); i++){
				Carta card = mao.get(i);
				Jogada mag;
				if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
					CartaMagia cardMagia = (CartaMagia)card;
					
					if (cardMagia.getMagiaTipo() == TipoMagia.BUFF) {
						if (lacaios.size() != 0) {
							mag = new Jogada(TipoJogada.MAGIA, card, lacaios.get(piorLacaio()));
						} else {
							continue;
						} 
					} else if (cardMagia.getMagiaTipo() == TipoMagia.AREA && lacaiosOponente.size() > 1){
						mag = new Jogada(TipoJogada.MAGIA, card, null);
					} else if (cardMagia.getMagiaTipo() == TipoMagia.ALVO && lacaiosOponente.size() > 3){
						mag = new Jogada(TipoJogada.MAGIA, card, null);
					} else {
						continue;
					}
					minhasJogadas.add(mag);
					minhaMana -= card.getMana();
					System.out.println("Jogada: Decidi uma jogada de baixar uma magia: "+ card);
					mao.remove(i);
					i--;
					//break;
				}
			}
			
			for(int i = 0; i < mao.size(); i++){
				Carta card = mao.get(i);
				if(card instanceof CartaLacaio && card.getMana() <= minhaMana){
					Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
					minhasJogadas.add(lac);
					minhaMana -= card.getMana();
					System.out.println("Jogada: Decidi uma jogada de baixar o lacaio: "+ card);
					mao.remove(i);
					i--;
					//break;
				}
			}
			
			for (int i = 0; i < lacaios.size(); i++) {
				CartaLacaio lacaio = lacaios.get(i);
				//if(lacaiosOponente.size() == 0) { 
					Jogada ata = new Jogada(TipoJogada.ATAQUE, lacaio, null);
					minhasJogadas.add(ata);
//				} else{
//					int piorOponente = piorLacaioOponente();
//					CartaLacaio oponente = lacaiosOponente.get(piorOponente);
//					if(oponente.getVidaAtual() > 0){
//						Jogada ata = new Jogada(TipoJogada.ATAQUE, lacaio, oponente);
//						minhasJogadas.add(ata);
//					}
//				}
				System.out.println("Jogada: Estou atacando com um lacaio: " + lacaio);
			}
			
			if(minhaMana >=2){
				Jogada jogada = new Jogada(TipoJogada.PODER,null,null);
				minhasJogadas.add(jogada);
			    minhaMana -= 2;
			}
			
			return minhasJogadas;
		}
		
		private int piorLacaio () {
			int primeiro = 0;
			
			for(int j = 0; j < lacaios.size(); j++) {
				if(lacaios.get(j).getVidaAtual() < lacaios.get(primeiro).getVidaAtual()) {
					primeiro = j;
				}	
			}
			return primeiro;
		}
		
		private int piorLacaioOponente () {
			int primeiro = 0;
			for(int i = 0; i < lacaiosOponente.size(); i++) {
				if(lacaiosOponente.get(i).getVidaAtual() < lacaiosOponente.get(primeiro).getVidaAtual()) {
					primeiro = i;
				}	
			}
			return primeiro;
		}
		
		private int lacaioMaisCaro (int minhaMana) {
			int base = -1;
			for (int i = 0; i < mao.size(); i++) {
				Carta card = mao.get(i);
				if(card instanceof CartaLacaio && base == -1 && card.getMana() <= minhaMana)
					base = i;
				if(card instanceof CartaLacaio && card.getMana() <= minhaMana){
					CartaLacaio cardLacaio = (CartaLacaio)card;
					if(cardLacaio.getMana() < mao.get(base).getMana()){
						base = i;
					}
				}
			}			
			return base;
		}
	}


import base.*;
import base.Carta;
import base.Lacaio;
import base.TipoCarta;
import util.Util;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by LuizCartolan on 16/05/17.
 */
public class Main {
    public static void main(String[]args) {
        Random gerador = new Random();
        // objetos do tipo baralho
        Baralho baralho1 = new Baralho();
        Baralho baralho2 = new Baralho();
        ArrayList<Lacaio>lacaios = new ArrayList<>();
        // objeto do tipo mesa
        Mesa mesa = new Mesa();

        baralho1.preencheAleatorio(gerador,30,10,10,10);
        baralho2.preencheAleatorio(gerador,30,12,12,12);

        for (int i = 0; i < 7; i++) {
            lacaios.add((Lacaio) Util.geraCartaAleatoria(gerador,10,10,10,TipoCarta.LACAIO));
        }
        mesa.setLacaioS(lacaios);
        mesa.setLacaioP(lacaios);

        ArrayList<Carta> mao = new ArrayList<>();
        ArrayList<Carta> mao2 = new ArrayList<>();
        for (int i = 0; i < Util.MAO_INI;i++) {
            mao.add(baralho1.comprarCarta());
        }
        for (int i = 0; i < Util.MAO_INI + 1 ;i++) {
            mao.add(baralho1.comprarCarta());
        }
        mesa.setMaoP(mao);
        mesa.setMaoS(mao2);

        Carta carta = mesa.sacarCarta('s');
        if(carta != null) {
            Jogada jogador1 = new Jogada(carta, null, 's');
            ProcessadorJogada.processar(jogador1, mesa);
        }

        Carta carta2 = mesa.sacarCarta('p');
        if (carta2 != null) {
            Jogada jogador2 = new Jogada(carta2, null, 'p');
            ProcessadorJogada.processar(jogador2, mesa);
        }

        if (mesa.getLacaioP().size() > 0) {
            Carta carta11 = mesa.sacarCarta('s');
            Carta cartaAdversario = mesa.sacarCarta('p');
            if((carta11 != null) && (cartaAdversario != null)) {
                Jogada jogador11 = new Jogada(carta11, cartaAdversario, 's');
                ProcessadorJogada.processar(jogador11, mesa);
            }
        }
        if (mesa.getLacaioS().size() > 0) {
            Carta carta22 = mesa.sacarCarta('s');
            Carta cartaAdversario2 = mesa.sacarCarta('p');
            if((carta22 != null) && (cartaAdversario2 != null)) {
                Jogada jogador22 = new Jogada(carta22, cartaAdversario2, 'p');
                ProcessadorJogada.processar(jogador22, mesa);
            }
        }

    }
}

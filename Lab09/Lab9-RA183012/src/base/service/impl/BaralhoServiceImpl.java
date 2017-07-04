package base.service.impl;

import base.cartas.Carta;
import base.exception.BaralhoVazioException;
import base.service.BaralhoSer;
import com.sun.tools.javac.util.*;
import util.Util;

import java.util.*;
import java.util.List;

/**
 * Created by duducartolano on 28/05/17.
 */
public class BaralhoServiceImpl implements BaralhoSer {

    private List<Carta> cartas;

    public BaralhoServiceImpl() {
        cartas = new ArrayList<Carta>();
    }


    public List<Carta> preencheAleatorio(Random gerador, int tamanho, int maxMana, int maxAtaque, int maxVida) {
        tamanho = (tamanho <= Util.MAX_CARDS) ? tamanho : Util.MAX_CARDS;

        CartaServiceImpl cartaService = new CartaServiceImpl();


        try {
            for (int i = 0; i < tamanho; ++i) {
                cartas.add(cartaService.geraCartaAleatoria(gerador, maxMana, maxAtaque, maxVida, null));
            }
        } catch (BaralhoVazioException e) {
            e.printStackTrace();
            System.out.print("Partida encerrada");
            System.exit(0);
        }


        return cartas;

    }
}

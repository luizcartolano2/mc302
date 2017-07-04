package base.service;

import base.cartas.Carta;

import java.util.List;
import java.util.Random;

/**
 * Created by duducartolano on 28/05/17.
 */
public interface BaralhoSer {

    public List<Carta> preencheAleatorio(Random gerador, int tamanho, int maxMana, int maxAtaque, int maxVida);

}

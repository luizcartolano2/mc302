package base.service;

import base.Baralho;
import base.Mesa;
import base.cartas.TipoCarta;
import base.exception.MesaNulaException;
import base.exception.ValorInvalidoException;

import java.util.Random;

/**
 * Created by duducartolano on 28/05/17.
 */
public interface MesaService {

    public Mesa adicionaLacaios(Mesa mesa, Random random, TipoCarta tipoCarta) throws MesaNulaException;
    public Mesa addMaoInicial(Mesa mesa, Baralho baralho1, Baralho baralho2, int inteiro) throws ValorInvalidoException;

}

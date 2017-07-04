package base.service.impl;

import base.Baralho;
import base.Mesa;
import base.cartas.Carta;
import base.cartas.Lacaio;
import base.cartas.TipoCarta;
import base.exception.MesaNulaException;
import base.exception.ValorInvalidoException;
import base.service.MesaService;
import com.sun.xml.internal.xsom.impl.scd.Iterators;
import util.Util;

import java.util.ArrayList;
import java.util.Random;

import static util.Util.MAO_INI;

/**
 * Created by duducartolano on 28/05/17.
 */
public class MesaServiceImpl implements MesaService {

    @Override
    public Mesa addMaoInicial(Mesa mesa, Baralho baralho1, Baralho baralho2, int inteiro) throws ValorInvalidoException {

        for (int i = 0; i < MAO_INI; i++) {
            mesa.getMaoP().add(baralho1.comprar());
            mesa.getMaoS().add(baralho2.comprar());
        }

        if ((mesa.getMaoS().size() < 3) || (mesa.getMaoP().size() < 3)) {
            throw new ValorInvalidoException("Valor inicial da maoP: " + mesa.getMaoP().size() +
                    "Valor inicial maoS: " + mesa.getMaoS().size());
        }

        return mesa;

    }

    @Override
    public Mesa adicionaLacaios(Mesa mesa, Random random, TipoCarta tipoCarta) throws MesaNulaException {
        CartaServiceImpl cartaService = new CartaServiceImpl();

        ArrayList<Carta> lacaiosP = mesa.getLacaiosP();
        ArrayList<Carta> lacaiosS = mesa.getLacaiosS();

        for (int i = 0; i < Util.MAX_LACAIOS; i++) {
            lacaiosP.add(cartaService.geraCartaAleatoria(random, Util.MAX_MANA,Util.MAX_ATAQUE, Util.MAX_VIDA, TipoCarta.LACAIO));
            lacaiosS.add(cartaService.geraCartaAleatoria(random, Util.MAX_MANA, Util.MAX_ATAQUE, Util.MAX_VIDA, TipoCarta.LACAIO));
        }

        mesa.setLacaiosP(lacaiosP);
        mesa.setLacaiosS(lacaiosS);
        if ((mesa.getLacaiosP() == null) || (mesa.getMaoS() == null))
            System.out.println("FODEUUUUUUUUUUUUUUUUUUUU");

        if (mesa == null) {
            throw new MesaNulaException("A mesa está vazia");
        }

        return mesa;
    }
}

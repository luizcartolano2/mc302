package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import base.cartas.Carta;
import base.service.CartaService;
import base.service.impl.CartaServiceImpl;
import util.Util;

public class Baralho {

    private List<Carta> cartas;
    private CartaService cartaService;
    
    public Baralho() {
		cartas = new ArrayList<Carta>();
		cartaService = new CartaServiceImpl();
	}
    
    public Baralho(List<Carta> cartas) {
    	cartaService = new CartaServiceImpl();
        this.cartas = cartas;
    }

    public Baralho adicionar(Carta card) {
        if (cartas.stream().filter(c -> c.equals(card)).count() < 2
            && cartas.size() < Util.MAX_CARDS) {
            cartas.add(card);
        }

        return this;
    }

    public void addCartas(List<Carta> cartas) {
        this.cartas = cartas;
    }

    public Carta comprar() {
        return cartas.remove(cartas.size() - 1);
    }

    public void embaralhar() {
        Collections.shuffle(cartas);
        System.out.println(cartas);
        Collections.reverse(cartas);
    }


    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof Baralho && cartas.equals(((Baralho) o).cartas);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 12 + Objects.hashCode(cartas);
        return hash;
    }

    public int size() {
        return cartas.size();
    }
}

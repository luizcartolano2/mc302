package base;

import util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Baralho {
    private ArrayList <base.Carta> vetorDeCartas;

    public Baralho() {
        vetorDeCartas = new ArrayList<base.Carta>();
    }

    public void adicionarCarta(base.Carta card) {
        if(vetorDeCartas.size() < 30) {
            vetorDeCartas.add(card);
        }
    }

    public ArrayList<base.Carta> getVetorDeCartas() {
        return vetorDeCartas;
    }

    public ArrayList<base.Carta> getBaralho() {
        return vetorDeCartas;
    }

    public base.Carta comprarCarta() {
        int i = vetorDeCartas.size();
        base.Carta nova = vetorDeCartas.get(i-1);
        vetorDeCartas.remove(i-1);
        return nova;
    }

    public void embaralharCartas() {
        Collections.shuffle(vetorDeCartas);
        Collections.reverse(vetorDeCartas);
        System.out.println(vetorDeCartas);
        Collections.reverse(vetorDeCartas);
    }


    public String toString() {
        for(int i = 0; i < vetorDeCartas.size(); i++)
            System.out.println(vetorDeCartas.get(i));
        return null;
    }

    public void preencheAleatorio (Random gerador, int tamanho, int maxMana, int maxAtaque, int maxVida) {
        for (int i = 0; i <tamanho; i++) {
            base.Carta carta = Util.geraCartaAleatoria(gerador, maxMana, maxAtaque, maxVida, null);
            vetorDeCartas.add(carta);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Baralho baralho = (Baralho)o;

        return vetorDeCartas != null ? vetorDeCartas.equals(baralho.vetorDeCartas) : baralho.vetorDeCartas == null;
    }

    @Override
    public int hashCode() {
        return vetorDeCartas != null ? vetorDeCartas.hashCode() : 0;
    }
}


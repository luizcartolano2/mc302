import base.Carta;

import java.util.ArrayList;
import java.util.Collections;


public class Baralho {
    private ArrayList <Carta> vetorDeCartas;

    public Baralho() {
        vetorDeCartas = new ArrayList<Carta>();
    }

    public void adicionarCarta(Carta card) {
        if(vetorDeCartas.size() < 30) {
            vetorDeCartas.add(card);
        }
    }

    public ArrayList<Carta> getBaralho() {
        return vetorDeCartas;
    }

    public Carta comprarCarta() {
        int i = vetorDeCartas.size();
        Carta nova = vetorDeCartas.get(i-1);
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


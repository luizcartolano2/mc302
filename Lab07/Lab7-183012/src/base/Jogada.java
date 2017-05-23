package base;

import base.Carta;

/**
 * Created by LuizCartolan on 08/05/17.
 */
public class Jogada {
    private Carta cartaJogada;
    private Carta cartaAlvo;
    private char autor;

    public Jogada(Carta cartaJogada, Carta cartaAlvo, char autor) {
        this.cartaJogada = cartaJogada;
        this.cartaAlvo = cartaAlvo;
        this.autor = autor;
    }

    public Carta getCartaJogada() {
        return cartaJogada;
    }

    public void setCartaJogada(Carta cartaJogada) {
        this.cartaJogada = cartaJogada;
    }

    public Carta getCartaAlvo() {
        return cartaAlvo;
    }

    public void setCartaAlvo(Carta cartaAlvo) {
        this.cartaAlvo = cartaAlvo;
    }

    public char getAutor() {
        return autor;
    }

    public void setAutor(char autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Jogada{" +
                "cartaJogada=" + cartaJogada +
                ", cartaAlvo=" + cartaAlvo +
                ", autor=" + autor +
                '}';
    }
}

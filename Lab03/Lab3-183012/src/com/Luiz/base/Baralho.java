package com.Luiz.base;

import java.util.Random;

public class Baralho {
	CartaLacaio [] vetorDeCartas;
	int numCartas;
	private static Random gerador = new Random();
	
	public Baralho() {
		vetorDeCartas = new CartaLacaio[10];
		numCartas = 0;
	}
	
	public void adicionarCarta(CartaLacaio card) {
		vetorDeCartas[numCartas] = card;
		numCartas++;
	}
	
	public CartaLacaio comprarCarta() {
		numCartas--;
		return vetorDeCartas[numCartas];
	}
	
	public void embaralharCartas() {
		int j;
		for(int i = 0; i < numCartas; i++) {
			j = gerador.nextInt(i+1);
			if (j != i) {
				CartaLacaio a = vetorDeCartas[i];
				CartaLacaio b = vetorDeCartas[j];
				vetorDeCartas[i] = b;
				vetorDeCartas[j] = a;
			}
		}
		for (int i = vetorDeCartas.length - 1; i >=0; i--) {
			System.out.println(vetorDeCartas[i]);
		}
		
	}
}

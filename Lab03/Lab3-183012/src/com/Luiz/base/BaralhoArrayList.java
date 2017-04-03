package com.Luiz.base;

import com.Luiz.util.Util;
import java.util.ArrayList;
import java.util.Collections;

public class BaralhoArrayList {
	private ArrayList <CartaLacaio > vetorDeCartas;
	
	public BaralhoArrayList() {
		vetorDeCartas = new ArrayList<CartaLacaio>();
	}
	
	public void adicionarCarta(CartaLacaio card) {
		if(vetorDeCartas.size() < Util.MAX_CARDS) {
			vetorDeCartas.add(card);
		}
	}
	
	public CartaLacaio comprarCarta() {
		int i = vetorDeCartas.size();
		CartaLacaio nova = vetorDeCartas.get(i-1);
		vetorDeCartas.remove(i-1);
		return nova;
	}
	
	public void embaralharCartas() {
		Collections.shuffle(vetorDeCartas);
		Collections.reverse(vetorDeCartas);
		System.out.println(vetorDeCartas);
		Collections.reverse(vetorDeCartas);
	}
	
}	

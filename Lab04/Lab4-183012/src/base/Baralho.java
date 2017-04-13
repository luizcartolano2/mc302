package base;

import java.util.ArrayList;
import java.util.Collections;
import util.Util;


public class Baralho {
	private ArrayList <Carta> vetorDeCartas;
	
	public Baralho() {
		vetorDeCartas = new ArrayList<Carta>();
	}
	
	public void adicionarCarta(Carta card) {
		if(vetorDeCartas.size() < Util.MAX_CARDS) {
			vetorDeCartas.add(card);
		}
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
	
}	


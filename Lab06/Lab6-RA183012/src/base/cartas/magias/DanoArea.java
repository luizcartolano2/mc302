package base.cartas.magias;

import java.util.ArrayList;
import java.util.UUID;

import base.Carta;
import base.Lacaio;

public class DanoArea extends Dano{

	public DanoArea(UUID iD, String nome, int custoMana, int dano) {
		super(iD, nome, custoMana, dano);
	}

	public DanoArea(String nome, int custoMana, int dano) {
		super(nome, custoMana, dano);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	public void usar(ArrayList<Carta> alvos) {
		for(int i = 0; i < alvos.size(); i++) {
			Lacaio novaLacaio = (Lacaio)alvos.get(i);
			novaLacaio.setVidaAtual(novaLacaio.getVidaAtual() - getDano());
		}
	}
}

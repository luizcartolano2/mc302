package base.cartas.magias;

import java.util.UUID;

import base.Carta;
import base.cartas.Lacaio;

public class Dano extends Magia{
	private int dano;

	public Dano(UUID iD, String nome, int custoMana, int dano) {
		super(iD, nome, custoMana);
		this.dano = dano;
	}

	public Dano(String nome, int custoMana, int dano) {
		super(nome, custoMana);
		this.dano = dano;
	}

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}
	
	@Override
	public String toString() {
		String out = super.toString();
		out = out + "dano = " + getDano() + "\n";
		return out;
	}
	
	@Override
	public void usar(Carta alvo) {
		Lacaio lacaioAlvo = (Lacaio)alvo;
		lacaioAlvo.setVidaAtual(lacaioAlvo.getVidaAtual() - this.dano);
	}
}

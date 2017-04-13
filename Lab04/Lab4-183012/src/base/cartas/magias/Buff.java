package base.cartas.magias;
import java.util.UUID;

import base.Carta;
import base.cartas.Lacaio;

public class Buff extends Magia {
	private int aumentoEmAtaque;
	private int aumentoEmVida;

	public Buff(UUID iD, String nome, int custoMana, int aumentoEmAtaque, int aumentoEmVida) {
		super(iD, nome, custoMana);
		this.aumentoEmAtaque = aumentoEmAtaque;
		this.aumentoEmVida = aumentoEmVida;
	}
	
	public Buff(String nome, int custoMana, int aumentoEmAtaque, int aumentoEmVida) {
		super(nome, custoMana);
		this.aumentoEmAtaque = aumentoEmAtaque;
		this.aumentoEmVida = aumentoEmVida;
	}
	
	public int getAumentoEmAtaque() {
		return aumentoEmAtaque;
	}

	public void setAumentoEmAtaque(int aumentoEmAtaque) {
		this.aumentoEmAtaque = aumentoEmAtaque;
	}

	public int getAumentoEmVida() {
		return aumentoEmVida;
	}

	public void setAumentoEmVida(int aumentoEmVida) {
		this.aumentoEmVida = aumentoEmVida;
	}

	@Override
	public void usar(Carta alvo) {
		Lacaio lacaioAlvo = (Lacaio)alvo;
		lacaioAlvo.setAtaque(lacaioAlvo.getAtaque()+this.aumentoEmAtaque);
		lacaioAlvo.setVidaAtual(lacaioAlvo.getVidaAtual()+this.aumentoEmVida);
		lacaioAlvo.setVidaMaxima(lacaioAlvo.getVidaMaxima()+this.aumentoEmVida);
	}	
}

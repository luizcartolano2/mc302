package base;

import java.util.UUID;

import base.Carta;
import base.cartas.HabilidadesLacaio;
import util.RandomString;

public class Lacaio extends Carta{
	private int ataque;
	private int vidaAtual;
	private int vidaMaxima;
	private HabilidadesLacaio habilidadesLacaio;

	// metodo construtor reduzido, instancia os atributos ID, nome e custo mana

	public Lacaio(String nome,int custoMana, int ataque, int vida, HabilidadesLacaio habilidadesLacaio) {
		super(nome,custoMana);
		this.ataque = ataque;
		this.vidaAtual = vida;
		this.vidaMaxima = vida;
		this.habilidadesLacaio = habilidadesLacaio;
	}

	public Lacaio(String nome, int mana, int ataque, int vidaAtual, int vidaMaxima) {
		super(nome, mana);
		this.ataque = ataque;
		this.vidaAtual = vidaAtual;
		this.vidaMaxima = vidaMaxima;
	}		
	
	// metodo construtor copia, ira copiar os atributos de um objeto passado por parametro
	public Lacaio(Lacaio origem ) {
		super(origem.getID(),origem.getNome(),origem.getCustoMana());
		this.ataque = origem.getAtaque();
		this.vidaAtual = origem.getVidaAtual();
		this.vidaMaxima = origem.getVidaAtual();
	}


	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getVidaAtual() {
		return vidaAtual;
	}

	public void setVidaAtual(int vidaAtual) {
		this.vidaAtual = vidaAtual;
	}

	public int getVidaMaxima() {
		return vidaMaxima;
	}

	public void setVidaMaxima(int vidaMaxima) {
		this.vidaMaxima = vidaMaxima;
	}

	public void setHabilidadeLacaio(HabilidadesLacaio habilidadeLacaio) {
		this.habilidadesLacaio = habilidadeLacaio;
	}

	public HabilidadesLacaio getHabilidadesLacaio() {
		return this.habilidadesLacaio;
	}

	@Override
	public void usar(Carta alvo) {
		Lacaio lacaioAlvo = (Lacaio)alvo;
		lacaioAlvo.setVidaAtual(lacaioAlvo.getVidaAtual() - lacaioAlvo.getAtaque());
	}
	
	@Override
	public String toString() {
		
		String out = super.toString();
		out = out + "Ataque = "+getAtaque()+"\n";
		out = out + "Vida Atual = "+getVidaAtual()+"\n";
		out = out + "Vida Maxima = "+getVidaMaxima()+"\n";
		
		return out;
	}
	

}

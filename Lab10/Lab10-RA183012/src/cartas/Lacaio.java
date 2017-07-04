package cartas;

import io.Escritor;
import java.io.IOException;
import java.util.UUID;
import cartas.*;
import InterfacesLaMa.*;

public class Lacaio extends Carta implements ILaMaSeriazable {

	private int ataque;
	private int vidaAtual;
	private int vidaMaxima;
	private HabilidadesLacaio habilidade;

	public Lacaio(){
		super();
	}

	public Lacaio(String nome, int custoMana, int ataque, int vidaAtual, int vidaMaxima) {
		this(UUID.randomUUID(), nome, custoMana, ataque, vidaAtual, vidaMaxima);
	}

	public Lacaio(String nome, int custoMana, int ataque, int vidaAtual, int vidaMaxima, HabilidadesLacaio habilidade) {
		this(UUID.randomUUID(), nome, custoMana, ataque, vidaAtual, vidaMaxima);
		this.habilidade = habilidade;
	}

	public Lacaio(UUID id, String nome, int custoMana, int ataque, int vidaAtual, int vidaMaxima) {
		super(id, nome, custoMana);
		this.ataque = ataque;
		this.vidaAtual = vidaAtual;
		this.vidaMaxima = vidaMaxima;
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

	public HabilidadesLacaio getHabilidade() {
		return habilidade;
	}

	public void setHabilidade(HabilidadesLacaio habilidade) {
		this.habilidade = habilidade;
	}

	@Override
	public void usar(Carta alvo) {
		Lacaio lacaio = (Lacaio) alvo;
		int novoValor = lacaio.getVidaAtual() - getAtaque();
		novoValor = (novoValor > 0) ? novoValor : 0;
		lacaio.setVidaAtual(novoValor);
	}

	@Override
	public String toString() {
		return String.format("%s [%d/%d] Habilidade: %s", super.toString(), getAtaque(), getVidaAtual(), getHabilidade());
	}

	@Override
	public void escreveArquivo(Escritor escritor) throws IOException {
		escritor.escreveDelimObj("Lacaio");
		if (getId() != null)
		    escritor.escreveAtributo("id", getId().toString());
		escritor.escreveAtributo("nome", getNome());
		escritor.escreveAtributo("custoMana", (new Integer(getCustoMana())).toString());
		escritor.escreveAtributo("ataque", (new Integer(getAtaque())).toString());
		escritor.escreveAtributo("vidaAtual", (new Integer(getVidaAtual())).toString());
		escritor.escreveAtributo("vidaMaxima", (new Integer(getVidaMaxima())).toString());
		if (getHabilidade() != null)
		    escritor.escreveAtributo("habilidade", getHabilidade().toString());
		escritor.escreveDelimObj("Lacaio");
		escritor.finalizar();
	}
}

package base;

import java.util.UUID;

import org.ietf.jgss.Oid;

public class Carta {
	private UUID ID;
	private String nome;
	private int custoMana;
	
	public Carta(UUID iD, String nome, int custoMana) {
		super();
		ID = iD;
		this.nome = nome;
		this.custoMana = custoMana;
	}

	public Carta(String nome, int custoMana) {
		super();
		this.nome = nome;
		this.custoMana = custoMana;
	}

	public int getCustoMana() {
		return custoMana;
	}

	public void setCustoMana(int custoMana) {
		this.custoMana = custoMana;
	}

	public UUID getID() {
		return ID;
	}

	public String getNome() {
		return nome;
	}
	
	public void usar(Carta alvo) {
		
	}
	
	protected static void Oi () {
		System.out.println("oi");
	}
	
	@Override
	public String toString() {
		String out = getNome() + "(ID = "+getID()+")\n";
		out = out + "Custo de Mana = "+getCustoMana()+"\n";
		return out;
	}
}

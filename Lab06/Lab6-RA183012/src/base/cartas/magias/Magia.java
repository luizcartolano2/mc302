package base.cartas.magias;
import java.util.UUID;

import base.Carta;

public class Magia extends Carta{

	public Magia(UUID iD, String nome, int custoMana) {
		super(iD, nome, custoMana);
	}
	
	public Magia(String nome, int custoMana) {
		super(nome, custoMana);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
}

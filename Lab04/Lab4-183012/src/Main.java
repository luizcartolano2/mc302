import java.util.UUID;

import base.Baralho;
import base.Carta;
import base.cartas.Lacaio;
import base.cartas.magias.Buff;
import base.cartas.magias.Dano;
import base.cartas.magias.DanoArea;
import base.cartas.magias.Magia;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Baralho baralho = new Baralho();
		Lacaio lacaio = new Lacaio(UUID.randomUUID(), "Lacaio", 3);
		Buff buff = new Buff("Buff", 2, 3, 4);
		Dano dano = new Dano("Dano", 4, 2);
		DanoArea danoArea = new DanoArea("DanoArea", 10, 11);
		Magia magia = new Magia("magia", 2);
		Carta carta =  new Carta(UUID.randomUUID(), "Carta", 2);	
		
		baralho.adicionarCarta(lacaio);
		baralho.adicionarCarta(buff);
		baralho.adicionarCarta(dano);
		baralho.adicionarCarta(danoArea);
		baralho.adicionarCarta(magia);
		
		// acessando o atributo nome indiretamente pelo objeto "Lacaio"
		String nome = lacaio.getNome();
		
		//tentando acessar um metodo estatico da superclasse
		//lacaio.Oi();

		System.out.println(baralho);
		System.out.println(nome);
		
	}

}

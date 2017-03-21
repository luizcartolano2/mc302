package lab02;

public class Main {

	public static void main(String[] args) {
		// instanciando os objetos
		CartaLacaio lac1 = new CartaLacaio(1 , "Frodo Bolseiro " , 2, 1, 1) ;
		CartaLacaio lac2 = new CartaLacaio(2, "Aragorn", 5, 7, 6);
		CartaLacaio lac3 = new CartaLacaio(3, "Legolas", 8, 4, 6);
		// instanciando com o construtor reduzido
		CartaLacaio lac4 = new CartaLacaio(4, "Darth Vader",6);
		// instanciando com o construtor copia
		CartaLacaio lac5 = new CartaLacaio(lac2);
		CartaMagia mag1 = new CartaMagia(4, "You shall not pass", 4, true , 7);
		CartaMagia mag2 = new CartaMagia(5, "Telecinese", 3, false , 2);

		// fazendo com que lac1 receba o ataque de lac3
		// lac1.setAtaque(lac3.getAtaque());

		// buffando as cartas
		lac1.buffar(8);
		lac3.buffar(10, 8);

		System.out.println(lac1);
		// System.out.println(lac2);
		System.out.println(lac3);
		// System.out.println(lac4);
		// System.out.println(lac5);
		// System.out.println(mag1);
		// System.out.println(mag2);

	}

}

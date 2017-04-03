import java.util.ArrayList;

import com.Luiz.util.Util;
import com.Luiz.base.Baralho;
import com.Luiz.base.BaralhoArrayList;
import com.Luiz.base.CartaLacaio;;

public class Main {

	public static void main(String[] args) {
		
		// instanciando os objetos CartaLacaio, Baralho, BaralhoArrayList
		CartaLacaio lac1 = new CartaLacaio(1 , "Frodo Bolseiro " , 2, 1, 1) ; 
		CartaLacaio lac2 = new CartaLacaio(2, "Aragorn", 5, 7, 6);
		CartaLacaio lac3 = new CartaLacaio(3, "Legolas", 8, 4, 6);
//		CartaLacaio lac4 = new CartaLacaio(10, "Oi", 8);
//		CartaLacaio lac5 = new CartaLacaio(10, "Oi", 8);
//		CartaLacaio lac6 = new CartaLacaio(10, "Oi", 8);
//		CartaLacaio lac7 = new CartaLacaio(10, "Oi", 8);
//		CartaLacaio lac8 = new CartaLacaio(10, "Oi", 8);
//		CartaLacaio lac9 = new CartaLacaio(10, "Oi", 8);
//		CartaLacaio lac10 = new CartaLacaio(10, "Oi", 8);
//		CartaLacaio lac11 = new CartaLacaio(10, "Oi", 8);
		
		Baralho bar1 = new Baralho();
		BaralhoArrayList bar2 = new BaralhoArrayList();

		// buffando as cartas do tipo CartaLacaio
		Util.buffar(lac1, 10);
		Util.buffar(lac2, 18);
		Util.buffar(lac3, 20, 40);
		
		// manuseando os objetos do tipo Baralho
		bar1.adicionarCarta(lac1);
		bar1.adicionarCarta(lac2);
		bar1.adicionarCarta(lac3);
//		bar1.adicionarCarta(lac4);
//		bar1.adicionarCarta(lac5);
//		bar1.adicionarCarta(lac6);
//		bar1.adicionarCarta(lac7);
//		bar1.adicionarCarta(lac8);
//		bar1.adicionarCarta(lac9);
//		bar1.adicionarCarta(lac10);
//		bar1.adicionarCarta(lac11);
		bar1.comprarCarta();
		bar1.embaralharCartas();

		// manuseando os objetos do tipo BaralhoArrayList
		bar2.adicionarCarta(lac1);
		bar2.adicionarCarta(lac2);
		bar2.adicionarCarta(lac3);
		bar2.comprarCarta();
		bar2.embaralharCartas();
		
	}

}

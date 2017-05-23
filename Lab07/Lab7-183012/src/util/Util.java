package util;
import base.Carta;
import base.Lacaio;
import base.TipoCarta;
import base.cartas.HabilidadesLacaio;
import base.cartas.magias.Buff;
import base.cartas.magias.Dano;
import base.cartas.magias.DanoArea;

import java.util.Random;
import java.util.UUID;

public class Util {
	public final static int MAX_CARDS = 30;
	public final static int PODER_HEROI = 5;
	public final static int MAO_INI = 3;
	
	public static void buffar(Lacaio lac, int a) {
		if(a > 0) {
			lac.setAtaque(lac.getAtaque() + a);
			lac.setVidaAtual(lac.getVidaAtual() + a);
			lac.setVidaMaxima(lac.getVidaMaxima() + a);
			alteraNomeFortalecido(lac);
		}
	}
	
	public static void buffar(Lacaio lac, int a, int v) {
		if(a > 0 && v > 0) {
			lac.setAtaque(lac.getAtaque() + a);
			lac.setVidaAtual(lac.getVidaAtual() + v);
			lac.setVidaMaxima(lac.getVidaMaxima() + v);
			alteraNomeFortalecido(lac);
		}
	}
	
	private static void alteraNomeFortalecido(Lacaio lac) {
		//lac.setNome(lac.getNome() + " Buffed");
	}

	public static Carta geraCartaAleatoria(Random gerador, int maxMana, int maxAtaque, int maxVida, TipoCarta tc) {

	    RandomString string = new RandomString(gerador, 10);
	    String nome = string.nextString();

		if (tc == TipoCarta.LACAIO) {
			HabilidadesLacaio habilidadesLacaio[] = new HabilidadesLacaio[3];
			habilidadesLacaio = HabilidadesLacaio.values();
			int i = gerador.nextInt(2);
            Lacaio lacaio = new Lacaio(nome, randInt(gerador,1,maxMana), randInt(gerador,1,maxAtaque), randInt(gerador,1,maxVida), habilidadesLacaio[i]);
            return lacaio;
		} else if (tc == TipoCarta.BUFF) {
            Buff buff = new Buff(nome, randInt(gerador,1,maxMana),randInt(gerador,1,maxAtaque),randInt(gerador,1,maxVida));
            return buff;
		} else if (tc == TipoCarta.DANO) {
            Dano dano = new Dano(nome, randInt(gerador,1,maxMana), randInt(gerador,1,maxAtaque));
            return dano;
		} else {
            DanoArea danoArea = new DanoArea(nome, randInt(gerador,1,maxMana), randInt(gerador,1,maxAtaque));
            return danoArea;
		}
	}

	public static int randInt (Random gerador , int min , int max) {
		return gerador.nextInt((max-min) + 1) + min;
	}

}

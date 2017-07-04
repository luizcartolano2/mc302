package base.controle;

import java.util.List;
import java.util.Random;

import base.cartas.Carta;
import base.exception.MesaNulaException;
import base.Baralho;
import base.Jogada;
import base.Mesa;
import base.cartas.TipoCarta;
import base.exception.ValorInvalidoException;
import base.service.*;
import base.service.impl.*;
import util.Util;

public class Controle {

	private Mesa mesa;
	private Baralho baralhoP;
	private Baralho baralhoS;
	private Random gerador;
	private JogadaService jogadaService;
	private BaralhoSer baralhoSer;
	private MesaService mesaService;
	private ProcessadorService processadorService;
	private CartaService cartaService;

	public Controle() {
		this.baralhoP = new Baralho();
		this.baralhoS = new Baralho();
		this.mesa = new Mesa();
		gerador = new Random();
		cartaService = new CartaServiceImpl();
		jogadaService = new JogadaServiceAgressivaImpl();
		baralhoSer = new BaralhoServiceImpl();
		mesaService = new MesaServiceImpl();
		processadorService = new ProcessadorServiceImpl();
	}

	public void executa() {


		baralhoP.addCartas(baralhoSer.preencheAleatorio(gerador, Util.MAX_CARDS, Util.MAX_MANA, Util.MAX_ATAQUE, Util.MAX_VIDA));
		baralhoS.addCartas(baralhoSer.preencheAleatorio(gerador, Util.MAX_CARDS, Util.MAX_MANA, Util.MAX_ATAQUE, Util.MAX_VIDA));

		try {
            mesa = mesaService.adicionaLacaios(mesa, gerador, TipoCarta.LACAIO);
        } catch (MesaNulaException e) {
            e.printStackTrace();
            System.out.print("Partida encerrada");
            System.exit(0);
        }

        try {
            mesa = mesaService.addMaoInicial(mesa, baralhoP, baralhoS, Util.MAO_INI);
        }catch (ValorInvalidoException e) {
            e.printStackTrace();
            System.out.print("Partida encerrada");
            System.exit(0);
        }

        List<Jogada> jogadas = jogadaService.criaJogada(mesa,'P');
		for (Jogada jogada : jogadas) {
			if (processadorService.processar(jogada,mesa)) {
				System.out.println("######" + jogada.getAutor() + " venceu!");
				break;
			}

		}

	}




}

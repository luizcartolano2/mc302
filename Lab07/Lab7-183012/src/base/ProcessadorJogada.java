package base;

import base.cartas.HabilidadesLacaio;
import base.cartas.magias.Buff;
import base.cartas.magias.Dano;
import base.cartas.magias.DanoArea;
import util.Util;

import java.util.ArrayList;

/**
 * Created by LuizCartolan on 08/05/17.
 */
public class ProcessadorJogada {
    public ProcessadorJogada() {
    }

    public static void processar(Jogada jogada, Mesa mesa) {
        char autor = jogada.getAutor();

        if (autor == 's') {
            mesa.setManaS(mesa.getManaS() - jogada.getCartaJogada().getCustoMana());
            System.out.println("Autor: " + jogada.getAutor() + "\n" + "Carta Jogada: " + jogada.getCartaJogada() + "\n" + "Alvo: " + jogada.getCartaAlvo() + "\n" + "Qte de lacaios do oponente: " + mesa.getLacaioP().size());
            System.out.println("Poder Heroico: " + mesa.getPoderHeroiS());
            for (int i = 0; i < mesa.getLacaioP().size(); i++) {
                System.out.println(mesa.getLacaioP().get(i));
            }
        } else {
            mesa.setManaP(mesa.getManaP() - jogada.getCartaJogada().getCustoMana());
            System.out.println("Autor: " + jogada.getAutor() + "\n" + "Carta Jogada: " + jogada.getCartaJogada() + "\n" + "Alvo: " + jogada.getCartaAlvo() + "\n" + "Qte de lacaios do oponente: " + mesa.getLacaioS().size());
            System.out.println("Poder Heroico: " + mesa.getPoderHeroiS());
            for (int i = 0; i < mesa.getLacaioS().size(); i++) {
                System.out.println(mesa.getLacaioS().get(i));
            }
        }

        if (jogada.getCartaJogada() instanceof DanoArea) {
            if (autor == 'p') {
                ArrayList<base.Lacaio> lacaioP = mesa.getLacaioP();
                for (int i = 0; i < lacaioP.size(); i++) {
                    lacaioP.get(i).setVidaAtual(lacaioP.get(i).getVidaAtual() - ((DanoArea) jogada.getCartaJogada()).getDano());
                    mesa.setPoderHeroiP(mesa.getPoderHeroiP() - 1);
                }
                System.out.println("Autor: " + jogada.getAutor() + "\n" + "Carta Jogada: " + jogada.getCartaJogada() + "\n" + "Alvo: " + jogada.getCartaAlvo() + "\n" + "Qte de lacaios do oponente: " + mesa.getLacaioS().size());
                System.out.println("Poder Heroico: " + mesa.getPoderHeroiS());
                for (int i = 0; i < mesa.getLacaioS().size(); i++) {
                    System.out.println(mesa.getLacaioS().get(i));
                }
            } else {
                ArrayList<base.Lacaio> lacaioS = mesa.getLacaioS();
                for (int i = 0; i < lacaioS.size(); i++) {
                    lacaioS.get(i).setVidaAtual(lacaioS.get(i).getVidaAtual() - ((DanoArea) jogada.getCartaJogada()).getDano());
                    mesa.setPoderHeroiS(mesa.getPoderHeroiS() - 1);
                }
                System.out.println("Autor: " + jogada.getAutor() + "\n" + "Carta Jogada: " + jogada.getCartaJogada() + "\n" + "Alvo: " + jogada.getCartaAlvo() + "\n" + "Qte de lacaios do oponente: " + mesa.getLacaioS().size());
                System.out.println("Poder Heroico: " + mesa.getPoderHeroiS());
                for (int i = 0; i < mesa.getLacaioS().size(); i++) {
                    System.out.println(mesa.getLacaioS().get(i));
                }
            }
        } else if (jogada.getCartaJogada() instanceof Dano) {
            if (autor == 'p') {
                ArrayList<base.Lacaio> lacaioP = mesa.getLacaioP();
                int i;
                boolean existe = false;
                for (i = 0; i < lacaioP.size(); i++) {
                    base.Lacaio lacaio = lacaioP.get(i);
                    if (lacaio.getHabilidadesLacaio() == HabilidadesLacaio.PROVOCAR) {
                        existe = true;
                        break;
                    }
                }
                if (existe) {
                    base.Lacaio lacaio = lacaioP.get(i);
                    lacaio.setVidaAtual(lacaio.getVidaAtual() - ((Dano) jogada.getCartaJogada()).getDano());
                } else {
                    base.Carta alvo = jogada.getCartaAlvo();
                    if (alvo instanceof base.Lacaio) {
                        ((base.Lacaio) alvo).setVidaAtual(((base.Lacaio) alvo).getVidaAtual() - ((Dano) jogada.getCartaJogada()).getDano());
                    }
                }
                System.out.println("Autor: " + jogada.getAutor() + "\n" + "Carta Jogada: " + jogada.getCartaJogada() + "\n" + "Alvo: " + jogada.getCartaAlvo() + "\n" + "Qte de lacaios do oponente: " + mesa.getLacaioS().size());
                System.out.println("Poder Heroico: " + mesa.getPoderHeroiS());
                for (int j = 0; i < mesa.getLacaioS().size(); j++) {
                    System.out.println(mesa.getLacaioS().get(j));
                }
            } else {
                ArrayList<base.Lacaio> lacaioS = mesa.getLacaioS();
                int i;
                boolean existe = false;
                for (i = 0; i < lacaioS.size(); i++) {
                    base.Lacaio lacaio = lacaioS.get(i);
                    if (lacaio.getHabilidadesLacaio() == HabilidadesLacaio.PROVOCAR) {
                        existe = true;
                        break;
                    }
                }
                if (existe) {
                    base.Lacaio lacaio = lacaioS.get(i);
                    lacaio.setVidaAtual(lacaio.getVidaAtual() - ((Dano) jogada.getCartaJogada()).getDano());
                } else {
                    base.Carta alvo = jogada.getCartaAlvo();
                    if (alvo instanceof base.Lacaio) {
                        ((base.Lacaio) alvo).setVidaAtual(((base.Lacaio) alvo).getVidaAtual() - ((Dano) jogada.getCartaJogada()).getDano());
                    }
                }
                System.out.println("Autor: " + jogada.getAutor() + "\n" + "Carta Jogada: " + jogada.getCartaJogada() + "\n" + "Alvo: " + jogada.getCartaAlvo() + "\n" + "Qte de lacaios do oponente: " + mesa.getLacaioS().size());
                System.out.println("Poder Heroico: " + mesa.getPoderHeroiS());
                for (int k = 0; i < mesa.getLacaioS().size(); k++) {
                    System.out.println(mesa.getLacaioS().get(k));
                }
            }
        } else if (jogada.getCartaJogada() instanceof Buff) {
            base.Carta alvo = jogada.getCartaAlvo();
            if (alvo instanceof base.Lacaio) {
                Util.buffar((base.Lacaio)alvo,((Buff) jogada.getCartaJogada()).getAumentoEmAtaque());
            }
        } else if (jogada.getCartaJogada() instanceof base.Lacaio) {
            base.Lacaio lacaioJogado = (base.Lacaio)jogada.getCartaJogada();
            if(autor == 's') {
                if (lacaioJogado.getHabilidadesLacaio() == HabilidadesLacaio.EXAUSTAO) {
                    ArrayList <base.Lacaio> lacaioS= mesa.getLacaioS();
                    lacaioJogado.setHabilidadeLacaio(HabilidadesLacaio.INVESTIDA);
                    lacaioS.add(lacaioJogado);
                    mesa.setLacaioS(lacaioS);
                } else {
                    base.Lacaio alvo = (base.Lacaio)jogada.getCartaAlvo();
                    alvo.setVidaAtual(alvo.getVidaAtual() - lacaioJogado.getAtaque());
                }
                System.out.println("Autor: " + jogada.getAutor() + "\n" + "Carta Jogada: " + jogada.getCartaJogada() + "\n" + "Alvo: " + jogada.getCartaAlvo() + "\n" + "Qte de lacaios do oponente: " + mesa.getLacaioS().size());
                System.out.println("Poder Heroico: " + mesa.getPoderHeroiS());
                for (int i = 0; i < mesa.getLacaioS().size(); i++) {
                    System.out.println(mesa.getLacaioS().get(i));
                }
            } else {
                if (lacaioJogado.getHabilidadesLacaio() == HabilidadesLacaio.EXAUSTAO) {
                    ArrayList <base.Lacaio> lacaioP= mesa.getLacaioP();
                    lacaioJogado.setHabilidadeLacaio(HabilidadesLacaio.INVESTIDA);
                    lacaioP.add(lacaioJogado);
                    mesa.setLacaioP(lacaioP);
                } else {
                    base.Lacaio alvo = (base.Lacaio)jogada.getCartaAlvo();
                    alvo.setVidaAtual(alvo.getVidaAtual() - lacaioJogado.getAtaque());
                }
                System.out.println("Autor: " + jogada.getAutor() + "\n" + "Carta Jogada: " + jogada.getCartaJogada() + "\n" + "Alvo: " + jogada.getCartaAlvo() + "\n" + "Qte de lacaios do oponente: " + mesa.getLacaioS().size());
                System.out.println("Poder Heroico: " + mesa.getPoderHeroiS());
                for (int i = 0; i < mesa.getLacaioS().size(); i++) {
                    System.out.println(mesa.getLacaioS().get(i));
                }
            }
        }

        ArrayList<base.Lacaio> lacaiosS = mesa.getLacaioS();
        ArrayList<base.Lacaio> lacaiosP = mesa.getLacaioP();
        for (int i = 0; i < lacaiosS.size(); i++) {
            if (lacaiosS.get(i).getVidaAtual() <= 0) {
                lacaiosS.remove(lacaiosS.get(i));
                i--;
            }
        }
        mesa.setLacaioS(lacaiosS);
        for (int i = 0; i < lacaiosP.size(); i++) {
            if (lacaiosP.get(i).getVidaAtual() <= 0) {
                lacaiosP.remove(lacaiosP.get(i));
                i--;
            }
        }
        mesa.setLacaioP(lacaiosP);
    }

}

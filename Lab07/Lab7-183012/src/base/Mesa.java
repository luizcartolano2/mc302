package base;

import java.util.ArrayList;
import base.Carta;
import base.Lacaio;
import util.Util;

/**
 * Created by LuizCartolan on 08/05/17.
 */
public class Mesa {

    private ArrayList<Carta> maoP;
    private ArrayList<Carta> maoS;
    private ArrayList<Lacaio> lacaioP;
    private ArrayList<Lacaio> lacaioS;
    private int poderHeroiP;
    private int poderHeroiS;
    private int manaP;
    private int manaS;

    public Mesa() {
        maoP = null;
        maoS = null;
        lacaioP = null;
        lacaioS = null;
        poderHeroiP = Util.PODER_HEROI;
        poderHeroiS = Util.PODER_HEROI;
        manaP = 1;
        manaS = 1;
    }

    public Mesa(ArrayList<Carta> maoP, ArrayList<Carta> maoS, ArrayList<Lacaio> lacaioP, ArrayList<Lacaio> lacaioS, int poderHeroiP, int poderHeroiS, int manaP, int manaS) {
        this.maoP = maoP;
        this.maoS = maoS;
        this.lacaioP = lacaioP;
        this.lacaioS = lacaioS;
        this.poderHeroiP = poderHeroiP;
        this.poderHeroiS = poderHeroiS;
        this.manaP = manaP;
        this.manaS = manaS;
    }

    public ArrayList<Carta> getMaoP() {
        return maoP;
    }

    public void setMaoP(ArrayList<Carta> maoP) {
        this.maoP = maoP;
    }

    public ArrayList<Carta> getMaoS() {
        return maoS;
    }

    public void setMaoS(ArrayList<Carta> maoS) {
        this.maoS = maoS;
    }

    public ArrayList<Lacaio> getLacaioP() {
        return lacaioP;
    }

    public void setLacaioP(ArrayList<Lacaio> lacaioP) {
        this.lacaioP = lacaioP;
    }

    public ArrayList<Lacaio> getLacaioS() {
        return lacaioS;
    }

    public void setLacaioS(ArrayList<Lacaio> lacaioS) {
        this.lacaioS = lacaioS;
    }

    public int getPoderHeroiP() {
        return poderHeroiP;
    }

    public void setPoderHeroiP(int poderHeroiP) {
        this.poderHeroiP = poderHeroiP;
    }

    public int getPoderHeroiS() {
        return poderHeroiS;
    }

    public void setPoderHeroiS(int poderHeroiS) {
        this.poderHeroiS = poderHeroiS;
    }

    public int getManaP() {
        return manaP;
    }

    public void setManaP(int manaP) {
        this.manaP = manaP;
    }

    public int getManaS() {
        return manaS;
    }

    public void setManaS(int manaS) {
        this.manaS = manaS;
    }

    public void decPoderHeroi(int poder, char jogador) {
        if(jogador == 's')
            setPoderHeroiS(this.getPoderHeroiS() - poder);
        else
            setPoderHeroiP(this.getPoderHeroiP() - poder);
    }

    public void decMana(int mana, char jogador) {
        if (jogador == 's')
            setManaS(this.getManaS() - mana);
        else
            setManaP(this.getManaP() - mana);
    }

    public Carta sacarCarta(char jogador) {
        if (jogador == 's') {
            if (maoS.size() > 0) {
                Carta carta = maoS.get(maoS.size() - 1);
                maoS.remove(carta);
                return carta;
            } else {
                return null;
            }
        } else {
            if (maoP.size() > 0) {
                Carta carta = maoP.get(maoP.size() - 1);
                maoP.remove(carta);
                return carta;
            } else {
                return null;
            }
        }
    }
}

package cartas.magias;

import java.io.IOException;
import java.util.UUID;

import InterfacesLaMa.ILaMaSeriazable;
import cartas.*;
import io.Escritor;

public class Buff extends Magia implements ILaMaSeriazable {

    private int aumentoEmAtaque;
    private int aumentoEmVida;

    public Buff(){
        super();
    }

    public Buff(UUID id, String nome, int custoMana, int aumentoEmAtaque, int aumentoEmVida) {
        super(id, nome, custoMana);
        this.aumentoEmAtaque = aumentoEmAtaque;
        this.aumentoEmVida = aumentoEmVida;
    }

    public Buff(String nome, int custoMana, int aumentoEmAtaque, int aumentoEmVida) {
        this(UUID.randomUUID(), nome, custoMana, aumentoEmAtaque, aumentoEmVida);
    }

    public int getAumentoEmAtaque() {
        return aumentoEmAtaque;
    }

    public void setAumentoEmAtaque(int aumentoEmAtaque) {
        this.aumentoEmAtaque = aumentoEmAtaque;
    }

    public int getAumentoEmVida() {
        return aumentoEmVida;
    }

    public void setAumentoEmVida(int aumentoEmVida) {
        this.aumentoEmVida = aumentoEmVida;
    }

    public void usar(Carta alvo) {        
        Lacaio lacaio = (Lacaio) alvo;
        
        lacaio.setAtaque(lacaio.getAtaque() + getAumentoEmAtaque());
        lacaio.setVidaAtual(lacaio.getVidaAtual() + getAumentoEmVida());
        lacaio.setVidaMaxima(lacaio.getVidaMaxima() + getAumentoEmVida());
    }

    @Override
    public String toString() {
        return String.format("%s [+%d/+%d]", super.toString(),
                getAumentoEmAtaque(), getAumentoEmVida());
    }

    @Override
    public void escreveArquivo(Escritor escritor) throws IOException {
        escritor.escreveDelimObj("Buff");
        escritor.escreveAtributo("id", getId().toString());
        escritor.escreveAtributo("nome", getNome());
        escritor.escreveAtributo("custoMana", (new Integer(getCustoMana())).toString());
        escritor.escreveAtributo("aumentoEmAtaque", (new Integer(getAumentoEmAtaque())).toString());
        escritor.escreveAtributo("aumentoEmVida", (new Integer(getAumentoEmVida())).toString());
        escritor.escreveDelimObj("Buff");
        escritor.finalizar();
    }
}

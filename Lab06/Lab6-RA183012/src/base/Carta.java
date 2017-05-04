package base;

import java.util.UUID;

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

    public Carta () {

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
        System.out.println("usar de carta nao foi implementado ainda");
    }


    @Override
    public String toString() {
        String out = "\n**********************************************************************\n";
        out = out + getNome() + " (ID = "+getID()+")\n";
        out = out + " Custo de Mana = "+getCustoMana()+"\n";
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Carta carta = (Carta) o;

        if (custoMana != carta.custoMana)
            return false;
        if (ID != null ? !ID.equals(carta.ID) : carta.ID != null)
            return false;

        return nome != null ? nome.equals(carta.nome) : carta.nome == null;
    }

    @Override
    public int hashCode() {
        int result = ID != null ? ID.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + custoMana;
        return result;
    }

}

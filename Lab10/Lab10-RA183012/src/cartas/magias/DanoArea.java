package cartas.magias;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import InterfacesLaMa.ILaMaSeriazable;
import cartas.*;
import io.Escritor;

public class DanoArea extends Dano implements ILaMaSeriazable{

    public DanoArea(){}

    public DanoArea(String nome, int custoMana, int dano) {
        this(UUID.randomUUID(), nome, custoMana, dano);
    }

    public DanoArea(UUID id, String nome, int custoMana, int dano) {
        super(id, nome, custoMana, dano);
    }

    public void usar(List<Carta> alvos) {
        for (Carta alvo : alvos) {
            usar(alvo);
        }
    }

    @Override
    public void escreveArquivo(Escritor escritor) throws IOException {
        escritor.escreveDelimObj("DanoArea");
        escritor.escreveAtributo("id", getId().toString());
        escritor.escreveAtributo("nome", getNome());
        escritor.escreveAtributo("custoMana", (new Integer(getCustoMana())).toString());
        escritor.escreveAtributo("dano", (new Integer(getDano())).toString());
        escritor.escreveDelimObj("DanoArea");
        escritor.finalizar();
    }
    
}

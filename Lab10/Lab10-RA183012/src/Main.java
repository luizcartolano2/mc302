
import InterfacesLaMa.ILaMaSeriazable;
import cartas.Lacaio;
import cartas.magias.Buff;
import cartas.magias.Dano;
import cartas.magias.DanoArea;
import io.Escritor;
import io.Leitor;
import java.io.IOException;
import java.util.List;

/**
 * Created by duducartolano on 06/06/17.
 */
public class Main {
    public static void main(String args[]) throws IOException {
        // instanciando as cartas
        Lacaio lacaio = new Lacaio("Lab",1,1,1,1);
        Dano dano = new Dano("chato",1,1);
        Buff buff = new Buff("pra",1,1,1);
        DanoArea danoArea = new DanoArea("p",1,1);

        // instanciando os escritores de cada carta
        Escritor escritorL = new Escritor("lacaio");
        Escritor escritorD = new Escritor("dano");
        Escritor escritorB = new Escritor("buff");
        Escritor escritorDa = new Escritor("danoArea");

        // imprimindo as cartas
        System.out.println("***************************************************");
        System.out.println("IMPRIMINDO AS CARTAS ANTES DE SERIALIZAR: ");
        System.out.println(lacaio);
        System.out.println(buff);
        System.out.println(dano);
        System.out.println(danoArea);
        System.out.println("***************************************************");


        lacaio.escreveArquivo(escritorL);
        dano.escreveArquivo(escritorD);
        buff.escreveArquivo(escritorB);
        danoArea.escreveArquivo(escritorDa);

        // lendo as cartas
        // instanciando os leitores
        Leitor leitorL = new Leitor("lacaio.txt");
        Leitor leitorD = new Leitor("dano.txt");
        Leitor leitorB = new Leitor("buff.txt");
        Leitor leitorDa = new Leitor("danoArea.txt");

        List<ILaMaSeriazable> list = leitorL.leObjetos();
        list.addAll(leitorD.leObjetos());
        list.addAll(leitorB.leObjetos());
        list.addAll(leitorDa.leObjetos());

        System.out.println("***************************************************");
        System.out.println("IMPRIMINDO AS CARTAS DEPOIS DE SERIALIZAR: ");
        System.out.println(list);
        System.out.println("***************************************************");
    }
}

import base.Carta;
import base.Lacaio;
import base.cartas.magias.Buff;
import base.cartas.magias.Dano;
import base.cartas.magias.Magia;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Created by LuizCartolano on 30/04/17.
 */
public class Main {
    public static void main(String[] args) {
//        Collection<Carta> colecaoCartas = Arrays.asList(
//                new Lacaio(UUID.randomUUID(),"LacaioA",1,5,5),
//                new Lacaio(UUID.randomUUID(),"LacaioB",1,6,6),
//                new Lacaio(UUID.randomUUID(),"LacaioC",1,7,6),
//                new Magia("Magia",4),
//                new Dano("Dano", 4, 4),
//                new Buff("Buff",1,1,1)
//        );
//
//
//        Collection<Carta> maiorAtaque = colecaoCartas.stream()
//                .filter(carta -> carta instanceof Lacaio)
//                .sorted((x1, x2) -> ((Lacaio)x2).getAtaque() - ((Lacaio)x1).getAtaque())
//                .collect(Collectors.toList());
//
//
//        System.out.println("Maior ataque: " + maiorAtaque);
//
//
//        Integer sumOfAtaques = colecaoCartas.stream()
//                .filter(carta -> carta instanceof Lacaio)
//                .mapToInt(c->((Lacaio)c).getAtaque())
//                .sum();
//        System.out.println("**********************************************************************");
//        System.out.println("Total de ataque: " + sumOfAtaques);
//
//
//        Collection <Carta> maiorVida = colecaoCartas.stream()
//                .filter(carta -> carta instanceof Lacaio)
//                .sorted((f1,f2) -> ((Lacaio)f2).getVidaAtual() - ((Lacaio)f1).getVidaAtual())
//                .collect(Collectors.toList());
//
//        System.out.println("**********************************************************************");
//        System.out.println("Ordem decrescente de vida: " + maiorVida);



        // declarando o ArrayList
//        ArrayList<Carta> baralho = new ArrayList<>();
        // declarando a LinkedList
//        LinkedList<Carta> baralho = new LinkedList<>();
        // declarando o HashSet
//        HashSet<Carta> baralho = new HashSet<>();
        // declarando a TreeSet
//        TreeSet<Carta> baralho = new TreeSet<>();

        Collection <Carta> baralho = new TreeSet<>((Carta carta1, Carta carta2) -> {
            return carta1.getCustoMana() - carta2.getCustoMana();
        });

        // nesse for adicionamos as 10000 cartas aleatorias
        for (int i = 0; i < 10000; i++) {

            Carta carta = new Carta(UUID.randomUUID(),(new Integer(i)).toString(), i);
            baralho.add(carta);


        }
        System.out.println(baralho.size());

        Iterator<Carta> it = baralho.iterator();

        while(it.hasNext()){
            System.out.println(it.next());
        }
        // aqui iniciamos o contador de tempo
//        long s = System.nanoTime();
//        for (int i = 0; i < baralho.size(); i++) {
//            // usando o metodo get
//            baralho.get(i);
//            Carta next = baralho.iterator().next();
//            // usando o metodo contains
//            baralho.contains(next);
//
//        }
//        // imprimindo o tempo gasto
//        System.out.printf("%f\t", (System.nanoTime() - s) / 1000000.0);

    }
}

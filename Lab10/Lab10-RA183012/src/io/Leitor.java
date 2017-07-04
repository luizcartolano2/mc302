/**
 * Created by duducartolano on 02/06/17.
 */

package io;
import InterfacesLaMa.ILaMaSeriazable;
import cartas.HabilidadesLacaio;
import cartas.Lacaio;
import cartas.magias.Buff;
import cartas.magias.Dano;
import cartas.magias.DanoArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Leitor {

    private File file;
    private Scanner fin;

    public Leitor(String filex) throws FileNotFoundException {
        file = new File(filex);
        try {
            this.fin = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List <ILaMaSeriazable> leObjetos () {
        // Crio a lista
        List<ILaMaSeriazable> lista = new ArrayList<ILaMaSeriazable>();

        if(!fin.hasNextLine()){
            System.out.println("Arquivo vazio, seu burro(a)!");
            return null;
        }

        while(fin.hasNextLine()) {
            // Pego o tipo do Objeto
            String linha = fin.nextLine();
            String[] palavras = linha.split(" ");

            if(palavras[1].compareTo("Lacaio") == 0){
                Lacaio carta = new Lacaio();
                linha = fin.nextLine();
                palavras = linha.split(" ");
                while((palavras[0].compareTo("obj")) != 0){
                    if(palavras[0].compareTo("id") == 0){
                        carta.setID(UUID.fromString(palavras[1]));
                    }

                    if(palavras[0].compareTo("nome") == 0){
                        carta.setNome(palavras[1]);
                    }

                    if(palavras[0].compareTo("custoMana") == 0){
                        carta.setCustoMana(Integer.parseInt(palavras[1]));
                    }

                    if(palavras[0].compareTo("ataque") == 0){
                        carta.setAtaque(Integer.parseInt(palavras[1]));
                    }

                    if(palavras[0].compareTo("vidaAtual") == 0){
                        carta.setAtaque(Integer.parseInt(palavras[1]));
                    }

                    if(palavras[0].compareTo("vidaMaxima") == 0){
                        carta.setAtaque(Integer.parseInt(palavras[1]));
                    }

                    if(palavras[0].compareTo("habilidade") == 0){
                        String hab = palavras[1];

                        if(hab.compareTo("EXAUSTAO") == 0){
                            carta.setHabilidade(HabilidadesLacaio.EXAUSTAO);
                        }

                        if(hab.compareTo("PROVOCAR") == 0){
                            carta.setHabilidade(HabilidadesLacaio.PROVOCAR);
                        }

                        if(hab.compareTo("INVESTIDA") == 0){
                            carta.setHabilidade(HabilidadesLacaio.INVESTIDA);
                        }
                    }
                    linha = fin.nextLine();
                    palavras = linha.split(" ");
                }

                lista.add(carta);
            }

            if(palavras[1].compareTo("Buff") == 0){
                Buff carta = new Buff();
                linha = fin.nextLine();
                palavras = linha.split(" ");

                while((palavras[0].compareTo("obj")) != 0) {

                    if (palavras[0].compareTo("id") == 0) {
                        carta.setID(UUID.fromString(palavras[1]));
                    }

                    if (palavras[0].compareTo("nome") == 0) {
                        carta.setNome(palavras[1]);
                    }

                    if (palavras[0].compareTo("custoMana") == 0) {
                        carta.setCustoMana(Integer.parseInt(palavras[1]));
                    }
                    if (palavras[0].compareTo("aumentoEmAtaque") == 0) {
                        carta.setAumentoEmAtaque(Integer.parseInt(palavras[1]));
                    }
                    if (palavras[0].compareTo("aumentoEmVida") == 0) {
                        carta.setAumentoEmVida(Integer.parseInt(palavras[1]));
                    }
                    linha = fin.nextLine();
                    palavras = linha.split(" ");
                }
                lista.add(carta);
            }

            if(palavras[1].compareTo("Dano") == 0){
                Dano carta = new Dano();
                linha = fin.nextLine();
                palavras = linha.split(" ");
                while((palavras[0].compareTo("obj")) != 0) {
                    if (palavras[0].compareTo("id") == 0) {
                        carta.setID(UUID.fromString(palavras[1]));
                    }
                    if (palavras[0].compareTo("nome") == 0) {
                        carta.setNome(palavras[1]);
                    }
                    if (palavras[0].compareTo("custoMana") == 0) {
                        carta.setCustoMana(Integer.parseInt(palavras[1]));
                    }
                    if (palavras[0].compareTo("dano") == 0) {
                        carta.setDano(Integer.parseInt(palavras[1]));
                    }
                    linha = fin.nextLine();
                    palavras = linha.split(" ");
                }
                lista.add(carta);
            }

            if(palavras[1].compareTo("DanoArea") == 0){
                DanoArea carta = new DanoArea();
                linha = fin.nextLine();
                palavras = linha.split(" ");
                while((palavras[0].compareTo("obj")) != 0) {
                    if (palavras[0].compareTo("id") == 0) {
                        carta.setID(UUID.fromString(palavras[1]));
                    }
                    if (palavras[0].compareTo("nome") == 0) {
                        carta.setNome(palavras[1]);
                    }
                    if (palavras[0].compareTo("custoMana") == 0) {
                        carta.setCustoMana(Integer.parseInt(palavras[1]));
                    }
                    if (palavras[0].compareTo("dano") == 0) {
                        carta.setDano(Integer.parseInt(palavras[1]));
                    }
                    linha = fin.nextLine();
                    palavras = linha.split(" ");
                }
                lista.add(carta);
            }

        }
        fin.close();

        return lista;
    }

}

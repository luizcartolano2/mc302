/**
 * Created by duducartolano on 02/06/17.
 */

package io;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Escritor {
    // private File arquivo;
    private FileWriter escreveArquivo;

    public Escritor(){}

    public Escritor(String nomeDoArquivo) throws IOException{
        nomeDoArquivo += ".txt";
        escreveArquivo = new FileWriter(nomeDoArquivo,false);
    }

    public void escreveAtributo(String nomeAtributo, String valor) throws IOException {
        try {
            escreveArquivo.write(nomeAtributo);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Erro ao escrever 'nomeAtributo' ");
            System.exit(0);
        }
        try {
            escreveArquivo.write(" ");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Erro ao escrever ' '");
            System.exit(0);
        }
        try {
            escreveArquivo.write(valor);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Erro ao escrever 'valor' ");
            System.exit(0);
        }
        try {
            escreveArquivo.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Erro ao escrever barraN ");
            System.exit(0);
        }
    }

    public void escreveDelimObj(String nomeObj) throws IOException{
        try {
            escreveArquivo.write("obj");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Erro ao escrever 'obj' ");
            System.exit(0);
        }
        try {
            escreveArquivo.write(" ");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Erro ao escrever ' '");
            System.exit(0);
        }
        try {
            escreveArquivo.write(nomeObj);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Erro ao escrever 'nomeObj' ");
            System.exit(0);
        }
        try {
            escreveArquivo.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Erro ao escrever 'barraN' ");
            System.exit(0);
        }
    }

    public void finalizar() throws IOException {
        try {
            escreveArquivo.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Erro ao finalizar o arquivo");
            System.exit(0);
        }
    }
}

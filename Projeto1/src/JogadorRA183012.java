import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Esta classe representa um Jogador com comportamentos pre-definidos. Antes de comecar a explicar cada um dos comportamentos, gostaria de explicar o por que
 * das variaveis minhaMana e vidaOponente, terem sido declaradas como variaveis de classe. O motivo se deu porque ao longo do desenvolvimento dividi os comportamentos
 * em metodos, de modo que declara-las como variaveis de classe facilitou o desenvolvimento. Agora explicaremos a logica usada em cada um dos comportamentos.
 *
 * Comportamento "Agressivo": O comportamento "Agressivo" tem como objetivo causar danos no heroi oponente o mais rapido possivel, para isso, as prioridades do comportamento
 * devem ser baixar lacaios de alto ataque, usar magia de alvos no heroi e evitar magias de area e trocas com o oponente. Para garantir que os lacaios de maior ataque
 * fossem os baixados, a primeira atitude do comportamento eh de organizar a mao do heroi deixando os lacaios com maior ataque no inicio da mao. Assim, quando iteramos
 * a mao (segunda atitude tomada pelo comportamento) os lacaios de maior ataque serao os primeiros a serem baixados a mesa (um detalhe interessante no momento de baixar
 * os lacaios eh a variavel "meusLacaios" que controla o numero de lacaios na mesa, a fim de garantir que nao sejam baixados mais que sete(7) deles na mesa. Apos isso,
 * o comportamento se preocupa com usar as cartas de magia da mao. Se a magia for do tipo "alvo", a usaremos diretamente no heroi. Se a magia for do tipo "area", so a
 * usaremos quando tiver um numero elevado de lacaios oponentes na mesa. Agora se a magia for do tipo "buff", primeiro verificamos se existem lacaios na mesa, caso eles
 * existam, chamamos o metodo "lacaioBuffar()"(o metodo em questao busca o lacaio da mesa com maior ataque - que consideramos a melhor opcao de buff disponivel).
 * Uma vez baixadas magias e lacaios, eh hora de usarmos os lacaios que estao na mesa para atacar o heroi inimigo. Como foi especificado pelo enunciado, nao iremos fazer
 * nenhuma troca (atacar outro lacaio nesse momento), dedicando o "poder de fogo" que possuimos para eliminar o heroi. A ultima atitude tomada pelo comportamento eh a
 * de usar o poder do heroi(poderHeroico) no heroi inimigo, caso ele ainda possua mana para tal.
 *
 * Comportamento "Controle": O comportamento "Controle" tem como objetivo manter um controle do campo, acumulando mais lacaios vivos na mesa do que o oponente, desse modo
 * ele pode realizar um numero maior de "trocas favoraveis". Consideraremos como "trocas favoraveis" as seguintes situacoes: quando um lacaio X atacar um lacaio Y, acontecera:
 * 1 - o lacaio X sobrevive e o lacaio Y nao; 2 - ambos os lacaios morrem, mas o custo de mana de Y eh maior que o de X; 3 - ambos os lacaios morrem, mas o lacaio X ja estava
 * danificado e com menos vida do que o lacaio Y. Alem disso, o comportamento prioriza o uso de magias para realizar o controle, sendo que estas devem seguir as seguintes
 * regras: 1 - para magias de areas devem haver pelo menos dois lacaios oponentes na mesa; 2 - magias de alvo sao usadas apenas se forem deixar o alvo com no maximo um(1)
 * de vida. As primeiras atitudes tomadas pelo comportamento estao relacionados as cartas de magia, se a carta magia for fdo tipo "area" usamos condicionais a fim de
 * garantir que existem no minimo dois lacaios oponentes na mesa. Agora se a carta magia for do tipo "alvo", chamamos a funcao "lacaioMagiaAlvo(CartaMagia)" - funcao que
 * retorna "-1" se nao houver um alvo que respeita as condicoes do enunciado, ou retorna a posicao do alvo a ser atacado. Por ultimo, se a magia for do tipo "buff" primeiro
 * verificamos se existem lacaios na mesa, caso eles existam, chamamos o metodo "lacaioBuffar()"(o metodo em questao busca o lacaio da mesa com maior ataque - que
 * consideramos a melhor opcao de buff disponivel). Depois de lidar com as cartas de magia, eh hora de lidar com as cartas lacaios, nesse momento nao adotaremos nenhum
 * criterio especial para baixa-los (novamente iremos usar uma variavel que controla o numero de lacaios na mesa). Uma vez baixados magias e lacaios, eh hora de atacar.
 * Diferente do modo "agressivo", dessa vez havera trocas entre os lacaios, o que faz com o que o comportamento seja mais delicado. A primeira verificacao feita eh se o
 * lacaio adversario ira morrer (caso ele nao morra, atacamos diretamente o heroi o inimigo), caso ele morra, novas verificacoes devem ser feitas. Caso o dano que o meu
 * lacaio ira sofrer for menor que a vida dele, realizamos o ataque. Se nao, verificamos se uma das condicoes eh satisfeita para realizar o ataque: 1 - o custo de mana
 * do lacaio oponente for maior; 2 - a vida do meu lacaio for menor que a do oponente. Caso essas condicoes nao sejam atendidas, atacamos diretamente o heroi do inimigo.
 * A ultima atitude tomada pelo comportamento eh a de usar o poder do heroi(poderHeroico) no heroi inimigo, caso ele ainda possua mana para tal.
 *
 * Comportamento "Curva de Mana": O comportamento "Curva de Mana" tem como objetivo ser um meio termo entre os comportamentos "agressivo" e "controle". Ele tem como objetivo
 * usar as cartas da mao de maneira a gastar toda a mana disponivel. A primeira atitude do comportamento eh organizar as cartas da mao de modo que as que possuem maior custo
 * de mana sejam as primeiras. Assim quando iteramos sobre as cartas para baixar os lacaios (segunda atitude do comportamento) as de maior mana serao as primeiras a serem
 * baixadas (novamente usamos a variavel para garantir o numero de lacaios na mesa). Apos baixados os lacaios, eh hora de lidar com as cartas do tipo magia. Se a magia for
 * do tipo "alvo", buscamos essas duas condicoes: 1 - o lacaio alvo ira morrer; 2 - o custo de mana do lacaio eh maior que o custo da minha carta magia; caso uma delas nao
 * seja atentida, atacamos o heroi oponente. Se a magia for do tipo "area", so a usaremos se todos os lacaios oponentes da mesa forem morrerem.
 * Agora se a magia for do tipo "buff", primeiro verificamos se existem lacaios na mesa, caso eles existam, chamamos o metodo "lacaioBuffar()"(o metodo em questao busca
 * o lacaio da mesa com maior ataque - que consideramos a melhor opcao de buff disponivel). Baixados lacaios e magias, eh hora de iterar sobre os meus lacaios para atacar
 * o oponente (e seus herois).A primeira verificacao feita eh se o lacaio adversario ira morrer (caso ele nao morra, atacamos diretamente o heroi o inimigo), caso ele
 * morra, novas verificacoes devem ser feitas. Caso o dano que o meu lacaio ira sofrer for menor que a vida dele, realizamos o ataque. Se nao, verificamos se uma das
 * condicoes eh satisfeita para realizar o ataque: 1 - o custo de mana do lacaio oponente for maior; 2 - a vida do meu lacaio for menor que a do oponente.
 * Caso essas condicoes nao sejam atendidas, atacamos diretamente o heroi do inimigo. A ultima atitude tomada pelo comportamento eh a de usar o poder do heroi(poderHeroico)
 * no heroi inimigo, caso ele ainda possua mana para tal.
 *
 * Explicados os comportamentos, vamos falar um pouco sobre como escolheremos cada um dos comportamentos. Se a vida do jogador adversario tiver proxima ao fim (menor ou
 * igual a dez), escolhemos o modo "agressivo", para acabar de vez com o mesmo. Se o oponente tiver com mais lacaios do que eu na mesa (pelo menos tres a mais), usamos a
 * estrategia de "controle", a fim de tentar igualar o jogo. Agora, se nenhuma das duas situacoes for abrangida, e ainda estivermos no comeco do jogo (ate a setima rodada)
 * usamos a estrategia de "curva de mana", para gastar melhor a mana e ganhar vantagem sobre o adversario. Em outras situacoes usamos a estrategia de "controle", visando
 * controlar o jogo.
 *
 * @see java.lang.Object
 * @author Luiz Eduardo Targa Chaves Cartolano, RA: 183012
 */
public class JogadorRA183012 extends Jogador {
    private ArrayList<CartaLacaio> lacaios;
    private ArrayList<CartaLacaio> lacaiosOponente;
    // definindo essas variaveis como de classe informacoes que serao usadas para definir que jogada chamar a cada momento
    private static int minhaMana;
    private static int vidaOponente;


    /**
     * O método construtor do JogadorRA183012.
     *
     * @param maoInicial Contém a mão inicial do jogador. Deve conter o número de cartas correto dependendo se esta classe Jogador que está sendo construída é o primeiro ou o segundo jogador da partida.
     * @param primeiro   Informa se esta classe Jogador que está sendo construída é o primeiro jogador a iniciar nesta jogada (true) ou se é o segundo jogador (false).
     * @return            um objeto JogadorRA183012
     */
    public JogadorRA183012(ArrayList<Carta> maoInicial, boolean primeiro){
        primeiroJogador = primeiro;

        mao = maoInicial;
        lacaios = new ArrayList<CartaLacaio>();
        lacaiosOponente = new ArrayList<CartaLacaio>();

        // Mensagens de depuração:
        // System.out.println("*Classe JogadorRAxxxxxx* Sou o " + (primeiro?"primeiro":"segundo") + " jogador (classe: JogadorAleatorio)");
        // System.out.println("Mao inicial:");
        // for(int i = 0; i < mao.size(); i++)
        // System.out.println("ID " + mao.get(i).getID() + ": " + mao.get(i));
    }

    /**
     * Um método que processa o turno de cada jogador. Este método deve retornar as jogadas do Jogador decididas para o turno atual (ArrayList de Jogada).
     *
     * @param mesa   O "estado do jogo" imediatamente antes do início do turno corrente. Este objeto de mesa contém todas as informações 'públicas' do jogo (lacaios vivos e suas vidas, vida dos heróis, etc).
     * @param cartaComprada   A carta que o Jogador recebeu neste turno (comprada do Baralho). Obs: pode ser null se o Baralho estiver vazio ou o Jogador possuir mais de 10 cartas na mão.
     * @param jogadasOponente   Um ArrayList de Jogada que foram os movimentos utilizados pelo oponente no último turno, em ordem.
     * @return            um ArrayList com as Jogadas decididas
     */
    public ArrayList<Jogada> processarTurno (Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente){
        if(cartaComprada != null)
            mao.add(cartaComprada);
        if(primeiroJogador){
            minhaMana = mesa.getManaJog1();
            vidaOponente = mesa.getVidaHeroi2();
            lacaios = mesa.getLacaiosJog1();
            lacaiosOponente = mesa.getLacaiosJog2();
            //System.out.println("--------------------------------- Começo de turno pro jogador1");
        }
        else {
            minhaMana = mesa.getManaJog2();
            vidaOponente = mesa.getVidaHeroi1();
            lacaios = mesa.getLacaiosJog2();
            lacaiosOponente = mesa.getLacaiosJog1();
            //System.out.println("--------------------------------- Começo de turno pro jogador2");
        }

        if(vidaOponente <= 10) {
            return agressivo();
        } else if ((lacaiosOponente.size() - lacaios.size() > 3)) {
            return controle();
        } else if (minhaMana <= 5) {
            return curvaDeMana();
        } else {
            return controle();
        }
    }

    /**
     * O método processa os danos de ataque produzidos no lacaio inimigo
     *
     * @param atacante   Contem a carta lacaio responsavel por atacar o heroi inimigo
     * @param atacado    Contem a carta lacaio que foi atacada
     * @param idAtacado  Contem o ID da carta lacaio que foi atacada
     *
     * @return           dados de vidaAtual do lacaio atacado sao atualizados
     */
    private void processaDanosDeAtaque (CartaLacaio atacante, CartaLacaio atacado, int idAtacado) {
        // aqui setamos a vida do lacaio atacado como sendo a vida atual dele subtraido do ataque do lacaio que o atacou
        atacado.setVidaAtual(atacado.getVidaAtual() - atacante.getAtaque());
        int marcador = 0;
        if(atacado.getVidaAtual() <= 0) {
            // aqui fazemos um for para descobrir a posicao do lacaio atacado (e que morreu) no ArrayList de lacaios oponentes a fim de podermos remove-lo
            for (int i = 0; i < lacaiosOponente.size();i++) {
                if (lacaiosOponente.get(i).getID() == idAtacado)
                    marcador = i;
            }
            lacaiosOponente.remove(marcador);
        }
    }

    /**
     * O método processa os danos de magia produzidos no lacaio inimigo
     *
     * @param usada      Contem a Carta Magia utilazada
     * @param idAlvo     Contem o id da CartaAlvo do outro usuário
     *
     * @return           dados de vidaAtual dos lacaios atacados sao atualizados
     */
    private void processaDanosDeMagia (CartaMagia usada, int idAlvo) {
        // se o idAlvo for maior que 0, significa que a carta magia usada foi de alvo
        if (usada.getMagiaTipo() == TipoMagia.ALVO) {
            int i;
            // sabendo que a carta usada possui um alvo, nesse for buscamos sua posicao no vetor de lacaios oponentes
            for (i = 0; i < lacaiosOponente.size(); i++) {
                if (lacaiosOponente.get(i).getID() == idAlvo) {
                    break;
                }
            }
            // aqui temos uma verificacao da posicao encontrada a fim de garantir que tudo correu bem
            if (i >= lacaiosOponente.size() || lacaiosOponente.get(i).getID() != idAlvo) {
                System.out.println("DEU RUIM");
            } else {
                // se tudo saiu dentro do esperado (temos um iD valido), iremos atualizar a vida do lacaio atacado
                lacaiosOponente.get(i).setVidaAtual(lacaiosOponente.get(i).getVidaAtual() - usada.getMagiaDano());
            }
            // caso o lacaio atacado tenha morrido, iremos remove-lo do vetor de lacaios do oponente
            if (lacaiosOponente.get(i).getVidaAtual() <= 0) {
                lacaiosOponente.remove(i);
            }
        } else {
            // caso a magia não seja de alvo, ela sera de magia, entao precisamos atualizar a vida de todos os lacaios oponentes
            for (int i = 0; i < lacaiosOponente.size(); i++) {
                // aqui atualizamos as vidas dos lacaios que sofreram danos (todos no caso)
                lacaiosOponente.get(i).setVidaAtual(lacaiosOponente.get(i).getVidaAtual() - usada.getMagiaDano());
                // caso os lacaios tenham morrido com o dano sofrido iremos remove-los do vetor de lacaios do oponente (e atualizar o contador do for)
                if (lacaiosOponente.get(i).getVidaAtual() <= 0) {
                    lacaiosOponente.remove(i);
                    i--;
                }
            }
        }
    }

    /**
     * O método acha o melhor lacaio que eu possuo para podermos "buffalo"
     *
     * @return           um inteiro com a posicao no ArrayList do melhor lacaio
     */
    private int lacaioBuffar () {
        int primeiro = 0;

        // assumiremos que o melhor lacaio a ser buffado eh o que tiver maior ataque, por isso buscaremos tal situacao no for que segue
        for(int i = 0; i < lacaios.size(); i++) {
            /* caso o ataque do lacaio do momento (no qual se encontra o contador), for maior que o ataque do lacaio base (primeiro lacaio do vetor ou ultimo marcado
            como sendo o melhor), atualizamos o marcador que indica o melhor lacaio */
            if(lacaios.get(i).getAtaque() > lacaios.get(primeiro).getAtaque()) {
                primeiro = i;
            }
        }
        return primeiro;
    }

    /**
     * O método pior lacaio encontra o lacaio com menor vida do oponente
     *
     * @return           um inteiro com a posicao do lacaio com menor vida do oponente
     */
    private int piorLacaioOponente () {
        int primeiro = 0;
        // trataremos o lacaio com menor vida como sendo o pior lacaio do jogador adversario
        for(int i = 0; i < lacaiosOponente.size(); i++) {
            /* caso a vida do lacaio do momento (no qual se encontra o contador), for menor que a vida do lacaio base (primeiro lacaio do vetor ou ultimo marcado
            como sendo o pior), atualizamos o marcador que indica o pior lacaio */
            if(lacaiosOponente.get(i).getVidaAtual() < lacaiosOponente.get(primeiro).getVidaAtual()) {
                primeiro = i;
            }
        }
        return primeiro;
    }

    /**
     * O método lacaioMagiaAlvo escolhe o melhor alvo para minha carta de magia do tipo alvo
     *
     * @param minhaMagia a carta magia do tipo alvo que ira atacar
     * @return           um inteiro com a posicao do lacaio a ser atacado
     */
    private int lacaioMagiaAlvo (CartaMagia minhaMagia) {
        int escolhido = -1;
        for (int i = 0; i < lacaiosOponente.size(); i++) {
            /* No comportamento de controle eh recomendado que as magias de alvo devem ser usadas apenas quando a diferenca entre o dano causado pela magia
             e a vida do lacaio atual seja menor que um(1), quando encontramos atualizamos o valor da variavel escolhido e saimos do loop */
            if (lacaiosOponente.get(i).getVidaAtual() - minhaMagia.getMagiaDano() <= 1) {
                escolhido = i;
                break;
            }
        }
        // caso nao encontremos um alvo adqueado retornamos "-1", caso tenhamos encontrado, retornamos o valor certo
        return escolhido;
    }

    /**
     * O método magiaArea decide se podemos usar a carta de Magia de area de acordo com as condicoes de curvaDeMana
     *
     * @param minhaMagia a carta magia do tipo alvo que ira atacar
     * @return           um boolean indicando se podemos ou nao usar a carta magia de area
     */
    private boolean magiaArea(CartaMagia minhaMagia) {
        /* no comportamento "Curva de Mana" eh recomendado o uso de cartas magias apenas quando ela "matam" o lacaio adversario,
         nesse for verificamos se a magia de area consegue matar todos os lacaios adversarios na mesa */
        for(int i = 0; i < lacaiosOponente.size(); i++) {
            if(minhaMagia.getMagiaDano() < lacaiosOponente.get(i).getVidaAtual()) {
                // caso algum dos lacaios do adversario continue vivo, retornamos falso
                return false;
            }
        }
        // caso todos morram, retornamos true
        return true;
    }

    /**
     * O método "agressivo" implementa o comportamento agressivo do meu jogador
     *
     * @return           um ArrayList com as jogadas que serao executadas pelo meu jogador no modo agressivo
     */
    private ArrayList<Jogada> agressivo() {
        // ArrayList que sera retornado com as jogadas do jogador no modo agressivo
        ArrayList<Jogada> minhasJogadas = new ArrayList<>();
        // aqui eh declarada uma variavel que ira ajudar a controlar o numero de lacaios na mesa, a fim de garantir que nao sejam baixados mais que 7 deles
        int meusLacaios = lacaios.size();

        // aqui ordenamos a mao do jogador para que os lacaios de maior ataque fiquem nas primeiras posicoes do ArrayList
        Collections.sort(mao, new Comparator<Carta>() {
            @Override
            public int compare (Carta card2, Carta card1) {
                if(card1 instanceof CartaMagia && card2 instanceof CartaMagia) {
                    // Ordena como magia
                    return 0;
                } else if(card1 instanceof CartaMagia && card2 instanceof CartaLacaio) {
                    return 1;
                } else if(card1 instanceof CartaLacaio && card2 instanceof CartaMagia){
                    return -1;
                } else {
                    // Ordena como Lacaio
                    CartaLacaio carta1 = (CartaLacaio)card1;
                    CartaLacaio carta2 = (CartaLacaio)card2;
                    if(carta1.getAtaque() < carta2.getAtaque())
                        return -1;
                    else
                        return 1;
                }
            }
        });

        // O laço abaixo cria jogas de baixar lacaios da mão para a mesa se houver mana disponível (como os lacaios de maior ataque estao no inicio serao os primeiros a serem baixados)
        for (int i = 0; i < mao.size(); i++) {
            // verificando se nao ha mais lacaios que o permitido
            if(meusLacaios < 7) {
                Carta card = mao.get(i);
                if (card instanceof CartaLacaio && card.getMana() <= minhaMana) {
                    Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
                    minhasJogadas.add(lac);
                    // aqui adicionamos o lacaio ao contador, garantindo que nao teremos mais que sete deles
                    meusLacaios++;
                    minhaMana -= card.getMana();
                    mao.remove(i);
                    i--;
                }
            } else {
                // se ja tiver "estourado" o numero de lacaios saimos do for
                break;
            }
        }

        // nesse laco iremos buscar as cartas magia do tipo ALVO e, caso elas existam e o jogador ainda possuir mana, usaremos no heroi inimigo
        for (int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
                CartaMagia cartaMagia = (CartaMagia)card;
                // se a carta de magia for do tipo "alvo", a usaremos no heroi inimigo
                if (cartaMagia.getMagiaTipo() == TipoMagia.ALVO) {
                    Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, null);
                    minhasJogadas.add(mag);
                    minhaMana -= card.getMana();
                    mao.remove(i);
                    i--;
                } else if (cartaMagia.getMagiaTipo() == TipoMagia.AREA) {
                    // como o enunciado pede para nao priorizarmos as magias do tipo "area", so usaremos quando tiver um numero elevado de lacaios oponentes na mesa
                    if(lacaiosOponente.size() >= 6) {
                        Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, null);
                        minhasJogadas.add(mag);
                        processaDanosDeMagia(cartaMagia,-1);
                        minhaMana -= card.getMana();
                        mao.remove(i);
                        i--;
                    }
                } else {
                    // caso seja uma magia de buff a usamos no melhor lacaio
                    if (lacaios.size() > 0) {
                        Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, lacaios.get(lacaioBuffar()));
                        minhasJogadas.add(mag);
                        minhaMana -= card.getMana();
                        mao.remove(i);
                        i--;
                    } else {
                        continue;
                    }
                }
            }
        }

        // usaremos os lacaios da mesa para atacar sempre o heroi inimigo
        for (int i = 0; i < lacaios.size(); i++) {
            CartaLacaio meuLacaio = lacaios.get(i);
            Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, null);
            minhasJogadas.add(atk);
        }

        // depois de baixados lacaios e magias, e uma vez usado os ataques dos lacaios, se ainda houver mana usamos o poder do heroi
        if(minhaMana >=2) {
            Jogada poderHeroico = new Jogada(TipoJogada.PODER,null,null);
            minhasJogadas.add(poderHeroico);
            minhaMana -= 2;
        }

        return minhasJogadas;
    }

    /**
     * O método "controle" implementa o comportamento controlador do meu jogador
     *
     * @return           um ArrayList com as jogadas que serao executadas pelo meu jogador no modo de controle
     */
    private ArrayList<Jogada> controle() {
        // ArrayList que sera retornado com as jogadas do jogador
        ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();
        // aqui eh declarada uma variavel que ira ajudar a controlar o numero de lacaios na mesa, a fim de garantir que nao sejam baixados mais que 7 deles
        int meusLacaios = lacaios.size();

        // aqui temos o laco que desce as cartas de magia a mesa
        for (int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if ((card instanceof CartaMagia) && (card.getMana() <= minhaMana)) {
                CartaMagia cartaMagia = (CartaMagia)card;
                if (cartaMagia.getMagiaTipo() == TipoMagia.AREA) {
                    // caso a carta magia seja do tipo "area", so usaremos caso o oponente tenha mais que 2 lacaios baixados
                    if (lacaiosOponente.size() >= 2) {
                        Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, null);
                        minhasJogadas.add(mag);
                        // passamos o idAlvo como sendo "-1" pois nao temos um id em especifico
                        processaDanosDeMagia(cartaMagia, -1);
                        minhaMana -= card.getMana();
                        mao.remove(i);
                        i--;
                    } else {
                        continue;
                    }
                } else if (cartaMagia.getMagiaTipo() == TipoMagia.ALVO) {
                    // aqui chamamos a funcao que escolhe o lacaio alvo de acordo com as convencoes do enunciado
                    int escolhido = lacaioMagiaAlvo(cartaMagia);
                    // o valor de escolhido sera diferente de "-1" quando acharmos um alvo que se encaixe com o descrito no enunciado
                    if (escolhido != -1) {
                        Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, lacaiosOponente.get(escolhido));
                        minhasJogadas.add(mag);
                        processaDanosDeMagia(cartaMagia, lacaiosOponente.get(escolhido).getID());
                        minhaMana -= card.getMana();
                        mao.remove(i);
                        i--;
                    } else {
                        // caso nao seja encontrado um lacaio dentro dos padroes atacamos o heroi adversario
                        Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, null);
                        minhasJogadas.add(mag);
                        minhaMana -= card.getMana();
                        mao.remove(i);
                        i--;
                    }
                } else {
                    // caso a magia seja de buff e um lacaio a ser bufado, escolheremos o melhor lacaoi para tal funcao
                    if (lacaios.size() > 0) {
                        Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, lacaios.get(lacaioBuffar()));
                        minhasJogadas.add(mag);
                        minhaMana -= card.getMana();
                        mao.remove(i);
                        i--;
                    } else {
                        continue;
                    }
                }
            }
        }

        // O laço abaixo cria jogas de baixar lacaios da mão para a mesa se houver mana disponível
        for (int i = 0; i < mao.size(); i++) {
            // verificando se existe espaco para baixar mais lacaios
            if(meusLacaios < 7) {
                Carta card = mao.get(i);
                if (card instanceof CartaLacaio && card.getMana() <= minhaMana) {
                    Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
                    minhasJogadas.add(lac);
                    // aqui adicionamos o lacaio ao contador, garantindo que nao teremos mais que sete deles
                    meusLacaios++;
                    minhaMana -= card.getMana();
                    mao.remove(i);
                    i--;
                }
            } else {
                // se ja tiver "estourado" o numero de lacaios saimos do for
                break;
            }
        }

        // aqui percorremos os lacaios para atacar o inimigo
        for (int i = 0; i < lacaios.size(); i++) {
            CartaLacaio meuLacaio = lacaios.get(i);
            // verificando se ha inimigos para serem atacados
            if (lacaiosOponente.size() > 0) {
                // aqui escolhemos o pior lacaio do oponente
                CartaLacaio lacaioOponente = lacaiosOponente.get(piorLacaioOponente());
                // caso o lacaio escolhido morra com o ataque que ele ira sofrer faremos algumas verificacoes
                if (lacaioOponente.getVidaAtual() <= meuLacaio.getAtaque()) {
                    // caso o dano que meu lacaio vai sofrer for menor que a vida dele (ele continue vivo), atacamos
                    if (lacaioOponente.getAtaque() < meuLacaio.getVidaAtual()) {
                        Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, lacaioOponente);
                        minhasJogadas.add(atk);
                        // aqui chamamos o metodo que processa danos causados no lacaio do oponente
                        processaDanosDeAtaque(meuLacaio, lacaioOponente, lacaioOponente.getID());
                    } else if (lacaioOponente.getAtaque() >= meuLacaio.getVidaAtual()) {
                        // caso o meu lacaio va morrer ao atacar o lacaio adversario, fazemos mais algumas verificacoes
                        if(lacaioOponente.getMana() > meuLacaio.getMana()) {
                            // se o custo de mana do lacaio oponente for maior que o do meu lacaio atacamos
                            Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, lacaioOponente);
                            minhasJogadas.add(atk);
                            // aqui chamamos o metodo que processa danos causados no lacaio do oponente
                            processaDanosDeAtaque(meuLacaio, lacaioOponente, lacaioOponente.getID());
                        } else if(lacaioOponente.getVidaAtual() > meuLacaio.getVidaAtual()) {
                            // se a vida do lacaio adversario for maior que a do meu lacaio, atacamos
                            Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, lacaioOponente);
                            minhasJogadas.add(atk);
                            // aqui chamamos o metodo que processa danos causados no lacaio do oponente
                            processaDanosDeAtaque(meuLacaio, lacaioOponente, lacaioOponente.getID());
                        }
                    } else {
                        // se nao atacamos nenhum lacaio ate o momento, entao atacamos o heroi
                        Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, null);
                        minhasJogadas.add(atk);
                    }
                } else {
                    // se nao atacamos nenhum lacaio ate o momento, entao atacamos o heroi
                    Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, null);
                    minhasJogadas.add(atk);
                }
            } else {
                // se nao tiver lacaio oponente atacamos o heroi inimigo
                Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, null);
                minhasJogadas.add(atk);
            }
        }

        // depois de baixados lacaios e magias, e uma vez usado os ataques dos lacaios, se ainda houver mana usamos o poder do heroi
        if(minhaMana >=2) {
            Jogada poderHeroico = new Jogada(TipoJogada.PODER,null,null);
            minhasJogadas.add(poderHeroico);
            minhaMana -= 2;
        }

        return minhasJogadas;
    }

    /**
     * O método "curvaDeMana" implementa o comportamento que busca gastar melhor a mana do meu jogador
     *
     * @return           um ArrayList com as jogadas que serao executadas pelo meu jogador no modo de curvaDeMana
     */
    private ArrayList<Jogada> curvaDeMana() {
        // ArrayList que sera retornado com as jogadas do jogador
        ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();
        // aqui eh declarada uma variavel que ira ajudar a controlar o numero de lacaios na mesa, a fim de garantir que nao sejam baixados mais que 7 deles
        int meusLacaios = lacaios.size();

        // aqui ordenamos a mao do jogador para que os lacaios de maior custo fiquem no inicio
        Collections.sort(mao, new Comparator<Carta>() {
            @Override
            public int compare (Carta card2, Carta card1) {
                if(card1 instanceof CartaMagia && card2 instanceof CartaMagia) {
                    // Ordena como magia
                    return 0;
                } else if(card1 instanceof CartaMagia && card2 instanceof CartaLacaio) {
                    return 1;
                } else if(card1 instanceof CartaLacaio && card2 instanceof CartaMagia){
                    return -1;
                } else {
                    // Ordena como Lacaio
                    CartaLacaio carta1 = (CartaLacaio)card1;
                    CartaLacaio carta2 = (CartaLacaio)card2;
                    if(carta1.getMana() < carta2.getMana())
                        return -1;
                    else
                        return 1;
                }
            }
        });

        // O laço abaixo cria jogas de baixar lacaios da mão para a mesa se houver mana disponível (como os lacaios de maior custo estao no inicio serao baixados primeiro)
            for (int i = 0; i < mao.size(); i++) {
                // verificando se existe espaco para baixar mais lacaios
                if(meusLacaios < 7) {
                    Carta card = mao.get(i);
                    if (card instanceof CartaLacaio && card.getMana() <= minhaMana) {
                        Jogada lac = new Jogada(TipoJogada.LACAIO, card, null);
                        minhasJogadas.add(lac);
                        // aqui adicionamos o lacaio ao contador, garantindo que nao teremos mais que sete deles
                        meusLacaios++;
                        minhaMana -= card.getMana();
                        mao.remove(i);
                        i--;
                    }
                } else {
                    // se ja tiver "estourado" o numero de lacaios saimos do for
                    break;
                }
            }


        // aqui temos o laco que desce as cartas de magia a mesa
        for (int i = 0; i < mao.size(); i++) {
            Carta card = mao.get(i);
            if ((card instanceof CartaMagia) && (card.getMana() <= minhaMana)) {
                CartaMagia cartaMagia = (CartaMagia)card;
                if (cartaMagia.getMagiaTipo() == TipoMagia.AREA) {
                    // caso a carta magia seja do tipo area, chamamos a funcao que verifica a aplicabilidade do seu uso (a funcao "magiaArea()" cuida dos requisitos do enunciado
                    if (magiaArea(cartaMagia)) {
                        // se os requisitos forem atentidos, fazemos a jogada
                        Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, null);
                        minhasJogadas.add(mag);
                        processaDanosDeMagia(cartaMagia,-1);
                        minhaMana -= card.getMana();
                        mao.remove(i);
                        i--;
                    }
                } else if (cartaMagia.getMagiaTipo() == TipoMagia.ALVO) {
                    // se a carta for do tipo alvo, chamamos a funcao que escolhe o alvo
                    int escolhido = lacaioMagiaAlvo(cartaMagia);
                    if (escolhido != -1) {
                        CartaLacaio lacaioOponente = lacaiosOponente.get(escolhido);
                        if (lacaioOponente.getVidaAtual() < cartaMagia.getMagiaDano()) {
                            // caso encontremos um alvo que ira morrer verificamos se a mana dele eh menor que a mana da minha carta de magia
                            if (lacaioOponente.getMana() > cartaMagia.getMana()) {
                                Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, lacaioOponente);
                                minhasJogadas.add(mag);
                                processaDanosDeMagia(cartaMagia,lacaioOponente.getID());
                                minhaMana -= card.getMana();
                                mao.remove(i);
                                i--;
                            }
                        }
                    } else {
                        // caso nao encontremos um alvo apropriado, atacamos o heroi inimigo
                        Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, null);
                        minhasJogadas.add(mag);
                        minhaMana -= card.getMana();
                        mao.remove(i);
                        i--;
                    }
                } else {
                    // caso a magia seja de buff e exista um lacaio a ser buffado, escolhemos o melhor lacaio a receber o buff
                    if (lacaios.size() > 0) {
                        Jogada mag = new Jogada(TipoJogada.MAGIA, cartaMagia, lacaios.get(lacaioBuffar()));
                        minhasJogadas.add(mag);
                        minhaMana -= card.getMana();
                        mao.remove(i);
                        i--;
                    }
                }
            }
        }

        // aqui percorremos os lacaios para atacar o inimigo
        for (int i = 0; i < lacaios.size(); i++) {
            CartaLacaio meuLacaio = lacaios.get(i);
            // verificando se ha inimigos para serem atacados
            if (lacaiosOponente.size() > 0) {
                // aqui escolhemos o pior lacaio do oponente
                CartaLacaio lacaioOponente = lacaiosOponente.get(piorLacaioOponente());
                // caso o lacaio escolhido morra com o ataque que ele ira sofrer faremos algumas verificacoes
                if (lacaioOponente.getVidaAtual() <= meuLacaio.getAtaque()) {
                    // caso o dano que meu lacaio vai sofrer for menor que a vida dele (ele continue vivo), atacamos
                    if (lacaioOponente.getAtaque() < meuLacaio.getVidaAtual()) {
                        Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, lacaioOponente);
                        minhasJogadas.add(atk);
                        // aqui chamamos o metodo que processa danos causados no lacaio do oponente
                        processaDanosDeAtaque(meuLacaio, lacaioOponente, lacaioOponente.getID());
                    } else if (lacaioOponente.getAtaque() >= meuLacaio.getVidaAtual()) {
                        // caso o meu lacaio va morrer ao atacar o lacaio adversario, fazemos mais algumas verificacoes
                        if(lacaioOponente.getMana() > meuLacaio.getMana()) {
                            // se o custo de mana do lacaio oponente for maior que o do meu lacaio atacamos
                            Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, lacaioOponente);
                            minhasJogadas.add(atk);
                            // aqui chamamos o metodo que processa danos causados no lacaio do oponente
                            processaDanosDeAtaque(meuLacaio, lacaioOponente, lacaioOponente.getID());
                        } else if(lacaioOponente.getVidaAtual() > meuLacaio.getVidaAtual()) {
                            // se a vida do lacaio adversario for maior que a do meu lacaio, atacamos
                            Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, lacaioOponente);
                            minhasJogadas.add(atk);
                            // aqui chamamos o metodo que processa danos causados no lacaio do oponente
                            processaDanosDeAtaque(meuLacaio, lacaioOponente, lacaioOponente.getID());
                        }
                    } else {
                        // se nao atacamos nenhum lacaio ate o momento, entao atacamos o heroi
                        Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, null);
                        minhasJogadas.add(atk);
                    }
                } else {
                    // se nao atacamos nenhum lacaio ate o momento, entao atacamos o heroi
                    Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, null);
                    minhasJogadas.add(atk);
                }
            } else {
                // se nao tiver lacaio oponente atacamos o heroi inimigo
                Jogada atk = new Jogada(TipoJogada.ATAQUE, meuLacaio, null);
                minhasJogadas.add(atk);
            }
        }

        // depois de baixados lacaios e magias, e uma vez usado os ataques dos lacaios, se ainda houver mana usamos o poder do heroi
        if(minhaMana >=2) {
            Jogada poderHeroico = new Jogada(TipoJogada.PODER,null,null);
            minhasJogadas.add(poderHeroico);
            minhaMana -= 2;
        }

        return minhasJogadas;
    }

}
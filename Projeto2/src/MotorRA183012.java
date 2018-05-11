/**
 * A classe MotorRA183012 eh a classe responsavel por executar as jogadas realizadas pelo Jogador,
 * atualizando os componentes da mesa. Alem de ser o responsavel por informar o jogador que ganhou o jogo de LaMa
 *
 *
 * @see java.lang.Object
 * @see Carta
 * @see Jogada
 * @see LamaException
 * @see Motor
 *
 *
 * @author Luiz Eduardo Cartolano - RA183012 - MC302
 */

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;

public class MotorRA183012 extends Motor {

    // 1 == turno do jogador1, 2 == turno do jogador2.
    private int jogador;
    private int turno;
    private int nCartasHeroi1;
    private int nCartasHeroi2;
    private Mesa mesa;

    // "Apontadores" - Assim podemos programar genericamente os métodos para funcionar com ambos os jogadores
    private ArrayList<Carta> mao;
    private ArrayList<Carta> lacaios;
    private ArrayList<Carta> lacaiosOponente;

    // "Memória" - Para marcar ações que só podem ser realizadas uma vez por turno.
    private boolean poderHeroicoUsado;
    private HashSet<Integer> lacaiosAtacaramID;
    private HashSet<Integer> lacaiosAtaqueDuploID;
    private HashSet<Integer> lacaiosProvocarID;
    public MotorRA183012(Baralho deck1, Baralho deck2, ArrayList<Carta> mao1,
                         ArrayList<Carta> mao2, Jogador jogador1, Jogador jogador2,
                         int verbose, int tempoLimitado, PrintWriter saidaArquivo, EnumSet<Funcionalidade> funcionalidadesAtivas) {

        super(deck1, deck2, mao1, mao2, jogador1, jogador2, verbose, tempoLimitado, saidaArquivo, funcionalidadesAtivas);
        imprimir("========================");
        imprimir("*** Classe "+this.getClass().getName()+" inicializada.");
        imprimir("Funcionalidade ativas no Motor:");
        imprimir("INVESTIDA: "+(this.funcionalidadesAtivas.contains(Funcionalidade.INVESTIDA)?"SIM":"NAO"));
        imprimir("ATAQUE_DUPLO: "+(this.funcionalidadesAtivas.contains(Funcionalidade.ATAQUE_DUPLO)?"SIM":"NAO"));
        imprimir("PROVOCAR: "+(this.funcionalidadesAtivas.contains(Funcionalidade.PROVOCAR)?"SIM":"NAO"));
        imprimir("========================");
    }

    /**
    * Metodo executar partida, responsavel por realizar por invocar os metodos que realizarao as jogadas, atualizar
    * as informacoes da mesa e informar o vencedor
    *
    *
    * @return int que informa o vencedor do jogo de LaMa
    */
    @Override
    public int executarPartida() throws LamaException {
        vidaHeroi1 = vidaHeroi2 = 30;
        nCartasHeroi1 = cartasIniJogador1;
        nCartasHeroi2 = cartasIniJogador2;
        ArrayList<Jogada> movimentos = new ArrayList<Jogada>();
        int noCardDmgCounter1 = 1;
        int noCardDmgCounter2 = 1;
        turno = 1;

        for(int k = 0; k < 60; k++) {
            imprimir("\n=== TURNO "+turno+" ===\n");
            // Atualiza mesa (com cópia profunda)
            @SuppressWarnings("unchecked")
            ArrayList<CartaLacaio> lacaios1clone = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa1);
            @SuppressWarnings("unchecked")
            ArrayList<CartaLacaio> lacaios2clone = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa2);
            mesa = new Mesa(lacaios1clone, lacaios2clone, vidaHeroi1, vidaHeroi2, nCartasHeroi1+1, nCartasHeroi2, (turno>10)?10:turno, (turno>10)?10:((turno==1)?2:turno));
            manaJogador1 = ((turno>10)?10:turno);
            manaJogador2 = (turno>10)?10:((turno==1)?2:turno);

            // Apontadores para jogador1
            mao = maoJogador1;
            lacaios = lacaiosMesa1;
            lacaiosOponente = lacaiosMesa2;
            jogador = 1;

            // Processa o turno 1 do Jogador1
            imprimir("\n----------------------- Começo de turno para Jogador 1:");
            long startTime, endTime, totalTime;

            // Cópia profunda de jogadas realizadas.
            @SuppressWarnings("unchecked")
            ArrayList<Jogada> cloneMovimentos1 = (ArrayList<Jogada>) UnoptimizedDeepCopy.copy(movimentos);

            startTime = System.nanoTime();
            if( baralho1.getCartas().size() > 0) {
                if(nCartasHeroi1 >= maxCartasMao) {
                    movimentos = jogador1.processarTurno(mesa, null, cloneMovimentos1);
                    comprarCarta(); // carta descartada
                }
                else
                    movimentos = jogador1.processarTurno(mesa, comprarCarta(), cloneMovimentos1);
            }
            else {
                imprimir("Fadiga: O Herói 1 recebeu "+noCardDmgCounter1+" de dano por falta de cartas no baralho. (Vida restante: "+(vidaHeroi1-noCardDmgCounter1)+").");
                vidaHeroi1 -= noCardDmgCounter1++;
                if( vidaHeroi1 <= 0){
                    // Jogador 2 venceu
                    imprimir("O jogador 2 venceu porque o jogador 1 recebeu um dano mortal por falta de cartas ! (Dano : " +(noCardDmgCounter1-1)+ ", Vida Herói 1: "+vidaHeroi1+")");
                    return 2;
                }
                movimentos = jogador1.processarTurno(mesa, null, cloneMovimentos1);
            }
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            if((tempoLimitado!=0) && (totalTime > 3e8)) { // 300ms
                // Jogador 2 venceu, Jogador 1 excedeu limite de tempo
                return 2;
            }
            else
                imprimir("Tempo usado em processarTurno(): "+totalTime/1e6+"ms");

            // Começa a processar jogadas do Jogador 1
            this.poderHeroicoUsado = false;
            this.lacaiosAtacaramID = new HashSet<>();
            this.lacaiosAtaqueDuploID = new HashSet<>();

            for(int i = 0; i < movimentos.size(); i++){
                processarJogada (movimentos.get(i));
            }

            if(vidaHeroi2 <= 0){
                // Jogador 1 venceu
                return 1;
            }

            // Atualiza mesa (com cópia profunda)
            @SuppressWarnings("unchecked")
            ArrayList<CartaLacaio> lacaios1clone2 = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa1);
            @SuppressWarnings("unchecked")
            ArrayList<CartaLacaio> lacaios2clone2 = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa2);
            mesa = new Mesa(lacaios1clone2, lacaios2clone2, vidaHeroi1, vidaHeroi2, nCartasHeroi1, nCartasHeroi2+1, (turno>10)?10:turno, (turno>10)?10:((turno==1)?2:turno));

            // Apontadores para jogador2
            mao = maoJogador2;
            lacaios = lacaiosMesa2;
            lacaiosOponente = lacaiosMesa1;
            jogador = 2;

            // Processa o turno 1 do Jogador2
            imprimir("\n\n----------------------- Começo de turno para Jogador 2:");

            // Cópia profunda de jogadas realizadas.
            @SuppressWarnings("unchecked")
            ArrayList<Jogada> cloneMovimentos2 = (ArrayList<Jogada>) UnoptimizedDeepCopy.copy(movimentos);

            startTime = System.nanoTime();


            if( baralho2.getCartas().size() > 0){
                if(nCartasHeroi2 >= maxCartasMao){
                    movimentos = jogador2.processarTurno(mesa, null, cloneMovimentos2);
                    comprarCarta(); // carta descartada
                }
                else {
                    movimentos = jogador2.processarTurno(mesa, comprarCarta(), cloneMovimentos2);
                }
            }
            else{
                imprimir("Fadiga: O Herói 2 recebeu "+noCardDmgCounter2+" de dano por falta de cartas no baralho. (Vida restante: "+(vidaHeroi2-noCardDmgCounter2)+").");
                vidaHeroi2 -= noCardDmgCounter2++;
                if( vidaHeroi2 <= 0){
                    // Vitoria do jogador 1
                    imprimir("O jogador 1 venceu porque o jogador 2 recebeu um dano mortal por falta de cartas ! (Dano : " +(noCardDmgCounter2-1)+ ", Vida Herói 2: "+vidaHeroi2 +")");
                    return 1;
                }
                movimentos = jogador2.processarTurno(mesa, null, cloneMovimentos2);
            }

            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            if( tempoLimitado!=0 && totalTime > 3e8){ // 300ms
                // Limite de tempo pelo jogador 2. Vitoria do jogador 1.
                return 1;
            }
            else
                imprimir("Tempo usado em processarTurno(): "+totalTime/1e6+"ms");

            this.poderHeroicoUsado = false;
            this.lacaiosAtacaramID = new HashSet<>();
            this.lacaiosAtaqueDuploID = new HashSet<>();

            for(int i = 0; i < movimentos.size(); i++){
                processarJogada (movimentos.get(i));
            }
            if(vidaHeroi1 <= 0){
                // Vitoria do jogador 2
                return 2;
            }
            turno = turno + 1;
        }

        // Nunca vai chegar aqui dependendo do número de rodadas
        imprimir("Erro: A partida não pode ser determinada em mais de 60 rounds. Provavel BUG.");
        throw new LamaException(-1, null, "Erro desconhecido. Mais de 60 turnos sem termino do jogo.", 0);
    }

    /**
     * Metodo processarJogada, responsavel por receber a Jogada que o Jogador deseja executar, e realiza-la com os
     * atributos e objetos da mesa, alem de informar o Jogador caso ele esteja fazendo movimentos errados
     *
     * @param umaJogada jogada do Jogador que deve ser processada
     *
     * @return o metodo nao possui retorno
     */
    protected void processarJogada(Jogada umaJogada) throws LamaException {

        Carta cartaJogada = umaJogada.getCartaJogada();
        Carta cartaAlvo = umaJogada.getCartaAlvo();
        this.lacaiosProvocarID = lacaioProvocar();
        int cartaAlvoID;
        int jogadorOponente = (jogador == 1) ? 2 : 1;

        switch (umaJogada.getTipo()) {

            case ATAQUE:

                if (umaJogada.getCartaJogada() instanceof CartaLacaio) {
                    for (Carta carta : lacaios) {
                        if (carta.getID() == umaJogada.getCartaJogada().getID()) {
                            cartaJogada = carta;
                            break;
                        }
                    }
                } else if (umaJogada.getCartaJogada() == null) {
                    cartaJogada = null;
                } else {
                    String errorMessage = "A carta jogada com o intuito de realizar o ataque nao eh um lacaio";
                    imprimir(errorMessage);
                    throw new LamaException(5,umaJogada,errorMessage,jogadorOponente);
                }

                if (umaJogada.getCartaAlvo() instanceof CartaLacaio) {
                    for (Carta carta : lacaiosOponente) {
                        if (carta.getID() == umaJogada.getCartaAlvo().getID()) {
                            cartaAlvo = carta;
                            break;
                        }
                    }
                } else if (umaJogada.getCartaAlvo() == null) {
                    cartaAlvo = null;
                } else {
                    String errorMessage = "O lacaio de id "+cartaJogada.getID()+" tentou atacar uma carta que nao eh lacaio e nem heroi";
                    imprimir(errorMessage);
                    throw new LamaException(8,umaJogada,errorMessage,jogadorOponente);
                }

                cartaAlvoID = (cartaAlvo != null) ? cartaAlvo.getID():-10;
                if ((lacaiosProvocarID.size() > 0) && (!(lacaiosProvocarID.contains(cartaAlvoID))) && (funcionalidadesAtivas.contains(Funcionalidade.PROVOCAR))){
                    String errorMessage;
                    if (cartaAlvo != null) {
                        errorMessage = "O alvo de ID " + cartaAlvo.getID() +
                                " nao eh o alvo com o efeito PROVOCAR ativado, este possui ID igual a ";
                        for (int i : lacaiosProvocarID)
                            errorMessage += i + " ";
                    } else {
                        errorMessage = "O alvo eh o Heroi e nao o alvo com o efeito PROVOCAR ativado, este possui ID igual a ";
                        for (int i : lacaiosProvocarID)
                            errorMessage += i;
                    }
                    imprimir(errorMessage);
                    throw new LamaException(13,umaJogada,errorMessage,jogadorOponente);
                }
                else {
                    if (cartaJogada instanceof CartaLacaio) {
                        if (lacaios.contains(cartaJogada)) {
                            if ((((CartaLacaio) cartaJogada).getTurno() != turno) || (((CartaLacaio) cartaJogada).getEfeito().equals(TipoEfeito.INVESTIDA)) && (funcionalidadesAtivas.contains(Funcionalidade.INVESTIDA))) {
                                if (cartaAlvo instanceof CartaLacaio) {
                                    if (!lacaiosAtacaramID.contains(cartaJogada.getID())) {
                                        if ((((CartaLacaio) cartaJogada).getEfeito().equals(TipoEfeito.ATAQUE_DUPLO)) && (funcionalidadesAtivas.contains(Funcionalidade.ATAQUE_DUPLO))) {
                                            if (!(lacaiosAtaqueDuploID.contains(cartaJogada.getID()))) {
                                                lacaiosAtaqueDuploID.add(cartaJogada.getID());
                                            } else if (lacaiosAtaqueDuploID.contains(cartaJogada.getID())) {
                                                lacaiosAtacaramID.add(cartaJogada.getID());
                                            }
                                        } else {
                                            lacaiosAtacaramID.add(cartaJogada.getID());
                                        }
                                        if (lacaiosOponente.contains(cartaAlvo)) {
                                            // decrementamos a vida do lacaio que sofreu o ataque
                                            ((CartaLacaio) cartaAlvo).setVidaAtual(((CartaLacaio) cartaAlvo).getVidaAtual() - ((CartaLacaio) cartaJogada).getAtaque());
                                            imprimir("JOGADA: O lacaio id = " + cartaAlvo.getID() +
                                                    " sofreu um ataque de = " + ((CartaLacaio) cartaJogada).getAtaque() +
                                                    " e tem uma vida restante de = " + ((CartaLacaio) cartaAlvo).getVidaAtual());

                                            // decrementamos a vida do lacaio que realizou o ataque ("dano espelho")
                                            ((CartaLacaio) cartaJogada).setVidaAtual(((CartaLacaio) cartaJogada).getVidaAtual() - ((CartaLacaio) cartaAlvo).getAtaque());
                                            imprimir("JOGADA: O lacaio de id = " + cartaJogada.getID() +
                                                    " recebeu um dano espelho de " + ((CartaLacaio) cartaAlvo).getAtaque() +
                                                    " e tem vida restante igual a " + ((CartaLacaio) cartaJogada).getVidaAtual());

                                            // verificamos se o lacaio que sofreu o ataque ainda esta vivo
                                            if (((CartaLacaio) cartaJogada).getVidaAtual() <= 0) {
                                                lacaios.remove(cartaJogada);
                                                imprimir("JOGADA: O lacaio de id = " + cartaJogada.getID() + " morreu");
                                            }

                                            //verficamos se o lacaio que realizou o ataque ainda esta vivo
                                            if (((CartaLacaio) cartaAlvo).getVidaAtual() <= 0) {
                                                lacaiosOponente.remove(cartaAlvo);
                                                imprimir("JOGADA: O lacaio de id = " + cartaAlvo.getID() + " morreu");
                                            }
                                        } else {
                                            String errorMessage = "O lacaio de id " + cartaJogada.getID() +
                                                    " tentou atacar o lacaio de id " + cartaAlvo.getID() +
                                                    " e esse nao esta contido no vetor de lacaiosOponentes no turno " + turno;
                                            imprimir(errorMessage);
                                            throw new LamaException(8, umaJogada, errorMessage, jogadorOponente);
                                        }
                                    } else {
                                        String errorMessage = "O lacaio de id " + cartaJogada.getID() + " ja atacou no turno";
                                        imprimir(errorMessage);
                                        throw new LamaException(7, umaJogada, errorMessage, jogadorOponente);
                                    }
                                } else if (cartaAlvo == null) {
                                    if (!(lacaiosAtacaramID.contains(cartaJogada.getID()))) {
                                        if ((((CartaLacaio) cartaJogada).getEfeito().equals(TipoEfeito.ATAQUE_DUPLO)) && (funcionalidadesAtivas.contains(Funcionalidade.ATAQUE_DUPLO))) {
                                            if (!(lacaiosAtaqueDuploID.contains(cartaJogada.getID()))) {
                                                lacaiosAtaqueDuploID.add(cartaJogada.getID());
                                            } else if (lacaiosAtaqueDuploID.contains(cartaJogada.getID())) {
                                                lacaiosAtacaramID.add(cartaJogada.getID());
                                            }
                                        } else {
                                            lacaiosAtacaramID.add(cartaJogada.getID());
                                        }
                                        if (jogador == 1) {
                                            vidaHeroi2 -= ((CartaLacaio) cartaJogada).getAtaque();
                                            imprimir("JOGADA: O heroi " + jogadorOponente +
                                                    " sofreu um ataque de = " + ((CartaLacaio) cartaJogada).getAtaque() +
                                                    " e tem vida restante de = " + vidaHeroi2);
                                        } else {
                                            vidaHeroi1 -= ((CartaLacaio) cartaJogada).getAtaque();
                                            imprimir("JOGADA: O heroi " + jogadorOponente +
                                                    " sofreu um ataque de = " + ((CartaLacaio) cartaJogada).getAtaque() +
                                                    " e tem vida restante de = " + vidaHeroi1);
                                        }
                                    } else {
                                        String errorMessage = "O lacaio de id " + cartaJogada.getID() + " ja atacou no turno";
                                        imprimir(errorMessage);
                                        throw new LamaException(7, umaJogada, errorMessage, jogadorOponente);
                                    }
                                } else {
                                    String errorMessage = "O lacaio de id " + cartaJogada.getID() + " tentou atacar uma carta que nao eh lacaio e nem heroi";
                                    imprimir(errorMessage);
                                    throw new LamaException(8, umaJogada, errorMessage, jogadorOponente);
                                }
                            } else {
                                String errorMessage = "O lacaio de id " + cartaJogada.getID() + " desceu nesse turno e o efeito de INVESTIDA nao esta funcionando";
                                imprimir(errorMessage);
                                throw new LamaException(6, umaJogada, errorMessage, jogadorOponente);
                            }
                        } else {
                            String errorMessage = "Erro: Tentou-se usar a carta_id = " + cartaJogada.getID() + ", porém esta carta não existe na mao. IDs de cartas na mao: ";
                            for (Carta card : mao) {
                                errorMessage += card.getID() + ", ";
                            }
                            imprimir(errorMessage);
                            throw new LamaException(5, umaJogada, errorMessage, jogadorOponente);
                        }
                    } else {
                        String errorMessage = "A carta jogada com o intuito de realizar o ataque nao eh um lacaio";
                        imprimir(errorMessage);
                        throw new LamaException(5, umaJogada, errorMessage, jogadorOponente);
                    }
                }
                break;

            case LACAIO:

                if (umaJogada.getCartaJogada() instanceof CartaLacaio) {
                    for (Carta carta : mao) {
                        if (carta.getID() == umaJogada.getCartaJogada().getID()) {
                            cartaJogada = carta;
                            break;
                        }
                    }
                } else {
                    String errorMessage = "O lacaio de id "+cartaJogada.getID()+" de nome "+cartaJogada.getNome()+" nao pode ser baixado a mesa pois nao eh do tipo lacaio";
                    imprimir(errorMessage);
                    throw new LamaException(3,umaJogada,errorMessage,jogadorOponente);
                }
                cartaAlvo = umaJogada.getCartaAlvo();

                if (lacaios.size() < 7) {
                    if (cartaJogada instanceof CartaLacaio) {
                        if (mao.contains(cartaJogada)) {
                            if (jogador == 1) {
                                if (cartaJogada.getMana() <= manaJogador1) {
                                    imprimir("JOGADA: O lacaio de id " + cartaJogada.getID() + " foi baixado a mesa");
                                    lacaios.add(cartaJogada);
                                    ((CartaLacaio) cartaJogada).setTurno(turno);
                                    manaJogador1 -= cartaJogada.getMana();
                                } else {
                                    String errorMessage = "A jogada eh do tipo " + umaJogada.getTipo()+
                                            " custa mana igual a " + cartaJogada.getMana() +
                                            " e o jogador1 possui mana igual a " + manaJogador1;
                                    imprimir(errorMessage);
                                    throw new LamaException(2,umaJogada,errorMessage,jogadorOponente);
                                }
                            } else {
                                if (cartaJogada.getMana() <= manaJogador2) {
                                    imprimir("JOGADA: O lacaio de id " + cartaJogada.getID() + " foi baixado a mesa");
                                    lacaios.add(cartaJogada);
                                    ((CartaLacaio) cartaJogada).setTurno(turno);
                                    manaJogador2 -= cartaJogada.getMana();
                                } else {
                                    String errorMessage = "A jogada eh do tipo " + umaJogada.getTipo()+
                                            " custa mana igual a " + cartaJogada.getMana() +
                                            " e o jogador2 possui mana igual a " + manaJogador2;
                                    imprimir(errorMessage);
                                    throw new LamaException(2,umaJogada,errorMessage,jogadorOponente);
                                }
                            }
                        } else {
                            String errorMessage = "Erro: Tentou-se usar a carta_id = "+cartaJogada.getID()+", porém esta carta não existe na mao. IDs de cartas na mao: ";
                            for(Carta card : mao){
                                errorMessage += card.getID() + ", ";
                            }
                            imprimir(errorMessage);
                            throw new LamaException(1, umaJogada, errorMessage, jogadorOponente);
                        }
                    } else {
                        String errorMessage = "O lacaio de id "+cartaJogada.getID()+" de nome "+cartaJogada.getNome()+" nao pode ser baixado a mesa pois nao eh do tipo lacaio";
                        imprimir(errorMessage);
                        throw new LamaException(3,umaJogada,errorMessage,jogadorOponente);
                    }
                } else {
                    String errorMessage = "O lacaio de id "+cartaJogada.getID()+" de nome "+cartaJogada.getNome()+" nao pode ser baixado a mesa pois ja existem 7 ou mais lacaios nela";
                    imprimir(errorMessage);
                    throw new LamaException(4,umaJogada,errorMessage,jogadorOponente);
                }
                break;


            case MAGIA:

                if (umaJogada.getCartaJogada() instanceof CartaMagia) {
                    for (Carta carta : mao) {
                        if (carta.getID() == umaJogada.getCartaJogada().getID()) {
                            cartaJogada = carta;
                            break;
                        }
                    }
                } else if (umaJogada.getCartaJogada() == null) {
                    cartaJogada = null;
                } else {
                    String errorMessage = "A carta de ID "+cartaJogada.getID()+" e de nome "+cartaJogada.getNome()+" foi usada incorretamente";
                    imprimir(errorMessage);
                    throw new LamaException(9,umaJogada,errorMessage,jogadorOponente);
                }

                if (((CartaMagia)umaJogada.getCartaJogada()).getMagiaTipo() == TipoMagia.BUFF) {
                    if (umaJogada.getCartaAlvo() == null) {
                        String errorMessage = "A magia de id = "+cartaJogada.getID()+" tentou buffar o lacaoi de id "+cartaAlvo.getID()+" que nao pertence ao vetor de lacaios";
                        imprimir(errorMessage);
                        throw new LamaException(10,umaJogada,errorMessage,jogadorOponente);
                    } else {
                        for (Carta carta : lacaios) {
                            if (carta.getID() == umaJogada.getCartaAlvo().getID()) {
                                cartaAlvo = carta;
                                break;
                            }
                        }
                    }
                } else {
                    if (umaJogada.getCartaAlvo() instanceof CartaLacaio) {
                        for (Carta carta : lacaiosOponente) {
                            if (carta.getID() == umaJogada.getCartaAlvo().getID()) {
                                cartaAlvo = carta;
                                break;
                            }
                        }
                    } else if (umaJogada.getCartaAlvo() == null) {
                        cartaAlvo = null;
                    } else {
                        String errorMessage = "O lacaio de id "+cartaJogada.getID()+" tentou atacar uma carta que nao eh lacaio e nem heroi";
                        imprimir(errorMessage);
                        throw new LamaException(8,umaJogada,errorMessage,jogadorOponente);
                    }
                }

                if (cartaJogada instanceof CartaMagia) {
                    if (mao.contains(cartaJogada)) {
                        if (jogador == 1) {
                            if (cartaJogada.getMana() <= manaJogador1) {
                                // decrementa a mana do jogador1
                                manaJogador1 = manaJogador1 - cartaJogada.getMana();

                                if (((CartaMagia) cartaJogada).getMagiaTipo() == TipoMagia.ALVO) {
                                    if (cartaAlvo instanceof CartaLacaio) {
                                        if (lacaiosOponente.contains(cartaAlvo)) {
                                            // decrementa a vida do lacaio atacado
                                            ((CartaLacaio) cartaAlvo).setVidaAtual(((CartaLacaio) cartaAlvo).getVidaAtual() - ((CartaMagia) cartaJogada).getMagiaDano());
                                            imprimir("JOGADA: O lacaio de id = " + cartaAlvo.getID() +
                                                    " sofreu um dano de MAGIA_ALVO de = " + ((CartaMagia) cartaJogada).getMagiaDano() +
                                                    " e tem vida restante = " + ((CartaLacaio) cartaAlvo).getVidaAtual());

                                            // verifica se o lacaio atacado morreu
                                            if (((CartaLacaio) cartaAlvo).getVidaAtual() <= 0) {
                                                imprimir("JOGADA: Lacaio alvo de id = "+cartaAlvo.getID()+" morreu");
                                                lacaiosOponente.remove(cartaAlvo);
                                            }
                                        } else {
                                            String errorMessage = "A magia de id = "+cartaJogada.getID()+" foi usada no alvo ID = "+cartaAlvo.getID()+" que nao pertence ao vetor de lacaiosOponente";
                                            imprimir(errorMessage);
                                            throw new LamaException(10,umaJogada,errorMessage,jogadorOponente);
                                        }
                                    } else if (cartaAlvo == null) {
                                        vidaHeroi2 -= ((CartaMagia) cartaJogada).getMagiaDano();
                                        imprimir("JOGADA: O heroi 2 sofreu um dano de "+((CartaMagia) cartaJogada).getMagiaDano() +
                                                " e tem vida restante igual a " + vidaHeroi2);
                                    } else {
                                        imprimir("A carta alvo eh do tipo MAGIA");
                                    }
                                } else if (((CartaMagia) cartaJogada).getMagiaTipo() == TipoMagia.BUFF) {
                                    if (cartaAlvo instanceof CartaLacaio) {
                                        if (lacaios.contains(cartaAlvo)) {
                                            ((CartaLacaio) cartaAlvo).setVidaAtual(((CartaLacaio) cartaAlvo).getVidaAtual() + ((CartaMagia) cartaJogada).getMagiaDano());
                                            ((CartaLacaio) cartaAlvo).setVidaMaxima(((CartaLacaio) cartaAlvo).getVidaMaxima() + ((CartaMagia) cartaJogada).getMagiaDano());
                                            ((CartaLacaio) cartaAlvo).setAtaque(((CartaLacaio) cartaAlvo).getAtaque()+((CartaMagia) cartaJogada).getMagiaDano());
                                            imprimir("JOGADA: O lacaio de id = " + cartaAlvo.getID() +
                                                    " recebeu um BUFF de " + ((CartaMagia) cartaJogada).getMagiaDano() +
                                                    " e esta com vidaAtual igual a " + ((CartaLacaio) cartaAlvo).getVidaAtual() +
                                                    " e ataque igual a " + ((CartaLacaio) cartaAlvo).getAtaque());
                                        } else {
                                            String errorMessage = "A magia de id = "+cartaJogada.getID()+" tentou buffar o lacaoi de id "+cartaAlvo.getID()+" que nao pertence ao vetor de lacaios";
                                            imprimir(errorMessage);
                                            throw new LamaException(10,umaJogada,errorMessage,jogadorOponente);
                                        }
                                    } else {
                                        imprimir("A carta alvo nao eh um lacaio");
                                    }
                                } else {
                                    // else para MAGIA_AREA
                                    for (Carta carta : lacaiosOponente) {
                                        // decrementamos a vida de todos os lacaios do valor de dano magia
                                        ((CartaLacaio) carta).setVidaAtual(((CartaLacaio) carta).getVidaAtual() - ((CartaMagia) cartaJogada).getMagiaDano());
                                        imprimir("JOGADA: O lacaio de id = " + carta.getID() +
                                                " sofreu um dano de MAGIA_AREA de " + ((CartaMagia) cartaJogada).getMagiaDano() +
                                                " e tem vida restante igual a " + ((CartaLacaio) carta).getVidaAtual());
                                    }
                                    for (int i = 0; i < lacaiosOponente.size(); i++) {
                                        // verificamos se o lacaio ainda esta vivo
                                        Carta carta = lacaiosOponente.get(i);

                                        if (((CartaLacaio) carta).getVidaAtual() <= 0) {
                                            imprimir("JOGADA: O lacaio de id " + carta.getID() + " morreu por dano de uma carta magia");
                                            lacaiosOponente.remove(carta);
                                            i--;
                                        }

                                    }
                                }
                            } else {
                                String errorMessage = "A jogada eh do tipo " + ((CartaMagia) cartaJogada).getMagiaTipo()+
                                        " necessita de mana igual a " + cartaJogada.getMana() +
                                        " e o jogador1 possui mana igual a " + manaJogador1;
                                imprimir(errorMessage);
                                throw new LamaException(2,umaJogada,errorMessage,jogadorOponente);
                            }
                        } else {
                            if (cartaJogada.getMana() <= manaJogador2) {
                                // decrementa a mana do jogador2
                                manaJogador2 -= cartaJogada.getMana();

                                if (((CartaMagia) cartaJogada).getMagiaTipo() == TipoMagia.ALVO) {
                                    if (cartaAlvo instanceof CartaLacaio) {
                                        if (lacaiosOponente.contains(cartaAlvo)) {
                                            // decrementa a vida do lacaio atacado
                                            ((CartaLacaio) cartaAlvo).setVidaAtual(((CartaLacaio) cartaAlvo).getVidaAtual() - ((CartaMagia) cartaJogada).getMagiaDano());
                                            imprimir("JOGADA: O lacaio de id = " + cartaAlvo.getID() +
                                                    " sofreu um dano de MAGIA_ALVO de = " + ((CartaMagia) cartaJogada).getMagiaDano() +
                                                    " e tem vida restante = " + ((CartaLacaio) cartaAlvo).getVidaAtual());

                                            // verifica se o lacaio atacado morreu
                                            if (((CartaLacaio) cartaAlvo).getVidaAtual() <= 0) {
                                                imprimir("JOGADA: Lacaio alvo de id = "+cartaAlvo.getID()+" morreu");
                                                lacaiosOponente.remove(cartaAlvo);
                                            }
                                        } else {
                                            String errorMessage = "A magia de id = "+cartaJogada.getID()+" foi usada no alvo ID = "+cartaAlvo.getID()+" que nao pertence ao vetor de lacaiosOponente";
                                            imprimir(errorMessage);
                                            throw new LamaException(10,umaJogada,errorMessage,jogadorOponente);
                                        }
                                    } else if (cartaAlvo == null) {
                                        vidaHeroi1 -= ((CartaMagia) cartaJogada).getMagiaDano();
                                        imprimir("JOGADA: O heroi 2 sofreu um dano de "+((CartaMagia) cartaJogada).getMagiaDano() +
                                                " e tem vida restante igual a " + vidaHeroi1);
                                    } else {
                                        imprimir("A carta alvo eh do tipo MAGIA");
                                    }
                                } else if (((CartaMagia) cartaJogada).getMagiaTipo() == TipoMagia.BUFF) {
                                    if (cartaAlvo instanceof CartaLacaio) {
                                        if (lacaios.contains(cartaAlvo)) {
                                            ((CartaLacaio) cartaAlvo).setVidaAtual(((CartaLacaio) cartaAlvo).getVidaAtual() + ((CartaMagia) cartaJogada).getMagiaDano());
                                            ((CartaLacaio) cartaAlvo).setVidaMaxima(((CartaLacaio) cartaAlvo).getVidaMaxima() + ((CartaMagia) cartaJogada).getMagiaDano());
                                            ((CartaLacaio) cartaAlvo).setAtaque(((CartaLacaio) cartaAlvo).getAtaque()+((CartaMagia) cartaJogada).getMagiaDano());
                                            imprimir("JOGADA: O lacaio de id = " + cartaAlvo.getID() +
                                                    " recebeu um BUFF de " + ((CartaMagia) cartaJogada).getMagiaDano() +
                                                    " e esta com vidaAtual igual a " + ((CartaLacaio) cartaAlvo).getVidaAtual() +
                                                    " e ataque igual a " + ((CartaLacaio) cartaAlvo).getAtaque());
                                        } else {
                                            String errorMessage = "A magia de id = "+cartaJogada.getID()+" tentou buffar o lacaoi de id "+cartaAlvo.getID();
                                            imprimir(errorMessage);
                                            throw new LamaException(10,umaJogada,errorMessage,jogadorOponente);
                                        }
                                    } else {
                                        imprimir("A carta alvo nao eh um lacaio");
                                    }
                                } else {
                                    // else para MAGIA_AREA
                                    for (Carta carta : lacaiosOponente) {
                                        // decrementamos a vida de todos os lacaios do valor de dano magia
                                        ((CartaLacaio) carta).setVidaAtual(((CartaLacaio) carta).getVidaAtual() - ((CartaMagia) cartaJogada).getMagiaDano());
                                        imprimir("JOGADA: O lacaio de id = " + carta.getID() +
                                                " sofreu um dano de MAGIA_AREA de " + ((CartaMagia) cartaJogada).getMagiaDano() +
                                                " e tem vida restante igual a " + ((CartaLacaio) carta).getVidaAtual());
                                    }
                                    for (int i = 0; i < lacaiosOponente.size(); i++) {
                                        // verificamos se o lacaio ainda esta vivo
                                        Carta carta = lacaiosOponente.get(i);

                                        if (((CartaLacaio) carta).getVidaAtual() <= 0) {
                                            imprimir("JOGADA: O lacaio de id " + carta.getID() + " morreu por dano de uma carta magia");
                                            lacaiosOponente.remove(carta);
                                            i--;
                                        }
                                    }
                                }
                            } else {
                                String errorMessage = "A jogada eh do tipo " + ((CartaMagia) cartaJogada).getMagiaTipo()+
                                        " necessita de mana igual a " + cartaJogada.getMana() +
                                        " e o jogador2 possui mana igual a " + manaJogador2;
                                imprimir(errorMessage);
                                throw new LamaException(2,umaJogada,errorMessage,jogadorOponente);
                            }
                        }

                        // removendo a carta usada da mao do jogador
                        mao.remove(cartaJogada);
                    } else {
                        String errorMessage = "Erro: Tentou-se usar a carta_id="+cartaJogada.getID()+", porém esta carta não existe na mao. IDs de cartas na mao: ";
                        for(Carta card : mao){
                            errorMessage += card.getID() + ", ";
                        }
                        imprimir(errorMessage);
                        throw new LamaException(1,umaJogada,errorMessage,jogadorOponente);
                    }
                } else {
                    String errorMessage = "A carta de ID "+cartaJogada.getID()+" e de nome "+cartaJogada.getNome()+" foi usada incorretamente";
                    imprimir(errorMessage);
                    throw new LamaException(9,umaJogada,errorMessage,jogadorOponente);
                }
                break;

            case PODER:

                cartaJogada = null;

                if (umaJogada.getCartaAlvo() == null) {
                    cartaAlvo = null;
                } else if (umaJogada.getCartaAlvo() instanceof CartaLacaio) {
                    for (Carta carta : lacaiosOponente) {
                        if (carta.getID() == umaJogada.getCartaAlvo().getID()) {
                            cartaAlvo = carta;
                            break;
                        }
                    }
                } else {
                    String errorMessage = "A carta de id "+cartaAlvo.getID()+" nao eh lacaio e nem heroi";
                    imprimir(errorMessage);
                    throw new LamaException(12,umaJogada,errorMessage,jogadorOponente);
                }

                cartaAlvoID = (cartaAlvo != null) ? cartaAlvo.getID():-10;
                if ((lacaiosProvocarID.size() > 0) && !(lacaiosProvocarID.contains(cartaAlvoID)) && (funcionalidadesAtivas.contains(Funcionalidade.PROVOCAR))){
                    String errorMessage;
                    if (cartaAlvo != null) {
                        errorMessage = "O alvo de ID " + cartaAlvo.getID() +
                                " nao eh o alvo com o efeito PROVOCAR ativado, este possui ID igual a ";
                        for (int i : lacaiosProvocarID)
                            errorMessage += " " + i;
                    } else {
                        errorMessage = "O alvo eh o Heroi e nao o alvo com o efeito PROVOCAR ativado, este possui ID igual a ";
                        for (int i : lacaiosProvocarID)
                            errorMessage += i;
                    }
                    imprimir(errorMessage);
                    throw new LamaException(13,umaJogada,errorMessage,jogadorOponente);
                } else {
                    if (cartaJogada == null) {
                        if (poderHeroicoUsado == false) {
                            if (cartaAlvo == null) {
                                if (jogador == 1) {
                                    if (manaJogador1 >= 2) {
                                        vidaHeroi2 -= 1;
                                        imprimir("JOGADA: A vida do heroi2 foi decrementada de 1 unidade e sua vida atual eh de " + vidaHeroi2);
                                        manaJogador1 -= 2;
                                        poderHeroicoUsado = true;
                                    } else {
                                        String errorMessage = "A jogada eh do tipo PODER HEROICO" +
                                                " e necessita de mana igual a " + 2 +
                                                " e o jogador1 possui mana igual a " + manaJogador1;
                                        imprimir(errorMessage);
                                        throw new LamaException(2, umaJogada, errorMessage, jogadorOponente);
                                    }
                                } else {
                                    if (manaJogador2 >= 2) {
                                        vidaHeroi1 -= 1;
                                        imprimir("JOGADA: A vida do heroi1 foi decrementada de 1 unidade e sua vida atual eh de " + vidaHeroi1);
                                        manaJogador2 -= 2;
                                        poderHeroicoUsado = true;
                                    } else {
                                        String errorMessage = "A jogada eh do tipo PODER HEROICO" +
                                                " e necessita de mana igual a " + 2 +
                                                " e o jogador2 possui mana igual a " + manaJogador2;
                                        imprimir(errorMessage);
                                        throw new LamaException(2, umaJogada, errorMessage, jogadorOponente);
                                    }
                                }
                            } else if (cartaAlvo instanceof CartaLacaio) {
                                if (lacaiosOponente.contains(cartaAlvo)) {
                                    if (jogador == 1) {
                                        if (manaJogador1 >= 2) {
                                            // decrementamos a vida do lacaio atacado em uma unidade
                                            ((CartaLacaio) cartaAlvo).setVidaAtual(((CartaLacaio) cartaAlvo).getVidaAtual() - 1);
                                            imprimir("JOGADA: A vida do lacaio de id = " + cartaAlvo.getID() +
                                                    " foi decrementada de uma unidade e sua vida atual eh de " + ((CartaLacaio) cartaAlvo).getVidaAtual());

                                            // decrementamos a vida do heroi que a realizou o ataque do valor espelho do dano
                                            vidaHeroi1 -= ((CartaLacaio) cartaAlvo).getAtaque();
                                            imprimir("JOGADA: A vida do heroi1 foi decrementada do valor de dano espelho e sua vida atual eh de " + vidaHeroi1);

                                            manaJogador1 -= 2;
                                            poderHeroicoUsado = true;

                                            // verficamos se o lacaio atacado ainda esta vivo
                                            if (((CartaLacaio) cartaAlvo).getVidaAtual() <= 0) {
                                                imprimir("JOGADA: O lacaio de id = " + cartaAlvo.getID() + " morreu ao ser atacado pelo heroi1");
                                                lacaiosOponente.remove(cartaAlvo);
                                            }
                                        } else {
                                            String errorMessage = "A jogada eh do tipo PODER HEROICO" +
                                                    " e necessita de mana igual a " + 2 +
                                                    " e o jogador1 possui mana igual a " + manaJogador1;
                                            imprimir(errorMessage);
                                            throw new LamaException(2, umaJogada, errorMessage, jogadorOponente);
                                        }
                                    } else {
                                        if (manaJogador2 >= 2) {
                                            // decrementamos a vida do lacaio atacado em uma unidade
                                            ((CartaLacaio) cartaAlvo).setVidaAtual(((CartaLacaio) cartaAlvo).getVidaAtual() - 1);
                                            imprimir("JOGADA: A vida do lacaio de id = " + cartaAlvo.getID() +
                                                    " foi decrementada de uma unidade e sua vida atual eh de " + ((CartaLacaio) cartaAlvo).getVidaAtual());

                                            // decrementamos a vida do heroi2, que realizou o ataque, do danoEspelho
                                            vidaHeroi2 -= ((CartaLacaio) cartaAlvo).getAtaque();
                                            imprimir("JOGADA: A vida do heroi2 foi decrementada do valor de dano espelho e sua vida atual eh de " + vidaHeroi2);
                                            manaJogador2 -= 2;
                                            poderHeroicoUsado = true;

                                            // verficamos se o lacaio atacado ainda esta vivo
                                            if (((CartaLacaio) cartaAlvo).getVidaAtual() <= 0) {
                                                imprimir("JOGADA: O lacaio de id = " + cartaAlvo.getID() + " morreu ao ser atacado pelo heroi2");
                                                lacaiosOponente.remove(cartaAlvo);
                                            }
                                        } else {
                                            String errorMessage = "A jogada eh do tipo PODER HEROICO" +
                                                    " e necessita de mana igual a " + 2 +
                                                    " e o jogador2 possui mana igual a " + manaJogador2;
                                            imprimir(errorMessage);
                                            throw new LamaException(2, umaJogada, errorMessage, jogadorOponente);
                                        }
                                    }

                                } else {
                                    String errorMessage = "O lacaio alvo de id " + cartaAlvo.getID() + " eh invalido";
                                    imprimir(errorMessage);
                                    throw new LamaException(12, umaJogada, errorMessage, jogadorOponente);
                                }
                            } else {
                                String errorMessage = "A carta de id " + cartaAlvo.getID() + " nao eh lacaio e nem heroi";
                                imprimir(errorMessage);
                                throw new LamaException(12, umaJogada, errorMessage, jogadorOponente);
                            }
                        } else {
                            String errorMessage = "O poder heroico ja foi usado pelo jogador e ele esta tentando atacar o alvo de id " + cartaAlvo.getID();
                            imprimir(errorMessage);
                            throw new LamaException(11, umaJogada, errorMessage, jogadorOponente);
                        }
                    } else {
                        imprimir("Houve uma carta jogada");
                    }
                }
                break;

            default:
                break;
        }

        return;
    }

    /**
     * Metodo comprarCarta, responsavel por comprar uma carta do Baralho e adiciona-la a Mao do Jogador
     *
     * @return o metodo retorna a Carta retirada do Baralho que deve ser adicionada a Mao do Jogador
     */
    private Carta comprarCarta() {
        if(this.jogador == 1) {
            if (baralho1.getCartas().size() <= 0)
                throw new RuntimeException("Erro: Não há mais cartas no baralho para serem compradas.");
            Carta nova = baralho1.comprarCarta();
            mao.add(nova);
            nCartasHeroi1++;
            return (Carta) UnoptimizedDeepCopy.copy(nova);
        }
        else {
            if (baralho2.getCartas().size() <= 0)
                throw new RuntimeException("Erro: Não há mais cartas no baralho para serem compradas.");
            Carta nova = baralho2.comprarCarta();
            mao.add(nova);
            nCartasHeroi2++;
            return (Carta) UnoptimizedDeepCopy.copy(nova);
        }
    }

    /**
     * Metodo lacaioProvocar, responsavel por percorrer o vetor de lacaios do oponente adicionando todos os
     * lacaios que possuam o efeito PROVOCAR em um HashSet<Integer>
     *
     * @return um HashSet<Integer> com os IDs de todos os lacaios do oponente que possuem o efeito de PROVOCAR
     */
    private HashSet<Integer> lacaioProvocar () {
        HashSet<Integer> lacaioProvocaID = new HashSet<>();
        for (Carta carta : lacaiosOponente) {
            if (((CartaLacaio)carta).getEfeito().equals(TipoEfeito.PROVOCAR)) {
                lacaioProvocaID.add(carta.getID());
            }
        }
        return lacaioProvocaID;
    }

}

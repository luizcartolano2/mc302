package lab02;

public class CartaLacaio {
	private int ID;
	private String nome;
	private int ataque;
	private int vidaAtual;
	private int vidaMaxima;
	private int custoMana;
	
	// metodo construtor original, que instancia todos os atributos
	public CartaLacaio(int ID, String nome, int ataque, int vida, int mana) {
		this.ID = ID;
		this.nome = nome;
		this.ataque = ataque;
		this.vidaAtual = vida;
		this.vidaMaxima = vida;
		this.custoMana = mana;
	}
	
	// metodo construtor reduzido, instancia os atributos ID, nome e custo mana
	public CartaLacaio(int ID, String nome, int mana) {
		this.ID = ID;
		this.nome = nome;
		this.custoMana = mana;
	}		
	
	// metodo construtor copia, ira copiar os atributos de um objeto passado por parametro
	public CartaLacaio(CartaLacaio origem ) {
		this.ID = origem.getID();
		this.nome = origem.getNome();
		this.ataque = origem.getAtaque();
		this.vidaAtual = origem.getVidaAtual();
		this.vidaMaxima = origem.getVidaAtual();
		this.custoMana = origem.getCustoMana();
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getVidaAtual() {
		return vidaAtual;
	}

	public void setVidaAtual(int vidaAtual) {
		this.vidaAtual = vidaAtual;
	}

	public int getVidaMaxima() {
		return vidaMaxima;
	}

	public void setVidaMaxima(int vidaMaxima) {
		this.vidaMaxima = vidaMaxima;
	}

	public int getCustoMana() {
		return custoMana;
	}

	public void setCustoMana(int custoMana) {
		this.custoMana = custoMana;
	}
	
	public String toString() {
		String out = getNome() + "(ID = "+getID()+")\n";
		out = out + "Ataque = "+getAtaque()+"\n";
		out = out + "Vida Atual = "+getVidaAtual()+"\n";
		out = out + "Vida Maxima = "+getVidaMaxima()+"\n";
		out = out + "Custo de Mana = "+getCustoMana()+"\n";
		return out;
	}
	
	// implementar os metodos "buffar" :
	void buffar (int a) {
		if (a > 0){
			setAtaque(getAtaque()+a);
			setVidaAtual(getVidaAtual()+a);
			setVidaMaxima(getVidaMaxima()+a);
			alteraNomeFortalecido();
		}
	}
	
	void buffar (int a, int v) {
		if (a > 0)
			setAtaque(getAtaque()+a);
		if (v > 0)
			setVidaAtual(getVidaAtual()+v);
			setVidaMaxima(getVidaMaxima()+v);
		if(a > 0 || v > 0)
			alteraNomeFortalecido();	
	}
	
	void alteraNomeFortalecido() {
		setNome(getNome()+"Buffed");
	}
	
}

package it.unibs.progettoarnaldo.adventuretime;

public class Personaggio {

	private String nome;
	private int vita;
	private int attacco;
	private int difesa;
	private Arma strumento;
	
	public Personaggio(String nome, int vita, int attacco, int difesa, Arma strumento) {
		this.nome = nome;
		this.vita = vita;
		this.attacco = attacco;
		this.difesa = difesa;
		this.strumento = strumento;
	}

	public int getVita() {
		return vita;
	}

	public void setVita(int vita) {
		this.vita = vita;
	}

	public Arma getStrumento() {
		return strumento;
	}

	public void setStrumento(Arma strumento) {
		this.strumento = strumento;
	}

	public String getNome() {
		return nome;
	}

	public int getAttacco() {
		return attacco;
	}

	public int getDifesa() {
		return difesa;
	}
	
	/**
	 * metodo per muovere il personaggio
	 * @param matrice
	 * @param x
	 * @param y
	 * @param xNuovo
	 * @param yNuovo
	 */
	public void muoviPersonaggio(String[][] matrice, int x, int y, int xNuovo, int yNuovo) {
		
		matrice[x][y] = ".";
		matrice[xNuovo][yNuovo] = "O";
	}
	
}

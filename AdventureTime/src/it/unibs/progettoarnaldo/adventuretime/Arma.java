package it.unibs.progettoarnaldo.adventuretime;

import java.util.Random;

public class Arma {

	private static final int DANNO_MASSIMO = 55;
	private static final int DANNO_MINIMO = 35;
	private static final int LUNGHEZZA_MAX = 20;
	
	private String nome;
	private String tipo;
	private int potenza;
	private int difesa;
	
	public Arma(String nome, String tipo, int potenza, int difesa) {
		this.nome = nome;
		this.tipo = tipo;
		this.potenza = potenza;
		this.difesa = difesa;
	}
	
	public void setDifesa(int difesa) {
		this.difesa = difesa;
	}

	public String getTipo() {
		return tipo;
	}

	public int getDifesa() {
		return difesa;
	}

	public String getNome() {
		return nome;
	}

	public int getPotenza() {
		return potenza;
	}
	
	/**
	 * metodo per creare un arma
	 * @return
	 */
	public Arma creaArma() {
		
		Random rand = new Random();
		String lettere = "abcdefghijklmnopqrstuvwxyz";
		String nome = "";
		int potenza = 0;
		//nome casuale
		int lunghezza = rand.nextInt(LUNGHEZZA_MAX);
		for (int i = 0; i < lunghezza; i++) {
	            int random = rand.nextInt(lettere.length());
	            nome += lettere.charAt(random);
	        }
		potenza = rand.nextInt(DANNO_MASSIMO - DANNO_MINIMO +1) + DANNO_MINIMO;
		Arma arma = new Arma(nome, "arma", potenza, 0);
		return arma;
	}
	
	/**
	 * metodo per creare uno scudo
	 * @return
	 */
	public Arma creaScudo() {
		
		Random rand = new Random();
		String lettere = "abcdefghijklmnopqrstuvwxyz";
		String nome = "";
		int lunghezza = rand.nextInt(LUNGHEZZA_MAX);
		for (int i = 0; i < lunghezza; i++) {
	            int random = rand.nextInt(lettere.length());
	            nome += lettere.charAt(random);
	        }
		Arma arma = new Arma(nome, "scudo", 0, 5);
		return arma;
	}

	@Override
	public String toString() {
		return "Arma [nome=" + nome + ", tipo=" + tipo + ", potenza=" + potenza + ", difesa=" + difesa + "]";
	}
	
	
	
	
}

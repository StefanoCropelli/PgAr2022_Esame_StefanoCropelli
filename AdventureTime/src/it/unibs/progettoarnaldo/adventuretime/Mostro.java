package it.unibs.progettoarnaldo.adventuretime;

import java.util.Random;

public class Mostro {

	private static final int VALORE_DIFESA = 5;
	private static final int VALORE_ATTACCO = 5;
	private static final String DIJKSTRA = "dijkstra";
	private static final int VITA_MASSIMA = 25;
	private static final int VITA_MINIMA = 15;
	private String nome;
	private int vita;
	private int attacco;
	private int difesa;
	private Arma arma;
	
	public Mostro(String nome, int vita, int attacco, int difesa, Arma arma) {
		this.nome = nome;
		this.vita = vita;
		this.attacco = attacco;
		this.difesa = difesa;
		this.arma = arma;
	}

	public int getVita() {
		return vita;
	}

	public void setVita(int vita) {
		this.vita = vita;
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

	public Arma getArma() {
		return arma;
	}
	
	/**
	 * metodo per creare i mostri
	 * @return
	 */
	public Mostro creaMostro() {
		
		Random rand = new Random();
		int posizione = 0;
		String nome = "";
		String permutazione = DIJKSTRA;
		int vita = rand.nextInt(VITA_MASSIMA - VITA_MINIMA + 1) + VITA_MINIMA;
		Arma arma = new Arma("", "",0,0);
		arma = arma.creaArma();
		//ciclo per creare i nomi come permutazioni di dijkstra
		for (int i = 0; i < DIJKSTRA.length(); i++)
        {
			posizione = rand.nextInt(permutazione.length());
            nome += permutazione.charAt(posizione);
            permutazione = permutazione.substring(0, posizione) + permutazione.substring(posizione + 1);
        }
		Mostro mostro = new Mostro(nome,vita,VALORE_ATTACCO,VALORE_DIFESA,arma);
		return mostro;
	}
}

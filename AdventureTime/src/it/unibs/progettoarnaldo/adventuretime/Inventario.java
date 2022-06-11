package it.unibs.progettoarnaldo.adventuretime;

import java.util.ArrayList;

public class Inventario {

	private ArrayList<Consumabile> consumabili;
	private ArrayList<Arma> armiEScudi;
	
	public Inventario(ArrayList<Consumabile> consumabili, ArrayList<Arma> armiEScudi) {
		this.consumabili = consumabili;
		this.armiEScudi = armiEScudi;
	}

	public ArrayList<Consumabile> getConsumabili() {
		return consumabili;
	}

	public void setConsumabili(ArrayList<Consumabile> consumabili) {
		this.consumabili = consumabili;
	}

	public ArrayList<Arma> getArmiEScudi() {
		return armiEScudi;
	}

	public void setArmiEScudi(ArrayList<Arma> armiEScudi) {
		this.armiEScudi = armiEScudi;
	}

	@Override
	public String toString() {
		int i;
		String frase = "Consumabili : ";
		for(i = 0; i < armiEScudi.size(); i++) {
			frase += (i+1) + ") " + armiEScudi.get(i).getNome() + " ";
		}
		frase += "Armi e scudi: ";
		for(int j = i; i < consumabili.size(); i++) {
			frase += (i+1) + ") " + consumabili.get(i).getNome() + " ";
		}
		return frase;
	}
	
	
	
}

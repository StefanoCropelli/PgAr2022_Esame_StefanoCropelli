package it.unibs.progettoarnaldo.adventuretime;

import java.util.Random;

public class Turno {

	private static final int VITA_MASSIMA = 20;
	private static final int CURA_MASSIMA = 10;
	private static final int CAPIENZA_MASSIMA = 4;
	private static final int TROVA_OGGETTO = 20;
	private static final int CALCOLO_MODIFICATORE = 200;
	
	Random rand = new Random();

	/**
	 * metodo per controllare non ci sia un muro nella direzione in cui si vuole muoversi
	 * @param matrice
	 * @param x
	 * @param y
	 * @param dimRighe
	 * @param dimColonne
	 * @return
	 */
	public boolean possoMuovere(String[][] matrice, int x, int y, int dimRighe, int dimColonne) {
		
		boolean possoMuovere = false;
		if(x >= 0 && x < dimRighe && y >= 0 && y < dimColonne) {
			if(matrice[x][y] != "#") {
				possoMuovere = true;
			}
		}
		return possoMuovere;
	}
	
	/**
	 * metodo per muovere il personaggio che ritornerà il carattere della casella in cui si è mosso
	 * @param matrice
	 * @param mossa
	 * @param x
	 * @param y
	 * @param dimRighe
	 * @param dimColonne
	 * @param eroe
	 * @return
	 */
	public String muoviPersonaggio(String[][] matrice, String mossa, int x, int y, int dimRighe, int dimColonne, Personaggio eroe) {
		
		boolean possoMuovere = false;
		String precedente = "";
		
		//controllo se posso muovermi
		if(mossa.equals("w") && possoMuovere(matrice, x-1, y, dimRighe, dimColonne) || mossa.equals("a") && possoMuovere(matrice, x, y-1, dimRighe, dimColonne) || mossa.equals("s") && possoMuovere(matrice, x+1, y, dimRighe, dimColonne) || mossa.equals("d") && possoMuovere(matrice, x, y+1, dimRighe, dimColonne)) {
			
			possoMuovere = true;
		}
		if(!possoMuovere) {
			System.out.println("non è possibile proseguire in questa direzione, c'è un muro");
		}
		else {
			//a seconda della direzione mi muovo di conseguenza
			if(mossa.equals("w")) {
				precedente = matrice[x-1][y];
				eroe.muoviPersonaggio(matrice, x, y, x-1, y);
			}
			if(mossa.equals("a")) {
				precedente = matrice[x][y-1];
				eroe.muoviPersonaggio(matrice, x, y, x, y-1);
			}
			if(mossa.equals("s")) {
				precedente = matrice[x+1][y];
				eroe.muoviPersonaggio(matrice, x, y, x+1, y);
			}
			if(mossa.equals("d")) {
				precedente = matrice[x][y+1];
				eroe.muoviPersonaggio(matrice, x, y, x, y+1);
			}
		}
		return precedente;
	}
	
	/**
	 * metodo per lo scontro contro i mostri
	 * @param eroe
	 * @return
	 */
	public boolean scontro(Personaggio eroe) {
		
		int i = 0;
		boolean sconfitta = false;
		boolean sconfittaMostro = false;
		int potenza = 0;
		Mostro mostro = new Mostro("",0,0,0,null);
		//creo un mostro
		mostro = mostro.creaMostro();
		if(eroe.getStrumento() != null) {
			potenza = eroe.getStrumento().getPotenza();
		}
		else {
			potenza = 1;
		}
		do {
			//turno del giocatore, inizierà sempre lui per primo ad attaccare
			if(i % 2 == 0) {
				mostro.setVita(mostro.getVita() - calcolaDanno(potenza, eroe.getAttacco(), mostro.getDifesa()));
			}
			//turno del mostro che fa danno all'eroe
			else {
				//se l'eroe ha uno scudo attivo aumenta la vita
				eroe.setVita((eroe.getVita() + eroe.getStrumento().getDifesa()) - calcolaDanno(mostro.getArma().getPotenza(), mostro.getAttacco(), eroe.getDifesa()));
				//tolgo la difesa allo scudo
				if(calcolaDanno(mostro.getArma().getPotenza(), mostro.getAttacco(), eroe.getDifesa()) >= eroe.getStrumento().getDifesa()) {
					eroe.getStrumento().setDifesa(0);
				}
				else {
					eroe.getStrumento().setDifesa(calcolaDanno(mostro.getArma().getPotenza(), mostro.getAttacco(), eroe.getDifesa()));
				}
			}
			if(mostro.getVita() <= 0) {
				sconfittaMostro = true;
			}
			else if(eroe.getVita() <= 0) {
				sconfitta = true;
			}
			i++;
		}while(!sconfitta && !sconfittaMostro);
		if(sconfittaMostro) {
			System.out.println("il mostro è stato abbattuto");
		}
		else {
			System.out.println("sei stato sconfitto dal mostro");
		}
		
		return sconfitta;
	}
	
	/**
	 * metodo per calcolare il danno effettuato
	 * @param potenza
	 * @param attacco
	 * @param difesa
	 * @return
	 */
	public int calcolaDanno(int potenza, int attacco, int difesa) {
		
		double danno = 0;
		double modificatore = 1;
		int random = rand.nextInt(CALCOLO_MODIFICATORE) + 1;
		double valoriMoltiplicatore = CALCOLO_MODIFICATORE/100*7.5;
		//controllo se il moltiplicatore è aumentato, se la probabilità è del 7.5% controllo che esca un numero tra 1 e 15 tra 200 numeri
		for(int i = 0; i < (int) valoriMoltiplicatore; i++) {
			if(random == i) {
				modificatore = 1.5;
			}
		}
		danno = ((2*potenza*attacco)/(25*difesa) + 2) * modificatore;
		
		return (int) danno;
	}
	
	/**
	 * metodo per raccogliere gli oggetti
	 * @param inventario
	 * @param personaggio
	 */
	public void prendiOggetti(Inventario inventario, Personaggio personaggio) {
		
		int scelta = 0;
		boolean inserito = false;
		//uso numeri randomici tra 0 e 20 per capire che tipo di oggetto uscirà, se è tra 0 e 8 è un arma, tra 9 e 15 uno scudo e tra 16 e 20 una pozione
		int tipoOggetto = rand.nextInt(TROVA_OGGETTO) +1;
		if(tipoOggetto <= 8) {
			Arma arma = new Arma("", "",0,0);
			arma = arma.creaArma();
			System.out.println(inventario.toString());
			//controllo se non ho armi
			if(inventario.getArmiEScudi().isEmpty()) {
				inventario.getArmiEScudi().add(arma);
			}
			else {
				//se ho già armi chiedo se si vuole sostituirle e le sostituisco
				scelta = InputDati.leggiIntero("Vuoi cambiare la tua arma con " + arma.toString() + "? inserire 1 se vuoi cambiare");
				if(scelta == 1) {
					for(int i = 0; i < inventario.getArmiEScudi().size(); i ++) {
						if(inventario.getArmiEScudi().get(i).getTipo().equals("arma")) {
							inventario.getArmiEScudi().remove(i);
							inventario.getArmiEScudi().add(arma);
							inserito = true;
						}
					}
					//se ho sostituito l'oggetto equipaggiato lo rimpiazzo con quello nuovo
					if(personaggio.getStrumento().getTipo().equals("arma")) {
						personaggio.setStrumento(arma);
					}
					if(inserito = false) {
						inventario.getArmiEScudi().add(arma);
					}
				}
			}
			
		}
		//idem per gli scudi
		else if(tipoOggetto > 8 && tipoOggetto <= 15) {
			Arma arma = new Arma("", "",0,0);
			arma = arma.creaScudo();
			
			System.out.println(inventario.toString());
			if(inventario.getArmiEScudi().isEmpty()) {
				inventario.getArmiEScudi().add(arma);
			}
			else {
				scelta = InputDati.leggiIntero("Vuoi cambiare il tuo scudo con " + arma.toString() + "? inserire 1 se vuoi cambiare");
				if(scelta == 1) {
					for(int i = 0; i < inventario.getArmiEScudi().size(); i ++) {
						if(inventario.getArmiEScudi().get(i).getTipo().equals("scudo")) {
							inventario.getArmiEScudi().remove(i);
							inventario.getArmiEScudi().add(arma);
							inserito = true;
						}
					}
					if(personaggio.getStrumento().getTipo().equals("scudo")) {
						personaggio.setStrumento(arma);
					}
					if(inserito = false) {
						inventario.getArmiEScudi().add(arma);
					}
				}
			}
		}
		//aggiungo le pozioni all'inventario se non si è già arrivati al massimo
		else if(tipoOggetto > 15) {
			Consumabile pozione = new Consumabile("pozione");
			if(inventario.getConsumabili().size() == CAPIENZA_MASSIMA) {
				System.out.println("Hai raggiunto il numero massimo di pozioni");
			}
			else {
				inventario.getConsumabili().add(pozione);
			}
		}
		
	}
	
	/**
	 * metodo per aumentare la vita consumando una pozione
	 * @param zaino
	 * @param personaggio
	 */
	public void usaPozione(Inventario zaino, Personaggio personaggio) {
		
		int nuovaVita;
		//controllo che ci siano pozioni
		if(zaino.getConsumabili().isEmpty()) {
			System.out.println("Non ci sono più pozioni da usare");
		}
		else {
			zaino.getConsumabili().remove(zaino.getConsumabili().size()-1);
		}
		//la vita ottenuta non può superare la vita massima (ossia 20)
		nuovaVita = personaggio.getVita() + CURA_MASSIMA;
		if(nuovaVita > VITA_MASSIMA) {
			nuovaVita = VITA_MASSIMA;
		}
		personaggio.setVita(nuovaVita);
	}
	
	/**
	 * metodo per lo scontro finale contro dijkstra
	 * @param eroe
	 */
	public void scontroFinale(Personaggio eroe) {
		
		int i = 0;
		boolean sconfitta = false;
		boolean sconfittaMostro = false;
		int potenza = 0;
		//creo un arma con potenza 70
		Arma arma = new Arma("armaFinale","arma", 70, 0);
		//creo un oggetto mostro con le caratteristiche di djkstra
		Mostro mostro = new Mostro("Dijkstra",40,10,10,arma);
		//controllo chel'eroe usi uno strumento
		if(eroe.getStrumento() != null) {
			potenza = eroe.getStrumento().getPotenza();
		}
		else {
			potenza = 1;
		}
		do {
			//inizia l'eroe come prima
			if(i % 2 == 0) {
				mostro.setVita(mostro.getVita() - calcolaDanno(potenza, eroe.getAttacco(), mostro.getDifesa()));
			}
			else {
				eroe.setVita((eroe.getVita() + eroe.getStrumento().getDifesa()) - calcolaDanno(mostro.getArma().getPotenza(), mostro.getAttacco(), eroe.getDifesa()));
				if(calcolaDanno(mostro.getArma().getPotenza(), mostro.getAttacco(), eroe.getDifesa()) >= eroe.getStrumento().getDifesa()) {
					eroe.getStrumento().setDifesa(0);
				}
				else {
					eroe.getStrumento().setDifesa(calcolaDanno(mostro.getArma().getPotenza(), mostro.getAttacco(), eroe.getDifesa()));
				}
			}
			if(mostro.getVita() <= 0) {
				sconfittaMostro = true;
			}
			else if(eroe.getVita() <= 0) {
				sconfitta = true;
			}
			i++;
		}while(!sconfitta && !sconfittaMostro);
		//stabilisco se dijkstra è stato sconfitto
		if(sconfittaMostro) {
			System.out.println("Dijkstra è stato abbattuto, hai salvato Kibo");
		}
		else {
			System.out.println("sei stato sconfitto da Dijkstra");
		}
		
	}
	
	
	
}

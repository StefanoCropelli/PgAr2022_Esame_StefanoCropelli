package it.unibs.progettoarnaldo.adventuretime;

import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;


public class Lettura {

	/**
	 * metodo per lettura del file
	 * @return
	 */
	public static String[][] letturaXml() {
		
		String [][] matrice = new String[trovaRigheColonne("righe")][trovaRigheColonne("colonne")];
		int colonne = 0;
		int righe = 0;
		int i = -1;
		int j = -1;
				
		XMLInputFactory xmlif = null;
		XMLStreamReader xmlr = null;
		try {
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader("src/it/unibs/progettoarnaldo/adventuretime/livello1.xml", new FileInputStream("src/it/unibs/progettoarnaldo/adventuretime/livello1.xml"));
		} catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del reader:");
			System.out.println(e.getMessage());
		}
		
		try {
			while(xmlr.hasNext()) {
				
				if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
					if(xmlr.getLocalName().equals("row")) {
						i++;
					}
					else if(xmlr.getLocalName().equals("cell")) {
						j++;
					}
					
				}
				else if(xmlr.getEventType() == XMLStreamConstants.END_ELEMENT && xmlr.getLocalName().equals("row")) {
					j = -1;
				}
				else if(xmlr.getEventType() == XMLStreamConstants.CHARACTERS) {
					if (xmlr.getText().trim().length() > 0) {
						//tolgo le implementazioni non ancora effettuate
						if(xmlr.getText().equals("S") || xmlr.getText().equals("T") || xmlr.getText().equals("B") || xmlr.getText().equals("P")) {
							matrice[i][j] = ".";
						}
						else {
							matrice[i][j] = xmlr.getText();
						}
					}
				}
				
				xmlr.next();
			}
		}
		 catch (Exception e)
        {
            System.out.println("Errore: non esiste una nuova riga da leggere\n");
        }
		
		matrice[2][27] = "D";

		return matrice;
	}
	
	/** 
	 * metodo per trovare il numero di colonne o di righe della matrice
	 * @param temp
	 * @return
	 */
	public static int trovaRigheColonne(String temp){
		
		int righeOColonne = 0;
		boolean trovato = false;
		
		XMLInputFactory xmlif = null;
		XMLStreamReader xmlr = null;
		try {
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader("src/it/unibs/progettoarnaldo/adventuretime/livello1.xml", new FileInputStream("src/it/unibs/progettoarnaldo/adventuretime/livello1.xml"));
		} catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del reader:");
			System.out.println(e.getMessage());
		}
		
		try {
			while(xmlr.hasNext() && !trovato) {
				
				if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
					
					if(xmlr.getLocalName().equals("mappa")) {
						if(temp == "colonne") {
							righeOColonne = Integer.parseInt(xmlr.getAttributeValue(0));	
						}
						else if(temp == "righe") {
							righeOColonne = Integer.parseInt(xmlr.getAttributeValue(1));	
						}
					}
				}
				xmlr.next();
			}
		}
		 catch (Exception e)
        {
            System.out.println("Errore: non esiste una nuova riga da leggere\n");
        }
		
		return righeOColonne;
	}
	
	
}

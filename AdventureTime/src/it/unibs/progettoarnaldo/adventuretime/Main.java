package it.unibs.progettoarnaldo.adventuretime;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		int dimRighe = Lettura.trovaRigheColonne("righe");
		int dimColonne = Lettura.trovaRigheColonne("colonne");
		int Xpersonaggio = 0;
		int Ypersonaggio = 0;
		boolean fine = false;
		String [][] matrice = new String[dimRighe][dimColonne];
		String mossa = "";
		String precedente = "";
		Personaggio personaggio = new Personaggio("Astar", 20, 5, 5, null);
		Turno turno = new Turno();
		ArrayList<Arma> armiPossedute = new ArrayList<Arma>(); 
		ArrayList<Consumabile> consumabiliPosseduti = new ArrayList<Consumabile>(); 
		Inventario zaino = new Inventario(consumabiliPosseduti, armiPossedute);
		
		matrice = Lettura.letturaXml();
		
		do {
			//stampa della matrice
			for(int i = 0; i < dimRighe; i++) {
				for(int j = 0; j < dimColonne; j++) {
					System.out.print(matrice[i][j] + " ");
					if(matrice[i][j].equals("O")) {
						Xpersonaggio = i;
						Ypersonaggio = j;
					}
				}
				System.out.println();
			}
			//menu per scegliere cosa fare
			System.out.println("Come vuole proseguire?\n");
	        mossa = InputDati.leggiStringaNonVuota("Premere WASD per muoversi\n"
	                + "Premere E per aprire chest\n"
	                + "Premere U per usare pozioni o amuleti\n"
	                + "Premere M per aprire l'inventario\n");
	        switch (mossa) {
	        case "w","a","s","d":
	        	precedente = turno.muoviPersonaggio(matrice, mossa, Xpersonaggio, Ypersonaggio, dimRighe, dimColonne, personaggio);
	        	if(precedente.equals("M")) {
	        		fine = turno.scontro(personaggio);
	        	}
	        	//se il precedente e dijkstra inizierà lo scontro finale e il programma terminerà
	        	if(precedente.equals("D")) {
	        		turno.scontroFinale(personaggio);
	        		fine = true;
	        	}
	        	break;
	        case "E":
	        	if(precedente.equals("C")) {
	        		turno.prendiOggetti(zaino, personaggio);
	        	}
	        	else {
	        		System.out.println("Non c'è nessuna chest qui ");
	        	}
	        	break;
	        case "U":
	        	turno.usaPozione(zaino, personaggio);
	        	break;
	        case "M":
	        	System.out.println(zaino.toString());
	        	break;
	        default:
	        	System.out.println("Mossa non valida");
	        	break;
	        }
		}while(!fine);
		
	}

}

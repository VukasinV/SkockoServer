package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;

public class ServerNit extends Thread {
	BufferedReader ulazniTokOdKlijenata = null;
	PrintStream izlazniTokKaKlijentu = null;
	Socket soketZaKom = null;
	LinkedList<ServerNit> klijenti;
	String linija;
	String ime;
	boolean uIgri = false;
	
	//Ovo je poslednji kod

	public ServerNit(Socket soket, LinkedList<ServerNit> klijent) {
		this.soketZaKom = soket;
		this.klijenti = klijent;
	}

	public void run() {
		try {
			ulazniTokOdKlijenata = new BufferedReader(new InputStreamReader(soketZaKom.getInputStream()));
			izlazniTokKaKlijentu = new PrintStream(soketZaKom.getOutputStream());
			String temp = null;
			//-------------------------
			while (true) {
				izlazniTokKaKlijentu.println("Unesite ime: "); // da bi pratili
				temp = ulazniTokOdKlijenata.readLine();
				izlazniTokKaKlijentu.println("Promenjiva temp je dobila vrednost " + temp);
				temp = proveriIme(temp);
				if (temp.equals("Postoji ime")) {
					izlazniTokKaKlijentu.println("Opet unesi ime: ");
					System.out.println("Poslao je klijentu da je ime pogresno");
				} else {
					izlazniTokKaKlijentu.println("Ime je ispravno");
					System.out.println("Poslao je klijentu da je ime ispravno");
					this.ime = temp;
					break;
				}
				
			}
			
			while (true) {
				if (ulazniTokOdKlijenata.readLine().equals("posalji listu")) {
					izlazniTokKaKlijentu.println(posaljiListu());
					break;
				}
			}
			//-------------------------
			/*while (true) {
				izlazniTokKaKlijentu.println("Unesite ime: "); // da bi pratili
				while (temp == null) {
					temp = ulazniTokOdKlijenata.readLine();
					System.out.println(temp);
				}
				izlazniTokKaKlijentu.println("Promenjiva temp je dobila vrednost " + temp);
				temp = proveriIme(temp);
				if (temp.equals("Postoji ime")) {
					izlazniTokKaKlijentu.println("Opet unesi ime: ");
					System.out.println("Poslao je klijentu da je ime pogresno");
					break;
				} else {
					izlazniTokKaKlijentu.println("Ime je ispravno");
					System.out.println("Poslao je klijentu da je ime ispravno");
					this.ime = temp;
					break;
				}
			}
*/
			// int brojac = 0;
			// while (brojac != 1) {
			// brojac = 0;
			// izlazniTokKaKlijentu.println("Unesite ime: ");
			// ime = ulazniTokOdKlijenata.readLine();
			// if (klijenti.size() == 1) {
			// break;
			// }
			// for (int i = 0; i < klijenti.size(); i++) {
			// if (klijenti.get(i) == this ||
			// klijenti.get(i).ime.equals(this.ime)) {
			// brojac++;
			// }
			// }
			// if (brojac != 1) {
			// izlazniTokKaKlijentu.print("Ime vec postoji. ");
			// }
			// }
			// izlazniTokKaKlijentu.println("Ovo su dostupni klijenti: ");
			//
			// if (klijenti.size() == 1) {
			// izlazniTokKaKlijentu.println("Trenutno nema igraca na serveru,
			// sacekajte");
			// } else {
			// izlazniTokKaKlijentu.println("Odaberite protivnika: ");
			// for (int i = 0; i < klijenti.size(); i++) {
			// if (klijenti.get(i) == this) {
			// continue;
			// }
			// izlazniTokKaKlijentu.println(klijenti.get(i).ime);
			// }
			// izlazniTokKaKlijentu.println("------------------------------------------------------");
			// izlazniTokKaKlijentu.println("Zelim da igram protiv: ");
			// }
			// String odabraniProtivnik = ulazniTokOdKlijenata.readLine();
			//
			// for (int i = 0; i < klijenti.size(); i++) {
			// if (klijenti.get(i).ime.equals(odabraniProtivnik)) {
			// klijenti.get(i).izlazniTokKaKlijentu.println("Izazvani ste od <"
			// + this.ime + "> na duel");
			// klijenti.get(i).izlazniTokKaKlijentu.println("DA, NE ?");
			// if(klijenti.get(i).ulazniTokOdKlijenata.readLine().equalsIgnoreCase("DA")){
			// klijenti.get(i).uIgri = true;
			// this.uIgri = true;
			// }
			// break;
			// }
			// }

			while (true) {
				linija = ulazniTokOdKlijenata.readLine();
				if (linija.startsWith("CAO")) {
					break;
				}
			}
			System.out.println("Stigao do soketa");
			soketZaKom.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		for (int i = 0; i < klijenti.size(); i++) {
			if (klijenti.get(i) == this) {
				klijenti.remove(i);
			}
		}
	}

	public String posaljiListu (){
		String lista = "Lista,";
		if (klijenti.size() == 1) {
			return "PLista";
		}
		for (int i = 0; i < klijenti.size(); i++) {
			lista = lista + klijenti.get(i).ime + ","; 
		}
		
		return lista;
	}
	public String proveriIme(String s) {
		if (s == null)
			return null;
		String imeZaProveriti = "";
		if (s.startsWith("Proveri ime: ")) {
			imeZaProveriti = s.substring(13);
			if (klijenti.size() == 1) {
				return imeZaProveriti;
			}

			for (int i = 0; i < klijenti.size(); i++) {
				if (klijenti.get(i).ime != null && klijenti.get(i).ime.equals(imeZaProveriti)) {
					System.out.println("Postoji ime: " + imeZaProveriti);
					return "Postoji ime";
				}
			}
		}
		return imeZaProveriti;
	}

}

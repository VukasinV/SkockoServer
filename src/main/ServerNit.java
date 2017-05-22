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

	public ServerNit(Socket soket, LinkedList<ServerNit> klijent) {
		this.soketZaKom = soket;
		this.klijenti = klijent;
	}

	public void run() {
		try {
			ulazniTokOdKlijenata = new BufferedReader(new InputStreamReader(soketZaKom.getInputStream()));
			izlazniTokKaKlijentu = new PrintStream(soketZaKom.getOutputStream());
			String temp = null;
			while (true) {
				izlazniTokKaKlijentu.println("Unesite ime: "); // da bi pratili
				while (temp == null) {
					temp = ulazniTokOdKlijenata.readLine();
					System.out.println(temp);
				}
				temp = proveriIme(temp);
				if (temp.equals("Postoji ime")) {
					izlazniTokKaKlijentu.println("Opet unesi ime: ");
				} else {
					izlazniTokKaKlijentu.println("Ime je ispravno");
					this.ime = temp;
					break;
				}
			}

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
				if (klijenti.get(i).ime.equals(imeZaProveriti)) {
					System.out.println("Postoji ime: " + imeZaProveriti);
					return "Postoji ime";
				}
			}
		}
		return imeZaProveriti;
	}

}

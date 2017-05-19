package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;

public class ServerNit extends Thread {
	String linija;
	String nik;
	BufferedReader ulazniTokOdKlijenta = null;
	PrintStream izlazniTokKaKlijentu = null;
	Socket soketZaKom = null;
	public boolean uIgri;
	
	ServerNit[] klijenti;
	
	public ServerNit(Socket soket, ServerNit[] klijent) {
		 this.soketZaKom = soket;
		 this.klijenti = klijent;
	}
	
	public void run() {
		
		try {
			ulazniTokOdKlijenta = new BufferedReader(new InputStreamReader(soketZaKom.getInputStream()));
			izlazniTokKaKlijentu = new PrintStream(soketZaKom.getOutputStream());
			
			izlazniTokKaKlijentu.println("***** Uspesno ste se povezali na server *****");
			izlazniTokKaKlijentu.println("Unesite nickname: ");
			nik = ulazniTokOdKlijenta.readLine();
			izlazniTokKaKlijentu.println("Dobrodosao/la <" + nik + ">.\nZa izlaz unessite /quit");
			
			while (true) {
				linija = ulazniTokOdKlijenta.readLine();
				if (linija.startsWith("/quit")) {
					break;
				}
				for (int i = 0; i < klijenti.length; i++) {
					if (klijenti[i] != null && linija != null) {
						klijenti[i].izlazniTokKaKlijentu.println("<" + nik + "> " + linija);
					}
				}
			}
			
			for (int i = 0; i < klijenti.length; i++) {
				if (klijenti[i] != null) {
					klijenti[i].izlazniTokKaKlijentu.println("***Korisnik " + nik + " izlazi iz chat sobe!!! ***");
				}
			}
			
			izlazniTokKaKlijentu.println("*** Dovidjenja " + nik + " ***");
			
			soketZaKom.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		for (int i = 0; i < klijenti.length; i++) {
			if (klijenti[i] == this) {
				klijenti[i] = null;
			}
		}
	}
}

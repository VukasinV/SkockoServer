package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static int port = 5533;
	public static ServerSocket serverSoket;
	public static Socket klijentSoket;
	public static BufferedReader ulazOdKlijenta;
	public static PrintStream izlazKaKlijentu;

	public static void main(String[] args) throws Exception {
		String recenica;
		serverSoket = new ServerSocket(port);
		while (true) {
			klijentSoket = serverSoket.accept();
			if (klijentSoket!=null) {
				System.out.println("Klijent je konektovan!");
			}
			ulazOdKlijenta = new BufferedReader(new InputStreamReader(klijentSoket.getInputStream()));
			izlazKaKlijentu = new PrintStream(klijentSoket.getOutputStream());
			izlazKaKlijentu.println("Dobrodosli!");
			recenica = ulazOdKlijenta.readLine();
			System.out.println(recenica);
			//logika
		}
		
		
	}
}

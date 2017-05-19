package main;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Server {
	static ServerNit klijenti[] = new ServerNit[10];
	
	static LinkedList<ServerNit> muskarci = new LinkedList<>();
	static LinkedList<ServerNit> zene = new LinkedList<>();

	public static void main(String[] args) {
		int port = 9999;

		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}

		Socket klijentSoket = null;
		try {
			ServerSocket serverSoket = new ServerSocket(port);
			while (true) {
				
				klijentSoket = serverSoket.accept();
				
				for (int i = 0; i < klijenti.length; i++) {
					if (klijenti[i] == null) {
						klijenti[i] = new ServerNit(klijentSoket, klijenti);
						klijenti[i].start();
						System.out.println("user je konektovan!");
						break;
					}
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}

package uf3;

import java.net.*;
import java.io.*;

public class ClientUDP3 {
	public static void main(String[] args) throws Exception {

		// FLUX PER A ENTRADA ESTÀNDARD
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		// Socket client
		DatagramSocket clientSocket = new DatagramSocket();
		byte[] enviats = new byte[1024];
		byte[] rebuts = new byte[1024];

		// DADES DEL SERVIDOR al qual s'envia el missatge
		InetAddress IPServidor = InetAddress.getLocalHost();
		int port = 9800;

		clientSocket.setSoTimeout(5000);
		DatagramPacket enviament;

		//Mentre no es tanqui la connexió, el programa continuarà demanant a l'usuari un missatge
		while (!clientSocket.isClosed()) {
			// INTRODUIR DADES PEL TECLAT
			System.out.print("Introdueix missatge: ");
			String cadena = in.readLine();
			//Si l'usuari escriu "exit", el programa finalitzarà
			if(cadena.equals("exit")) {
				clientSocket.close();
			} else {
				enviats = cadena.getBytes();

				// ENVIANT DATAGRAMA AL SERVIDOR
				System.out.println("Enviant " + enviats.length + "bytes al servidor.");
				enviament = new DatagramPacket(enviats, enviats.length, IPServidor, port);
				clientSocket.send(enviament);

				// REBENT DATAGRAMA DEL SERVIDOR

				DatagramPacket rebut = new DatagramPacket(rebuts, rebuts.length);
				System.out.println("Esperant datagrama...");
				//Si passen mes de 5 segons, es tancarà la connexió.
				try {
					clientSocket.receive(rebut);
				} catch (SocketTimeoutException e) {
					clientSocket.close();
					System.out.println("CONNEXIÓ TANCADA");
				}
				String majuscula = new String(rebut.getData());

				// ACONSEGUINT INFORMACIÓ DEL DATAGRAMA
				InetAddress IPOrigen = rebut.getAddress();
				int portOrigen = rebut.getPort();
				System.out.println("\tProcedent de: " + IPOrigen + ":" + portOrigen);
				System.out.println("\tDades: " + majuscula.trim());
			}
			
		}
		// Tanca el socket
		clientSocket.close();

	}
}

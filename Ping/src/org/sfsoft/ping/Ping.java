package org.sfsoft.ping;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Aplicaci�n que realiza un ping sobre un equipo remoto
 * @author Santi
 *
 */
public class Ping {

	public static final int INTENTOS = 3;
	public static final String IP = "192.168.0.2";
	
	public static void main(String args[]) {
		
		try {
			// Obtiene la direcci�n del equipo remoto
			InetAddress equipoRemoto = InetAddress.getByName(IP);
			
			// Realiza varias intentos
			for (int i = 0; i < INTENTOS; i++) {
				// Comprueba si el equipo respone con un timeout m�ximo de 3000 ms
				if (equipoRemoto.isReachable(3000))
					System.out.println("El equipo responde");
				else
					System.out.println("El equipo no responde");
			}
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

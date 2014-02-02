package org.sfsoft.holasocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Proyecto de prueba que utiliza un socket para conectar
 * con un servidor de echo en un equipo remoto
 * Un servidor de echo es un servicio que emite el mismo mensaje que se le env�a
 * 
 * @author Santiago Faci
 *
 */
public class HolaSocket {

	public static void main(String args[]) {
		
		// Nombre del host remoto, donde haya disponible un servidor echo
		String hostname = "videosdeinformatica.com";
		// Puerto donde conectar� el socket
		int puerto = 7;
		
		try {
			// Establece la conexi�n con el servicio remoto
			Socket socket = new Socket(hostname, puerto);
			// Establece el flujo de salida hacia el servicio remoto
			PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
			// Establece el flujo de entrada, desde el servicio remoto
			BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			
			String cadena = null;
			/*
			 * Captura por teclado una l�nea, la env�a al servicio y lee la respuesta
			 * de �ste
			 */
			while ((cadena = teclado.readLine()) != null) {
				// Env�a el mensaje al servicio a trav�s del socket
				salida.println(cadena);
				// Lee la respuesta del servicio a trav�s del socket
				System.out.println(entrada.readLine());
			}
			
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
}

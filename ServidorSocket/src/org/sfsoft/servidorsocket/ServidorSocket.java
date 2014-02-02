package org.sfsoft.servidorsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Ejemplo de aplicaci�n que funciona como servidor
 * Funciona como un servidor echo: 
 * 	Recibe mensajes y los reenv�a al cliente
 * 
 * @author Santiago Faci
 *
 */
public class ServidorSocket {

	public static void main(String args[]) {
		
		int puerto = 7;
		
		try {
			// Se inicia el servidor en el equipo local en el puerto indicado
			ServerSocket socketServidor = new ServerSocket(puerto);
			// Espera la conexi�n con un cliente
			Socket socketCliente = socketServidor.accept();
			
			// Establece los flujos de salida y entrada (desde y hacia el cliente, respectivamente)
			PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			
			// Env�a algunos mensajes al cliente en cuanto �ste se conecta
			salida.println("Hola " + socketCliente.getInetAddress().getHostAddress());
			salida.println("S�lo s� repetir lo que me escribas");
			salida.println("Cuando escribas '.', se terminar� la conexi�n");
			
			String linea = null;
			/*
			 * Espera la entrada por parte del cliente y act�a seg�n
			 * su protocolo: Repetir los mensajes y si el cliente
			 * env�a el caracter . salir
			 */
			while ((linea = entrada.readLine()) != null) {
				
				if (linea.equals(".")) {
					salida.println("Saliendo . . .");
					// Cierra la conexi�n con el cliente
					socketCliente.close();
					// Para el servidor
					socketServidor.close();
					break;
				}
				
				salida.println("Has escrito: " + linea);
			}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

package org.sfsoft.servidorthreadsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Gestiona la comunicaci�n con cada uno de los clientes
 * conectados al servidor
 * @author Santiago Faci
 *
 */
public class ConexionCliente extends Thread {

	private Socket socket;
	private PrintWriter salida;
	private BufferedReader entrada;
	
	public ConexionCliente(Socket socket) throws IOException {
		this.socket = socket;
		
		salida = new PrintWriter(socket.getOutputStream(), true);
		entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	@Override
	public void run() {
		System.out.println("Iniciando comunicaci�n con el cliente");
		
		// Env�a algunos mensajes al cliente en cuanto �ste se conecta
		salida.println("Hola " + socket.getInetAddress().getHostName());
		salida.println("S�lo s� repetir lo que me escribas");
		salida.println("Cuando escribas '.', se terminar� la conexi�n");
		
		try {
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
					socket.close();
					break;
				}
				
				salida.println("Has escrito: " + linea);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

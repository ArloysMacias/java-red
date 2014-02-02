package org.sfsoft.servidorweb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Clase que se encarga de gestionar la comunicaci�n con cada cliente
 * que se conecta a este servidor web
 * 
 * Es capaz de enviar p�gina HTML e im�genes JPG
 * 
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Cliente extends Thread {

	private BufferedReader entrada;
	private DataOutputStream salida;
	private Socket socket;
	
	public Cliente(Socket socket) throws IOException {
		
		this.socket = socket;
		entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		salida = new DataOutputStream(socket.getOutputStream());
	}
	
	/*
	 * Cierra la conexi�n con el cliente
	 */
	private void desconectar() throws IOException {
		
		// Desconecta del servidor	
		if (entrada != null) 
			entrada.close();
		
		if (salida != null)
			salida.close();
	
		if (socket != null)
			socket.close();
	}
	
	@Override
	public void run() {
		
		try {
			// Lee la petici�n del navegador (s�lo la primera l�nea)
			String peticion = entrada.readLine();
			System.out.println(peticion);
			
			// El navegador est� solicitando una p�gina web
			if (peticion.startsWith("GET")) {
				String[] partes = peticion.split(" ");
				String rutaFichero = partes[1].substring(1);
				
				System.out.println(rutaFichero);
				
				/*
				 *  Si no ha solicitado ninguna p�gina es que ha solicitado la p�gina por defecto
				 *  que normalmente es index.html
				 */
				if (rutaFichero.equals(""))
					rutaFichero = "index.html";
				
				File fichero = new File("htdocs" + File.separator + rutaFichero);
				
				// Si el fichero no existe se envia el mensaje de error
				if (!fichero.exists()) {
					salida.writeBytes("HTTP/1.0 404 Not Found\r\n");
					salida.writeBytes("\r\n");
					salida.writeBytes("<html><body>Documento no encontrado</body></html>\r\n");
					System.out.println("p�gina no encontrada");
					desconectar();
					return;
				}
				
				// Prepara el fichero que se tiene que enviar
				FileInputStream fis = new FileInputStream(fichero);
				int tamanoFichero = (int) fichero.length();
				byte[] bytes = new byte[tamanoFichero];
				fis.read(bytes);
				fis.close();
				
				// Prepara las cabecera de salida para el navegador
				salida.writeBytes("HTTP/1.0 200 OK\r\n");
				salida.writeBytes("Server: MiJavaHTTPServer\r\n");
				if (rutaFichero.endsWith(".jpg"))
					salida.writeBytes("Content-Type: image/jpg\r\n");
				else if (rutaFichero.endsWith(".html"))
					salida.writeBytes("Content-Type: text/html\r\n");
				salida.writeBytes("Content-Length: " + tamanoFichero + "\r\n");
				// L�nea en blanco, obligatoria seg�n el protocolo
				salida.writeBytes("\r\n");
				// Env�a el contenido del fichero
				salida.write(bytes, 0, tamanoFichero);
				
				desconectar();
			}
		
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

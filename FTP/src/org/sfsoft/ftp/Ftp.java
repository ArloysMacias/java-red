package org.sfsoft.ftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * Cliente FTP Java que conecta con un servidor FTP
 * Lista el contenido del directorio ra�z y descarga un fichero
 * 
 * Se ha utilizado la librer�a Apache commons net que proporciona librer�as
 * y un API para trabajar con diferentes protocolos desde Java
 * http://commons.apache.org/proper/commons-net/
 * 
 * TODO Subir un fichero al servidor
 * 
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class Ftp {

	public static final String IP = "localhost";
	public static final int PUERTO = 21;
	public static final String USUARIO = "superman";
	public static final String CONTRASENA = "superman";
	
	public static void main(String args[]) {
		
		FTPClient clienteFtp = null;
	
		try {
			// Conecta con el servidor FTP e inicia sesi�n
			System.out.println("Conectando e iniciando sesi�n . . .");
			clienteFtp = new FTPClient();
			clienteFtp.connect(IP, PUERTO);
			clienteFtp.login(USUARIO, CONTRASENA);
			
			/*
			 *  En el modo pasivo es siempre el cliente quien abre las conexiones
			 *  Da menos problemas si estamos detr�s de un firewall, por ejemplo
			 */
			clienteFtp.enterLocalPassiveMode();
			clienteFtp.setFileType(FTPClient.BINARY_FILE_TYPE);
			
			// Lista el directorio del servidor FTP
			System.out.println("Listando el directorio ra�z del servidor . . ");
			FTPFile[] ficheros = clienteFtp.listFiles();
			for (int i = 0; i < ficheros.length; i++) {
				System.out.println(ficheros[i].getName());
			}
			
			// Fija los ficheros remoto y local
			String ficheroRemoto = "/modelo.txt";
			File ficheroLocal = new File("c:" + File.separator + "modelo.txt");
			
			System.out.println("Descargando fichero '" + ficheroRemoto + "' del servidor . . .");
			// Descarga un fichero del servidor FTP
			OutputStream os = new BufferedOutputStream(new FileOutputStream(ficheroLocal));
			if (clienteFtp.retrieveFile(ficheroRemoto, os))
				System.out.println("El fichero se ha recibido correctamente");
			
			os.close();
			
			/*
			 * TODO
			 * Con el m�todo clienteFtp.storeFile(String nombreFicheroRemoto, InputStream isFicheroLocal)
			 * se pueden subir ficheros al servidor FTP
			 */
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			/*
			 * Cierra la sesi�n y desconecta del servidor FTP
			 */
			if (clienteFtp != null)
				try {
					System.out.println("Cerrando conexi�n y desconectando del servidor . . .");
					if (clienteFtp.isConnected()) {
						clienteFtp.logout();
						clienteFtp.disconnect();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
		}
	}
}

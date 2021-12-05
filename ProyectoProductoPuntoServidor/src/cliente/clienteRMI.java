package cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;

import interfacee.chatServidor;

public class clienteRMI {

	public static void main(String[] args) {
		try {
			String nombre = JOptionPane.showInputDialog("Ingresa tu nombre");
			String nom = nombre;
			
			Registry rmii = LocateRegistry.getRegistry("192.168.100.18", 1005);
			System.out.println("Hola " + nom);
			
			chatServidor servidor = (chatServidor) rmii.lookup("Chat");
			//new Thread(new implementacionClienteChat(nom, servidor)).start();
			ClienteView cliente = new ClienteView(nom, servidor);
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace(); //Hola
		}
	}
}

package Cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import Interfaces.chatCliente;
import Interfaces.chatServidor;
import java.io.Serializable;

public class implementacionClienteChat extends UnicastRemoteObject implements chatCliente, Runnable,Serializable{

	chatServidor servidor;
	ClienteInfo cliente;
	boolean bandera = true;
	
	
	implementacionClienteChat(ClienteInfo cliente, chatServidor servidor) throws RemoteException {
		this.cliente = cliente;
		this.servidor = servidor;
		//this.cliente.imp = implementacionClienteChat;
		servidor.registro(this.cliente);
	}

	@Override
	public void run() {
		while(bandera) {
			try {
				servidor.mensaje("" + cliente.tam);
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("ERROR ***********");
			}
		}
	}

	@Override
	public void mensajeCliente(String mensaje) throws RemoteException {
		System.err.println(mensaje);
		bandera = false;
	}
}
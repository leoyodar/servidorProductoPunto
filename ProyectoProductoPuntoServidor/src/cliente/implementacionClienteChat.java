package cliente;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import interfacee.chatCliente;
import interfacee.chatServidor;

public class implementacionClienteChat extends UnicastRemoteObject implements chatCliente, Runnable, Serializable{

	public chatServidor servidor;
	public ClienteInfo cliente;
	boolean bandera = true;
	JLabel tiempo;
	JTextArea tr, textv2;
	
	
	implementacionClienteChat(ClienteInfo cliente, chatServidor servidor, JLabel tiempo, JTextArea resu, JTextArea V2text) throws RemoteException {
		this.cliente = cliente;
		this.servidor = servidor;
		this.cliente.cliente = this;
		
		this.tiempo = tiempo;
		this.tr = resu;
		this.textv2 = V2text;
		
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
		String[] respuesta = mensaje.split(" ");
                
                if(respuesta.length>3){
                    this.tr.setText(mensaje);
                }else{
                    this.tiempo.setText(respuesta[0]);
                    this.tr.setText(respuesta[1]);    
                }
		
		bandera = false;
	}
	
	@Override
	public void set_v2(int[] v2) {
		String text = "";
		for(int i=0; i<v2.length; i++){
            if(i<8000){
                text += v2[i] + ", ";    
            }    
        }
		
		textv2.setText(text);
	}
}
package Cliente;

import Interfaces.chatServidor;
import Servidor.implementacionChat;
import java.io.Serializable;

public class ClienteInfo implements Serializable{
	public String nombre;
	public int tam;
	public int[] vector1;
        public int[] vector2;
	public boolean is_ready;
	public chatServidor servidor;
	//public implementacionClienteChat imp;
	public int op; //0=secuencial, 1=fork, 2=exec
	
	public ClienteInfo(String nombre, chatServidor servidor) {
		this.nombre = nombre;
		this.servidor = servidor;
		this.is_ready = false;
		this.tam = 1;
	}
	
	public void set_vector1(int[] vector1) {
		this.vector1 = vector1;
	}
        
        public void set_vector2(int[] vector2) {
		this.vector2 = vector2;
	}
	
	public void set_tam(int i) {
		this.tam = i;
	}
	
	public void set_op(int i) {
		this.op = i;
	}
	
}

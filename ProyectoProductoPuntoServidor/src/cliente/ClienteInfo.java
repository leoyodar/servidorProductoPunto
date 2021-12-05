package cliente;

import java.io.Serializable;

import interfacee.chatCliente;

public class ClienteInfo implements Serializable{
	public String nombre;
	public int tam;
	public int[] vector;
	public boolean is_ready;
	public int op; //0=secuencial, 1=fork, 2=exec
	public chatCliente cliente;
	
	public ClienteInfo(String nombre) {
		this.nombre = nombre;
		this.is_ready = false;
		this.tam = 1;
	}
	
	public void set_vector(int[] vector) {
		this.vector = vector;
	}
	
	public void set_tam(int i) {
		this.tam = i;
	}
	
	public void set_op(int i) {
		this.op = i;
	}
	
	
}

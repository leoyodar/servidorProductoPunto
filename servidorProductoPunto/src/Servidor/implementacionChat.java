package Servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import Cliente.ClienteInfo;
import Interfaces.chatCliente;
import Interfaces.chatServidor;

public class implementacionChat extends UnicastRemoteObject implements chatServidor{

	public ArrayList<ClienteInfo> clientes;
	public float n1=0, n2=0;
	public boolean n1_vacio=true, n2_vacio=true;
	
	public static double tiempo, resultado;
	
	implementacionChat() throws RemoteException {
		clientes = new ArrayList<ClienteInfo>();
	}

	@Override
	public void registro(ClienteInfo cliente) throws RemoteException {
		this.clientes.add(cliente);
	}

	@Override
	public void mensaje(String mensaje) throws RemoteException {
		int a = 0;

		if(clientes.size() >= 2) {
			while(a < clientes.size()) {
                            //clientes.get(a++).mensajeCliente(mensaje);
                            String todo = tiempo + " " + resultado;
                            hacer_producto(clientes.get(a).op);
                            a++;
                            //clientes.get(a++).imp.mensajeCliente(todo);
			}
			clientes.clear();
		}
	}
	
	public void hacer_producto(int op) {
            
	if(op == 0){
            long inicio=0, fin=0;
            inicio=System.currentTimeMillis();
            //resultado = secuencial(clientes.get(0).vector1, clientes.get(0).vector2);
           
            
            resultado = secuencial(clientes.get(0).vector1, clientes.get(1).vector1);
            
            fin = System.currentTimeMillis();
            tiempo=(double)(fin-inicio);
        }else if(op == 1){
            long inicio=0, fin=0;
            inicio=System.currentTimeMillis();
            
             for(int i=0;i<clientes.get(0).vector1.length;i++){
                System.out.println(clientes.get(0).vector1[i]);
            }
            
            for(int i=0;i<clientes.get(1).vector1.length;i++){
                System.out.println(clientes.get(1).vector1[i]);
            }
            
            resultado = fork(clientes.get(0).vector1, clientes.get(1).vector1);
            
            fin = System.currentTimeMillis();
            tiempo=(double)(fin-inicio);
        }else if(op == 2){
            long inicio=0, fin=0;
            inicio=System.currentTimeMillis();
            
            resultado = executor(clientes.get(0).vector1, clientes.get(1).vector1);
            
            fin = System.currentTimeMillis();
            tiempo=(double)(fin-inicio);
        }
	}
	
    public double secuencial(int[] v1, int[] v2) {
        double res = 0;

        for(int i=0; i<v1.length; i++) {
                res += v1[i] * v2[i];
        }
        System.out.println(res);
        return res;
    }
    
    public double fork(int[] v1, int[] v2){
        Runtime runtime = Runtime.getRuntime();
        int Nucleos = runtime.availableProcessors();
        int aux = v1.length;
        int max = v1.length/Nucleos;
        double [] resultado={0};
        
        if(v1.length<Nucleos){
           max=v1.length; 
        }
        
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new ProductoFork (max,v1,v2,0,aux,resultado));
        System.out.println(resultado[0]);
        return resultado[0];
    }
    
    public double executor(int[] v1, int[] v2){
        Runtime runtime = Runtime.getRuntime();
        int Nucleos = runtime.availableProcessors();
        int aux = v1.length;
        int max = v1.length/Nucleos;

        if(v1.length<Nucleos){
           max=v1.length; 
        }
        
        double [] resultado={0};
        
        ExecutorService service = Executors.newCachedThreadPool();

        ProductoExecutor em = new ProductoExecutor(max,v1,v2,0,aux,resultado,service);  
        em.run();
        System.out.println(resultado[0]);
        return resultado[0];
    }
	
	
}
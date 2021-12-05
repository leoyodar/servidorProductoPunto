package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import cliente.ClienteInfo;
import cliente.implementacionClienteChat;
import interfacee.chatCliente;
import interfacee.chatServidor;

public class implementacionChat extends UnicastRemoteObject implements chatServidor{

	public ArrayList<ClienteInfo> clientes;
	public static int cont = 0;
	
	public static double tiempo, resultado_final;
	
	implementacionChat() throws RemoteException {
		clientes = new ArrayList<ClienteInfo>();
	}

	@Override
	public void registro(ClienteInfo cliente) throws RemoteException {
		this.clientes.add(cliente);
		cont++;
	}

	@Override
	public void mensaje(String mensaje) throws RemoteException {
		if(cont == 2) {
                    int a = 0;
                    if(clientes.get(0).vector.length==clientes.get(1).vector.length){
                        
			try {
				clientes.get(0).cliente.set_v2(clientes.get(1).vector);
				clientes.get(1).cliente.set_v2(clientes.get(0).vector);
				while(a < 2) {
					int o = clientes.get(a).op;
					hacer_producto(o);
					String todo = tiempo + " " + resultado_final;
					clientes.get(a).cliente.mensajeCliente(todo);
					a++;
				}
			} catch(Exception ex) {

			}    
                    }else{
                        while(a < 2) {
                            clientes.get(a).cliente.mensajeCliente("ADVERTENCIA: La longitud de los vectores no coincide");
                            a++;
			}
                    }

                    clientes.clear();
                    cont = 0;
		}
	}
	
	public void hacer_producto(int op) {
		if(op == 0){
            long inicio=0, fin=0;
            inicio=System.currentTimeMillis();

            resultado_final = secuencial(clientes.get(0).vector, clientes.get(1).vector);

            fin = System.currentTimeMillis();
            tiempo=(double)(fin-inicio);
        }else if(op == 1){
            long inicio=0, fin=0;
            inicio=System.currentTimeMillis();
            try {
            	resultado_final =fork(clientes.get(0).vector, clientes.get(1).vector);
            } catch(Exception ex) {
            	System.out.println("Le dio amsieda");
            }
            
            
            fin = System.currentTimeMillis();
            tiempo=(double)(fin-inicio);
        }else if(op == 2){
            long inicio=0, fin=0;
            inicio=System.currentTimeMillis();
            
            try {
            	resultado_final = executor(clientes.get(0).vector, clientes.get(1).vector);
            } catch(Exception ex) {
            	System.out.println("Le dio amsieda");
            }
            
            fin = System.currentTimeMillis();
            tiempo=(double)(fin-inicio);
        }
	}
	
    public double secuencial(int[] v1, int[] v2) {
        double res = 0;

        for(int i=0; i<v1.length; i++) {
                res += v1[i] * v2[i];
        }
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
        
        return resultado[0];
    }
	
	
}
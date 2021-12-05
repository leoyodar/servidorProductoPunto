package servidor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ProductoExecutor implements Runnable{
    
    private int max;
    private int [] v1;
    private int [] v2;
    private int inicio;
    private int fin;
    private double [] resultado;
    private ExecutorService service;
    
    public ProductoExecutor(int max,int[]v1,int[]v2,int inicio,int fin,double [] resultado, ExecutorService executorService){
        this.max = max;
        this.v1 = v1;
        this.v2 = v2;
        this.inicio = inicio;
        this.fin = fin;
        this.resultado = resultado;
        this.service = executorService;
    }

    @Override
    public void run() {
        if(fin-inicio>=max){
            int med = (inicio+fin) / 2;
            
            Future izquierda = service.submit(new ProductoExecutor(max, v1,v2,inicio,med,resultado,service));   
            Future derecha = service.submit(new ProductoExecutor(max, v1,v2,med,fin,resultado,service));    

            try {
                izquierda.get();
                derecha.get();
            }catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }      
        }else{
            producto();
        }
    }
    
    public void producto(){
        double total=0;
        //System.out.println(inicio+" "+fin);
        for(int i=inicio; i<fin; i++) {
            total += v1[i] * v2[i];
        } 
        resultado[0]+=total;
    }
    
}

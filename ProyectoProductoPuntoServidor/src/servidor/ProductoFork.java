package servidor;

import java.util.concurrent.RecursiveAction;

public class ProductoFork extends RecursiveAction{
    
    private int max;
    private int [] v1;
    private int [] v2;
    private int inicio;
    private int fin;
    private double [] resultado;
    
    public ProductoFork(int max,int[]v1,int[]v2,int inicio,int fin,double [] resultado){
        this.max = max;
        this.v1 = v1;
        this.v2 = v2;
        this.inicio = inicio;
        this.fin = fin;
        this.resultado = resultado;
    }

    @Override
    protected void compute(){
        if(fin-inicio>=max){
            int med = (inicio+fin) / 2;
            
            ProductoFork izquierda = new ProductoFork (max, v1,v2,inicio,med,resultado);
            ProductoFork derecha = new ProductoFork (max, v1,v2,med,fin,resultado);

            invokeAll(izquierda, derecha);      
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
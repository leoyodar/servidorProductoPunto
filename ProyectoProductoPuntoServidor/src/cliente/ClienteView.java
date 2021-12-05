package cliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import interfacee.chatServidor;


public class ClienteView extends JFrame implements ActionListener{

    public int num;
    public static String aux="";
    public static int auxTam=0;
    public int [] auxv1;
    public int [] auxv2;
    
    JButton secuencial,fork,executor,generar;
    
    JScrollPane V1scroll,V2scroll,Resscroll;
    JTextArea V1text,V2text,Restext;
    JLabel Tamlabel,V1label,V2label,Slabel,Flabel,Elabel,Reslabel,t1,t2,t3;
    JTextField T1;
    
    chatServidor servidor;
	public String nombre = null;
	ClienteInfo cliente;
    
    public ClienteView(String nombre, chatServidor servidor){
    	//Conexion
    	this.nombre = nombre;
    	this.servidor = servidor;
    	this.cliente = new ClienteInfo(nombre);
    	
    	//Vista
        setTitle("Producto Punto" + this.nombre);
        setSize(950,750);
        setLayout(null); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Input vectores
        Tamlabel = new JLabel("Tamaño Vectores: ");
        Tamlabel.setBounds(40,10,130,30);
        Tamlabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(Tamlabel);
        
        T1=new JTextField();
        T1.setBounds(165,10,120,30);
        add(T1);
        
        generar = new JButton("Generar");
        generar.setBounds(300,10,100,30);
        generar.setFont(new Font("Arial", Font.PLAIN, 14));
        add(generar);
        generar.addActionListener(this);
        
        //Vector 1
        V1label = new JLabel("Vector 1");
        V1label.setBounds(40,60,130,30);
        V1label.setFont(new Font("Arial", Font.BOLD, 14));
        add(V1label);
        V1scroll = new JScrollPane(V1text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        V1text = new JTextArea(10,20);
        
        V1text.setLineWrap(true);         
        V1text.setWrapStyleWord(true);
        
        V1scroll.setBounds(40, 90, 250, 400);
        V1scroll.setViewportView(V1text);
        add(V1scroll);
        
        //Vector 2
        V2label = new JLabel("Vector 2");
        V2label.setBounds(350,60,130,30);
        V2label.setFont(new Font("Arial", Font.BOLD, 14));
        add(V2label);
        V2scroll = new JScrollPane(V2text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        V2text = new JTextArea(10,20);
        
        V2text.setLineWrap(true);         
        V2text.setWrapStyleWord(true);
        
        V2scroll.setBounds(350, 90, 250, 400);
        V2scroll.setViewportView(V2text);
        add(V2scroll);
        
        //Secuencial
        secuencial = new JButton("Secuencial");
        secuencial.setBounds(715,90,110,30);
        secuencial.setFont(new Font("Arial", Font.PLAIN, 14));
        add(secuencial);
        secuencial.addActionListener(this);
        
        Slabel = new JLabel("Tiempo:");
        Slabel.setBounds(720,150,200,40);
        Slabel.setFont(new Font("Arial", Font.PLAIN, 17));
        add(Slabel);
        
        t1 = new JLabel("");
        t1.setBounds(800,150,200,40);
        t1.setFont(new Font("Arial", Font.ITALIC, 16));
        t1.setForeground(Color.BLUE);
        add(t1);
        
        //ForkJoin
        fork = new JButton("Fork/Join");
        fork.setBounds(720,250,100,30);
        fork.setFont(new Font("Arial", Font.PLAIN, 14));
        add(fork);
        fork.addActionListener(this);
        
        Flabel = new JLabel("Tiempo:");
        Flabel.setBounds(720,310,200,40);
        Flabel.setFont(new Font("Arial", Font.PLAIN, 17));
        add(Flabel);
        
        t2 = new JLabel("");
        t2.setBounds(800,310,200,40);
        t2.setFont(new Font("Arial", Font.ITALIC, 16));
        t2.setForeground(Color.BLUE);
        add(t2);
        
        //Executor
        executor = new JButton("executor service");
        executor.setBounds(700,420,140,30);
        executor.setFont(new Font("Arial", Font.PLAIN, 14));
        add(executor);
        executor.addActionListener(this);
        
        Elabel = new JLabel("Tiempo:");
        Elabel.setBounds(720,480,200,40);
        Elabel.setFont(new Font("Arial", Font.PLAIN, 17));
        add(Elabel);
        
        t3 = new JLabel("");
        t3.setBounds(800,480,200,40);
        t3.setFont(new Font("Arial", Font.ITALIC, 16));
        t3.setForeground(Color.BLUE);
        add(t3);
        
        //Resultado
        Reslabel = new JLabel("Resultado");
        Reslabel.setBounds(40,540,130,30);
        Reslabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(Reslabel);
        
        Resscroll = new JScrollPane(Restext, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Restext = new JTextArea(10,20);
        Restext.setFont(new Font("Arial", Font.BOLD, 50));
        Restext.setLineWrap(true);         
        Restext.setWrapStyleWord(true);
        
        Resscroll.setBounds(40, 570, 850, 100);
        Resscroll.setViewportView(Restext);
        add(Resscroll);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == generar){
            auxTam=Integer.parseInt(T1.getText());
            V2text.setText("");
            Restext.setText("");
	        if(auxTam<350000000){ 
	            auxv1 = generar_arreglo(auxTam,V1text);
	            this.cliente.vector = auxv1; //A�adimos el vector al cliente que se manda
	            this.cliente.set_tam(auxTam);
	            this.cliente.is_ready = true;
	        }else{
	            JOptionPane.showMessageDialog(null, "El tamaño de los vectores excede la memoria\n Intentalo de Nuevo","Atención!", JOptionPane.ERROR_MESSAGE); 
	        }
	        
	        
	        t1.setText("");
	        t2.setText("");
	        t3.setText("");
	        System.gc(); 

        }else if(e.getSource() == secuencial && this.cliente.is_ready == true){
            System.gc();
            Restext.setText("");
            this.cliente.op = 0;
            //this.cliente.is_ready = true;
            try {
    			new Thread(new implementacionClienteChat(this.cliente, servidor, t1, Restext, V2text)).start();
    		} catch (RemoteException ex) {
    			// TODO Auto-generated catch block
    			ex.printStackTrace();
    		}
        }else if(e.getSource() == fork && this.cliente.is_ready == true){
            System.gc();
            Restext.setText("");
            this.cliente.op = 1;
            //this.cliente.is_ready = true;
            try {
    			new Thread(new implementacionClienteChat(this.cliente, servidor, t2, Restext, V2text)).start();
    		} catch (RemoteException ex) {
    			// TODO Auto-generated catch block
    			ex.printStackTrace();
    		}
        }else if(e.getSource() == executor && this.cliente.is_ready == true){
            System.gc();
            Restext.setText("");
            this.cliente.op = 2;
            //this.cliente.is_ready = true;
            try {
    			new Thread(new implementacionClienteChat(this.cliente, servidor, t3, Restext, V2text)).start();
    		} catch (RemoteException ex) {
    			// TODO Auto-generated catch block
    			ex.printStackTrace();
    		}
        }
    }
    
    public int[] generar_arreglo(int tamano, JTextArea vector) {
        int arreglo[] = new int[tamano];
        String text = "";
        vector.setText("");

            for(int i=0; i<tamano; i++){
                arreglo[i] = (int)(Math.random()*100+1);  
                if(i<8000){
                    text += arreglo[i] + ", ";    
                }    
            }
        vector.setText(text);    
        return arreglo;
    }
}

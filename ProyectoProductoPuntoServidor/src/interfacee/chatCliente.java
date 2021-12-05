package interfacee;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface chatCliente extends Remote{
	void mensajeCliente(String mensaje) throws RemoteException;
	void set_v2(int[] v2) throws RemoteException;
}

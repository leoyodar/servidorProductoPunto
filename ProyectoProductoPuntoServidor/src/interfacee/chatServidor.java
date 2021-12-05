package interfacee;

import java.rmi.Remote;
import java.rmi.RemoteException;

import cliente.ClienteInfo;

public interface chatServidor extends Remote{
	void registro(ClienteInfo cliente) throws RemoteException;
	void mensaje(String mensaje) throws RemoteException;
}

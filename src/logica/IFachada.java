package logica;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFachada extends Remote {

    public VOPostre listarPostre(int cod) throws RemoteException, PostreException;

    public void altaPostre() throws RemoteException, PostreException;
}
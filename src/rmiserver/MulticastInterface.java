package rmiserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MulticastInterface extends Remote {
    public String getDepartamento() throws RemoteException;
}

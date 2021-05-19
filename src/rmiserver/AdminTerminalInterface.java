package rmiserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

//interface do AdminTerminal com as funções disponíveis para callback
public interface AdminTerminalInterface extends Remote {

    public void voteUpdate(String departamento, int count) throws RemoteException;
    public void tableUpdate(String dep) throws RemoteException;
    public void tableDisconnectedUpdate(String dep) throws RemoteException;
    public void terminalUpdate(String departamento) throws RemoteException;
    public void sendMessage(String text) throws RemoteException;
}

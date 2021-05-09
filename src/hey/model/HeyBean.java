
package hey.model;



import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import rmiserver.RMIServerInterface;
import rmiserver.RmiInterface;

public class HeyBean {
	private RMIServerInterface server;
	private String username; // username and password supplied by the user
	private String password;

	public HeyBean() {
		try {
			server = (RMIServerInterface) Naming.lookup("server");
		}
		catch(NotBoundException|MalformedURLException|RemoteException e) {
			e.printStackTrace(); // what happens *after* we reach this line?
		}
	}

	public ArrayList<String> getAllUsers() throws RemoteException {
		return server.getAllUsers(); // are you going to throw all exceptions?
	}

	public boolean getUserMatchesPassword(String username, String password) throws RemoteException {
		return server.userMatchesPassword(username, password);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}

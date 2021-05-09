
package hey.model;



import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import rmiserver.GetPropertyValues;
import rmiserver.RmiInterface;

public class HeyBean {
	private RmiInterface server;

	private String username; // username and password supplied by the user
	private String password;

	public HeyBean() {
		try {
			GetPropertyValues properties = new GetPropertyValues();
			try {
				properties.setPropValues();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String rmiport = properties.getRmiport();
			String rminame = properties.getRminame();
			String registry = properties.getRegistry();
			System.setProperty("java.rmi.server.hostname", registry);


			server = (RmiInterface) LocateRegistry.getRegistry(registry, Integer.parseInt(rmiport)).lookup(rminame);
		}
		catch(NotBoundException|RemoteException e) {
			e.printStackTrace(); // what happens *after* we reach this line?
		}
	}

	public boolean tryRmi() throws RemoteException{
		for (int i = 0; i <= 6; i++) {
			try {
				titulobool=ri.checkNomeEleicao(titulo);
				break;
			} catch (RemoteException e) {
				try {
					ri = (RmiInterface) LocateRegistry.getRegistry(registry, Integer.parseInt(rmiport)).lookup(rminame);
				} catch (NotBoundException | RemoteException ignored) {

				}
				if (i == 6) {
					System.out.println("Impossivel conectar aos servidores RMI");
					return false;
				}
			}
		}
	}


	public ArrayList<String> getAllUsers() throws RemoteException {
		ArrayList<String> bruh = new ArrayList<>();
		bruh.add("A");
		bruh.add("B");
		return bruh;// are you going to throw all exceptions?
	}

	public boolean getUserMatchesPassword(String username, String password) throws RemoteException {
		boolean ret = server.login(username, password);
		System.out.println(ret);
		return ret;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}

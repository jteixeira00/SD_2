
package hey.model;



import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import rmiserver.GetPropertyValues;
import rmiserver.RmiInterface;

public class HeyBean {
	private RmiInterface server;

	private String username; // username and password supplied by the user
	private String password;
	private String rmiport;
	private String rminame;
	private String registry;

	private int choiceGerirEleicao;


	public HeyBean() {
		try {
			GetPropertyValues properties = new GetPropertyValues();
			try {
				properties.setPropValues();
			} catch (IOException e) {
				e.printStackTrace();
			}
			rmiport = properties.getRmiport();
			rminame = properties.getRminame();
			registry = properties.getRegistry();
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
				server.heartbeat();
				return true;
			} catch (RemoteException e) {
				try {
					server = (RmiInterface) LocateRegistry.getRegistry(registry, Integer.parseInt(rmiport)).lookup(rminame);
				} catch (NotBoundException | RemoteException ignored) {

				}
				if (i == 6) {
					System.out.println("Impossivel conectar aos servidores RMI");
					return false;
				}
			}
		}
		return false;
	}

	public boolean checkTitulo(String titulo) throws RemoteException{
		if(tryRmi()){
			return server.checkNomeEleicao(titulo);
		}
		return false;
	}


	public ArrayList<String> getAllUsers() throws RemoteException {
		ArrayList<String> bruh = new ArrayList<>();
		bruh.add("A");
		bruh.add("B");
		return bruh;// are you going to throw all exceptions?
	}

	public ArrayList<String> getAllEleicoes() throws RemoteException {
		try {
			if(tryRmi()){
				String array[] = server.showEleicoesFuturas().split("\n");
				List<String> al = new ArrayList<String>();
				al = Arrays.asList(array);
				return new ArrayList<>(al);
			}
		}catch (RemoteException ignored){

		}
		return new ArrayList<>();
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

	public void setChoiceGerirEleicao(int choice) {
		this.choiceGerirEleicao = choice;
	}

	public boolean criaEleicao(String titulo, String descricao, String datainicio, String horainicio, String minutoinicio, String datafim, String horafim, String minutofim, String s, String tipovoter) {
		try {
			if(tryRmi()){
				if(server.createEleicaoRMI(titulo, descricao, datainicio, Integer.parseInt(horainicio), Integer.parseInt(minutoinicio), datafim, Integer.parseInt(horafim), Integer.parseInt(minutofim), "", Integer.parseInt(tipovoter)) != null){
					return true;
				}
				return false;
			}
		}catch (RemoteException ignored){

		}
		return false;
	}

	public boolean createUser(String tipo, String nome, String password, String numerouni, String ncc, String valcc, String numerotelefonico, String morada, String departamento, String faculdade) {
		try {
			if(tryRmi()){
				return server.createUserRMI(Integer.parseInt(tipo), nome, numerouni, departamento, faculdade, numerotelefonico, morada, ncc, valcc, password);
			}
		}catch (RemoteException ignored){

		}
		return false;
	}

	public int getSizeEleicoesFuturas() throws RemoteException {
		try {
			if(tryRmi()){
					return server.sizeEleicoesFuturas();
				}
				return -1;

		}catch (RemoteException ignored){

		}
		return -1;
	}

	public String getNomeEleicao(){
		try {
			if(tryRmi()){
				return server.getEleicoesFuturas().get(choiceGerirEleicao - 1).getTitulo();
			}
			return "";

		}catch (RemoteException ignored){

		}
		return "";

	}

}

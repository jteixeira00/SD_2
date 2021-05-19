
package hey.model;



import com.github.scribejava.core.model.OAuth2AccessToken;
import org.json.simple.parser.ParseException;
import rmiserver.GetPropertyValues;
import rmiserver.RmiInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HeyBean {
	private RmiInterface server;

	private String username; // username and password supplied by the user
	private String password;
	private String rmiport;
	private String rminame;
	private String registry;
	private FacebookREST fb;
	private String authURL;
	private String authCode;



	private String secretState;
	private OAuth2AccessToken accessToken;
	private String name;

	private int choiceGerirEleicao = 0;
	private String eleicaoChoice;

	private int choiceLista = 0;

	private int choiceUser = -1;


	public HeyBean() {
		this.fb = new FacebookREST();

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

	public void logout() throws RemoteException{
		for (int i = 0; i <= 6; i++) {
			try {

				server.logout(this.username);


			} catch (RemoteException e) {
				try {
					server = (RmiInterface) LocateRegistry.getRegistry(registry, Integer.parseInt(rmiport)).lookup(rminame);
				} catch (NotBoundException | RemoteException ignored) {

				}
				if (i == 6) {
					System.out.println("Impossivel conectar aos servidores RMI");

				}
			}
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


	public ArrayList<String> getAllPessoas() throws RemoteException {
		try {
			if(tryRmi()){
				String array[] = server.showPessoas().split("\n");
				List<String> al = new ArrayList<String>();
				al = Arrays.asList(array);
				return new ArrayList<>(al);
			}
		}catch (RemoteException ignored){

		}
		return new ArrayList<>();
	}

	public String getNumeroPessoa(int index) throws RemoteException {
		try {
			if(tryRmi()){
				return server.getPessoas().get(index).getNumero();
			}
		}catch (RemoteException ignored){

		}
		return "";
	}

	public ArrayList<String> getAllEleicoes() throws RemoteException {
		try {
			if(tryRmi()){
				String[] array = server.showEleicoesFuturas().split("\n");
				List<String> al = new ArrayList<String>();
				al = Arrays.asList(array);
				return new ArrayList<>(al);
			}
		}catch (RemoteException ignored){

		}
		return new ArrayList<>();
	}

	public ArrayList<String> getAllListas() throws RemoteException {
		try {
			if(tryRmi()){
				String[] array = server.getEleicoesFuturas().get(choiceGerirEleicao - 1).showListasCandidatas().split("\n");
				List<String> al = new ArrayList<String>();
				al = Arrays.asList(array);
				return new ArrayList<>(al);
			}
		}catch (RemoteException ignored){

		}
		return new ArrayList<>();
	}

	public ArrayList<String> getAllDepartamentos() throws RemoteException {
		try {
			if(tryRmi()){
				String array[] = server.showDepartamentos(choiceGerirEleicao - 1).split("\n");
				List<String> al = new ArrayList<String>();
				al = Arrays.asList(array);
				return new ArrayList<>(al);
			}
		}catch (RemoteException ignored){

		}
		return new ArrayList<>();
	}

	public ArrayList<String> getVotosUser() throws RemoteException {
		try {
			if(tryRmi()){
				String array[] = server.showVotoDetalhesRMI(choiceUser - 1).split("\n");
				List<String> al = new ArrayList<String>();
				al = Arrays.asList(array);
				return new ArrayList<>(al);
			}
		}catch (RemoteException ignored){

		}
		return new ArrayList<>();
	}

	public ArrayList<String> getAllCandidatos() throws RemoteException {
		try {
			if(tryRmi()){
				String array[] = server.getEleicoesFuturas().get(choiceGerirEleicao - 1).getListasCandidatas().get(choiceLista - 1).showCandidatos().split("\n");
				List<String> al = new ArrayList<String>();
				al = Arrays.asList(array);
				return new ArrayList<>(al);
			}
		}catch (RemoteException ignored){

		}
		return new ArrayList<>();
	}

	public boolean getUserMatchesPassword(String username, String password) throws RemoteException {
		return server.login(username, password);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername(){
		return this.username;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public void setChoiceUser(int choice) {
		this.choiceUser = choice;
	}

	public int getChoiceUser() {
		return choiceUser;
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

	public boolean addDepatamento(String nome) {
		try {
			if(tryRmi()){
				server.addDepartamentos(choiceGerirEleicao - 1,nome);
				return true;

			}
		}catch (RemoteException e){
			return false;
		}
		return false;
	}

	public boolean addLista(String nome) {
		try {
			if(tryRmi()){
				server.createListaRMI(choiceGerirEleicao - 1,nome);
				return true;

			}
		}catch (RemoteException e){
			return false;
		}
		return false;
	}

	public boolean addCandidato(int indx, int indxL){
		try {
			if(tryRmi()){
				server.addCandidateRMI(choiceGerirEleicao - 1,indxL, indx - 1);
				return true;

			}
		}catch (RemoteException e){
			return false;
		}
		return false;
	}

	public boolean delCandidato(int indx, int indxL){
		try {
			if(tryRmi()){
				server.deleteCandidateRMI(choiceGerirEleicao - 1,indxL, indx - 1);
				return true;

			}
		}catch (RemoteException e){
			return false;
		}
		return false;
	}

	public boolean delLista(int indx){
		try {
			if(tryRmi()){
				server.eliminarListaCandidatos(choiceGerirEleicao - 1, indx - 1);
				return true;

			}
		}catch (RemoteException e){
			return false;
		}
		return false;
	}

	public boolean delDepatamento(int index) {
		try {
			if(tryRmi()){
				server.deleteDepartamentos(choiceGerirEleicao - 1, index - 1);
				return true;

			}
		}catch (RemoteException e){
			return false;
		}
		return false;
	}

	public boolean addMesa(int index) {
		try {
			if(tryRmi()){
				server.criaMesaRMI(choiceGerirEleicao - 1,index - 1);
				return true;

			}
		}catch (RemoteException e){
			return false;
		}
		return false;
	}

	public boolean delMesa(int index) {
		try {
			if(tryRmi()){
				server.deleteMesaRMI(choiceGerirEleicao - 1,index - 1);
				return true;

			}
		}catch (RemoteException e){
			return false;
		}
		return false;
	}

	public int getsizeDepartamento() {
		try {
			if(tryRmi()){
				return server.sizeDepartamentos(choiceGerirEleicao - 1);
			}
		}catch (RemoteException e){
			return -1;
		}
		return -1;
	}

	public int getsizeMesas() {
		try {
			if(tryRmi()){
				return server.sizeMesas();
			}
		}catch (RemoteException e){
			return -1;
		}
		return -1;
	}

	public int getsizePessoas() {
		try {
			if(tryRmi()){
				return server.sizePessoas();
			}
		}catch (RemoteException e){
			return -1;
		}
		return -1;
	}

	public int getsizeMesasEleicao() {
		try {
			if(tryRmi()){
				return server.getEleicoesFuturas().get(choiceGerirEleicao - 1).getMesas().size();
			}
		}catch (RemoteException e){
			return -1;
		}
		return -1;
	}

	public int getsizeLista() {
		try {
			if(tryRmi()){
				return server.getEleicoesFuturas().get(choiceGerirEleicao - 1).getListasCandidatas().size();
			}
		}catch (RemoteException e){
			return -1;
		}
		return -1;
	}

	public int getsizeCandidatos() {
		try {
			if(tryRmi()){
				return server.getEleicoesFuturas().get(choiceGerirEleicao - 1).getListasCandidatas().get(choiceLista - 1).getSize();
			}
		}catch (RemoteException e){
			return -1;
		}
		return -1;
	}

	public String getRmiUserNome() {
		System.out.println("HERE");
		System.out.println(this.username);
		System.out.println(this);
		try {
			if(tryRmi()){
				return server.getPessoabyNumber(username).getNome();
			}
		}catch (RemoteException ignored){

		}
		return "";
	}

	public int getsizeEleicoesEnded() {
		try {
			if(tryRmi()){
				return server.getEleicoesEnded().size();
			}
		}catch (RemoteException e){
			return -1;
		}
		return -1;
	}

	public ArrayList<String> getEleicoesPassadas() throws RemoteException {
		try {
			if(tryRmi()){
				String array[] = server.eleicoesEndedRMI().split("\n");
				List<String> al = new ArrayList<String>();
				al = Arrays.asList(array);
				return new ArrayList<>(al);
			}
		}catch (RemoteException ignored){

		}
		return new ArrayList<>();
	}

	public int getChoiceLista() {
		return choiceLista;
	}

	public void setChoiceLista(int choiceLista) {
		this.choiceLista = choiceLista;
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

	public ArrayList<String> getEleicoesUser(){
		try {
			if(tryRmi()){
				return server.getEleicoesUser(this.username);
			}
			return new ArrayList<>();

		}catch (RemoteException ignored){

		}
		return new ArrayList<>();
	}

	public String getDescricaoEleicao(){
		try {
			if(tryRmi()){
				return server.getEleicoesFuturas().get(choiceGerirEleicao - 1).getDescricao();
			}
			return "";

		}catch (RemoteException ignored){

		}
		return "";

	}

	public String getInicioEleicao(){
		try {
			if(tryRmi()){
				return dateToString(server.getEleicoesFuturas().get(choiceGerirEleicao - 1).getStartDate());
			}
			return "";

		}catch (RemoteException ignored){

		}
		return "";

	}

	public ArrayList<String> getListasEleicao(String eleicao){

		try {
			if(tryRmi()){
				return server.getListasEleicao(eleicao);
			}
			return new ArrayList<>();

		}catch (RemoteException ignored){

		}
		return new ArrayList<>();

	}

	public ArrayList<String> getAllMesas() throws RemoteException {
		try {
			if(tryRmi()){
				String array[] = server.showMesas().split("\n");
				List<String> al = new ArrayList<String>();
				al = Arrays.asList(array);
				return new ArrayList<>(al);
			}
		}catch (RemoteException ignored){

		}
		return new ArrayList<>();
	}

	public ArrayList<String> getAllMesasEleicao() throws RemoteException {
		try {
			if(tryRmi()){
				String array[] = server.showMesasEleicao(choiceGerirEleicao - 1).split("\n");
				List<String> al = new ArrayList<String>();
				al = Arrays.asList(array);
				return new ArrayList<>(al);
			}
		}catch (RemoteException ignored){

		}
		return new ArrayList<>();
	}

	public String getFimEleicao(){
		try {
			if(tryRmi()){
				return dateToString(server.getEleicoesFuturas().get(choiceGerirEleicao - 1).getEndDate());
			}
			return "";

		}catch (RemoteException ignored){

		}
		return "";

	}

	public String dateToString(Date date){
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm");
		String strDate = dateFormat.format(date);
		String[] arr = strDate.split("T");
		return arr[0] + " " + arr[1];
	}

	public boolean changeEleicao(String change, int answer) {
		try {
			if(tryRmi()){
				return server.changeEleicoesRMI(choiceGerirEleicao - 1,answer,change);
			}
		}catch (RemoteException ignored){

		}
		return false;
	}

	public void setEleicaoChoice(String choice){
		this.eleicaoChoice = choice;
	}

	public boolean votar( String voto){
		try {
			System.out.println(eleicaoChoice);
			System.out.println(voto);
			return server.votarweb(eleicaoChoice, voto, username, "WEB");
		} catch (RemoteException e) {
			return false;
		}
	}

	public boolean votoBranco(){
		try{
			return server.votarbrancoweb(eleicaoChoice,username);
		} catch (RemoteException e) {
			return false;
		}

	}
	public boolean votoNulo(){
		try{
			return server.votarnuloweb(eleicaoChoice,username);
		} catch (RemoteException e) {
			return false;
		}

	}

	public boolean setFacebookID(String id) {
		try {
			if(tryRmi()){
				server.setFacebookID_PessoabyNumber(username,id);
				return true;
			}
		}catch (RemoteException ignored){

		}
		return false;
	}

	public String getUserFacebookID() {
		try {
			if(tryRmi()){
				return server.getFacebookID_PessoabyNumber(username);
			}
		}catch (RemoteException ignored){

		}
		return "";
	}

	public int findFacebookIDUser(String id) {
		try {
			if(tryRmi()){
				return server.findFacebookID_Pessoa(id);
			}
		}catch (RemoteException ignored){

		}
		return -1;
	}

	//FACEBOOK BEAN

	public boolean getAccessToken(){
		this.accessToken = this.fb.getAccessToken(this.authCode,this.secretState);
		return this.accessToken != null;
	}

	public String getAuthURL(){
		this.authURL = this.fb.getAuthorizationURL();
		return authURL;
	}
	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getSecretState() {
		return secretState;
	}

	public void setSecretState(String secretState) {
		this.secretState = secretState;
	}

	public void setName(String name){this.name = name;}

	public String getName() throws ParseException {
		return this.fb.getAccountName(this.accessToken);
	}

	public String getFacebookID() throws ParseException {
		return this.fb.getAccountID(this.accessToken);
	}


}

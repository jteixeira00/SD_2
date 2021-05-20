package rmiserver;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class RmiServer extends UnicastRemoteObject implements RmiInterface {

    private static final long serialVersionUID = 1L;
    public int addressEnd = 1;
    public String baseAddress;
    public String secondaryAddress;
    private ArrayList<Eleicao> listaEleicoes;
    private ArrayList<Pessoa> listaPessoas;
    private ArrayList<Pessoa> pessoasOnline;
    private ArrayList<Mesa> listaMesas;
    private ArrayList<AdminTerminalInterface> terminais = new ArrayList<>();
    private String rmiport;
    private String rminame;
    private String registry;
    private AdminTerminalInterface WS;
    public RmiServer() throws RemoteException {
        super();
        this.listaPessoas = new ArrayList<>();
        this.listaEleicoes = new ArrayList<>();
        this.pessoasOnline = new ArrayList<>();
        this.listaMesas = new ArrayList<>();

        //load properties file
        GetPropertyValues properties = new GetPropertyValues();
        try {
            properties.setPropValues();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.baseAddress = properties.getMain_multicast_pool();
        this.secondaryAddress = properties.getSecondary_multicast_pool();
        this.rmiport = properties.getRmiport();
        this.rminame = properties.getRminame();
        this.registry = properties.getRegistry();

        load();
        System.out.println(showEleicoesFuturas());
    }

    public double add(double a, double b) throws RemoteException {
        return a + b;
    }


    @Override
    public String getNewAddress() throws RemoteException {
        return baseAddress + addressEnd;
    }
    //em caso de falha do rmi principal
    public String getSecondaryAddress() throws RemoteException{
        return secondaryAddress + addressEnd;
    }

    public int getTableNumber(String arg) throws RemoteException {
        return addressEnd++;
    }

    public void notifyOfNewTable(String arg) throws RemoteException{
        if(WS!=null) {
            WS.sendMessage("New table connected at department " + arg);
        }
        for(AdminTerminalInterface a:getTerminais()){
            try{
                a.tableUpdate(arg);}
            catch(RemoteException e){
                //ignore
            }
        }
    }

    public static void main(String args[]) {


        //load properties
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
        System.setProperty("java.security.policy", "client.policy");
        try {
            Registry rmi = LocateRegistry.getRegistry(registry, Integer.parseInt(rmiport));
            RmiInterface ri;
            int tries = 1;
            while(tries<=3){
                try{
                    ri = (RmiInterface) rmi.lookup(rminame);
                    ri.heartbeat();
                    tries = 0;
                }
                catch (RemoteException e){
                    tries+=1;
                    System.out.println("Tentei ligar-me ao servidor RMI primário sem sucesso " + tries+"x");
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ri = new RmiServer();
            LocateRegistry.createRegistry(Integer.parseInt(rmiport)).rebind(rminame, ri);
            System.out.println("Servidor RMI secundário é agora o servidor primário");

        } catch (RemoteException | NotBoundException ex1) {
            try {
                RmiInterface ri = new RmiServer();
                LocateRegistry.createRegistry(Integer.parseInt(rmiport)).rebind(rminame, ri);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void heartbeat(){
        return;
    }

    public ArrayList<AdminTerminalInterface> getTerminais(){
        return terminais;
    }

    @Override
    public ArrayList<Eleicao> getEleicoes() throws RemoteException {
        return listaEleicoes;
    }

    @Override
    public ArrayList<Pessoa> getPessoas() throws RemoteException {
        return listaPessoas;
    }

    @Override
    public ArrayList<MulticastServer> getMesas() throws RemoteException {
        return null;
    }

    /* ================ LOGINS LOGOUTS ======================== */

    @Override
    public boolean login(String numero, String password) throws RemoteException {
        System.out.println("Procurando utilizador com nº " + numero);
        Pessoa p = getPessoabyNumber(numero);

        if(p == null) {
            if(WS!=null){
            WS.sendMessage("Failed login attempt");}
            return false;
        }
        if (p.getPassword().equals(password)) {
            if(WS!=null){
            WS.sendMessage("User with number " + numero + " logged in");}
            return true;
        } else {
            if(WS!=null){
            WS.sendMessage("User with number " + numero + " wrong password");}
            return false;
        }

    }

    public void logout(String numero) throws RemoteException {
        if(WS!=null) {
            WS.sendMessage("User with number " + numero + " logged out");
        }
        //this.pessoasOnline.remove(getPessoabyNumber(numero));
    }

    /* =============== Eleições e votar ===============*/

    public ArrayList<Eleicao> eleicoesOngoing() throws RemoteException {
        ArrayList<Eleicao> res = new ArrayList<>();
        Date date = new Date();
        for (Eleicao e : getEleicoes()) {
            if (date.after(e.getStartDate()) && date.before(e.getEndDate())) {
                res.add(e);
            }
        }
        return res;
    }

    public boolean votar(int eleicao, int choiceLista, String number, String departamento, int tableCount) throws RemoteException{
        ArrayList<Eleicao> eArray = new ArrayList<>();
        Date date = new Date();
        Pessoa p = getPessoabyNumber(number);



        for (Eleicao e1 : getMesaByName(departamento).getEleicoesEspecificas(p.getType().toString())) {
            if(e1.getEndDate().after(date) && e1.getStartDate().before(date)) {
                /*System.out.println("---------");
                System.out.println(e1.getTitulo());
                System.out.println(e1.getListasCandidatas().get(0).getNome());
                System.out.println("---------");*/
                eArray.add(e1);
            }
        }

        Eleicao e = eArray.get(eleicao);



        if(choiceLista == 0){
            e.addVotoNulo();
            Voto v = new Voto(p, departamento);
            e.addVoto(v);
            save();
        }

        else if(choiceLista == 1){
            e.addVotoBranco();
            Voto v = new Voto(p, departamento);
            e.addVoto(v);
            save();
        }

        else {

            Voto v = new Voto(p, departamento);
            System.out.println(e.getTitulo());
            e.getListasCandidatas().get(choiceLista-2).addVoto();
            /*
            try {

                if (!this.eleicoesOngoing().contains(e)) {
                    System.out.println("here");
                    return false;
                }
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
                return false;
            }
                */
            e.addVoto(v);
        }

        tableCount = countVotosDep(departamento);

        for(AdminTerminalInterface ad: terminais){
            try{
                ad.voteUpdate(departamento, tableCount);}
            catch(RemoteException e1){
                //ignore
            }
        }
        save();
        if(WS!=null) {
            WS.sendMessage("User with number " + number + " casted a vote");
        }
        return true;
    }

    public int countVotosDep(String dep){
        int count = 0;
        for(Eleicao e: listaEleicoes){
            for(Voto v: e.getVotos()){
                if(v.getLocal().equals(dep)){
                    count++;
                }
            }
        }
        return count;
    }

    //verifica se nome da eleição já existe
    public boolean checkNomeEleicao(String titulo) throws RemoteException{
        for(Eleicao e: listaEleicoes){
            if(e.getTitulo().equals(titulo)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Eleicao createEleicaoRMI(String titulo, String descricao, String startDate, int startHour, int startMinute, String endDate, int endHour, int endMinute, String departamento, int type) throws RemoteException {
        Date startDate1 = null;
        try {
            startDate1 = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm").parse(parseDate(startDate, startHour, startMinute));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate1 = null;
        try {
            endDate1 = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm").parse(parseDate(endDate, endHour, endMinute));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        if (startDate1.after(endDate1) || startDate1.before(date)) {
            return null;
        }

        try {
            Eleicao e = new Eleicao(titulo, descricao, startDate, startHour, startMinute, endDate, endHour, endMinute, departamento, type);
            this.listaEleicoes.add(e);
            save();
            return e;
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        if(WS!=null) {
            WS.sendMessage("Eleição criada");
        }
        return null;
    }

    @Override
    public boolean createUserRMI(int tipo, String nome, String numero, String dep, String fac, String contacto, String morada, String cc, String validadecc, String password) throws RemoteException {
        for (Pessoa aux : getPessoas()) {
            if (aux.getNumero().equals(numero)) {
                return false;
            }
        }
        Pessoa p = new Pessoa(tipo, nome, numero, dep, fac, contacto, morada, cc, validadecc, password);
        this.addPessoaLista(p);
        save();
        return true;
    }

    //eliminar candidato com index delete, na lista com index choice, na eleição com index indx na lista de eleições futuras
    @Override
    public boolean deleteCandidateRMI(int indx, int choice, int delete) throws RemoteException {
         ArrayList<Lista> aux = getEleicoesFuturas().get(indx).getListasCandidatas();
        aux.get(choice).getMembros().remove(delete);
        getEleicoesFuturas().get(indx).setListasCandidatas(aux);
        save();
        return true;
    }

    //devolve string para ser usada posteriormente no AdminTerminal para apresentar as pessoas presentes no rmi
    @Override
    public String showPessoas() throws RemoteException {
        String peeps = "";
        int i = 1;
        String is;
        for(Pessoa p: listaPessoas){
            is = String.valueOf(i);
            peeps += is + " - " + p.getNome() + " || " + p.getNumero() + " || " + p.getType() + " || " + p.getDepartamento() + '\n';
            i++;
        }
        return peeps;
    }

    public ArrayList<String> showPessoasArray() throws RemoteException {
        ArrayList<String> pessoas = new ArrayList<>();
        for(Pessoa p: listaPessoas){
            pessoas.add(p.getNome());
        }
        return pessoas;
    }

    @Override
    public int sizePessoas() throws RemoteException {
        return listaPessoas.size();
    }

    //indx - Eleicao || choice - lista || addm - pessoa
    @Override
    public boolean addCandidateRMI(int indx, int choice, int addm) throws RemoteException {
        ArrayList<Lista> aux = getEleicoesFuturas().get(indx).getListasCandidatas();
        Pessoa p = listaPessoas.get(addm);
        for(Pessoa pep : getEleicoesFuturas().get(indx).getListasCandidatas().get(choice).getMembros()){
            if(pep == p)
                return false;
        }
        aux.get(choice).getMembros().add(p);
        getEleicoesFuturas().get(indx).setListasCandidatas(aux);
        save();
        return true;
    }

    //adiciona mesa a eleição e vice-versa
    //indxE - indice eleição na lista de eleições futuras no rmi
    //indexM - indice mesa na lista de mesas no rmi
    @Override
    public boolean criaMesaRMI(int indexE, int indexM) throws RemoteException {
        for(Mesa m: getEleicoesFuturas().get(indexE).getMesas()){
            if(m.getDepartamento().equals(listaMesas.get(indexM).getDepartamento())){
                return false;
            }
        }
        getEleicoesFuturas().get(indexE).addMesa(listaMesas.get(indexM));
        listaMesas.get(indexM).addEleicao(getEleicoesFuturas().get(indexE));
        save();
        return true;
    }

    //devolve string para ser usada posteriormente no AdminTerminal para apresentar as mesas presentes no rmi
    @Override
    public String showMesas() throws RemoteException {
        String str = "";
        String number;
        int i = 0;
        for(Mesa m: listaMesas){
            i++;
            number = String.valueOf(i);
            str += number + " - " + m.getDepartamento() + "\n";
        }
        return str;
    }

    //devolve string para ser usada posteriormente no AdminTerminal para apresentar os departamentos de uma eleição com index indexE
    @Override
    public String showDepartamentos(int indexE) throws RemoteException {
        Eleicao e = getEleicoesFuturas().get(indexE);
        String str = "";
        String number;
        int i = 0;
        for(String s: e.getDepartamentos()){
            i++;
            number = String.valueOf(i);
            str += number + " - " + s + "\n";
        }
        return str;
    }



    //devolve mesa com o departamento dep
    public Mesa getMesaByName(String dep) throws RemoteException{
        for (Mesa m: listaMesas){
            if( m.getDepartamento().equals(dep)){
                return m;
            }
        }
        return  null;
    }

    //devolve string para ser usada posteriormente no AdminTerminal para apresentar as mesas de uma eleição com index indx
    @Override
    public String showMesasEleicao(int indx) throws RemoteException {
        String str = "";
        String number;
        int i = 0;
        for(Mesa m: getEleicoesFuturas().get(indx).getMesas()){
            i++;
            number = String.valueOf(i);
            str += number + " - " + m.getDepartamento() + "\n";
        }
        return str;
    }

    //verifica se o user já votou
    public boolean alreadyVoted(String departamento, int choice, String tipoUser, String numeroUc) throws RemoteException {
        Mesa mesaByName = null;
        try {
            mesaByName = getMesaByName(departamento);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        ArrayList<Eleicao> aux = new ArrayList<>();
        for (Eleicao e : mesaByName.getEleicoes()) {
            if(e.getEndDate().after(date) && e.getStartDate().before(date)) {
                if(e.getTipoVoters().toString().equals(tipoUser)){
                    aux.add(e);
                }
            }
        }
        Eleicao e = aux.get(choice-1);

        for(Voto v: e.getVotos()){
            if (v.getEleitor().getNumero().equals(numeroUc)){
                return true;
            }
        }

        return false;
    }

    @Override
    public int sizeMesas() throws RemoteException {
        if(listaMesas == null)
            return 0;
        return listaMesas.size();
    }

    @Override
    public int sizeMesasEleicao(int indx) throws RemoteException {
        if(getEleicoesFuturas().get(indx).getMesas() == null)
            return 0;
        return getEleicoesFuturas().get(indx).getMesas().size();
    }

    //elimina a mesa com index indexM da eleição com index indexE
    @Override
    public boolean deleteMesaRMI(int indexE,int indexM) throws RemoteException {
        for(Mesa m: listaMesas){
            if(m.getDepartamento().equals(getEleicoesFuturas().get(indexE).getMesas().get(indexM).getDepartamento())){
                m.getEleicoes().remove(getEleicoesFuturas().get(indexE));
            }
        }
        getEleicoesFuturas().get(indexE).getMesas().remove(indexM);
        save();
        return true;
    }

    //devolve string para ser usada posteriormente no AdminTerminal para apresentar as eleições que ainda não começaram
    @Override
    public String showEleicoesFuturas() throws RemoteException{
        ArrayList<Eleicao> res = this.getEleicoesFuturas();
        String str = "";
        int indx;
        String indxS;
        for(int i = 0; i < this.getEleicoesFuturas().size(); i++) {
            indx = i + 1;
            indxS = Integer.toString(indx);
            str += indxS + " - " + this.getEleicoesFuturas().get(i).getTitulo() + '\n';
        }
        return str;
    }



    /* ====================== FILES =========================== */
    public void load() {
        ObjectInputStream is1 = null;
        ObjectInputStream is2 = null;
        ObjectInputStream is3 = null;
        File f = new File("eleicoes.ser");
        File f2 = new File("pessoas.ser");
        File f3 = new File("mesas.ser");

        if(f2.exists() && !f2.isDirectory()){
            try {
                FileInputStream stream = new FileInputStream("pessoas.ser");
                is2 = new ObjectInputStream(stream);
                this.listaPessoas = (ArrayList<Pessoa>) is2.readObject();
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                if (is2 != null) {
                    try {
                        is2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(f3.exists() && !f3.isDirectory() && f.exists() && !f.isDirectory()  ){
            try {
                FileInputStream stream = new FileInputStream("mesas.ser");
                is3 = new ObjectInputStream(stream);
                this.listaMesas = (ArrayList<Mesa>) is3.readObject();
                FileInputStream stream2 = new FileInputStream("eleicoes.ser");
                is1 = new ObjectInputStream(stream2);
                this.listaEleicoes = (ArrayList<Eleicao>) is1.readObject();

            } catch(Exception e){
                e.printStackTrace();
            } finally {
                try {
                    if (is1 != null) {
                        is1.close();
                    }
                    if (is2 != null) {
                        is2.close();
                    }
                    if (is3 != null) {
                        is3.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    synchronized public void save() {
        ObjectOutputStream os1 = null;
        ObjectOutputStream os2 = null;
        ObjectOutputStream os3 = null;

        try {
            FileOutputStream stream = new FileOutputStream("eleicoes.ser");
            os1 = new ObjectOutputStream(stream);
            os1.writeObject(this.listaEleicoes);

            stream = new FileOutputStream("pessoas.ser");
            os2 = new ObjectOutputStream(stream);
            os2.writeObject(this.listaPessoas);

            stream = new FileOutputStream("mesas.ser");
            os3 = new ObjectOutputStream(stream);
            os3.writeObject(this.listaMesas);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os1 != null) {
                    os1.close();
                }
                if (os2 != null) {
                    os2.close();
                }
                if (os3 != null) {
                    os3.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* === AUX METHODS === */

    //devolve a pessoa com o número de estudante numero
    public Pessoa getPessoabyNumber(String numero) throws RemoteException{
        for (Pessoa p : this.listaPessoas) {
            if (p.getNumero().equals(numero)) {
                return p;
            }
        }
        return null;
    }

    public void setFacebookID_PessoabyNumber(String numero,String facebookID) throws RemoteException{
        for (Pessoa p : this.listaPessoas) {
            if (p.getNumero().equals(numero)) {
                p.setFacebookID(facebookID);
            }
        }
        save();
    }

    public int findFacebookID_Pessoa(String facebookID) throws RemoteException{
        int index = 0;
        for (Pessoa p : this.listaPessoas) {
            if (p.getFacebookID().equals(facebookID)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public String getFacebookID_PessoabyNumber(String numero) throws RemoteException{
        for (Pessoa p : this.listaPessoas) {
            if (p.getNumero().equals(numero)) {
                return p.getFacebookID();
            }
        }
        return "";
    }

    //adiciona departamento à eleição com o nome nome
    @Override
    public void addDepartamentos(String nome, String departamento) throws RemoteException {
        for(Eleicao e: getEleicoesFuturas()){
            if(e.getTitulo().equals(nome)){
                if(!e.getDepartamentos().contains(departamento))
                    e.addDepartamento(departamento);
            }
        }
    }

    //adiciona departamento à eleição com o indexE na lista de eleições futuras
    @Override
    public void addDepartamentos(int indexE, String departamento) throws RemoteException {
        Eleicao e = getEleicoesFuturas().get(indexE);
        if(!e.getDepartamentos().contains(departamento))
            e.addDepartamento(departamento);
    }

    //elimina o departamento com o index departamento na eleição com o indexE na lista de eleições futuras
    @Override
    public void deleteDepartamentos(int indexE, int departamento) throws RemoteException {
        Eleicao e = getEleicoesFuturas().get(indexE);
        String dep = e.getDepartamentos().get(departamento);
        e.deleteDepartamento(dep);
    }

    @Override
    public int sizeDepartamentos(int indexE) throws RemoteException{
        return getEleicoesFuturas().get(indexE).sizeDepartamentos();
    }

    public void addOnlineUser(Pessoa p) {
        this.pessoasOnline.add(p);
    }

    //adiciona a pessoa p à lista de todas as pessoas no rmi
    public void addPessoaLista(Pessoa p) {
        this.listaPessoas.add(p);
        save();
    }

    public String parseDate(String date, int hour, int minute) {
        String sHour = "" + hour;
        if (sHour.length() == 1) {
            sHour = "0" + sHour;
        }
        String sMinute = "" + minute;
        if (sMinute.length() == 1) {
            sMinute = "0" + sMinute;
        }
        return date + "T" + sHour + ":" + sMinute;
    }

    //devolve o arraylist com as eleições que ainda não começaram
    @Override
    public ArrayList<Eleicao> getEleicoesFuturas() throws RemoteException {
        Date date = new Date();
        ArrayList<Eleicao> res = new ArrayList<>();
        try {
            for (Eleicao e : getEleicoes()) {
                if (date.before(e.getStartDate())) {

                    res.add(e);
                }
            }
            return res;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;

    }

    //devolve o arraylist com as eleições que já terminaram
    @Override
    public ArrayList<Eleicao> getEleicoesEnded() throws RemoteException {
            Date date = new Date();
            ArrayList<Eleicao> res = new ArrayList<>();
            try {
                for (Eleicao e : getEleicoes()) {
                    if (e.getEndDate().before(date) || e.getEndDate().equals(date)) {
                        res.add(e);
                    }
                }
                return res;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return null;

        }

    @Override
    public int sizeEleicoesFuturas() throws RemoteException{
        return getEleicoesFuturas().size();
    }

    //delove uma string com os detalhes de uma eleição
    @Override
    public String showEleicoesDetalhes(int index) throws RemoteException {
        Eleicao eleicao = getEleicoesFuturas().get(index);
        return "\n1 - Titulo: " + eleicao.getTitulo() + "\n2 - Descrição: " + eleicao.getDescricao() + "\n3 - Data de Inicio (dd-MM-yyyy  HH:mm): " + eleicao.dateToString(eleicao.getStartDate()) + "\n4 - Data de Fim (dd-MM-yyyy  HH:mm): " + eleicao.dateToString(eleicao.getEndDate());
    }

    @Override
    public String showEleicoesDetalhesAgora(int index) throws RemoteException {
        Eleicao eleicao = getEleicoes().get(index);
        String mesasS = "";
        String listasS = "";
        if(eleicao.getEndDate().after(new Date())) {
            if(eleicao.getMesas().size() > 0){
                mesasS = "\n----------------\nMesas:";
                for(Mesa m : eleicao.getMesas()){
                    mesasS += "\n" + m.getDepartamento();
                }
            }
            if(eleicao.getListasCandidatas().size() > 0){
                listasS = "\n----------------\nListas:";
                for(Lista l : eleicao.getListasCandidatas()){
                    listasS += "\n" + l.getNome();
                }
            }
            return "\n==================\nTitulo: " + eleicao.getTitulo() + "\nDescrição: " + eleicao.getDescricao() + "\nData de Inicio (dd-MM-yyyy  HH:mm): " + eleicao.dateToString(eleicao.getStartDate()) + "\nData de Fim (dd-MM-yyyy  HH:mm): " + eleicao.dateToString(eleicao.getEndDate()) + mesasS + listasS;
        }
        return "";
    }

    @Override
    public String showEleicoesDetalhesEnded(int index) throws RemoteException {
        Eleicao eleicao = getEleicoesEnded().get(index);
        return "\n1 - Titulo: " + eleicao.getTitulo() + "\n2 - Descrição: " + eleicao.getDescricao() + "\n3 - Data de Inicio (dd-MM-yyyy  HH:mm): " + eleicao.dateToString(eleicao.getStartDate()) + "\n4 - Data de Fim (dd-MM-yyyy  HH:mm): " + eleicao.dateToString(eleicao.getEndDate());
    }

    //altera as propriedades textuais de uma eleição
    //index: index da eleição
    //answer: que propriedade queremos alterar
    //change: como queremos alterar
    @Override
    public boolean changeEleicoesRMI(int index, int answer, String change) throws RemoteException {
        Eleicao eleicao = getEleicoesFuturas().get(index);
        switch (answer) {
            case 1:
                //alterar titulo
                for(Eleicao e : getEleicoesFuturas()){
                    if(change.equals(e.getTitulo()))
                        return false;
                }
                eleicao.setTitulo(change);
                break;
            case 2:
                //alterar descrição
                eleicao.setDescricao(change);
                break;
            case 3:
                //alterar data inicio
                try {
                    Date data = parseDateString(change);
                    if(data.before(eleicao.getEndDate()))
                        eleicao.setStartDate(data);
                    else
                        return false;
                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }
                break;
            case 4:
                //alterar data de fim
                try {
                    Date data = parseDateString(change);
                    if(data.after(eleicao.getStartDate()))
                        eleicao.setEndDate(data);
                    else
                        return false;

                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }
                break;

            default:
                return false;
        }
        save();
        return true;
    }

    //devolve uma string com os detalhes de voto de um user: eleição|local|momento
    @Override
    public String showVotoDetalhesRMI(int indx) throws RemoteException {
        Pessoa eleitor = listaPessoas.get(indx);
        String str = "";
        int posicao = 0;
        for(Eleicao e : getEleicoes()){
            for(Voto v : e.getVotos()){
                if(v.getEleitor().getNome().equals(eleitor.getNome())){
                     str += "\n.................\n" + posicao + " - Eleição: " + e.getTitulo() + "\nLocal de Voto: " + v.getLocal() + "\nMomento de Voto: " + v.getData() + "\n";
                }
            }
            posicao++;
        }
        if(str.equals("")){
            return "\n.................\nO eleitor ainda não votou.\n.................\n";
        }
        return str;
    }

    @Override
    public String showVotoDetalhesRMINome(String nome) throws RemoteException {
        Pessoa eleitor = getPessoabyNumber(nome);
        String str = "";
        int posicao = 0;
        for(Eleicao e : getEleicoes()){
            for(Voto v : e.getVotos()){
                if(v.getEleitor().getNome().equals(eleitor.getNome())){
                    str += "\n.................\n" + posicao + " - Eleição: " + e.getTitulo() + "\nLocal de Voto: " + v.getLocal() + "\nMomento de Voto: " + v.getData() + "\n";
                }
            }
            posicao++;
        }
        if(str.equals("")){
            return "\n.................\nO eleitor ainda não votou.\n.................\n";
        }
        return str;
    }

    //devolve uma string com os resultados de uma eleição: lista - #votos|percentagem
    @Override
    public String showVotosRMI(Eleicao eleicao) throws RemoteException {
        int count;
        int percent;
        String countS;
        String percentS;
        String str = "\nResultados:\n";
        if(eleicao.getListasCandidatas().size() != 0) {
            for (Lista list : eleicao.getListasCandidatas()) {
                count = list.getVotos();
                if (eleicao.votosTotal() == 0)
                    percent = 0;
                else
                    percent = (count * 100)/ eleicao.votosTotal();
                countS = Integer.toString(count);
                percentS = Integer.toString(percent);
                str += "\n.................\nLista " + list.getNome() + "\nVotos: " + countS + " | " + percentS +"%";
            }

            count = eleicao.getVotosBrancos();
            if (eleicao.votosTotal() == 0)
                percent = 0;
            else
                percent = (count * 100)/ eleicao.votosTotal();
            countS = Integer.toString(count);
            percentS = Integer.toString(percent);
            str += "\n................." + "\nVotos em Branco: " + countS + " | " + percentS + "%";

            count = eleicao.getVotosNulos();
            if (eleicao.votosTotal() == 0)
                percent = 0;
            else
                percent = (count * 100)/ eleicao.votosTotal();
            countS = Integer.toString(count);
            percentS = Integer.toString(percent);
            str += "\n................." + "\nVotos Nulos: " + countS + " | " + percentS + "%\n.................\n";
        }
        else
            str = "\nResultados:\nSem Listas Candidatas\n";
        return str;
    }

    //devolve uma string com as eleições que já terminaram
    @Override
    public String eleicoesEndedRMI() throws RemoteException {
        String str = "";
        if(getEleicoesEnded().size() != 0) {
            for (int i = 0; i < getEleicoesEnded().size(); i++) {
                str += showEleicoesDetalhesEnded(i) + "\n" + showVotosRMI(getEleicoesEnded().get(i));
            }
        }
        else{
            return "Impossivel Realizar Operação: Eleições Passadas Inexistentes.\n";
        }
        return str;
    }


    public String eleicoesAgoraRMI() throws RemoteException {
        String str = "";
        if(getEleicoes().size() != 0) {
            for (int i = 0; i < getEleicoes().size(); i++) {
                str += showEleicoesDetalhesAgora(i) + "\n";
            }
        }
        else{
            return "Impossivel Realizar Operação: Eleições Inexistentes.\n";
        }
        return str;
    }

    public Date parseDateString(String string) throws RemoteException, ParseException {
        Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(string);
        return date;
    }

    //adiciona uma mesa mesa a uma eleição e
    public void adicionarMesa(Eleicao e, Mesa mesa) throws RemoteException {
        mesa.addEleicao(e);
        e.addMesa(mesa);
        save();
    }


    //adiciona uma mesa m à lista de mesas do rmi
    public void addMesa(Mesa m) throws RemoteException{
        for(Mesa m2: listaMesas){
            if(m2.getDepartamento().equals(m.getDepartamento())){
                return;
            }
        }
        listaMesas.add(m);
        save();
    }

    //procura user na lista de Pessoas
    public String identificarUser(String input){
        for(Pessoa p: listaPessoas){
            if(p.getNumero().equals(input)){
                return "Bem vindo "+ p.getNome();
            }
        }
        return "Utilizador inexistente";
    }

    //cria uma lista com o nome nome na eleição com index inx
    @Override
    public boolean createListaRMI(int inx, String nome) throws RemoteException {
        Eleicao eleicao = getEleicoesFuturas().get(inx);
        for(Lista l : eleicao.getListasCandidatas()){
            if(l.getNome().equals(nome)){
                return false;
            }
        }
        Lista list = new Lista(null,nome);
        eleicao.addListasCandidatas(list);
        save();
        return true;
    }

    //elimina a lista com index i da eleição com index indx
    @Override
    public void eliminarListaCandidatos(int indx, int i) throws RemoteException{
        getEleicoesFuturas().get(indx).getListasCandidatas().remove(i);
        save();
    }

    //verifica se user pode votar na eleição em relação departamento
    public boolean doesItBelong(String departamento, int choice, String numeroUc, String tipoUser) throws RemoteException{
        Mesa mesaByName = null;
        try {
            mesaByName = getMesaByName(departamento);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        ArrayList<Eleicao> aux = new ArrayList<>();
        for (Eleicao e : mesaByName.getEleicoes()) {
            if(e.getEndDate().after(date) && e.getStartDate().before(date)) {
                if(e.getTipoVoters().toString().equals(tipoUser)){
                    aux.add(e);
                }
            }
        }
        Eleicao e = aux.get(choice-1);
        if(e.getDepartamentos().size() == 0){
            return true;
        }
        Pessoa p = null;
        try {
            p = getPessoabyNumber(numeroUc);
        } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
        }
        for(String s: e.getDepartamentos()){
            if(p.getDepartamento().equals(s)){
                return true;
            }
        }
        return false;
    }

    //guarda um AdminTerminalInterface admin numa lista do Rmi
    //De modo a poder dar callback e usar as funções aí presentes,
    public void subscribe(AdminTerminalInterface admin) throws RemoteException{
        terminais.add(admin);
    }

    //cria e devolve a mensagem do tipo "type|item_list;item_count|" + listasCandidatas
    //para ser entregue ao Voting term de modo ao votante poder votar
    public String generateLista(int eleicaoC, String dep) throws RemoteException{
        ArrayList<Eleicao> eArray = new ArrayList<>();
        Date date = new Date();



        for (Eleicao e1 : getMesaByName(dep).getEleicoes()) {
            if(e1.getEndDate().after(date) && e1.getStartDate().before(date)) {
                //System.out.println(e1.getTitulo());
                if(e1.getListasCandidatas().size()>0){
                    //System.out.println(e1.getListasCandidatas().get(0).getNome());
                }

                eArray.add(e1);
            }
        }

        Eleicao eleicao = eArray.get(eleicaoC);

        /*
        for(Eleicao e: getMesaByName(dep).getEleicoes()){
            System.out.println("eleição: " + e.getTitulo());
            for(Lista l: e.getListasCandidatas()){
                System.out.println("lista "+ l.getNome());
            }
        }*/

        StringBuilder str = new StringBuilder("type|item_list;item_count|" + eleicao.getListasCandidatas().size());
        int i = 0;
        for(Lista l : eleicao.getListasCandidatas()){
            //System.out.println(l.getNome());
            str.append(";item_").append(i).append("_name|");
            for(Pessoa p : l.getMembros()){
                //System.out.println(p.getNome());
                str.append(p.getNome()).append(",");
            }
            i++;
        }
        //System.out.println(str);
        return str.toString();
    }

    //Avisa que se desconectou dos terminais - callback
    public void terminarMesa(String departamento) throws RemoteException{
        if(WS!=null) {
            WS.sendMessage("Table at department " + departamento + " terminated");
        }
        for(AdminTerminalInterface a:getTerminais()){
            try{

                a.tableDisconnectedUpdate(departamento);}
            catch(RemoteException e){
                //ignore
            }
        }
    }

    //Avisa que se conectou aos terminais - callback
    public void newTerminal(String departamento) throws RemoteException{
        if(WS!=null) {
            WS.sendMessage("New terminal at department " + departamento);
        }
        for(AdminTerminalInterface a:getTerminais()){
            try{
                a.terminalUpdate(departamento);}
            catch(RemoteException e){
                //ignore
            }
        }
    }

    public ArrayList<String> getEleicoesUser(String username) throws RemoteException{
        ArrayList<String> res = new ArrayList<>();
        Date date = new Date();
        for (Eleicao e : listaEleicoes) {
            if(e.getEndDate().after(date) && e.getStartDate().before(date)) {
                if(e.getTipoVoters().toString().equals(getPessoabyNumber(username).getType().toString())){
                    for(String dep : e.getDepartamentos()){
                        if(getPessoabyNumber(username).getDepartamento().equals(dep)){
                            res.add(e.getTitulo());
                        }
                    }
                }
            }
        }
        return res;
    }

    public ArrayList<String> getListasEleicao(String eleicao) throws RemoteException{
        ArrayList<String> res = new ArrayList<>();
        for(Eleicao e: listaEleicoes){
            if(e.getTitulo().equals(eleicao)){
                for(Lista l: e.getListasCandidatas()){
                    res.add(l.getNome());
                }
            }
        }
        return res;
    }

    public boolean votarbrancoweb(String nomeEleicao, String number) throws RemoteException{
        Pessoa p = getPessoabyNumber(number);
        Eleicao e= null;
        for (Eleicao e1 : getEleicoes()) {
            if(e1.getTitulo().equals(nomeEleicao)) {
                e = e1;
            }
        }

        for(Voto v: e.getVotos()){
            if(v.getEleitor() == p){
                return false;
            }
        }
        if(e==null){
            return false;
        }
        e.addVotoBranco();
        Voto v = new Voto(p, "WEB");
        e.addVoto(v);
        save();
        return true;
    }

    public boolean votarnuloweb(String nomeEleicao, String number) throws RemoteException{
        Pessoa p = getPessoabyNumber(number);
        Eleicao e= null;
        for (Eleicao e1 : getEleicoes()) {
            if(e1.getTitulo().equals(nomeEleicao)) {
                e = e1;
            }
        }

        for(Voto v: e.getVotos()){
            if(v.getEleitor() == p){
                return false;
            }
        }
        if(e==null){
            return false;
        }
        e.addVotoNulo();
        Voto v = new Voto(p, "WEB");
        e.addVoto(v);
        save();
        return true;
    }


    public boolean votarweb(String nomeEleicao, String nomeLista, String number, String departamento) throws RemoteException{

        Date date = new Date();
        Pessoa p = getPessoabyNumber(number);
        Eleicao e = null;



        for (Eleicao e1 : getEleicoes()) {
            if(e1.getTitulo().equals(nomeEleicao)) {
                e = e1;
            }
        }
        for(Voto v: e.getVotos()){
            if(v.getEleitor() == p){
                return false;
            }
        }
        if(e==null){
            return false;
        }


        Voto v = new Voto(p, departamento);
        System.out.println(e.getTitulo());
        for(Lista l: e.getListasCandidatas()){
            if(l.getNome().equals(nomeLista)){
                l.addVoto();
            }
        }
        e.addVoto(v);


        int tableCount = countVotosDep(departamento);

        for(AdminTerminalInterface ad: terminais){
            try{
                ad.voteUpdate(departamento, tableCount);}
            catch(RemoteException e1){
                //ignore
            }
        }
        save();
        return true;
    }
    public void subscribewebsocket(AdminTerminalInterface websocket) throws RemoteException{
        WS = websocket;
    }

}


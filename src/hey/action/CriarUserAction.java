package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CriarUserAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String tipo = null, nome = null, password = null, numerouni = null, ncc = null, valcc = null;
    private String numerotelefonico = null, morada = null, departamento = null, faculdade = null;

    @Override
    public String execute() {

        if (!tipo.equals("1") && !tipo.equals("2") && !tipo.equals("3")) {
            return ERROR;
        }

        if(!this.getHeyBean().createUser(tipo,nome,password,numerouni,ncc,valcc,numerotelefonico,morada,departamento,faculdade)){
            //numerouni já existe
            return ERROR;
        }


        return SUCCESS;
    }

    public HeyBean getHeyBean() {
        if(!session.containsKey("heyBean"))
            this.setHeyBean(new HeyBean());
        return (HeyBean) session.get("heyBean");
    }

    public void setHeyBean(HeyBean heyBean) {
        this.session.put("heyBean", heyBean);
    }


    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public static boolean isParsableDate(String input){
        try {
            Date startDate = new SimpleDateFormat("dd-MM-yyyy").parse(input);
            return true;
        } catch (ParseException e) {
            return  false;
        }
    }

    public static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNumerouni(String numerouni) {
        this.numerouni = numerouni;
    }

    public void setNcc(String ncc) {
        this.ncc = ncc;
    }

    public void setValcc(String valcc) {
        this.valcc = valcc;
    }

    public void setNumerotelefonico(String numerotelefonico) {
        this.numerotelefonico = numerotelefonico;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setFaculdade(String faculdade) {
        this.faculdade = faculdade;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

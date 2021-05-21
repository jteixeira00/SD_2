package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class CriarUserAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String tipo = "null", nome = "null", password = "null", numerouni = "null", ncc = "null", valcc = "10-10-2022";
    private String numerotelefonico = "null", morada = "null", departamento = "null", faculdade = "null", exit = null;
    private ArrayList<String> tiposusers = new ArrayList<String>();
    @Override
    public String execute() {

        if(exit != null)
            return SUCCESS;

        if(nome != "null" && password != "null" && numerouni != "null" && departamento != "null") {
            if (tipo.equals("Estudante")) {
                tipo = "1";
            } else if (tipo.equals("Funcionário")) {
                tipo = "3";
            } else if (tipo.equals("Docente")) {
                tipo = "2";
            }


            if (!this.getHeyBean().createUser(tipo, nome, password, numerouni, ncc, valcc, numerotelefonico, morada, departamento, faculdade)) {
                //numerouni já existe
                return ERROR;
            }
        }
        else{
            return ERROR;
        }

        System.out.println("SUCCESS");
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

    public String get(){
        return SUCCESS;
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

    public void setExit(String exit){this.exit = exit;}

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setFaculdade(String faculdade) {
        this.faculdade = faculdade;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<String> getTiposusers(){
        if(tiposusers!=null && tiposusers.size()<2){

            tiposusers.add("Estudante");
            tiposusers.add("Docente");
            tiposusers.add("Funcionário");
        }
        return tiposusers;
    }
}

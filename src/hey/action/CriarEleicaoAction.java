package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class CriarEleicaoAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String titulo = null, descricao = null, datainicio = null, horainicio = null, minutoinicio = null;
    private String datafim = null, horafim = null, minutofim = null, tipovoter = null, exit = null;
    private ArrayList<String> tiposvoters = new ArrayList<String>();
    @Override
    public String execute() {

        if(exit != null)
            return SUCCESS;

        System.out.println(titulo);
        System.out.println(descricao);
        System.out.println(datainicio);
        System.out.println(horainicio);
        System.out.println(minutoinicio);
        System.out.println(datafim);
        System.out.println(horafim);
        System.out.println(minutofim);

        if(titulo != null && descricao != null && datainicio != null && horainicio != null && minutoinicio != null && datafim != null && horafim != null && minutofim != null) {
            System.out.println("HERE");
            try {
                if (!this.getHeyBean().checkTitulo(titulo)) {
                    System.out.println("TITULO");
                    return ERROR;
                }

                if (!isParsableDate(datainicio)) {
                    System.out.println("dinicio");
                    return ERROR;
                }

                if (!isParsable(horainicio)) {
                    System.out.println("hinicio");
                    return ERROR;
                }

                if (!isParsable(minutoinicio)) {
                    System.out.println("minicio");
                    return ERROR;
                }

                if (!isParsableDate(datafim)) {
                    System.out.println("dfim");
                    return ERROR;
                }

                if (!isParsable(horafim)) {
                    System.out.println("hfim");
                    return ERROR;
                }

                if (!isParsable(minutofim)) {
                    System.out.println("mfim");
                    return ERROR;
                }

                if (!tipovoter.equals("Estudantes") && !tipovoter.equals("Funcionários") && !tipovoter.equals("Funcionários")) {
                    System.out.println("TIPO VOTER");
                    return ERROR;
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }

            String tipovoterint = "1";

            if (tipovoter.equals("Funcionários")) {
                tipovoterint = "3";
            } else if (tipovoter.equals("Docentes")) {
                tipovoterint = "2";
            }
            if (!this.getHeyBean().criaEleicao(titulo, descricao, datainicio, horainicio, minutoinicio, datafim, horafim, minutofim, "", tipovoterint)) {
                //data invalida
                System.out.println("DATA");
                return ERROR;
            }
        }
        else {
            System.out.println("ERROR");
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

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public String get(){
        return SUCCESS;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDatainicio(String datainicio) {
        this.datainicio = datainicio;
    }

    public void setHorainicio(String horainicio) {
        this.horainicio = horainicio;
    }

    public void setMinutoinicio(String minutoinicio) {
        this.minutoinicio = minutoinicio;
    }

    public void setDatafim(String datafim) {
        this.datafim = datafim;
    }

    public void setHorafim(String horafim) {
        this.horafim = horafim;
    }

    public void setMinutofim(String minutofim) {
        this.minutofim = minutofim;
    }

    public void setTipovoter(String tipovoter) {
        this.tipovoter = tipovoter;
    }

    public void setExit(String exit) {
        this.exit = exit;
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

    public ArrayList<String> getTiposvoters(){
        if(tiposvoters!=null && tiposvoters.size()<2){

            tiposvoters.add("Estudantes");
            tiposvoters.add("Docentes");
            tiposvoters.add("Funcionários");
        }
        return tiposvoters;
    }


}

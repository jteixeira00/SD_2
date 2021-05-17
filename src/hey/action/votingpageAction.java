package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;
import java.util.Map;

public class votingpageAction extends ActionSupport implements SessionAware
{
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    private ArrayList<String> eleicoes;

    private String choice = null;

    public String getVoto() {
        return voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    private String voto = null;
    private ArrayList<String> listas = new ArrayList<>();


    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public HeyBean getHeyBean() {
        if(!session.containsKey("heyBean"))
            this.setHeyBean(new HeyBean());
        return (HeyBean) session.get("heyBean");
    }
    public void setHeyBean(HeyBean heyBean) {
        this.session.put("heyBean", heyBean);
    }

    public ArrayList<String> getEleicoes(){
        return this.getHeyBean().getEleicoesUser();
    }

    public String getChoice() {
        return choice;
    }
    public void setChoice(String choice){
        this.choice = choice;
    }
    public ArrayList<String> getListas(){
        return this.getHeyBean().getListasEleicao(choice);
    }
    public String get(){
        return SUCCESS;
    }

    public String votar(){

        this.getHeyBean().votar(choice, voto);
        return SUCCESS;
    }
}

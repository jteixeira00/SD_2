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

public class GerirEleicaoAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;


    private String choice = null;
    private  int intChoice;
    private int size;



    private ArrayList<String> eleicoes;
    private String myElection;
    @Override
    public String execute() throws RemoteException {

        size = this.getHeyBean().getSizeEleicoesFuturas();
        if(size == -1){
            return ERROR;
        }
        intChoice = this.getHeyBean().getAllEleicoes().indexOf(choice)+1;
        if(intChoice >= 1 && intChoice <= size) {
            this.getHeyBean().setChoiceGerirEleicao(intChoice);
            return SUCCESS;
        }
        else
            return ERROR;

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


    public static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }


    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getChoice() {
        return choice;
    }

    public ArrayList<String> getEleicoes() {
        try {

            return this.getHeyBean().getAllEleicoes();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }
    public String get(){
        return SUCCESS;
    }

}

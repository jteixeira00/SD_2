package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public class adicionarCandidatosAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    private String add = null;
    private String exit = null;
    private String choice = null;


    @Override
    public String execute() {


        if(choice != null){
            choice = choice.split(" ")[0];
        }

        if (add != null && choice != null && isParsable(choice))
            if(this.getHeyBean().addCandidato(Integer.parseInt(choice),this.getHeyBean().getChoiceLista() - 1)) {
                return ERROR;
            }

        if(exit != null)
            return SUCCESS;

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

    public void setAdd(String add) {
        this.add = add;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    public ArrayList<String> getPessoas(){
        try {
            return this.getHeyBean().getAllPessoas();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public String get(){
        return SUCCESS;
    }

}

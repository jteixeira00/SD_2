package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public class escolherListaAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;


    private String choice = null;
    private  int intChoice;
    private int size = -1;
    private ArrayList<String> listas;


    @Override
    public String execute() throws RemoteException {

        choice  = choice.split(" ")[0];

        size = this.getHeyBean().getsizeLista();
        if(size == -1){
            return ERROR;
        }

        if(isParsable(choice)){
            intChoice = Integer.parseInt(choice);
            if(intChoice >= 1 && intChoice <= size) {
                this.getHeyBean().setChoiceLista(intChoice);
                return SUCCESS;
            }
            else
                return ERROR;
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

    public ArrayList<String> getListas() {
        try {
            return this.getHeyBean().getAllListas();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    public String get(){
        return SUCCESS;
    }



}

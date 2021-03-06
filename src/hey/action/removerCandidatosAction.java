package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public class removerCandidatosAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    private String del = null;
    private String exit = null;
    private String choice = null;


    @Override
    public String execute() {

        if (choice != null) {
            choice = choice.split(" ")[0];
        }

        if (del != null && choice != null && isParsable(choice))
            if (this.getHeyBean().delCandidato(Integer.parseInt(choice), this.getHeyBean().getChoiceLista() - 1)) {
                return ERROR;
            }

        if (exit != null)
            return SUCCESS;

        return ERROR;
    }

    public HeyBean getHeyBean() {
        if (!session.containsKey("heyBean"))
            this.setHeyBean(new HeyBean());
        return (HeyBean) session.get("heyBean");
    }

    public void setHeyBean(HeyBean heyBean) {
        this.session.put("heyBean", heyBean);
    }

    public String get() {
        return SUCCESS;
    }

    public ArrayList<String> getCandidatos()
    {
        try {
            return this.getHeyBean().getAllCandidatos();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public void setDel(String add) {
        this.del = add;
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



}

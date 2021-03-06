package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class removerMesaAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;

    private String choice = null;
    private int size = 0;


    @Override
    public String execute() {

        if(choice != null){
            choice = choice.split(" ")[0];
        }

        if (isParsable(choice))
            size = Integer.parseInt(choice);
            if(size > 0 & size <= this.getHeyBean().getsizeMesasEleicao() && this.getHeyBean().delMesa(size)) {
                return SUCCESS;
            }

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

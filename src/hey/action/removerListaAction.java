package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class removerListaAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String index = null;
    private int indx;

    @Override
    public String execute() {

        if(isParsable(index)) {
            int indx = Integer.parseInt(index);
            if(indx >= 1 && indx <= this.getHeyBean().getsizeDepartamento()){
                if (this.getHeyBean().delDepatamento(indx))
                    return SUCCESS;
            }
            else
                return ERROR;

        }else
            return ERROR;


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

    public void setIndex(String index){
        this.index = index;
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


}

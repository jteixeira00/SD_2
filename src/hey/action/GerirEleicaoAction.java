package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class GerirEleicaoAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String titulo = null, descricao = null, datainicio = null, horainicio = null, minutoinicio = null;
    private String datafim = null, horafim = null, minutofim = null, tipovoter = null;

    @Override
    public String execute() {

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


}
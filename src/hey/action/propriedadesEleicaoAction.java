package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;
import sun.awt.windows.WPrinterJob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class propriedadesEleicaoAction extends ActionSupport implements SessionAware {
    private static final long serialVersionUID = 4L;
    private Map<String, Object> session;
    private String titulo = null, descricao = null, dataInicio = null, dataFim = null;

    @Override
    public String execute() {

        if(titulo != null && !titulo.equals("")){
            if(!getHeyBean().changeEleicao(titulo,1)) {
                return ERROR;
            }
        }

        if(descricao != null && !descricao.equals("")){
            if(!getHeyBean().changeEleicao(descricao,2)) {
                return ERROR;
            }
        }

        if(dataInicio != null && !dataInicio.equals("")){
            if(!isParsableDate_v2(dataInicio)) {
                return ERROR;
            }
            else if(!getHeyBean().changeEleicao(dataInicio,3)) {
                return ERROR;
            }
        }

        if(dataFim != null &&!dataFim.equals("")){
            if(!isParsableDate_v2(dataFim))
                return ERROR;
            else if(!getHeyBean().changeEleicao(dataFim,4)) {
                return ERROR;
            }
        }

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

    public static boolean isParsableDate_v2(String input){
        try {
            Date startDate = new SimpleDateFormat("dd-MM-yyyy hh:mm").parse(input);
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

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }


}

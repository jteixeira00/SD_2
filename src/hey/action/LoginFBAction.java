/**
 * Raul Barbosa 2014-11-07
 */
package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class LoginFBAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;



	@Override
	public String execute() {
		return SUCCESS;
	}

	public HeyBean getHeyBean(){
		if(!session.containsKey("fbBean"))
			this.setHeyBean(new HeyBean());
		return (HeyBean) session.get("fbBean");
	}

	public void setHeyBean(HeyBean fbBean){
		this.session.put("fbBean",fbBean);
	}


	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}

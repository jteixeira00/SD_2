/**
 * Raul Barbosa 2014-11-07
 */
package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;
import java.util.Map;

public class LogoutAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null;


	private String login = null, facebook = null;

	private ArrayList<String> eleicoes = new ArrayList<>();
	@Override
	public String execute() {

		// any username is accepted without confirmation (should check using RMI)
		this.getHeyBean().setUsername(null);
		this.getHeyBean().setPassword(null);
		session.put("username", null);
		session.put("loggedin", false);
		return SUCCESS;

	}
	
	public void setUsername(String username) {
		this.username = username; // will you sanitize this input? maybe use a prepared statement?
	}

	public void setPassword(String password) {
		this.password = password; // what about this input? 
	}
	
	public HeyBean getHeyBean() {
		if(!session.containsKey("heyBean")) {
			this.setHeyBean(new HeyBean());
		}
		return (HeyBean) session.get("heyBean");
	}

	public void setHeyBean(HeyBean heyBean) {
		this.session.put("heyBean", heyBean);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public ArrayList<String> getEleicoes(){
		return this.getHeyBean().getEleicoesUser();
	}
}

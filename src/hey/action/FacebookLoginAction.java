/**
 * Raul Barbosa 2014-11-07
 */
package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.parser.ParseException;

import java.rmi.RemoteException;
import java.util.Map;

public class FacebookLoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String code = null, state = null;


	@Override
	public String execute() throws ParseException, RemoteException {
		HeyBean fb = this.getHeyBean();
		System.out.println(fb);
		fb.setAuthCode(this.code);
		fb.setSecretState(this.state);


		if(fb.getAccessToken()){
			if(session.get("loggedin") != null && session.get("loggedin").equals(true)){
				fb.setFacebookID(fb.getFacebookID());
			}else{
				if(fb.findFacebookIDUser(fb.getFacebookID()) != -1) {

					String username = fb.getNumeroPessoa((fb.findFacebookIDUser(fb.getFacebookID())));

					System.out.println(username);
					fb.setUsername(username);
					session.put("username", username);
					session.put("loggedin", true);
					return "facebook";
				}else{
					return ERROR;
				}
			}

			return SUCCESS;
		}
		return ERROR;
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

	public String getCode(){
		return code;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getState(){
		return state;
	}

	public void setState(String state){
		this.state = state;
	}
}

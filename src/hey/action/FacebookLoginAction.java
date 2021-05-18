/**
 * Raul Barbosa 2014-11-07
 */
package hey.action;

import com.opensymphony.xwork2.ActionSupport;
import hey.model.HeyBean;
import org.apache.struts2.interceptor.SessionAware;
import org.json.simple.parser.ParseException;

import java.util.Map;

public class FacebookLoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String code = null, state = null;


	@Override
	public String execute() throws ParseException {
		HeyBean fb = this.getHeyBean();
		fb.setAuthCode(this.code);
		fb.setSecretState(this.state);
		if(fb.getAccessToken()){
			if(session.get("loggedin").equals(true)){
				fb.setFacebookID(fb.getFacebookID());
			}else{
				if(fb.findFacebookIDUser(fb.getFacebookID()) != -1)
					System.out.println("I EXIST");
				session.put("loggedin",true);
			}

			return SUCCESS;
		}
		return ERROR;
	}

	public HeyBean getHeyBean(){
		if(!session.containsKey("heyBean"))
			this.setHeyBean(new HeyBean());
		return (HeyBean) session.get("heyBean");
	}

	public void setHeyBean(HeyBean fbBean){
		this.session.put("heyBean",fbBean);
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

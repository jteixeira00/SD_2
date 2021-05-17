
package hey.model;


import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class FacebookREST {
	private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
	private static final String apiKey = "182920250372527";
	private static final String apiSecret = "1c94fb9957d1ae730ce9d659c3c9e4f5";
	private OAuth20Service service;
	private String secretState;


	public FacebookREST() {
		this.service = new ServiceBuilder(apiKey)
				.apiKey(apiKey)
				.apiSecret(apiSecret)
				//configurar para correr como https? guia oficial? :crying_emojie:
				.callback("http://localhost:8080/Hey/fblogin.action")
				.build(FacebookApi.instance());
		this.secretState = "secret" + new Random().nextInt(999_999);
	}

	public String getAuthorizationURL(){
		String autorizationUrl = this.service.getAuthorizationUrl(secretState);
		return autorizationUrl;
	}

	public OAuth2AccessToken getAccessToken(String authCode, String secretState){
		OAuth2AccessToken accessToken = null;
		try{
			if(this.secretState.equals(secretState)){
				System.out.println("State value does match");
				accessToken = this.service.getAccessToken(authCode);
			}
			else{
				System.out.println("Oopsie, state does not match!");
			}
		}catch (IOException e){
			e.printStackTrace();
		}catch (InterruptedException e){
			e.printStackTrace();
		}catch (ExecutionException e){
		e.printStackTrace();
	}
		return accessToken;
	}



	public String getAccountName(OAuth2AccessToken accessToken) throws ParseException{
		OAuthRequest request = new OAuthRequest(Verb.GET,PROTECTED_RESOURCE_URL);
		this.service.signRequest(accessToken,request);
		try (Response response = this.service.execute(request)){
			String reply = response.getBody();
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(reply);
			return (String) json.get("name");
		}catch (InterruptedException e){
			e.printStackTrace();
		}catch (ExecutionException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return "";
	}




	public String getSecretState() {
		return secretState;
	}

	public void setSecretState(String secretState) {
		this.secretState = secretState;
	}



}

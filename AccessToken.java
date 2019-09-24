package rest;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class AccessToken {

	private static final String BASE_URI = "https://";
	private static final String CLIENT_ID = "";
	private static final String CLIENT_SECRET = "";
	
	public static void main(String[] args) {
		try {
			
			OAuthClient client = new OAuthClient(new URLConnectionClient());

			OAuthClientRequest request = OAuthClientRequest.tokenLocation(BASE_URI + "/oauth/token")
                    .setClientId(CLIENT_ID)
                    .setClientSecret(CLIENT_SECRET) 
                    .buildHeaderMessage();
			String encoding = Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes(java.nio.charset.StandardCharsets.UTF_8));
			request.addHeader("Authorization", "Basic " + encoding);
			request.addHeader("Content-Type", "application/x-www-form-urlencoded");
			request.setBody("grant_type=client_credentials");
			System.out.println("headers = " + request.getHeaders());
			StringEntity input = null;
			String grant_type = "client_credentials"; 
			try {    			
				input = new StringEntity("grant_type=" + grant_type); 
				HttpPost httpPost = new HttpPost(BASE_URI + "/oauth/token");
				httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded"); 

				    httpPost.setEntity(input);    
				 } 
				 catch (UnsupportedEncodingException e) {    
				        e.printStackTrace();    
				   }
			
			String token = client.accessToken(request, OAuth.HttpMethod.POST, OAuthJSONAccessTokenResponse.class)
					.getAccessToken();
			System.out.println(token);
			OAuthAccessTokenResponse oAuthResponse = client.accessToken(request);
			System.out.println(oAuthResponse.getResponseCode());

		} catch (Exception exn) {
			exn.printStackTrace();
		}
	}

}

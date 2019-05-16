package com.hada.api.service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hada.api.model.User;

@Service
public class AndroidPushNotificationService {
	
    private static final String firebase_server_key="AAAAs3y_CWY:APA91bEuPwUvoiCg5wfBCwxaavcpmpPVoIBIzB_GQ4bTghWD46EZsllXC34RFMnkfxyth2vT3Qrhwb9tUFplZ0YZV3v7SmKqsYsjL9H1iuqROTMaq6rFXj2MN7RGrXPvW5bZkjcWx_kx";
    private static final String firebase_api_url="https://fcm.googleapis.com/fcm/send";

	@Autowired UserServiceImpl userServiceImpl;
	@Autowired ChallengeServiceImpl challengeServiceImpl;

    @Async
    public CompletableFuture<String> pushChallengeNotification(String email, int cno) {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

        interceptors.add(new HeaderRequestInterceptor("Authorization",  "key=" + firebase_server_key));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json; UTF-8 "));
        restTemplate.setInterceptors(interceptors);

        HttpEntity<String> entity;
		entity = createChallengeNotification(email, cno);
	    String firebaseResponse = restTemplate.postForObject(firebase_api_url, entity, String.class);

	    return CompletableFuture.completedFuture(firebaseResponse);
    }
    
    public HttpEntity<String> createChallengeNotification(String email, int cno) throws JSONException {
        JSONObject json = new JSONObject();
        
        json.put("to", selectUserToken(email));
        
        JSONObject notification = new JSONObject();
        notification.put("title","hello!");
        JSONObject body = new JSONObject(challengeServiceImpl.selectChallengeDetail(cno));
        notification.put("body", body.toMap());

        json.put("notification", notification);

        System.out.println(json.toString());
        HttpEntity<String> entity = new HttpEntity<>(json.toString());

        return entity;
    }
	
	private String selectUserToken(String email) {
		User user = userServiceImpl.selectUserDetail(email);
		return user.getToken();
	}
}
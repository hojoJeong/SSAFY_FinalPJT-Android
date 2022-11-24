package com.ssafy.cafe.model.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.ssafy.cafe.model.dto.FireStoreMessage;
import com.ssafy.cafe.message.FcmDataMessage;
import com.ssafy.cafe.message.FcmMessage.Message;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * FCM 알림 메시지 생성
 * background 대응을 위해서 data로 전송한다.
 *   
 * @author taeshik.heo
 *
 */
@Component
public class FirebaseCloudMessageDataService {
	private static final Logger logger = LoggerFactory.getLogger(FirebaseCloudMessageDataService.class);

    public final ObjectMapper objectMapper;

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/silence-lake/messages:send";
     
    /**
     * FCM에 push 요청을 보낼 때 인증을 위해 Header에 포함시킬 AccessToken 생성
     * @return
     * @throws IOException
     */
    private String getAccessToken() throws IOException {
    	String firebaseConfigPath = "firebase/firebase_service_key.json";
    	//String firebaseConfigPath = "firebase/firebase_service_key.json";

        // GoogleApi를 사용하기 위해 oAuth2를 이용해 인증한 대상을 나타내는객체
        GoogleCredentials googleCredentials = GoogleCredentials
                // 서버로부터 받은 service key 파일 활용
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                // 인증하는 서버에서 필요로 하는 권한 지정
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        
        googleCredentials.refreshIfExpired();
        String token = googleCredentials.getAccessToken().getTokenValue();
        
        return token;
    }
    
    /**
     * FCM 알림 메시지 생성
     * background 대응을 위해서 data로 전송한다.  
     * @param targetToken
     * @param title
     * @param body
     * @return
     * @throws JsonProcessingException
     */
    private String makeDataMessage(String targetToken, String title, String body) throws JsonProcessingException {
//        Notification noti = new FcmMessage.Notification(title, body, null);
    	Map<String,String> map = new HashMap<>();
    	map.put("title", title);
    	map.put("body", body);
    	
    	FcmDataMessage.Message message = new FcmDataMessage.Message();
        message.setToken(targetToken);
        message.setData(map);
        
        FcmDataMessage fcmMessage = new FcmDataMessage(false, message);
        
        return objectMapper.writeValueAsString(fcmMessage);
    }
    

    /**
     * targetToken에 해당하는 device로 FCM 푸시 알림 전송
     * background 대응을 위해서 data로 전송한다.  
     * @param targetToken
     * @param title
     * @param body
     * @throws IOException
     */
    public Boolean sendDataMessageTo(String targetToken, String title, String body) throws IOException {
    	try {
            String message = makeDataMessage(targetToken, title, body);
            logger.info("message : {}", message);
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
                    // 전송 토큰 추가
                    .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                    .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                    .build();

            Response response = client.newCall(request).execute();

            System.out.println(response.body().string());
//            logger.info("message : {}", message);
            return true;
		} catch (Exception e) {
			return false;
		}
    }
    public Boolean sendMessageToAdmin() throws IOException {
        String targetToken = "chSXihAVQKGPuKKCaKSZvQ:APA91bGx_tkwCu8JJR8sCUH_o0p_7TolBPMBwRKYkMYbXcmNhWbvaoxVMXREA2qgjMEt7FcjqXVZ_amAd8C1p2nHgMQIniEoX_lDEjwP2xQKcfmLwRJqIOWs7c3KeUCveOY9TI3PCRn9";
        String title = "admin";
        String body = "새로운 주문이 들어왔습니다.";
        try {
            
        String message = makeDataMessage(targetToken, title, body);
        logger.info("message : {}", message);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                // 전송 토큰 추가
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();
         
        System.out.println(response.body().string());
//        logger.info("message : {}", message);
        return true;
        }catch(Exception e) {
            return false;
        }
       
    }

    private List<String> clientTokens = new ArrayList<>();
    
    public FirebaseCloudMessageDataService(ObjectMapper objectMapper){
    	this.objectMapper = objectMapper;
    }

    
    // 클라이언트 토큰 관리
    public void addToken(String token) {
        clientTokens.add(token);
    }
    
    // 등록된 모든 토큰을 이용해서 broadcasting
    public Boolean broadCastDataMessage(FireStoreMessage msg) throws IOException {
       try {
    	   for(String token: msg.getTokenList()) {
        	   sendDataMessageTo(token, msg.getTitle(), msg.getBody());
           }
    	   return true;
		} catch (Exception e) {
			return false;
		}
      
    }


}

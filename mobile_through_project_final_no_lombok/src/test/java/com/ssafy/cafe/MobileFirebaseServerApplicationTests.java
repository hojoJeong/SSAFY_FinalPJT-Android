package com.ssafy.cafe;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.cafe.model.service.FirebaseCloudMessageDataService;
import com.ssafy.cafe.model.service.FirebaseCloudMessageService;

@SpringBootTest
class MobileFirebaseServerApplicationTests {

    @Autowired
    FirebaseCloudMessageService service;

    @Autowired
    FirebaseCloudMessageDataService dataService;

    //기본적으로 data를 보내면 foreground에 있을때만 받을 수 있다. 
    //background에서도 대응하기 위해서는 data에 담아서 전달해야 하는데, 아래 dataMessage로 호출해야 한다.  
    @Test
    void sendMessage() throws IOException {
    	
        String token = "cuJpYiQARMSftpEQhRfCeW:APA91bGt8VyDJIH2m-vH_7_0RDiESgpQ0HDrZfGwYEaAKFCn-0lgUPmGidC_uwJdjJduGlIueGaqeNQDzdjvTjfAii5V_vFo6JNcFzvJQ_xTrU72J7I3wtQb6QVwsl9FyXtNjth7AdzI";
//        String token1 = "cHI9bL4QQTKQI_nJVtoUt7:APA91bF-VKnKY0NxeikBXORc0FhmS3iRHFYZgH5yBF34GNt-tYaUCvABVal1CSibhAEWkj9DDcmpZWlD4lA481JoC_UjVnykSYhyd5OHi3-91E-kVlX4YjEEK2v0fTQlqoW35yjNnlrd";
//		한건 메시지
        service.sendMessageTo(token, "from 사무국", "싸피 여러분 화이팅!!");
        
//		멀티 메시지        
//        service.addToken(token);
//        service.addToken(token1);
        
//        service.broadCastMessage("from 사무국1", "싸피 여러분 화이팅!!!!!!!!");
    }

    
    //Notification아니라 Data에 데이터 담아서 전송함. 
    @Test
    void sendDataMessage() throws IOException {
    	
        String token = "cuJpYiQARMSftpEQhRfCeW:APA91bGt8VyDJIH2m-vH_7_0RDiESgpQ0HDrZfGwYEaAKFCn-0lgUPmGidC_uwJdjJduGlIueGaqeNQDzdjvTjfAii5V_vFo6JNcFzvJQ_xTrU72J7I3wtQb6QVwsl9FyXtNjth7AdzI";
//        String token1 = "cHI9bL4QQTKQI_nJVtoUt7:APA91bF-VKnKY0NxeikBXORc0FhmS3iRHFYZgH5yBF34GNt-tYaUCvABVal1CSibhAEWkj9DDcmpZWlD4lA481JoC_UjVnykSYhyd5OHi3-91E-kVlX4YjEEK2v0fTQlqoW35yjNnlrd";

//		한건 메시지
        dataService.sendDataMessageTo(token, "from 사무국", "싸피 여러분 화이팅!!");
        
//		멀티 메시지        
//        dataService.addToken(token);
//        service.addToken(token1);
        
//        service.broadCastMessage("from 사무국1", "싸피 여러분 화이팅!!!!!!!!");
    }
}

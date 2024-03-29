package com.ssafy.cafe.controller.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cafe.model.dto.Admin;
import com.ssafy.cafe.model.dto.FireStoreMessage;
import com.ssafy.cafe.model.service.AdminService;
import com.ssafy.cafe.model.service.FirebaseCloudMessageDataService;
import com.ssafy.cafe.model.service.FirebaseCloudMessageService;

@RestController
@CrossOrigin("*")
public class TokenController {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    FirebaseCloudMessageService service;
    
    @Autowired
    FirebaseCloudMessageDataService dataService;
    
    @Autowired
    AdminService aService;
    
    @PostMapping("/token")
    public String registToken(String token) {
    	logger.info("registToken : token:{}", token);
        service.addToken(token);
        return "'"+token+"'" ;
    }
    
    @PostMapping("/broadcast")
    public Integer broadCast(String title, String body) throws IOException {
    	logger.info("broadCast : title:{}, body:{}", title, body);
    	return service.broadCastMessage(title, body);
    }

    @PostMapping("/sendMessageTo")
    public void sendMessageTo(String token, String title, String body) throws IOException {
    	logger.info("sendMessageTo : token:{}, title:{}, body:{}", token, title, body);
        service.sendMessageTo(token, title, body);
    }
    @PostMapping("/sendMessageToAdmin")
    public void sendMessageToAdmin() throws IOException{
        service.sendMessageToAdmin();
    };
    
    @PostMapping("/fcm")
    public Boolean sendFCM(@RequestBody FireStoreMessage msg) throws IOException {
    	return dataService.broadCastDataMessage(msg);
    }
    @PostMapping("/registAdmin")
    public Boolean registAdmin(@RequestBody String token) {
        aService.updateAdmin(new Admin(1,token));
        return true;
    }
}


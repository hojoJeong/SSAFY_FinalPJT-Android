package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.AdminDao;
import com.ssafy.cafe.model.dao.CommentDao;
import com.ssafy.cafe.model.dto.Admin;

@Service
public class AdminServiceImpl implements AdminService{

    
    @Autowired
    AdminDao aDao;
    
    @Override
    public void updateAdmin(Admin admin) {
    	List<Admin> list = aDao.select();
    	System.out.println(list.toString());
        if(list.size() > 0) {
        	System.out.println(admin.toString());
        	System.out.println(list.get(0).getToken());
        	aDao.updateAdmin(admin);
        }else {
        	System.out.println("insert");
        	System.out.println(admin.toString());
        	aDao.insert(admin);
        }
        
    }
}

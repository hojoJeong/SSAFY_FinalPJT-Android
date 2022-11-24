package com.ssafy.cafe.model.service;

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
        
        aDao.updateAdmin(admin);
    }
}

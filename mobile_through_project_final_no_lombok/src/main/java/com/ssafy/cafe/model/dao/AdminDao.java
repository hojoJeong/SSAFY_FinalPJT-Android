package com.ssafy.cafe.model.dao;

import java.util.List;

import com.ssafy.cafe.model.dto.Admin;

public interface AdminDao {
    int updateAdmin(Admin admin);
    List<Admin> select();
    void insert(Admin admin);
}

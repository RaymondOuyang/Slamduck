package com.missingvia.slamduck.sys.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.missingvia.slamduck.sys.user.bean.UserInfo;
import com.missingvia.slamduck.sys.user.dao.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public List<UserInfo> findAll() {
		return userDao.findAll();
	}
}

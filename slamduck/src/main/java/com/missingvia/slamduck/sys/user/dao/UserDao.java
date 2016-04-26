package com.missingvia.slamduck.sys.user.dao;

import java.util.List;

import com.missingvia.slamduck.common.mybatis.MyBatisRepository;
import com.missingvia.slamduck.sys.user.bean.UserInfo;

@MyBatisRepository
public interface UserDao {

	public List<UserInfo> findAll();
}

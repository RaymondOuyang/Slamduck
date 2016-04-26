package com.missingvia.slamduck.sys.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.missingvia.slamduck.common.web.CommonResponse;
import com.missingvia.slamduck.sys.user.service.UserService;

@Controller
@RequestMapping("/user/")
public class UserAction {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/userList")
	public String findAll(Model model) {
		model.addAttribute("allUser", userService.findAll());
		return "sys/userList";
	}
	
	@RequestMapping("/all")
	@ResponseBody
	public CommonResponse all(Model model) {
		CommonResponse c = new CommonResponse();
		c.setData(userService.findAll());;
		return c;
	}
}

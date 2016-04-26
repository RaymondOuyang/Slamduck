package com.missingvia.slamduck.common.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  主框架索引。
 *
 */
@Controller("mainframeIndexAction")
public class IndexAction extends BaseAction {
	
	/**
	 * 跳转到首页。
	 * @return
	 */
	@RequestMapping("/index.view")
	public String gotoIndex(HttpServletRequest req) {
		return "index";
	}
	
	/**
	 * 跳转到首页。
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	/**
	 * 跳转到首页。
	 * @return
	 */
	@RequestMapping("/json")
	@ResponseBody
	public CommonResponse json() {
		CommonResponse c = new CommonResponse();
		c.setCode(000);
		c.setMessage("test");
		return c;
	}
	
	
}

package com.goodidea.sso.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goodidea.sso.core.JsonVo;
import com.goodidea.sso.domin.Log;
import com.goodidea.sso.domin.Setting;
import com.goodidea.sso.service.LogService;
import com.goodidea.sso.util.SystemUtils;

/**
 * 
* @ClassName: LogoutController 
* @Description:  注销
* @author lsg
* @date 2017年8月10日 下午4:24:08 
*
 */
@Controller
public class LogoutController extends BaseController{
	
	@Autowired
	private LogService logService;
	/**
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session,HttpServletRequest request,HttpServletResponse response,Model model){
		Setting setting = SystemUtils.getSetting();
		try {
			AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
			Map<String, Object> attributes = principal.getAttributes();
			logService.saveInfo(request,(String) attributes.get("username"));
			session.invalidate();
			session.setMaxInactiveInterval(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		return "redirect:http://"+setting.getCasUrl()+":8080/cas-server/logout?service=http://"+setting.getSiteUrl()+":8088/sso/home";
		return "redirect:"+setting.getCasUrl()+"/cas-server/logout?service="+setting.getSiteUrl()+"/sso/home";
	}
}

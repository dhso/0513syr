package modules.site.controller;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.PropKit;

import frame.kit.HttpKit;
import modules.site.entity.IkeaUser;

public class SystemController extends Controller {

	// 登录页面
	@ActionKey("/system/login")
	@Before(value = { GET.class })
	public void login() {
		render("login.html");
	}

	// 登录页面
	@ActionKey("/system/verify-error")
	@Before(value = { GET.class })
	public void verifyError() {
		render("verify-error.html");
	}

	// 登录方法
	@SuppressWarnings("serial")
	@Before(value = { POST.class })
	@ActionKey("/system/login-action")
	public void loginAction() {
		JSONObject jsonData = new JSONObject() {
			{
				put("name", getPara("name"));
				put("mobile", getPara("mobile"));
				put("idcardnum", getPara("idcardnum"));
				put("membershipnum", getPara("membershipnum"));
				put("source", (String) getSessionAttr("_template"));
			}
		};
		String result = HttpKit.post(PropKit.get("remote.webservice").concat("/ws/customer/customerLogin"), jsonData.toJSONString(), HttpKit.HTTP_HEADER_JSON);
		JSONObject jsonResult = JSON.parseObject(result);
		if (200 != jsonResult.getInteger("responseCode")) {
			renderError(403);
		}
		IkeaUser ikeaUser = JSON.parseObject(jsonResult.getString("data"), IkeaUser.class);
		setSessionAttr("_ikeaUser", ikeaUser);
		if (null == getSessionAttr("_nextUrl")) {
			redirect("/");
			return;
		}
		redirect((String) getSessionAttr("_nextUrl"));

	}

	// 登出页面
	@ActionKey("/system/logout")
	@Before(value = { GET.class })
	public void logout() {
		removeSessionAttr("_ikeaUser");
		removeSessionAttr("_nextUrl");
		redirect("/");
	}

	// 一键登录
	public void onekey() {
		render("onekey.html");
	}

	// 401错误
	@ActionKey("/system/error/401")
	public void systemError401() {
		redirect("/system/login");
	}

	// 403错误
	@ActionKey("/system/error/403")
	public void systemError403() {
		redirect("/system/verify-error");
	}

	// 404错误
	@ActionKey("/system/error/404")
	public void systemError404() {
		renderText("404 Not Found");
	}

	// 500错误
	@ActionKey("/system/error/500")
	public void systemError500() {
		renderText("500 Service Error");
	}

	/**
	 * 远程调用服务
	 * 
	 * _path:'/ws/customer/updateCustomerInfo'
	 * 
	 * __method:'get'
	 * 
	 * _data:'{"data1":"1","data2","2"}'
	 * 
	 * __queryParas:'{"queryParas1":"1","queryParas2","2"}'
	 * 
	 * _type:'json'
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void remoteWS() {
		String result = "";
		String path = PropKit.get("remote.webservice").concat(getPara("_path", ""));
		Map queryParas = (Map) JSON.parseObject(getPara("__queryParas", ""));
		String data = getPara("__data", "");
		String type = getPara("_type", "json");
		if ("get".equalsIgnoreCase(getPara("_method", "get"))) {
			result = HttpKit.get(path, queryParas, HttpKit.HTTP_HEADER_JSON);
		} else {
			result = HttpKit.post(path, queryParas, data, HttpKit.HTTP_HEADER_JSON);
		}
		if ("json".equalsIgnoreCase(type)) {
			renderJson(result);
		} else {
			renderText(result);
		}
	}
}

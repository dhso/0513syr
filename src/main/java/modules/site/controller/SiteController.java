package modules.site.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

import frame.interceptor.AuthInterceptor;

public class SiteController extends Controller {

	// 默认登录页面
	@ActionKey("/")
	public void index() {
		render("index.html");
	}

	@ActionKey("/activity-rules")
	public void activityRules() {
		render("activity-rules.html");
	}

	@ActionKey("/customerInfoUpdateA")
	@Before(value = { AuthInterceptor.class })
	public void customerInfoUpdateA() {
		renderError(403);
	}

}

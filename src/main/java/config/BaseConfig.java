package config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.IErrorRenderFactory;
import com.jfinal.render.RedirectRender;
import com.jfinal.render.Render;

import frame.interceptor.ReqResInViewInterceptor;
import frame.interceptor.SwitchViewInterceptor;
import frame.plugin.freemarker.BlockDirective;
import frame.plugin.freemarker.ExtendsDirective;
import frame.plugin.freemarker.OverrideDirective;
import modules.site.controller.SiteController;
import modules.site.controller.SystemController;

public class BaseConfig extends JFinalConfig {

	@SuppressWarnings("unused")
	private Routes routes;

	public void configConstant(Constants me) {
		// 加载配置/国际化
		PropKit.use("config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		// 设置错误模板
		me.setErrorView(401, "/system/error/401");
		me.setErrorView(403, "/system/error/403");
		me.setErrorView(404, "/system/error/404");
		me.setErrorView(500, "/system/error/500");
		me.setErrorRenderFactory(new IErrorRenderFactory() {
			@Override
			public Render getRender(int errorCode, String view) {
				return new RedirectRender(view, true);
			}
		});
	}

	public void configRoute(Routes me) {
		this.routes = me;
		me.add("/", SiteController.class, "/site");
		me.add("/system", SystemController.class, "/site");
	}

	public void configPlugin(Plugins me) {

	}

	public void configInterceptor(Interceptors me) {
		// 让 模版 可以使用session
		me.add(new SessionInViewInterceptor(true));
		// 让 模版 可以使用request/response
		me.add(new ReqResInViewInterceptor());
		// 让 模版 可以使用切换
		me.add(new SwitchViewInterceptor());
	}

	public void configHandler(Handlers me) {
		me.add(new UrlSkipHandler(".*/static/.*", false));
		me.add(new ContextPathHandler("baseUrl"));
	}

	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/", 5);
	}

	public void afterJFinalStart() {
		super.afterJFinalStart();
		FreeMarkerRender.getConfiguration().setClassicCompatible(true);
		FreeMarkerRender.getConfiguration().setSharedVariable("block", new BlockDirective());
		FreeMarkerRender.getConfiguration().setSharedVariable("override", new OverrideDirective());
		FreeMarkerRender.getConfiguration().setSharedVariable("extends", new ExtendsDirective());
	}
}
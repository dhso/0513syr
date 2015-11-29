package frame.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;

import frame.kit.HttpKit;
import modules.site.entity.IkeaUser;

public class ReqResInViewInterceptor implements Interceptor {
	private static final Logger logger = Logger.getLogger(ReqResInViewInterceptor.class);

	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		IkeaUser ikeaUser = controller.getSessionAttr("_ikeaUser");
		logger.info(" idcardNum:" + (null != ikeaUser ? ikeaUser.getMmbMembershipnum() : "") + "|~~|name:" + (null != ikeaUser ? ikeaUser.getCstName() : "") + "|~~|visit:" + HttpKit.getUrl(controller.getRequest()));
		inv.invoke();
		// TokenManager.createToken(inv.getController(), "jockillerToken", 30 * 60);
		// inv.getController().setAttr("_request", inv.getController().getRequest());
		// inv.getController().setAttr("_response", inv.getController().getResponse());
	}
}

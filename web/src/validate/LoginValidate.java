package validate;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;

import validate.base.ShortCircuitValidate;

/**
 * 登录校验器
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date: 2015年8月2日 下午4:56:42
 */
public class LoginValidate extends ShortCircuitValidate {

	@Override
	protected void validate(Controller c) {
		validateRequired("email", "message", "请输入您的邮箱");
		validateEmail("email", "message", "请检查您的邮箱");

		validateRequired("password", "message", "请输入您的密码");
		validateString("password", 6, 24, "message", "请输入6~24位的密码");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("code", 1);
		c.render(new JsonRender());
	}

}

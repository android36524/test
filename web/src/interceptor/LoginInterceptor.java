package interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import common.WebUtils;
import model.User;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginInterceptor implements Interceptor {
    private Log logger = Log.getLog(User.class);
    @Override
    public void intercept(Invocation invocation) {
        Controller controller = invocation.getController();
        User user = WebUtils.currentUser(controller);
        if (user !=null){
            invocation.invoke();
//            return;
        }else {
            //登录地址
            //测试sessionID
//            String sessionId = controller.getRequest().getRequestedSessionId();
//            System.out.println(sessionId);
            String loginPath = "/userLogin?from=%s";
            HttpServletRequest request = controller.getRequest();
           //下段还得debug深入
            String queryString = request.getQueryString();
            // 被拦截前的请求URL
            String redirectUrl = request.getRequestURI();
            if (StrKit.notBlank(queryString)) {
                redirectUrl = redirectUrl.concat("?").concat(queryString);
            }
            try {
                redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
            String tagPath = String.format(loginPath, redirectUrl);
            logger.info("login[redirectUrl]:\t" + tagPath);
            controller.redirect(tagPath);
        }
    }
}

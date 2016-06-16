package common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import model.User;
/**
 * Web相关工具类
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date 2015年7月5日下午7:48:48
 */
public final class WebUtils {

    private WebUtils() {}
    private final static String USER_COOKIE_KEY    = "user";
    /**
     * 返回当前用户
     *  Controller
     * @return UserModel
     */
    public static User currentUser(Controller c) {
        HttpServletRequest request = c.getRequest();
        String cookieKey = USER_COOKIE_KEY;
        // 获取cookie信息
        String userCookie = getCookie(request, cookieKey);
        // 1.cookie为空，直接清除
        if (StrKit.isBlank(userCookie)) {
            removeCookie(c, cookieKey);
            return null;
        }
        // 2.解密cookie
        String[] userInfo = userCookie.split("-");
        String userId   = userInfo[0];
        String oldTime  = userInfo[1];
        String maxAge   = userInfo[2];
        // 5.判定时间区间，超时的cookie清理掉
        if (!"-1".equals(maxAge)) {
            long now  = System.currentTimeMillis();
            long time = Long.parseLong(oldTime) + (Long.parseLong(maxAge) * 1000);
            if (time <= now) {
                removeCookie(c, cookieKey);
                return null;
            }
        }
        return User.dao.findById(userId);
    }

    /**
     * 用户登陆状态维持
     *
     * cookie设计为: des(私钥).encode(userId~time~maxAge~ip)
     *
     *  Controller 控制器
     *  UserModel  用户model
     * @param remember   是否记住密码、此参数控制cookie的 maxAge，默认为-1（只在当前会话）<br>
     *                   记住密码默认为一周
     * @return void
     */
    public static void loginUser(Controller c, User user, boolean... remember) {
        // 获取用户的id、nickName
        String uid     = user.get("id") + "";
        // 当前毫秒数
        long   now      = System.currentTimeMillis();
        // 超时时间
        int    maxAge   = -1;
        if (remember.length > 0 && remember[0]) {
            maxAge      = 60 * 60 * 24 * 7;
        }
        // 用户id地址
        // 构造cookie
        StringBuilder cookieBuilder = new StringBuilder()
                .append(uid).append("-")
                .append(now).append("-")
                .append(maxAge);
        // cookie 私钥
        c.setCookie(USER_COOKIE_KEY,cookieBuilder.toString(),maxAge,"/",true);
    }

    /**
     * 退出即删除用户信息
     *  Controller
     * @return void
     */
    public static void logoutUser(Controller c) {
        removeCookie(c, USER_COOKIE_KEY);
    }

    /**
     * 读取cookie
     * @param request
     * @param key
     * @return
     */
    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if(null != cookies){
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    /**
     * 清除 某个指定的cookie
     * @param
     * @param key
     */
    public static void removeCookie(Controller c, String key) {
        c.setCookie( key, null, 0);
    }
    /**
     * 获取浏览器信息
     * @param
     * @return String
     */
    public static String getUserAgent(Controller c)
    {
        return getUserAgent(c.getRequest());
    }
    /**
     * 获取浏览器信息
     * @param
     * @return String
     */
    public static String getUserAgent(HttpServletRequest request)
    {
        return request.getHeader("User-Agent");
    }
    /**
     * 获取ip
     * @param
     * @return
     */
    public static String getIP(Controller c) {
        HttpServletRequest request = c.getRequest();
        return getIP(request);
    }
    /**
     * 获取ip
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Requested-For");
        if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StrKit.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}

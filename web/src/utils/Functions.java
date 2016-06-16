package utils;

import com.jfinal.core.Controller;
import common.WebUtils;
import model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Functions {
    /**
     * 继续encode URL (url,传参tomcat会自动解码)
     * 要作为参数传递的话，需要再次encode
     * @param encodeUrl
     * @return String
     */
    public String encodeUrl(String url) {
        try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        return url;
    }

    /**
     * 获取登录的用户
     * @return
     */
    public User currentUser(Controller c) {
        return WebUtils.currentUser(c);
    }
}

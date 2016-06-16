package controller;

import ajax.AjaxResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.log.Log;
import common.WebUtils;
import model.User;
import service.UserService;
import validate.LoginValidate;
import validate.RegisterValidate;


public class AjaxController extends Controller {
    private Log logger = Log.getLog(User.class);
    private AjaxResult result = new AjaxResult();
    @Before(LoginValidate.class)
    public void session() {
        String email = getPara("email");
        String password = getPara("password");
        String remember = getPara("remember", "0");
        UserService userService = new UserService();
        User user = userService.findByEmail(email);
        if (null == user) {
            renderJson(result.adderror("该邮箱尚未注册"));
            return;
        }
        password = HashKit.md5(password);
        // 比较密码
        String oldPwd = user.get("password");
        if (!oldPwd.equals(password)) {
            renderJson(result.adderror("您输入的密码不正确"));
            return;
        }
//		setSessionAttr("user", user);
        WebUtils.loginUser(this, user, "1".equals(remember));
//        setCookie("user",user.getPassword(),-1);
        renderJson(result);
    }
    @Before(RegisterValidate.class)
    public void register(){
        String email = getPara("email");
        String password = getPara("password");
        String remember = getPara("remember", "0");
        UserService userService = new UserService();
        User user = userService.findByEmail(email);
        if (null != user) {
            renderJson(result.adderror("该邮箱已经存在"));
            return;
        }
        User newUser = new User();
        newUser.setPassword(HashKit.md5(password));
        newUser.setEmail(email);
        try {
            newUser.save();
            result.success(newUser);
        }catch (Exception e){
            logger.error("写入数据库失败");
        }finally {
           renderJson(result);
        }
    }
}

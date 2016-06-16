package controller;

import ajax.AjaxResult;
import com.jfinal.core.Controller;

public class IndexController extends Controller {
    private AjaxResult result = new AjaxResult();
    public void index(){

    }
    public void image_code() {
        renderCaptcha();
    }
    public void userLogin(){
        String from = getPara("from","/");
        setAttr("from",from);
        render("/front/login.html");
    }
    public void userRegister() {
        String from = getPara("from", "/");
        setAttr("from", from);
        render("/front/register.html");
    }
}

package controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import com.jfinal.plugin.activerecord.Page;
import interceptor.LoginInterceptor;
import model.User;
import service.UserService;
import utils.Token;

public class UserController extends Controller {

    public void index(){
        Integer PageNumber = getParaToInt(0,1);
        Page<User> users = User.dao.paginate(PageNumber,10,"select *","from user");
        setAttr("users",users);
        render("list.html");
    }
    public void form(){
        render("form.html");
    }
    public void submit(){
        User user = getModel(User.class,"user");
        user.setToken(Token.getUUID());
        user.save();
        redirect("/user");
    }
    /*
       编辑页面
     */
    @Before(LoginInterceptor.class)
    public void edit(){
        Integer id = getParaToInt(0);
        if (id!=null&&id>0){
            User user = User.dao.findById(id);
            setAttr("user",user);
            form();
        }else {
            renderText("该用户不存在");
        }
    }

    /**
     * 删除
     */
    public void del(){
        User.dao.deleteById(getPara(0));
        redirect("/user");
    }

    /**
     * 更新
     */
    public void update(){
        User user = getModel(User.class,"user");
        user.update();
        redirect("/user");
    }
    /**
     * 条件查询
     */
    public void search(){
        String username = getPara("username");
        String email = getPara("email");
        UserService us = new UserService();

        if (us.searchBy(username,email)==null){
            renderText("查无此人");
        }else {
            setAttr("users",us.searchBy(username,email));
        }
        render("search.html");
    }
}

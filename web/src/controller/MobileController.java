package controller;

import ajax.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.Page;
import model.Module;
import model.Topic;
import model.User;
import service.UserService;
import validate.MobileRegisterValidate;

import java.util.*;

public class MobileController extends Controller {
    private AjaxResult result = new AjaxResult();
    public void index(){
        this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        Integer PageNumber = getParaToInt(0,1);
        //int PageNumber = Integer.parseInt(getPara("page","1"));
        Page<User> users = User.dao.paginate(PageNumber,10,"select *","from user");
        List<User> userLists = users.getList();
        String jsonText = JSON.toJSONString(userLists,true);
        renderText("{"+"\"result\":"+jsonText+"}");
    }
    public void article(){
        this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        Integer id = getParaToInt(0,1);
        User topic = User.dao.findById(id);
        String topicText = JSON.toJSONString(topic,true);
        renderText("{\"result\":["+topicText+"]}");
    }
    public void topics(){
        this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        Integer PageNumber = getParaToInt(0,1);
        Page<Topic> topics = Topic.dao.paginate(PageNumber,10,"select *","from topic");
        List<Topic> topicLists = topics.getList();
        String jsonText = JSON.toJSONString(topicLists,true);
        renderText("{\"result\":"+jsonText+"}");
    }
    public void UserLogin(){
        this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
//        Map<String,String[]> params =  getParaMap();
//        HashMap<Object, String> newMap = new HashMap<>();
//        for (Map.Entry<String, String[]> element : params.entrySet()) {
//            Object strKey = element.getKey();
//            String[] value = element.getValue();
//            String valueStr = "";
//            System.out.print(strKey.toString() + "=");
//            for (String aValue : value) {
//                System.out.print(aValue + ",");
//                valueStr += aValue + ",";
//            }
//            System.out.println();
//
//            newMap.put(strKey, valueStr);
//        }
        String email = getPara("email");
        String password = getPara("password");
        UserService userService = new UserService();
        User user = userService.findByEmail(email);
        if (user == null){
            result.adderror("该用户不存在");
            String JsonText = JSON.toJSONString(result,true);
            renderText("{\"result\":"+JsonText+"}");
           // System.out.print("{\"result\":"+JsonText+"}");
            return;
        }
        String realPassword = HashKit.md5(password);
        if (user.getPassword().equals(realPassword)){
            result.success(user);
            String JsonText = JSON.toJSONString(result,true);
            //System.out.print(JsonText);
            renderText("{\"result\":"+JsonText+"}");
        }else {
            result.adderror("密码错误");
            String JsonText = JSON.toJSONString(result,true);
            //System.out.print(JsonText);
            renderText("{\"result\":"+JsonText+"}");
        }
    }
    @Before(MobileRegisterValidate.class)
    public void UserRegister(){
        this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        String email = getPara("email");
        String password = getPara("password");
        String sex = getPara("sex");
        String username = getPara("username");
        UserService userService = new UserService();
        if (userService.findByEmail(email)!=null){
            result.adderror("该邮箱已经存在");
            String JsonText = JSON.toJSONString(result,true);
            renderText("{\"result\":"+JsonText+"}");
        }else {
            if (userService.InsertUser(email,password,sex,username)){
                result.adderror("恭喜你注册成功");
                result.success(userService.findByEmail(email));
                String JsonText = JSON.toJSONString(result,true);
                renderText("{\"result\":"+JsonText+"}");
            }else {
                result.success("抱歉，你注册失败了");
                String JsonText = JSON.toJSONString(result,true);
                renderText("{\"result\":"+JsonText+"}");
            }
        }
    }
    public void NewTopics(){
        this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        String id = getPara("id");
        String content = getPara("content");
        String title = getPara("title");
        User user = User.dao.findById(id);
        if (user!=null){
            if (user.getToken().equals(getPara("sign"))){
                try {
                    Module module = new Module();
                    module.setName(title);
                    module.save();
                    Integer moduleIdId = module.getId();
                    System.out.println(moduleIdId);
                    Topic topic = new Topic();
                    topic.setContent(content);
                    topic.setModuleID(moduleIdId);
                    topic.setUserID(Integer.parseInt(id));
                    topic.save();
                }catch (Exception e){
                      System.out.println("保存文章错误");
                }
            }else {
                renderText("对不起，请先登录");
            }
        }
        renderJson(result);
    }
}

package controller;

import ajax.AjaxResult;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import model.Testgroup;
import model.Teststudent;
import service.CompetitionService;
import validate.CompetitionValidate;

import java.util.List;

public class CompetitionController extends Controller {
    private AjaxResult result = new AjaxResult();
//    @Before(LoginInterceptor.class)
    public void register(){
        String from = getPara("from", "/");
        setAttr("from", from);
    }
    @Before(CompetitionValidate.class)
    public void join(){
        String name = getPara("name");
        String studentNumber = getPara("studentNumber");
        String major = getPara("major");
        String academy = getPara("academy");
        Teststudent teststudent = new Teststudent();
        teststudent.setName(name);teststudent.setStudentNumber(studentNumber);
        teststudent.setMajor(major); teststudent.setAcademy(academy);
        String gruopName = getPara("groupname");
        Testgroup testgroup = new Testgroup();
        CompetitionService competitionService = new CompetitionService();
        Teststudent student = competitionService.findBystudentNumber(teststudent.getStudentNumber());
        if (null != student) {
            renderJson(result.adderror("该学号已经存在"));
            return;
        }
        if ( gruopName==null||"".equals(gruopName)) {
            teststudent.setGroupid(null);
               try {
                   teststudent.save();
               }catch (Exception e){
                   result.adderror("报名出错了，请稍后再试");
               }
        }else {
            testgroup.setGruopName(gruopName);
            try {
                testgroup.save();
                Integer id = testgroup.getId();
                teststudent.setGroupid(id);
                teststudent.save();
            }catch (Exception e) {
                result.adderror("保存队名出错");
            }
        }
        Integer id = teststudent.getId();
        result.setId(id);
//        setCookie("student",Integer.toString(id),-1,true);
        renderJson(result);
    }
    public void allGruop(){
        Integer PageNumber = getParaToInt(0,1);
        Page<Testgroup> gruops = Testgroup.dao.paginate(PageNumber,10,"select *","from testgroup");
        setAttr("gruops",gruops);
        render("list.html");
    }

    public void detail(){
        Integer id = getParaToInt(0);
        if (id!=null&&id>0){
            Teststudent student = Teststudent.dao.findById(id);
            Testgroup group = Testgroup.dao.findById(student.getGroupid());
            setAttr("student",student);
            setAttr("group",group);
            setCookie("student",Integer.toString(id),-1,true);
            render("form.html");
        }else {
            renderText("该用户不存在");
        }
    }
    public void update(){
        Teststudent student = getModel(Teststudent.class);
        student.update();
        redirect("/competition/detail/"+student.getId());
    }
    public void together(){
        List<Teststudent> teststudents = null;
        Integer id = getParaToInt(0);
        Testgroup testgroup = Testgroup.dao.findById(id);
        String sql = "SELECT * FROM teststudent WHERE groupid=?";
         teststudents = Teststudent.dao.find(sql,id);
        if (teststudents.size()>3||teststudents.size()==3){
            setAttr("error","该队伍人员已满！");
            setAttr("gruop",testgroup);
            render("group.html");
            return;
        }
        String studentId = getCookie("student");
        if (studentId==null){
             render("Cookie.html");
             return;
        }
        Teststudent teststudent = Teststudent.dao.findById(studentId);
        if (teststudent.getGroupid()!=null){
            setAttr("error","你已经参加了一个队伍，请退出当前队友后参加！");
            setAttr("gruop",testgroup);
            render("group.html");
        }
        teststudent.setGroupid(id);
        teststudent.update();
        teststudents = Teststudent.dao.find(sql,id);
        setAttr("students",teststudents);
        setAttr("gruop",testgroup);
        render("group.html");
    }
    public void search(){
        String gruopName = getPara("groupName");
        setAttr("search","search");
        if ("".equals(gruopName)||gruopName==null){
            render("list.html");
        }
        CompetitionService competitionService = new CompetitionService();
        List<Testgroup> testgroups = competitionService.findGroupByName(gruopName);
        if (testgroups == null){
            setAttr("error","不存在该队名");
            render("list.html");
        }else {
            setAttr("gruops",testgroups);
            render("list.html");
        }
    }
    public void groupDetail(){
        Integer id = getParaToInt(0);
        String sql = "SELECT * FROM teststudent WHERE groupid=?";
        List<Teststudent> teststudents = Teststudent.dao.find(sql,id);
        Testgroup testgroup = Testgroup.dao.findById(id);
        setAttr("students",teststudents);
        setAttr("gruop",testgroup);
        render("group.html");
    }
    public void person(){
        String studentNumber = getPara(0);
        if (studentNumber==null||"".equals(studentNumber)){
            renderText("学号不能为空！");
            return;
        }
        String sql = "SELECT * FROM teststudent WHERE studentNumber=?";
        Teststudent teststudent = Teststudent.dao.findFirst(sql,studentNumber);
        if (teststudent == null){
            renderText("改学号尚未报名！");
            return;
        }
        Testgroup group = Testgroup.dao.findById(teststudent.getGroupid());
        Integer id = teststudent.getId();
        setAttr("student",teststudent);
        setAttr("group",group);
        setCookie("student",Integer.toString(id),-1,true);
        render("form.html");
    }
    public void del(){
        Integer id = getParaToInt(0);
        String sql = "SELECT * FROM teststudent WHERE groupid=?";
        Teststudent teststudent = Teststudent.dao.findFirst(sql,id);
        teststudent.setGroupid(null);
        teststudent.update();
        setAttr("student",teststudent);
        render("form.html");
        redirect("/competition/person/"+teststudent.getStudentNumber());
    }
}

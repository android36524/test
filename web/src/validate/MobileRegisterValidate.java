package validate;
import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;
import validate.base.ShortCircuitValidate;

public class MobileRegisterValidate extends ShortCircuitValidate {
    @Override
    protected void handleError(Controller controller) {
        controller.setAttr("code",1);//往request对象中再放入值，已遍Jsonrender导出
        controller.render(new JsonRender());//JsonRender方法会自动遍历request中的值，并已json方式render出来
    }
    @Override
    protected void validate(Controller controller) {
        String value = controller.getPara("email");
        validateRequired("email", "message", "请输入您的邮箱");
        validateEmail("email", "message", "请检查您的邮箱");
    }

}

package validate;

import com.jfinal.core.Controller;
import validate.base.ShortCircuitValidate;

/**
 * Created by dddnkj on 2016/3/7.
 */
public class CompetitionValidate extends ShortCircuitValidate {
    @Override
    protected void validate(Controller controller) {
        validateRequired("name", "message", "请输入您的姓名");
        validateRequired("studentNumber", "message", "请输入您的学号");
        validateRequiredString("major","message","请输入你的专业");
        validateRequired("academy", "message", "请输入学院");
    }

    @Override
    protected void handleError(Controller controller) {

        controller.renderJson();
    }
}

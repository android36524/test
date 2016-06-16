package controller.front;

import com.jfinal.config.Routes;
import controller.AjaxController;
import controller.CompetitionController;
import controller.IndexController;
import controller.MobileController;


public class FrontRoutes extends Routes {
    @Override
    public void config() {
        this.add("/", IndexController.class);
        this.add("/mobile", MobileController.class);
        this.add("/ajax", AjaxController.class);
        this.add("/competition", CompetitionController.class);
    }
}

package controller.admin;

import com.jfinal.config.Routes;
import controller.UserController;
public class AdminRoutes extends Routes {
    @Override
    public void config() {
        this.add("/user", UserController.class);
    }
}

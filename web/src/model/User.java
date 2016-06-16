package model;
import model.base.BaseUser;

@SuppressWarnings("serial")
public class User extends BaseUser<User> {
    public static final User dao = new User();
}

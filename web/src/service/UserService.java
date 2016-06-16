package service;

import com.jfinal.kit.HashKit;
import model.User;
import utils.Token;
import java.util.List;
public class UserService {
    private StringBuilder sql = new StringBuilder("SELECT * FROM user as u ");
    public List<User> searchBy(String username, String email){
        StringBuilder sql1 = new StringBuilder("SELECT * FROM user as u ");
        if (username==null||"".equals(username)){
                if (email==null||"".equals(email)){
                    return null;
                }else {
                    sql1.append(" WHERE u.email LIKE '%").append(email).append("%'");
                }
        }else {
            if (email==null||"".equals(email)){
                sql1.append(" WHERE u.username LIKE '%").append(username).append("%'");
            }else {
                sql1.append("WHERE u.username LIKE '%").append(username).append("%' ").append(" AND u.email LIKE '%")
                        .append(email).append("%' ");
            }
        }
        return User.dao.find(sql1.toString());
    }
    public User findByEmail(String email) {
        //   sql.append(" WHERE u.email = '").append(email).append("'");
        //   return User.dao.findFirst(sql.toString());
        String sql = "SELECT * FROM user WHERE email = ? LIMIT 1";
        return User.dao.findFirst(sql, email);
    }
    public boolean InsertUser(String email,String password,String sex,String username){
        String realPassword = HashKit.md5(password);
        User user = new User();
        Integer sexId = Integer.parseInt(sex);
        if (sexId==1){
            user.setSex(true);
        }
        user.setEmail(email);
        user.setPassword(realPassword);
        user.setUsername(username);
        user.setToken(Token.getUUID());
        return user.save();
    }
}

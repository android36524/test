package service;

import model.Testgroup;
import model.Teststudent;

import java.util.List;


public class CompetitionService {
    public  List<Testgroup> findGroupByName(String groupName){
        String sql = "SELECT * FROM testgroup WHERE gruopName = ?";
        return Testgroup.dao.find(sql,groupName);
    }
    public Teststudent findBystudentNumber(String studentNumber) {
        //   sql.append(" WHERE u.email = '").append(email).append("'");
        //   return User.dao.findFirst(sql.toString());
        String sql = "SELECT * FROM teststudent WHERE studentNumber = ? LIMIT 1";
        return Teststudent.dao.findFirst(sql, studentNumber);
    }
}

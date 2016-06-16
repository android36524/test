package common;

public class SqlUtils {
    private StringBuilder sql;
    public SqlUtils(){
        sql = new StringBuilder().append("SELECT ");
    }
    public String getLikeSql(String[] fields,String tableName,String likeQuery,String likeField){
        for (String field:fields
                ) {
            sql.append(field).append(" FROM ").append(tableName);
        }
        if ("".equals(likeField)&&"".equals(likeQuery)){
            return sql.toString();
        }else {
            sql.append(" WHERE ").append(likeField).append(" LIKE '%").append(likeQuery)
                    .append("%'");
            return sql.toString();
        }
    }
}

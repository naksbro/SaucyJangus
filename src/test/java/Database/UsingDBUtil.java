package Database;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.DBType;
import util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UsingDBUtil {

    @BeforeClass
    public void setUP() throws SQLException {
        DBUtil.establishDBConnection(DBType.ORACLE);
    }

    @AfterClass
    public void closeConnectons(){
        DBUtil.closeConnections();
    }

    @Test
    public void test1() throws SQLException {
        List<Map<String, Object>> result = DBUtil.runSQLQuery("select department_name from departments");
        for(Map<String, Object> m : result)
            System.out.println(m);
    }

    @Test
    public void test2() throws SQLException {
        List<Map<String, Object>> result = DBUtil.runSQLQuery("select first_name, last_name from employees where employee_id = 105");
        Assert.assertEquals(result.get(0).get("FIRST_NAME"), "David");
        Assert.assertEquals(result.get(0).get("LAST_NAME"), "Austin");
    }

    @Test
    public void test3() throws SQLException {
        String query = "select country_id from countries where country_id = 'BR'";
        List<Map<String, Object>> result = DBUtil.runSQLQuery(query);
        Assert.assertEquals(result.get(0).get("COUNTRY_ID"),"BR");
    }

}

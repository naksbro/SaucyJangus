package Database;

import org.junit.Test;
import java.sql.*;
import java.util.*;

/**
 * @Author Mark Admana
 * dbConnectionJDBC will set connection to Oracle db
 * Connection takes dbURL, dbUsername, dbPassword as params
 * Statement will allow us to scroll
 * ResultSet sends Query
 * @throws SQLException
 */

public class databaseConnection {

    String dbURL = "jdbc:oracle:thin:@54.158.120.139:1521:xe";
    // url = library:db name:type of connection:DNS/IP:port#:SID

    String dbUser = "hr";
    String dbPw = "hr";

    @Test
    public void dbConnectionJDbC() throws SQLException {
        Connection con = DriverManager.getConnection(dbURL, dbUser, dbPw);
        Statement stmnt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resSet = stmnt.executeQuery("select * from employees");

        resSet.last();
        int rowCount = resSet.getRow();
        String rowData = resSet.getString(1);
//        System.out.println("Row count: "+rowCount);
//        System.out.println(resSet.getString(2)+" "+resSet.getString(3));
        resSet.first();

        int i = 0;
        while (i < 5) {
            System.out.println(resSet.getRow()+" <===> "+resSet.getString("first_name")+" "+resSet.getString("last_name"));
            i++;
        }

        resSet.close();
        stmnt.close();
        con.close();
    }

    @Test
    public void getEmployeeCountDep100() throws SQLException {
        Connection con = DriverManager.getConnection(dbURL, dbUser, dbPw);
        Statement stmnt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resSet = stmnt.executeQuery("select count(first_name) from employees\n" +
                "where department_id = 100");

        resSet.first();
        System.out.println(resSet.getString(1));

        resSet.close();
        stmnt.close();
        con.close();
    }

    @Test
    public void firstFiveEmps() throws SQLException {
        Connection con = DriverManager.getConnection(dbURL, dbUser, dbPw);
        Statement stmnt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resSet = stmnt.executeQuery("select * from employees where rownum <= 5");

        resSet.first();
        do{
            System.out.println(resSet.getString("first_name")+" "+resSet.getString("last_name"));
        }
        while(resSet.next());

        resSet.close();
        stmnt.close();
        con.close();
    }

    @Test
    public void dbMetaData() throws SQLException {
        Connection con = DriverManager.getConnection(dbURL, dbUser, dbPw);
        Statement stmnt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String query = "select employee_id, last_name, job_id, salary from employees";
        ResultSet resSet = stmnt.executeQuery(query);

        // collect metadata
        DatabaseMetaData dbmd = con.getMetaData();
//        System.out.println("User: "+dbmd.getUserName());
//        System.out.println("Database type: "+dbmd.getDatabaseProductName());
//        System.out.println("Database version: "+dbmd.getDatabaseProductVersion());
//
        ResultSetMetaData resMD = resSet.getMetaData();
//        System.out.println("Columns count: "+resMD.getColumnCount());
//        System.out.println("First Column: "+resMD.getColumnName(1));
//        System.out.println("Last Column: "+resMD.getColumnName(4));

//        for(int i = 1; i <= resMD.getColumnCount(); i++){
//            System.out.println("Column name: "+resMD.getColumnName(i));
//        }

        List<Map<String, Object>> mapList = new ArrayList<>();
        int colCount =resMD.getColumnCount();

        while(resSet.next()){
            Map<String, Object> rowMap = new LinkedHashMap<>();
            for(int i = 1; i <= colCount; i++){
                rowMap.put(resMD.getColumnName(i),resSet.getObject(i));
            }
            mapList.add(rowMap);
        }

        for(Map<String, Object> m : mapList){
            System.out.println(m);
        }

        resSet.close();
        stmnt.close();
        con.close();
    }

}
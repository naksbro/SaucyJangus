package Database;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.sql.*;

public class colDBTest {

    String dbURL = "jdbc:oracle:thin:@54.158.120.139:1521:xe";
    String dbUser = "hr";
    String dbPw = "hr";

    @Test
    public void employeeID150To1060() throws SQLException {
        Connection con = DriverManager.getConnection(dbURL, dbUser, dbPw);
        Statement stmnt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resSet = stmnt.executeQuery("select count(*) from employees where department_id = 20");
        ResultSetMetaData resMD = resSet.getMetaData();

        while(resSet.next()){
            for(int i = 1; i <= resMD.getColumnCount(); i++){
                System.out.println(resMD.getColumnName(i)+": "+resSet.getString(i));
            }
            System.out.println();
        }
        resSet.close();
        stmnt.close();
        con.close();
    }
}

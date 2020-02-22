package Database;

import util.DBConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class mySQLDB {

    public static void main(String[] args) throws SQLException {
        String q = "select * from students";

        String[][] students = fetchDataFromDB(q);
        //System.out.println(Arrays.deepToString(students));

        int i = 0;
        while(i < students.length){
            for (int j = 0; j < students[i].length; j++){
                System.out.print(students[i][j]+" ");
            }
            System.out.println();
            i++;
        }
    }

    public static String[][] fetchDataFromDB(String query) throws SQLException {
        String url = "jdbc:mysql://db4free.net:3306/techleadacademy?user=techlead&password=students";

        DBConnectionManager dbInstance = DBConnectionManager.getInstance(url);
        Connection conn = dbInstance.getConn();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(query);

        int colCount = rs.getMetaData().getColumnCount();
        rs.last(); // moves rs to last row
        int rowCount = rs.getRow(); // returns index of rows
        rs.beforeFirst(); // brings back to first row

        String[][] results = new String[rowCount][colCount];

        int i = 0;
        while(rs.next()){
            for (int j = 0; j < colCount; j++) {
                results[i][j] = rs.getString(j + 1);
            }
            i++;
        }
        return results;
    }
}

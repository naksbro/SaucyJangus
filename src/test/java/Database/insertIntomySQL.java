package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class insertIntomySQL {

    public static void main(String[] args) {
        insertToDB();
    }

    public static void insertToDB(){
        try {
            String myUrl = "jdbc:mysql://db4free.net:3306/techleadacademy";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(myUrl, "techlead", "students");

            String postQuery = "INSERT INTO students (name,lastname,team,student_id) VALUE(?,?,?,?)";

            // SQL interface allows to post in DB table
            PreparedStatement prepStat = conn.prepareStatement(postQuery);
            prepStat.setString(1, "Uptown");
            prepStat.setString(2,"Funk");
            prepStat.setString(3, "ninja turtles");
            prepStat.setInt(4, 69);

            prepStat.execute();

            conn.close();
        }catch(Exception e){
            System.err.println("--- EXCEPTION ---");
            System.err.println(e.getMessage());
        }
    }
}

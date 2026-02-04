import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Scanner;

public class Alpha {
    public static void main(String[] args) throws SQLException {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Enter dbname:");
        String databaseName = inputScanner.nextLine();

        System.out.println("Enter table:");
        String tableName = inputScanner.nextLine();

        String connectionUrl = "jdbc:mysql://localhost:3306/" + databaseName;
        String userName = "root";
        String userPassword = "dauren";

        Connection connection = DriverManager.getConnection(connectionUrl, userName, userPassword);
        Statement statement = connection.createStatement();

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (id INTEGER, name VARCHAR(50), gpa DOUBLE, happy BOOLEAN)");

        statement.executeUpdate("DELETE FROM " + tableName);

        statement.executeUpdate("INSERT INTO " + tableName + " VALUES (25027,'Dauren',3.7,true)");
        statement.executeUpdate("INSERT INTO " + tableName + " VALUES (25028,'Alice',3.5,true)");
        statement.executeUpdate("INSERT INTO " + tableName + " VALUES (25029,'Bob',2.7,true)");
        statement.executeUpdate("INSERT INTO " + tableName + " VALUES (25030,'Trudy',1.0,false)");
        statement.executeUpdate("INSERT INTO " + tableName + " VALUES (25031,'John',3.0,true)");

        ResultSet res = statement.executeQuery("SELECT * FROM " + tableName);

        while (res.next()) {
            int studentId = res.getInt("id");
            String studentName = res.getString("name");
            double studentGpa = res.getDouble("gpa");
            boolean isHappy = res.getBoolean("happy");

            System.out.println(studentId + "  " + studentName + "  " + studentGpa + "  " + isHappy);
        }

        res.close();
        statement.close();
        connection.close();
        inputScanner.close();
    }
}
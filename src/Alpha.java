import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Alpha {
    static void main(String[] args){
        Scanner input=new Scanner(System.in);
        System.out.println("Enter dbname:");
        String dbname=input.nextLine();

        System.out.println("Enter table:");
        String table=input.nextLine();

        Connection connection= DriverManager.getConnection("");
        Statement statement=connection.createStatement();

        statement.executeQuery("CREATE table student(id integer,name varchar(50),gpa double,happy boolean)");
        statement.executeQuery("insert into student values (25027,'Dauren',3.7,true)");
        statement.executeQuery("insert into student values (25028,'Alice',3.5,true)");
        statement.executeQuery("insert into student values (25029,'Bob',2.7,true)");
        statement.executeQuery("insert into student values (25030,'Trudy',1.0,false)");
        statement.executeQuery("insert into student values (25031,'John',3.0,true)");

        ResultSet rs=statement.executeQuery("select *from "+table);
        while (rs.next()){
            int id=rs.getInt("id");
            String name=rs.getString("name");
            double gpa=rs.getDouble("gpa");
            boolean happy=rs.getBoolean(4);
            System.out.println(id+" "+name+" "+gpa+" "+happy);
        }
    }
}

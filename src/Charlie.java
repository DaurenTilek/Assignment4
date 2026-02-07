import java.sql.*;

public class Charlie {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/my_database";
        String user = "root";
        String pass = "dauren";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            buildTableStructure(connection);
            processMigration(connection);
            printResults(connection);
        } catch (SQLException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    public static void processMigration(Connection connection) throws SQLException {
        String select = "SELECT * FROM Student1";
        String insert = "INSERT INTO Student2 (username, password, firstname, lastname) VALUES (?, ?, ?, ?)";

        try (Statement selectstatement = connection.createStatement();
             ResultSet res = selectstatement.executeQuery(select);
             PreparedStatement insertStmt = connection.prepareStatement(insert)) {

            Statement delete = connection.createStatement();
            delete.executeUpdate("DELETE FROM Student2");

            while (res.next()) {
                String fullName = res.getString("fullname").trim();
                String[] parts = fullName.split("\\s+");

                String firstName = "";
                String lastName = "";

                if (parts.length >= 2) {
                    firstName = parts[0];
                    lastName = parts[parts.length - 1];
                } else if (parts.length == 1) {
                    firstName = parts[0];
                }

                insertStmt.setString(1, res.getString("username"));
                insertStmt.setString(2, res.getString("password"));
                insertStmt.setString(3, firstName);
                insertStmt.setString(4, lastName);
                insertStmt.executeUpdate();
            }
        }
    }

    private static void buildTableStructure(Connection conn) throws SQLException {
        try (Statement runner = conn.createStatement()) {
            runner.executeUpdate("CREATE TABLE IF NOT EXISTS Student1 (username varchar(50) primary key, password varchar(50), fullname varchar(200))");
            runner.executeUpdate("CREATE TABLE IF NOT EXISTS Student2 (username varchar(50) primary key, password varchar(50), firstname varchar(100), lastname varchar(100))");
            runner.executeUpdate("DELETE FROM Student1");
            runner.executeUpdate("INSERT INTO Student1 VALUES ('a_brown', 'pass', 'Alice Brown')");
            runner.executeUpdate("INSERT INTO Student1 VALUES ('j_smith', 'pass', 'John Smith')");
            runner.executeUpdate("INSERT INTO Student1 VALUES ('t_dauren', 'pass', 'Tilek Dauren')");
        }
    }

    private static void printResults(Connection conn) throws SQLException {
        System.out.println("\n             Student2");
        System.out.printf("%-10s | %-12s | %-12s%n", "User", "Firstname", "Lastname");
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Student2")) {
            while (rs.next()) {
                System.out.printf("%-10s | %-12s | %-12s%n",
                        rs.getString("username"),
                        rs.getString("firstname"),
                        rs.getString("lastname"));
            }
        }
    }
}

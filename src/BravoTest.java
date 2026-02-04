import java.sql.*;
import java.util.Scanner;

public class BravoTest {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/my_database";
        String user = "root";
        String pass = "dauren";

        Connection connection = DriverManager.getConnection(url, user, pass);
        Statement statement = connection.createStatement();
        Scanner input = new Scanner(System.in);

        ResultSet res = statement.executeQuery("SELECT * FROM Quiz");

        int score = 0;
        int totalquestions = 0;

        System.out.println("НАЧИНАЕМ ТЕСТ");

        while (res.next()) {
            totalquestions++;
            System.out.println("\nВопрос №" + res.getInt("questionId") + ": " + res.getString("question"));
            System.out.println("A) " + res.getString("choicea"));
            System.out.println("B) " + res.getString("choiceb"));
            System.out.println("C) " + res.getString("choicec"));
            System.out.println("D) " + res.getString("choiced"));

            System.out.print("Ваш ответ: ");
            String Response = input.nextLine().toUpperCase();
            String correctAnswer = res.getString("answer").trim();

            if (Response.equals(correctAnswer)) {
                System.out.println("Правильно!");
                score++;
            } else {
                System.out.println("Ошибка! Правильный ответ: " + correctAnswer);
            }
        }

        System.out.println("\nТЕСТ ЗАВЕРШЕН");
        System.out.println("Ваш результат: " + score + " из " + totalquestions);

        res.close();
        statement.close();
        connection.close();
    }
}
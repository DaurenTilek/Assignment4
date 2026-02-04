import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Bravo {
    public static void main(String[] args) {
        String connectionUrl = "jdbc:mysql://localhost:3306/my_database";
        String userName = "root";
        String userPassword = "dauren";
        String filePath = "quiz.txt";

        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, userPassword)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Quiz (" +
                    "questionId INT, " +
                    "question VARCHAR(4000), " +
                    "choicea VARCHAR(1000), " +
                    "choiceb VARCHAR(1000), " +
                    "choicec VARCHAR(1000), " +
                    "choiced VARCHAR(1000), " +
                    "answer VARCHAR(5))");

            String insertSql = "INSERT INTO Quiz VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedstatement = connection.prepareStatement(insertSql);

            File quizFile = new File(filePath);
            Scanner fileReader = new Scanner(quizFile);
            int currentQuestionId = 1;

            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                if (line.trim().isEmpty()) continue;

                String questionText = line;
                String choiceA = fileReader.nextLine().substring(3);
                String choiceB = fileReader.nextLine().substring(3);
                String choiceC = fileReader.nextLine().substring(3);
                String choiceD = fileReader.nextLine().substring(3);
                String answerLine = fileReader.nextLine().substring(8);

                preparedstatement.setInt(1, currentQuestionId++);
                preparedstatement.setString(2, questionText);
                preparedstatement.setString(3, choiceA);
                preparedstatement.setString(4, choiceB);
                preparedstatement.setString(5, choiceC);
                preparedstatement.setString(6, choiceD);
                preparedstatement.setString(7, answerLine);

                preparedstatement.executeUpdate();
            }

            fileReader.close();
            preparedstatement.close();
            statement.close();

        } catch (SQLException e) {
            System.err.println("Ошибка БД: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден!");
        }
    }
}
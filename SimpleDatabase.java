import java.sql.*;
import java.util.Scanner;

public class SimpleDatabase {
    private static final String DATABASE_URL = "jdbc:sqlite:sample.db";
    private static final String TABLE_NAME = "my_table";

    public static void main(String[] args) {
        createTable("CREATE TABLE my_table (col1 INTEGER, col2 STRING)");
        insertIntoTable();
    }

    private static void createTable(String query) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(query);
            System.out.println("Table created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertIntoTable() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO " + TABLE_NAME + " VALUES (?, ?)")) {

            // Prompting user for input
            String col1Value = getUserInput("Enter value for col1: ");
            String col2Value = getUserInput("Enter value for col2: ");

            // Setting parameter values
            preparedStatement.setString(1, col1Value);
            preparedStatement.setString(2, col2Value);

            preparedStatement.executeUpdate();
            System.out.println("Row inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getUserInput(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
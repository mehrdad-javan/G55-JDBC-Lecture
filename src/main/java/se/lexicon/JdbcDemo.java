package se.lexicon;

import se.lexicon.db.MySQLConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class JdbcDemo {

    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static void main(String[] args) {
        ex2();
    }

    public static void ex1() {
        try (
                Connection connection = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select id, name, class_group, create_date from student");
        ) {
            // executeQuery: - Used for SELECT statements that return a ResultSet
            // executeUpdate: - Used for INSERT, UPDATE, and DELETE statements
            // execute: - Used for both SELECT and non-SELECT statements
            // executeBatch: - Executes multiple statements in one batch for performance optimization

            while (resultSet.next()) {
                int studentId = resultSet.getInt("id");
                String studentName = resultSet.getString("name");
                String groupName = resultSet.getString("class_group");
                LocalDateTime createDate = resultSet.getTimestamp("create_date").toLocalDateTime();
                System.out.println("Id: " + studentId + " , Name: " + studentName + " , GroupName: " + groupName + " , CreateDate: " + createDate);
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // close the result set
            // close the statement
            // close the connection
        }
    }

    public static void ex2() {
        try (
                Connection connection = MySQLConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, class_group, create_date FROM student WHERE class_group LIKE ?");

                ) {
            String classGroupName = "G1";
            preparedStatement.setString(1, classGroupName);

            // todo: check that next method will close the result set at the end of the process
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int studentId = resultSet.getInt("id");
                    String studentName = resultSet.getString("name");
                    String groupName = resultSet.getString("class_group");
                    LocalDateTime createDate = resultSet.getTimestamp("create_date").toLocalDateTime();
                    System.out.println("Id: " + studentId + " , Name: " + studentName + " , GroupName: " + groupName + " , CreateDate: " + createDate);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        } finally {

        }


    }

}

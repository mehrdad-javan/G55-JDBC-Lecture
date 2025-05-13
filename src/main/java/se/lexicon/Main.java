package se.lexicon;

import se.lexicon.dao.StudentDao;
import se.lexicon.dao.impl.StudentDaoImpl;
import se.lexicon.db.MySQLConnection;
import se.lexicon.model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // MySQL
        try {
            Connection mySqlConnection = MySQLConnection.getConnection();
            StudentDao studentDao = new StudentDaoImpl(mySqlConnection);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your Name:");
            String name = scanner.nextLine();
            System.out.println("Enter your GroupName:");
            String groupName = scanner.nextLine();

            Student student = new Student(name, groupName);
            Student savedStudent = studentDao.save(student);
            System.out.println("savedStudent = " + savedStudent);
            System.out.println("Operation is Done!");

        } catch (SQLException e) {
            System.out.println("MySQL DB Connection Failed.");
        }
    }
}
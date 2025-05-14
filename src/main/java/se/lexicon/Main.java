package se.lexicon;

import se.lexicon.dao.AttendanceDao;
import se.lexicon.dao.StudentDao;
import se.lexicon.dao.impl.AttendanceDaoImpl;
import se.lexicon.dao.impl.StudentDaoImpl;
import se.lexicon.db.MySQLConnection;
import se.lexicon.model.Attendance;
import se.lexicon.model.AttendanceStatus;
import se.lexicon.model.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection mySqlConnection = null;
        try {
            mySqlConnection = MySQLConnection.getConnection();
            mySqlConnection.setAutoCommit(false); // start transaction

            // MySQL
            try {
                StudentDao studentDao = new StudentDaoImpl(mySqlConnection);
                AttendanceDao attendanceDao = new AttendanceDaoImpl(mySqlConnection);

                Student student = new Student("Josip", "G55");
                Student savedStudent = studentDao.save(student); // DONE
                System.out.println("savedStudent = " + savedStudent);
                attendanceDao.save(new Attendance(student, AttendanceStatus.PRESENT)); // DONE
                System.out.println("Operation is Done!");


            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            // an error happened
            mySqlConnection.commit(); // save both records permanently

        } catch (SQLException e) {
            try {
                mySqlConnection.rollback(); // Rollback transaction (Undo both insert queries)
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }


    }
}
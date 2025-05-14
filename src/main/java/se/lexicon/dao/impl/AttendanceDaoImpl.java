package se.lexicon.dao.impl;

import se.lexicon.dao.AttendanceDao;
import se.lexicon.model.Attendance;
import se.lexicon.model.AttendanceStatus;
import se.lexicon.model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceDaoImpl implements AttendanceDao {
    private final Connection connection;

    public AttendanceDaoImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Attendance save(Attendance attendance) {
        String sql = "INSERT INTO attendance (student_id, attendance_date, status) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            attendance.setAttendanceDate(LocalDate.now());
            preparedStatement.setInt(1, attendance.getStudent().getId());
            preparedStatement.setDate(2, Date.valueOf(attendance.getAttendanceDate()));
            preparedStatement.setString(3, attendance.getStatus().name());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys();) {
                    if (keys.next()) {
                        attendance.setId(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error saving attendance: " + e.getMessage());
        }
        return attendance;
    }


    @Override
    public List<Attendance> findAll() {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT a.id, a.attendance_date, a.status, " +
                "s.id AS student_id, s.name, s.class_group " +
                "FROM attendance a " +
                "INNER JOIN student s ON a.student_id = s.id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Fetch Student details
                Student student = new Student(
                        resultSet.getInt("student_id"),
                        resultSet.getString("name"),
                        resultSet.getString("class_group"),
                        resultSet.getTimestamp("create_date").toLocalDateTime()
                );

                // Fetch Attendance details
                Attendance attendance = new Attendance(
                        resultSet.getInt("id"),
                        student, // Now we have full Student details
                        resultSet.getDate("attendance_date").toLocalDate(),
                        AttendanceStatus.valueOf(resultSet.getString("status"))
                );

                attendances.add(attendance);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving all attendance records: " + e.getMessage());
        }
        return attendances;
    }


    @Override
    public Optional<Attendance> findById(int id) {
        String sql = "SELECT a.id, a.attendance_date, a.status, " +
                "s.id AS student_id, s.name, s.class_group " +
                "FROM attendance a " +
                "INNER JOIN student s ON a.student_id = s.id " +
                "WHERE a.id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {

                if (resultSet.next()) {
                    // Fetch Student details
                    Student student = new Student(
                            resultSet.getInt("student_id"),
                            resultSet.getString("name"),
                            resultSet.getString("class_group"),
                            resultSet.getTimestamp("create_date").toLocalDateTime()
                    );

                    // Fetch Attendance details
                    Attendance attendance = new Attendance(
                            resultSet.getInt("id"),
                            student, // Now we have full Student details
                            resultSet.getDate("attendance_date").toLocalDate(),
                            AttendanceStatus.valueOf(resultSet.getString("status"))
                    );

                    return Optional.of(attendance);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving attendance: " + e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public void update(Attendance attendance) {
        String sql = "UPDATE attendance SET student_id = ?, attendance_date = ?, status = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, attendance.getStudent().getId());
            preparedStatement.setDate(2, Date.valueOf(attendance.getAttendanceDate()));
            preparedStatement.setString(3, attendance.getStatus().name());
            preparedStatement.setInt(4, attendance.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error updating attendance: " + e.getMessage());
        }
    }


    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM attendance WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error deleting attendance: " + e.getMessage());
        }
        return false;
    }
}

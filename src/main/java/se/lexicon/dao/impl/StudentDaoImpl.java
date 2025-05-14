package se.lexicon.dao.impl;

import se.lexicon.dao.StudentDao;
import se.lexicon.model.Student;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDaoImpl implements StudentDao {

    private Connection connection;

    public StudentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student save(Student student) {
        String sql = "INSERT INTO student (name, class_group, create_date) VALUES(?, ?, ?)";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getClassGroup());
            student.setCreateDate(LocalDateTime.now());
            preparedStatement.setTimestamp(3,  Timestamp.valueOf(student.getCreateDate()));

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows);

            if (affectedRows > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next()){
                    int generatedStudentId = resultSet.getInt(1);
                    System.out.println("generatedStudentId = " + generatedStudentId);
                    student.setId(generatedStudentId);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error saving student");
            e.printStackTrace();
        }
        return student;
    }


    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                students.add(new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("class_group"),
                        resultSet.getTimestamp("create_date").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving all students: " + e.getMessage());
        }
        return students;
    }



    @Override
    public Optional<Student> findById(int id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    Student student = new Student(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("class_group"),
                            resultSet.getTimestamp("create_date").toLocalDateTime()
                    );
                    return Optional.of(student);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving student: " + e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public void update(Student student) {
        String sql = "UPDATE student SET name = ?, class_group = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getClassGroup());
            preparedStatement.setInt(3, student.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error updating student: " + e.getMessage());
        }
    }


    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM student WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error deleting student: " + e.getMessage());
        }
        return false;
    }
}

package se.lexicon.dao.impl;

import se.lexicon.dao.StudentDao;
import se.lexicon.model.Student;

import java.sql.*;
import java.time.LocalDateTime;
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
        return null;
    }

    @Override
    public Optional<Student> findById(int id) {
        // todo: needs completion
        return Optional.empty();
    }

    @Override
    public void update(Student student) {
        // todo: needs completion
    }

    @Override
    public boolean delete(int id) {
        // todo: needs completion
        return false;
    }
}

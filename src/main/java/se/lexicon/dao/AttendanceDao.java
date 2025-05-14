package se.lexicon.dao;

import se.lexicon.model.Attendance;

import java.util.List;
import java.util.Optional;

public interface AttendanceDao {

    Attendance save(Attendance attendance);

    List<Attendance> findAll();

    Optional<Attendance> findById(int id);

    void update(Attendance attendance);

    boolean delete(int id);
}

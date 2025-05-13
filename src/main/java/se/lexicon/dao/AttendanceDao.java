package se.lexicon.dao;

import se.lexicon.model.Attendance;

import java.util.List;

public interface AttendanceDao {

    Attendance save(Attendance attendance);
    List<Attendance> findAll();
}

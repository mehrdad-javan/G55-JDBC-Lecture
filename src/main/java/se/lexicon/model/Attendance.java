package se.lexicon.model;

import java.time.LocalDate;

public class Attendance {
    private int id;
    private Student student;
    private LocalDate attendanceDate;
    private AttendanceStatus status;

    public Attendance(int id, Student student, LocalDate attendanceDate, AttendanceStatus status) {
        this.id = id;
        this.student = student;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public Attendance(Student student, AttendanceStatus status) {
        this.student = student;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public void setStatus(AttendanceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", student=" + student +
                ", attendanceDate=" + attendanceDate +
                ", status=" + status +
                '}';
    }
}

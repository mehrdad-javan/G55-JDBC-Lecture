package se.lexicon.model;

import java.time.LocalDateTime;

public class Student {
    private int id;
    private String name;
    private String classGroup;
    private LocalDateTime createDate;

    public Student(int id, String name, String classGroup, LocalDateTime createDate) {
        this.id = id;
        this.name = name;
        this.classGroup = classGroup;
        this.createDate = createDate;
    }

    public Student(String name, String classGroup) {
        this.name = name;
        this.classGroup = classGroup;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClassGroup() {
        return classGroup;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassGroup(String classGroup) {
        this.classGroup = classGroup;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", classGroup='" + classGroup + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}

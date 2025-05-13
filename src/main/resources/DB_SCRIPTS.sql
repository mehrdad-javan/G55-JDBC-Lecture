CREATE DATABASE IF NOT EXISTS student_db
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS student (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    class_group VARCHAR(50) NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO student (name, class_group) VALUES
('Alice Johnson', 'G1'),
('Bob Smith', 'G1'),
('Charlie Brown', 'G2'),
('David Wilson', 'G2');

Select id, name, class_group, create_date from student;
SELECT id, name, class_group, create_date FROM student WHERE class_group LIKE 'G1';


CREATE TABLE IF NOT EXISTS attendance (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    attendance_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    status ENUM('Present', 'Absent') NOT NULL,

    FOREIGN KEY (student_id) REFERENCES student(id),
    UNIQUE (student_id, attendance_date)
);
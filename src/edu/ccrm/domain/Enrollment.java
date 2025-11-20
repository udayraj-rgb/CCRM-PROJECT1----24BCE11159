package edu.ccrm.domain;
import java.time.LocalDate;
public class Enrollment {
    private final Student student;
    private final Course course;
    private double marks;           
    private Grade grade;            
    private final LocalDate date;   

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.date = LocalDate.now();
        this.marks = 0.0;  
        this.grade = null;  
    }
    public Student getStudent() {
        return student;
    }
    public Course getCourse() {
        return course;
    }
    public double getMarks() {
        return marks;
    }
    public Grade getGrade() {
        return grade;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setMarks(double marks) {
        this.marks = marks;
    }
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    public void assignMarks(double marks, Grade grade) {
    this.marks = marks;
    this.grade = grade;
}
    @Override
    public String toString() {
        return "Enrollment[" +
                "Student=" + student.getFullName() +
                ", Course=" + course.getCode() +
                ", Marks=" + marks +
                ", Grade=" + (grade != null ? grade : "N/A") +
                ", Date=" + date +
                "]";
    }
}
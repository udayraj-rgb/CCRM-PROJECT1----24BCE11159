package edu.ccrm.domain;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String regNo;
    private boolean active;
    private LocalDate enrollmentDate;
    private List<Course> enrolledCourses;
    public Student(String id, String regNo, String fullName, String email) {
        super(id, fullName, email);
        this.regNo = regNo;
        this.active = true;
        this.enrollmentDate = LocalDate.now();
        this.enrolledCourses = new ArrayList<>();
    }
    public void enrollCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }
    public void unenrollCourse(Course course) {
        enrolledCourses.remove(course);
    }
    public void deactivate() {
        this.active = false;
    }
    public String getRegNo() { return regNo; }
    public boolean isActive() { return active; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public List<Course> getEnrolledCourses() { return enrolledCourses; }
    @Override
    public void printProfile() {
        System.out.println("Student Profile: " + this);
    }
    @Override
    public String toString() {
        return "Student[ID=" + getId() +
                ", RegNo=" + regNo +
                ", Name=" + getFullName() +
                ", Email=" + getEmail() +
                ", Active=" + active +
                ", Courses=" + enrolledCourses.size() +
                "]";
    }
}

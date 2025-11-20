package edu.ccrm.service;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;
import java.util.ArrayList;
import java.util.List;
public class EnrollmentService {
    private List<Enrollment> enrollments = new ArrayList<>();
    public void enrollStudent(Student student, Course course) {
        Enrollment enrollment = new Enrollment(student, course);
        enrollments.add(enrollment);
        student.enrollCourse(course);
        System.out.println("Student " + student.getFullName() + " enrolled in course " + course.getCode());
    }
    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments.clear();
        this.enrollments.addAll(enrollments);
    }
    public void assignGrade(Student student, Course course, double marks, Grade grade) {
        Enrollment enrollment = enrollments.stream()
                .filter(e -> e.getStudent().equals(student) && e.getCourse().equals(course))
                .findFirst()
                .orElse(null);
        if (enrollment != null) {
            enrollment.assignMarks(marks, grade);
            System.out.println("Grade assigned: " + grade + " for " + student.getFullName());
        } else {
            System.out.println("Enrollment not found.");
        }
    }
    public List<Enrollment> getEnrollmentsByStudent(String studentId) {
    return enrollments.stream()
            .filter(e -> e.getStudent().getId().equalsIgnoreCase(studentId)).toList();
}
    public List<Enrollment> listEnrollments() {
        return enrollments;
    }
}

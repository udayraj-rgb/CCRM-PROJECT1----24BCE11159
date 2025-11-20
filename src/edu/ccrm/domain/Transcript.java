package edu.ccrm.domain; 
import java.util.ArrayList;
import java.util.List;

public class Transcript {
    private Student student;               
    private List<Enrollment> enrollments;  
    public Transcript(Student student) {
        this.student = student;
        this.enrollments = new ArrayList<>();
    }
    public void addEnrollment(Enrollment e) {
        enrollments.add(e);
    }
    public double computeGPA() {
        double totalPoints = 0;
        int totalCredits = 0;
        for (Enrollment e : enrollments) {
            if (e != null && e.getGrade() != null) {
                totalPoints += e.getGrade().getPoints() * e.getCourse().getCredits();
                totalCredits += e.getCourse().getCredits();
            }
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }
    @Override
    public String toString() {
        return "Transcript for " + student.getFullName() +
               "\nEnrollments=" + enrollments +
               "\nGPA=" + computeGPA();
    }
}

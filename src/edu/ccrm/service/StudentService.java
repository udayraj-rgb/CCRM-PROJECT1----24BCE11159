package edu.ccrm.service;
import edu.ccrm.domain.Student;
import java.util.ArrayList;
import java.util.List;
public class StudentService {
    private List<Student> students = new ArrayList<>();
    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Student added: " + student);
    }
    public List<Student> listStudents() {
        return students;
    }
    public void setStudents(List<Student> students) {
        this.students.clear();
        this.students.addAll(students);
    }
    public Student findById(String id) {
        return students.stream()
                .filter(s -> s.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
    public void deactivateStudent(String id) {
        Student student = findById(id);
        if (student != null) {
            student.deactivate();
            System.out.println("Student deactivated: " + student.getFullName());
        } else {
            System.out.println("Student ID not found.");
        }
    }
}

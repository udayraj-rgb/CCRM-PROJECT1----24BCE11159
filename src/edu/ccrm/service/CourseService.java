package edu.ccrm.service;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseService {
    private List<Course> courses = new ArrayList<>();
    public void addCourse(Course course) {
        courses.add(course);
        System.out.println("Course added: " + course);
    }
    public List<Course> listCourses() {
        return courses;
    }
    public void setCourses(List<Course> courses) {
        this.courses.clear();
        this.courses.addAll(courses);
    }
    public List<Course> filterByDepartment(String dept) {
        return courses.stream()
                .filter(c -> c.getDepartment().equalsIgnoreCase(dept))
                .collect(Collectors.toList());
    }
    public List<Course> filterBySemester(Semester sem) {
        return courses.stream()
                .filter(c -> c.getSemester() == sem)
                .collect(Collectors.toList());
    }
}

package edu.ccrm.config;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;
public class CourseBuilder {
    private String code;
    private String title;
    private int credits;
    private Instructor instructor;
    private Semester semester;
    private String department;
    public CourseBuilder setCode(String code) {
        this.code = code;
        return this;
    }
    public CourseBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
    public CourseBuilder setCredits(int credits) {
        this.credits = credits;
        return this;
    }
    public CourseBuilder setInstructor(Instructor instructor) {
        this.instructor = instructor;
        return this;
    }
    public CourseBuilder setSemester(Semester semester) {
        this.semester = semester;
        return this;
    }
    public CourseBuilder setDepartment(String department) {
        this.department = department;
        return this;
    }
    public Course build() {
        return new Course(code, title, credits, instructor, semester, department);
    }
}

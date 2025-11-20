package edu.ccrm.domain; 
public class Course {
    private String code;       
    private String title;     
    private int credits;       
    private Instructor instructor; 
    private Semester semester;  
    private String department;  

    public static class CourseCode {
        private final String value;
        public CourseCode(String value) {
            this.value = value;
        }
        public String getValue() { return value; }
        @Override
        public String toString() { return value; }
    }
    public class CourseSchedule {
        private String day;
        private String time;

        public CourseSchedule(String day, String time) {
            this.day = day;
            this.time = time;
        }

        @Override
        public String toString() {
            return "Schedule: " + day + " at " + time;
        }
    }
    public Course(String code, String title, int credits, Instructor instructor, Semester semester, String department) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.instructor = instructor;
        this.semester = semester;
        this.department = department;
    }
    public Course(String code, String title, int credits, Instructor instructor) {
        this(code, title, credits, instructor, Semester.FALL, "General"); 
    }
    public Course() {
        this("UNDEFINED", "Untitled", 0, null, Semester.FALL, "General");
    }

    // ---------- Getters and Setters ----------
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getCredits() {
        return credits;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
    public Instructor getInstructor() {
        return instructor;
    }
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }
    public Semester getSemester() {
        return semester;
    }
    public void setSemester(Semester semester) {
        this.semester = semester;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    @Override
    public String toString() {
        return "Course[" +
                "Code=" + code +
                ", Title=" + title +
                ", Credits=" + credits +
                ", Instructor=" + (instructor != null ? instructor.getFullName() : "TBA") +
                ", Semester=" + semester +
                ", Dept=" + department +
                "]";
    }
}

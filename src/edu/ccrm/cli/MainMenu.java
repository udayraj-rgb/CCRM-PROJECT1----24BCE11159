package edu.ccrm.cli;
import edu.ccrm.domain.*;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final EnrollmentService enrollmentService = new EnrollmentService();
    public void start() {
        studentService.setStudents(ImportExportService.importStudents(Path.of("data/students.csv")));
        courseService.setCourses(ImportExportService.importCourses(Path.of("data/courses.csv")));
        enrollmentService.setEnrollments(
        ImportExportService.importEnrollments(
                Path.of("data/enrollments.csv"),
                studentService.listStudents(),
                courseService.listCourses()
            )
        );
        boolean running = true;
        loadAllData();
        while (running) {
            System.out.println("\n=== Campus Course & Records Manager ===");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Enrollments & Grades");
            System.out.println("4. Import/Export Data");
            System.out.println("5. Backup & Reports");
            System.out.println("6. Print Transcript");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> manageStudents();
                case 2 -> manageCourses();
                case 3 -> manageEnrollments();
                case 4 -> importExportMenu();
                case 5 -> backupAndReportsMenu();
                case 6 -> printTranscript();
                case 7 -> {
                    saveAllData();
                    System.out.println("Exiting...");
                    ImportExportService.exportStudents(Path.of("data/students.csv"), studentService.listStudents());
                    ImportExportService.exportCourses(Path.of("data/courses.csv"), courseService.listCourses());
                    ImportExportService.exportEnrollments(Path.of("data/enrollments.csv"), enrollmentService.listEnrollments());
                    ImportExportService.backup(Path.of("data"), Path.of("backup"));
                    System.out.println("Backup created in 'backup' folder.");
                    System.out.println("Exiting... Goodbye!");

                    running = false;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
    // ---------------- Students ----------------
    private void manageStudents() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.println("3. Deactivate Student");
        System.out.println("4. Back");
        System.out.print("Choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1 -> addStudent();
            case 2 -> listStudents();
            case 3 -> deactivateStudent();
            case 4 -> {}
            default -> System.out.println("Invalid option.");
        }
    }
    private void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        String id = "S" + (studentService.listStudents().size() + 1);
        Student student = new Student(id, "REG" + id, name, name.toLowerCase() + "@university.com");
        studentService.addStudent(student);
    }
    private void listStudents() {
        List<Student> students = studentService.listStudents();
        if (students.isEmpty()) {
            System.out.println("No students.");
        } else {
            students.forEach(s -> System.out.println(s.toString()));
        }
    }
    private void deactivateStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        studentService.deactivateStudent(id);
    }

    // ---------------- Courses ----------------
    private void manageCourses() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add Course");
        System.out.println("2. List Courses");
        System.out.println("3. Back");
        System.out.print("Choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1 -> addCourse();
            case 2 -> listCourses();
            case 3 -> {}
            default -> System.out.println("Invalid option.");
        }
    }
    private void addCourse() {
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter instructor name: ");
        String instructorName = scanner.nextLine();

        Instructor instructor = new Instructor(
                "I" + (courseService.listCourses().size() + 1),
                instructorName,
                instructorName.toLowerCase() + "@university.com",
                "CS"
        );
        Course course = new Course(code, title, 3, instructor, Semester.FALL, "CS");
        courseService.addCourse(course);
    }
    private void listCourses() {
        List<Course> courses = courseService.listCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses.");
        } else {
            courses.forEach(c -> System.out.println(c.toString()));
        }
    }
    // ---------------- Enrollments ----------------
    private void manageEnrollments() {
        System.out.println("\n--- Enrollments & Grades ---");
        System.out.println("1. Enroll Student");
        System.out.println("2. Assign Grade");
        System.out.println("3. List Enrollments");
        System.out.println("4. Back");
        System.out.print("Choose: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1 -> enrollStudent();
            case 2 -> assignGrade();
            case 3 -> listEnrollments();
            case 4 -> {}
            default -> System.out.println("Invalid option.");
        }
    }
    private void enrollStudent() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentService.findById(studentId);
        if (student == null) { System.out.println("Student not found."); return; }

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = courseService.listCourses().stream()
                .filter(c -> c.getCode().equalsIgnoreCase(courseCode))
                .findFirst().orElse(null);
        if (course == null) { System.out.println("Course not found."); return; }

        enrollmentService.enrollStudent(student, course);
    }
    private void assignGrade() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = studentService.findById(studentId);
        if (student == null) { System.out.println("Student not found."); return; }

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        Course course = courseService.listCourses().stream()
                .filter(c -> c.getCode().equalsIgnoreCase(courseCode))
                .findFirst().orElse(null);
        if (course == null) { System.out.println("Course not found."); return; }

        System.out.print("Enter marks: ");
        double marks = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter grade (A/B/C/D/F): ");
        String gradeStr = scanner.nextLine().toUpperCase();

        Grade grade;
        try { grade = Grade.valueOf(gradeStr); }
        catch (IllegalArgumentException e) { System.out.println("Invalid grade."); return; }

        enrollmentService.assignGrade(student, course, marks, grade);
    }
    private void listEnrollments() {
        List<Enrollment> enrollments = enrollmentService.listEnrollments();
        if (enrollments.isEmpty()) System.out.println("No enrollments.");
        else enrollments.forEach(e -> System.out.println(e.toString()));
    }
    private void loadAllData() {
        List<Student> students = ImportExportService.importStudents(Paths.get("data/students.csv"));
        studentService.setStudents(students);

        List<Course> courses = ImportExportService.importCourses(Paths.get("data/courses.csv"));
        courseService.setCourses(courses);

        List<Enrollment> enrollments = ImportExportService.importEnrollments(
            Paths.get("data/enrollments.csv"),
            students,
            courses
        );
        enrollmentService.setEnrollments(enrollments);
    }
    private void importExportMenu() {
    boolean inMenu = true;
    while (inMenu) {
        System.out.println("\n--- Import / Export Data ---");
        System.out.println("1. Import All Data");
        System.out.println("2. Export All Data");
        System.out.println("3. Back");
        System.out.print("Choose: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                studentService.setStudents(
                        ImportExportService.importStudents(Path.of("data/students.csv"))
                );
                courseService.setCourses(
                        ImportExportService.importCourses(Path.of("data/courses.csv"))
                );
                enrollmentService.setEnrollments(
                        ImportExportService.importEnrollments(
                                Path.of("data/enrollments.csv"),
                                studentService.listStudents(),
                                courseService.listCourses()
                        )
                );
                System.out.println("Import completed successfully!");
            }
            case 2 -> {
                ImportExportService.exportStudents(Path.of("data/students.csv"), studentService.listStudents());
                ImportExportService.exportCourses(Path.of("data/courses.csv"), courseService.listCourses());
                ImportExportService.exportEnrollments(Path.of("data/enrollments.csv"), enrollmentService.listEnrollments());
                System.out.println("Export completed successfully!");
            }
            case 3 -> inMenu = false;
            default -> System.out.println("Invalid choice.");
        }
    }
}
    private void backupAndReportsMenu() {
    boolean inMenu = true;
    while (inMenu) {
        System.out.println("\n--- Backup & Reports ---");
        System.out.println("1. Create Backup Now");
        System.out.println("2. Show Reports");
        System.out.println("3. Back");
        System.out.print("Choose: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                ImportExportService.backup(Path.of("data"), Path.of("backup"));
                System.out.println("Backup created in 'backup' folder successfully!");
            }
            case 2 -> showReports();
            case 3 -> inMenu = false;
            default -> System.out.println("Invalid choice.");
        }
    }
}

private void printTranscript() {
    System.out.print("Enter student ID: ");
    String studentId = scanner.nextLine();
    Student student = studentService.findById(studentId);
    if (student == null) {
        System.out.println("Student not found.");
        return;
    }

    List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);
    if (enrollments.isEmpty()) {
        System.out.println("No enrollments for this student.");
        return;
    }
     System.out.println("\n=== Transcript for " + student.getFullName() + " ===");
    double totalPoints = 0;
    int totalCredits = 0;
    for (Enrollment e : enrollments) {
        Course c = e.getCourse();
        Grade g = e.getGrade();
        System.out.printf("%s - %s | Credits: %d | Grade: %s\n",
                c.getCode(), c.getTitle(), c.getCredits(), g);
        if (g != null) {
            totalPoints += g.getPoints() * c.getCredits();
            totalCredits += c.getCredits();
        }
    }
    double gpa = totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    System.out.printf("GPA: %.2f\n", gpa);
}


private void showReports() {
    long activeStudents = studentService.listStudents().stream().filter(Student::isActive).count();
    long deactivatedStudents = studentService.listStudents().size() - activeStudents;
    System.out.println("\n--- Reports ---");
    System.out.println("Total Students: " + studentService.listStudents().size());
    System.out.println("Active Students: " + activeStudents);
    System.out.println("Deactivated Students: " + deactivatedStudents);
    System.out.println("Total Courses: " + courseService.listCourses().size());
    System.out.println("Total Enrollments: " + enrollmentService.listEnrollments().size());
}
    private void saveAllData() {
        ImportExportService.exportStudents(Paths.get("data/students.csv"), studentService.listStudents());
        ImportExportService.exportCourses(Paths.get("data/courses.csv"), courseService.listCourses());
        ImportExportService.exportEnrollments(Paths.get("data/enrollments.csv"), enrollmentService.listEnrollments());
    }

    
}

/*
cd C:\Users\HP\Desktop\CCRM
javac -d bin src/edu/ccrm/cli/MainMenu.java src/edu/ccrm/cli/CLIApp.java src/edu/ccrm/domain/*.java src/edu/ccrm/service/*.java src/edu/ccrm/io/*.java

java -cp bin edu.ccrm.cli.CLIApp

*/
package edu.ccrm.io;
import edu.ccrm.domain.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ImportExportService {
    private static final String DELIMITER = ",";
    // ----------------- STUDENTS -----------------
    public static List<Student> importStudents(Path file) {
        List<Student> students = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(file);
            if (!lines.isEmpty()) lines.remove(0); // Remove header
            for (String line : lines) {
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 5) {
                    String id = parts[0].trim();
                    String regNo = parts[1].trim();
                    String fullName = parts[2].trim();
                    String email = parts[3].trim();
                    String status = parts[4].trim();
                    Student student = new Student(id, regNo, fullName, email);
                    if ("INACTIVE".equalsIgnoreCase(status)) student.deactivate();
                    students.add(student);
                }
            }
        } catch (IOException e) {
            System.err.println("Error importing students: " + e.getMessage());
        }
        return students;
    }
    public static void exportStudents(Path file, List<Student> students) {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("id,regNo,fullName,email,status");
            writer.newLine();
            for (Student s : students) {
                writer.write(String.join(DELIMITER,
                        s.getId(),
                        s.getRegNo(),
                        s.getFullName(),
                        s.getEmail(),
                        s.isActive() ? "ACTIVE" : "INACTIVE"));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error exporting students: " + e.getMessage());
        }
    }
    // ----------------- COURSES -----------------
    public static List<Course> importCourses(Path file) {
        List<Course> courses = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(file);
            if (!lines.isEmpty()) lines.remove(0);
            for (String line : lines) {
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 6) {
                    String code = parts[0].trim();
                    String title = parts[1].trim();
                    int credits = Integer.parseInt(parts[2].trim());
                    String instructorName = parts[3].trim();
                    Semester semester = Semester.valueOf(parts[4].trim().toUpperCase());
                    String department = parts[5].trim();

                    Instructor instructor = new Instructor(
                            "I" + code,
                            instructorName,
                            instructorName.toLowerCase().replaceAll(" ", "") + "@example.com",
                            department
                    );
                    Course course = new Course(code, title, credits, instructor, semester, department);
                    courses.add(course);
                }
            }
        } catch (IOException e) {
            System.err.println("Error importing courses: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid course data: " + e.getMessage());
        }
        return courses;
    }
    public static void exportCourses(Path file, List<Course> courses) {
        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            writer.write("code,title,credits,instructor,semester,department");
            writer.newLine();
            for (Course c : courses) {
                writer.write(String.join(DELIMITER,
                        c.getCode(),
                        c.getTitle(),
                        String.valueOf(c.getCredits()),
                        c.getInstructor() != null ? c.getInstructor().getFullName() : "TBA",
                        c.getSemester().name(),
                        c.getDepartment()
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error exporting courses: " + e.getMessage());
        }
    }
    // ----------------- ENROLLMENTS -----------------
    public static List<Enrollment> importEnrollments(Path file, List<Student> students, List<Course> courses) {
    List<Enrollment> enrollments = new ArrayList<>();
    try {
        List<String> lines = Files.readAllLines(file);
        lines.remove(0); // remove header
        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            if (parts.length >= 4) {   // changed from 3 → 4
                String studentId = parts[0].trim();
                String courseCode = parts[1].trim();
                double marks = Double.parseDouble(parts[2].trim());
                String gradeStr = parts[3].trim();
                Optional<Student> studentOpt = students.stream().filter(s -> s.getId().equals(studentId)).findFirst();
                Optional<Course> courseOpt = courses.stream().filter(c -> c.getCode().equals(courseCode)).findFirst();
                if (studentOpt.isPresent() && courseOpt.isPresent()) {
                    Enrollment e = new Enrollment(studentOpt.get(), courseOpt.get());
                    e.setMarks(marks);
                    if (!gradeStr.isEmpty()) {
                        try {
                            e.setGrade(Grade.valueOf(gradeStr.toUpperCase()));
                        } catch (IllegalArgumentException ex) {
                            System.err.println("Invalid grade in CSV: " + gradeStr);
                        }
                    }
                    enrollments.add(e);
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Error importing enrollments: " + e.getMessage());
    }
    return enrollments;
}
    public static void exportEnrollments(Path file, List<Enrollment> enrollments) {
    try (BufferedWriter writer = Files.newBufferedWriter(file)) {
        writer.write("studentId,courseCode,marks,grade");
        writer.newLine();
        for (Enrollment e : enrollments) {
            writer.write(e.getStudent().getId() + DELIMITER +
                    e.getCourse().getCode() + DELIMITER +
                    e.getMarks() + DELIMITER +
                    (e.getGrade() != null ? e.getGrade().name() : ""));
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Error exporting enrollments: " + e.getMessage());
    }
}
    // ----------------- BACKUP -----------------
    public static void backup(Path sourceDir, Path backupDir) {
        try {
            if (!Files.exists(backupDir)) Files.createDirectories(backupDir);
            Files.walk(sourceDir)
                    .filter(Files::isRegularFile)
                    .forEach(src -> {
                        Path dest = backupDir.resolve(sourceDir.relativize(src));
                        try {
                            Files.createDirectories(dest.getParent());
                            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            System.err.println("Failed to backup file: " + src);
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error during backup: " + e.getMessage());
        }
    }
}

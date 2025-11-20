package edu.ccrm.util;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;

import java.util.Comparator;

public class Comparators {
    public static final Comparator<Student> BY_NAME =
            Comparator.comparing(Student::getFullName);

    public static final Comparator<Student> BY_ID =
            Comparator.comparing(Student::getId);

    public static final Comparator<Course> BY_CODE =
            Comparator.comparing(Course::toString);
}

package edu.ccrm.config;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.Transcript;
import java.util.ArrayList;
import java.util.List;

public class TranscriptBuilder {
    private Student student;
    private List<Enrollment> enrollments = new ArrayList<>();
    public TranscriptBuilder setStudent(Student student) {
        this.student = student;
        return this;
    }
    public TranscriptBuilder addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
        return this;
    }
    public Transcript build() {
        Transcript transcript = new Transcript(student);
        for (Enrollment e : enrollments) {
            transcript.addEnrollment(e);
        }
        return transcript;
    }
}

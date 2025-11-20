package edu.ccrm.service; 
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.Transcript;
import java.util.List;
public class TranscriptService {
    public Transcript generateTranscript(Student student, List<Enrollment> enrollments) {
        Transcript transcript = new Transcript(student); // create transcript object
        for (Enrollment e : enrollments) {
            transcript.addEnrollment(e); // add each enrollment to transcript
        }
        return transcript;
    }
    public void printTranscript(Transcript transcript) {
        System.out.println(transcript);
    }
}

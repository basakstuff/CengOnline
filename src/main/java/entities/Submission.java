package entities;

import java.sql.Blob;

public class Submission {

    private int  id;


    private String submission;

    private Assignment assignment;

    private User student;

    private String filename;

    public Submission() {
        submission=null;
        assignment=null;
        student=null;
        filename=null;
    }

    public Submission(int id, String submission, Assignment assignment, User student, String filename) {
        this.id = id;
        this.submission = submission;
        this.assignment = assignment;
        this.student = student;
        this.filename = filename;
    }

    public Submission(String submission, Assignment assignment, User student, String filename) {
        this.submission = submission;
        this.assignment = assignment;
        this.student = student;
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubmission() {
        return submission;
    }

    public void setSubmission(String submission) {
        this.submission = submission;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        String result="";
        result+="----------------------------------\n";
        result+="id:\t"+ id+"\n";
        result+="Assignment:\t"+ assignment.getTitle()+"\n";
        result+="Student:\t"+ student.getUsername()+"\n";
        String type;
        if(filename==null)
            type="Text";
        else
            type="File";
        result+="Type:\t"+ type+"\n";
        result+="Submission:\t"+ submission+"\n";
        result+="Filename:\t"+ filename+"\n";
        result+="----------------------------------\n";
        return result;
    }
}

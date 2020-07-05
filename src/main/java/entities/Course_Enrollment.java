package entities;


public class Course_Enrollment {

    private int id;

    private Course course;

    private User student;


    public Course_Enrollment() {
    }

    public Course_Enrollment(Course course, User student) {
        this.course = course;
        this.student = student;
    }

    public Course_Enrollment(int id, Course course, User student) {
        this.id = id;
        this.course = course;
        this.student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }
}

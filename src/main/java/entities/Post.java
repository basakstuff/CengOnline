package entities;



public class Post {


    private int id;


    private Course course;

    private User teacher;

    private String description;


    public Post() {
    }

    public Post(Course course, User teacher, String description) {
        this.course = course;
        this.teacher = teacher;
        this.description = description;
    }

    public Post(int id, Course course, User teacher, String description) {
        this.id = id;
        this.course = course;
        this.teacher = teacher;
        this.description = description;
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

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String result="";
        result+="POST------------------------------\n";
        result+="Post Id    : "+id+"\n";
        result+="Course     : "+course.getCode()+"\n";
        result+="User       : "+teacher.getUsername()+"\n";
        result+="Description: "+description+"\n";
        result+="----------------------------------\n";
        return result;
    }
}

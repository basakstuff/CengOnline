package entities;




public class Assignment {


    private int id;


    private Course course;


    private String title;


    private String content;

    public Assignment() {
    }

    public Assignment(Course course, String title, String content) {
        this.course = course;
        this.title = title;
        this.content = content;
    }

    public Assignment(int id, Course course, String title, String content) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.content = content;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        String result="";
        result+="----------------------------------\n";
        result+="Id:\t"+id+"\n";
        result+="Course:\t"+course.getCode()+"\n";
        result+="Title:\t"+title+"\n";
        result+="Assignment:\t"+content+"\n";
        result+="----------------------------------\n";
        return result;
    }
}

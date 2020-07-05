package entities;




public class Announcement {


    private int id;
    private User teacher;
    private String title;
    private String content;


    public Announcement() {
    }

    public Announcement(int id, User teacher, String title, String content) {
        this.id = id;
        this.teacher = teacher;
        this.title = title;
        this.content = content;
    }

    public Announcement(User teacher, String title, String content) {
        this.teacher = teacher;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
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
        result+="From:\t"+teacher.getUsername()+"\n";
        result+="Title:\t"+title+"\n";
        result+="Announcement:\t"+content+"\n";
        result+="----------------------------------\n";
        return result;
    }
}

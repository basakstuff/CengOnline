package entities;



public class Course  {


    private int id;


    private String name;

    private String code;

    private User teacher;


    public Course() {
    }

    public Course(String name, String code, User teacher) {
        this.name = name;
        this.code = code;
        this.teacher = teacher;
    }

    public Course(int id, String name, String code, User teacher) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        String result="";
        result+="----------------------------------\n";
        result+="Id:\t"+id+"\n";
        result+="Name:\t"+name+"\n";
        result+="Code:\t"+code+"\n";
        result+="Teacher:\t"+teacher.getUsername()+"\n";
        result+="----------------------------------\n";
        return result;
    }
}

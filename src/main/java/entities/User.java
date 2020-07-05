package entities;


public class User  {

    private int id;

    private String username;

    private String password;

    private int type;

    public User() {
    }

    public User(int id, String username, String password, int type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public User(String username, String password, int type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String result="";
        result+="----------------------------------\n";
        result+="Id:\t"+id+"\n";
        result+="Username:\t"+username+"\n";
        String user_type="";
        if(type==0)
            user_type="Teacher";
        else
            user_type="Student";
        result+="Type:\t"+user_type+"\n";

        result+="----------------------------------\n";
        return result;
    }
}

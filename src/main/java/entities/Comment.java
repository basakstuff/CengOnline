package entities;



public class Comment {


    private int id;


    private Post post;

    private User user;

    private String comment;


    public Comment() {
    }

    public Comment(int id, User user, Post post, String comment) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.comment = comment;
    }

    public Comment(User user, Post post, String comment) {
        this.post = post;
        this.user = user;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        String result="";
        result+="\t\tCOMMENT---------------------------\n";
        result+="\t\tComment Id :"+id+"\n";
        result+="\t\tPost       :"+post.getId()+"\n";
        result+="\t\tUser       :"+user.getUsername()+"\n";
        result+="\t\tComment    :"+comment+"\n";
        result+="\t\t----------------------------------\n";
        return result;
    }
}

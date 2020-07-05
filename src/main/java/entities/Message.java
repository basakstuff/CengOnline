package entities;


public class Message {


    private int id;


    private User sender;


    private User receiver;


    private String content;


    public Message() {
    }

    public Message(int id, User sender, User receiver, String content) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public Message(User sender, User receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
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
        result+="From:\t"+ sender.getUsername()+"\n";
        result+="To:\t"+ receiver.getUsername()+"\n";
        result+="Message:\t"+content+"\n";
        result+="----------------------------------\n";
        return result;

    }
}

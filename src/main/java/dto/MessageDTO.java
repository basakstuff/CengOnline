package dto;

import entities.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageDTO extends BaseDTO{

    UserDTO userDTO;
    public MessageDTO(Connection connection) {
        super(connection);
        userDTO = new UserDTO(connection);
    }

    public void saveMessage(Message message){

        String sql="INSERT INTO messages (sender,receiver,content) VALUES (?,?,?);";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,message.getSender().getId());
            pst.setInt(2,message.getReceiver().getId());
            pst.setString(3,message.getContent());
            pst.executeUpdate();

            System.out.println("A new message has been inserted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(MessageDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public void updateMessage(Message message){

//        String sql=String.format("UPDATE messages SET teacher=%d,name=%s,code=%s WHERE message_id=%d;",message.getTeacher().getId(),message.getName(),message.getCode(),message.getId());
        String sql="UPDATE messages SET sender=?,receiver=?,content=? WHERE message_id=?;";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,message.getSender().getId());;
            pst.setInt(2,message.getReceiver().getId());
            pst.setString(3,message.getContent());
            pst.setInt(4,message.getId());
            pst.executeUpdate();

            System.out.println("Message has been updated.");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(MessageDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public void deleteMessage(Message message){

        String sql=String.format("DELETE FROM messages WHERE message_id=%d;",message.getId());

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();

            System.out.println("Message has been deleted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(MessageDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }


    public Message getMessageById(int id){

//        String sql=String.format("SELECT * from messages WHERE  message_id=%d;",id);
        String sql="SELECT * from messages WHERE  message_id=?";
        Message message = null;
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                message = new Message(rs.getInt(1),userDTO.getUser(rs.getInt(2)),userDTO.getUser(rs.getInt(3)),rs.getString(4));
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(MessageDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return message;
    }


    public ArrayList<Message> getInbox(int id){

        String sql="SELECT * from messages WHERE receiver = ?;";
        ArrayList<Message> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt(1),userDTO.getUser(rs.getInt(2)),userDTO.getUser(rs.getInt(3)),rs.getString(4));

                result.add(message);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(MessageDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }

    public ArrayList<Message> getOutbox(int id){

        String sql="SELECT * from messages WHERE sender = ?;";
        ArrayList<Message> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt(1),userDTO.getUser(rs.getInt(2)),userDTO.getUser(rs.getInt(3)),rs.getString(4));

                result.add(message);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(MessageDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }
    public ArrayList<Message> getMessages(){

        String sql="SELECT * from messages;";
        ArrayList<Message> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Message message = new Message(rs.getInt(1),userDTO.getUser(rs.getInt(2)),userDTO.getUser(rs.getInt(3)),rs.getString(4));

                result.add(message);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(MessageDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }

}

package dto;

import entities.User;



import java.sql.*;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserDTO extends BaseDTO {


    public UserDTO() {
    }

    public UserDTO(Connection connection) {
        super(connection);
    }

    public void saveUser(User user){

        String sql="INSERT INTO users (username,password,type) VALUES (?,?,?);";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1,user.getUsername());
            pst.setString(2,user.getPassword());
            pst.setInt(3,user.getType());
            pst.executeUpdate();

            System.out.println("A new author has been inserted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(UserDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public User getUser(String username, String password){

        String sql="SELECT * from users WHERE username='"+username+"' and password='"+password+"';";

        User user = null;
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                user = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(UserDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return user;
    }


    public User getUser(String username){

        String sql="SELECT * from users WHERE username='"+username+"';";

        User user = null;
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                user = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(UserDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return user;
    }


    public User getUser(int id){

        String sql="SELECT * from users WHERE user_id="+id+";";

        User user = null;
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                user = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(UserDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return user;
    }

    public Hashtable<String,String> getUsers(){

        Hashtable<String,String> users = new Hashtable<>();
        String sql="SELECT * from users;";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                users.put(rs.getString(2),rs.getString(3));
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(UserDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return users;
    }

}

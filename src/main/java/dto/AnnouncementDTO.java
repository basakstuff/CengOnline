package dto;

import entities.Announcement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnnouncementDTO extends BaseDTO{

    UserDTO userDTO;
    public AnnouncementDTO(Connection connection) {
        super(connection);
        userDTO = new UserDTO(connection);
    }

    public void saveAnnouncement(Announcement announcement){

        String sql="INSERT INTO announcements (teacher,title,content) VALUES (?,?,?);";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,announcement.getTeacher().getId());
            pst.setString(2,announcement.getTitle());
            pst.setString(3,announcement.getContent());
            pst.executeUpdate();

            System.out.println("A new announcement has been inserted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AnnouncementDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public void updateAnnouncement(Announcement announcement){

//        String sql=String.format("UPDATE announcements SET teacher=%d,name=%s,code=%s WHERE announcement_id=%d;",announcement.getTeacher().getId(),announcement.getName(),announcement.getCode(),announcement.getId());
        String sql="UPDATE announcements SET teacher=?,title=?,content=? WHERE announcement_id=?;";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,announcement.getTeacher().getId());;
            pst.setString(2,announcement.getTitle());
            pst.setString(3,announcement.getContent());
            pst.setInt(4,announcement.getId());
            pst.executeUpdate();

            System.out.println("Announcement has been updated.");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AnnouncementDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public void deleteAnnouncement(Announcement announcement){

        String sql=String.format("DELETE FROM announcements WHERE announcement_id=%d;",announcement.getId());

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();

            System.out.println("Announcement has been deleted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AnnouncementDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }


    public Announcement getAnnouncementById(int id){

//        String sql=String.format("SELECT * from announcements WHERE  announcement_id=%d;",id);
        String sql="SELECT * from announcements WHERE  announcement_id=?";
        Announcement announcement = null;
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                 announcement = new Announcement(rs.getInt(1),userDTO.getUser(rs.getInt(2)),rs.getString(3),rs.getString(4));
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AnnouncementDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return announcement;
    }


    public ArrayList<Announcement> getAnnouncementsOfTeacher(int teacher_id){

        String sql="SELECT * from announcements WHERE teacher = ?;";
        ArrayList<Announcement> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,teacher_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Announcement announcement = new Announcement(rs.getInt(1),userDTO.getUser(rs.getInt(2)),rs.getString(3),rs.getString(4));
                result.add(announcement);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AnnouncementDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }


    public ArrayList<Announcement> getAnnouncements(){

        String sql="SELECT * from announcements;";
        ArrayList<Announcement> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Announcement announcement = new Announcement(rs.getInt(1),userDTO.getUser(rs.getInt(2)),rs.getString(3),rs.getString(4));
                result.add(announcement);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AnnouncementDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }

}

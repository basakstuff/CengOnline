package dto;

import entities.Assignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignmentDTO extends BaseDTO {


    CourseDTO courseDTO;
    public AssignmentDTO(Connection connection) {
        super(connection);
        courseDTO = new CourseDTO(connection);
    }

    public void saveAssignment(Assignment assignment){

        String sql="INSERT INTO assignments (course_id,title,content) VALUES (?,?,?);";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,assignment.getCourse().getId());
            pst.setString(2,assignment.getTitle());
            pst.setString(3,assignment.getContent());
            pst.executeUpdate();

            System.out.println("A new assignment has been inserted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AssignmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public void updateAssignment(Assignment assignment){

//        String sql=String.format("UPDATE assignments SET teacher=%d,name=%s,code=%s WHERE assignment_id=%d;",assignment.getTeacher().getId(),assignment.getName(),assignment.getCode(),assignment.getId());
        String sql="UPDATE assignments SET course_id=?,title=?,content=? WHERE assignment_id=?;";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,assignment.getCourse().getId());;
            pst.setString(2,assignment.getTitle());
            pst.setString(3,assignment.getContent());
            pst.setInt(4,assignment.getId());
            pst.executeUpdate();

            System.out.println("Assignment has been updated.");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AssignmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public void deleteAssignment(Assignment assignment){

        String sql=String.format("DELETE FROM assignments WHERE assignment_id=%d;",assignment.getId());

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();

            System.out.println("Assignment has been deleted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AssignmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }


    public Assignment getAssignmentById(int id){

//        String sql=String.format("SELECT * from assignments WHERE  assignment_id=%d;",id);
        String sql="SELECT * from assignments WHERE  assignment_id=?";
        Assignment assignment = null;
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                assignment = new Assignment(rs.getInt(1),courseDTO.getCourseById(rs.getInt(2)),rs.getString(3),rs.getString(4));
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AssignmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return assignment;
    }

    public ArrayList<Assignment> getAssignmentsOfCourse(int course_id){

        String sql="SELECT * from assignments WHERE course_id = ?;";
        ArrayList<Assignment> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,course_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Assignment assignment = new Assignment(rs.getInt(1),courseDTO.getCourseById(rs.getInt(2)),rs.getString(3),rs.getString(4));
                result.add(assignment);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AssignmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }
    public ArrayList<Assignment> getAssignmentsOfTeacher(int teacher_id){

        String sql="SELECT * from assignments WHERE teacher = ?;";
        ArrayList<Assignment> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,teacher_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Assignment assignment = new Assignment(rs.getInt(1),courseDTO.getCourseById(rs.getInt(2)),rs.getString(3),rs.getString(4));
                result.add(assignment);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AssignmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }


    public ArrayList<Assignment> getAssignments(){

        String sql="SELECT * from assignments;";
        ArrayList<Assignment> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Assignment assignment = new Assignment(rs.getInt(1),courseDTO.getCourseById(rs.getInt(2)),rs.getString(3),rs.getString(4));
                result.add(assignment);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(AssignmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }
}

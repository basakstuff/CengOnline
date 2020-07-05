package dto;

import entities.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseDTO extends BaseDTO{

    UserDTO userDTO;
    public CourseDTO(Connection connection) {
        super(connection);
        userDTO = new UserDTO(connection);
    }

    public void saveCourse(Course course){

        String sql="INSERT INTO courses (teacher,name,code) VALUES (?,?,?);";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,course.getTeacher().getId());
            pst.setString(2,course.getName());
            pst.setString(3,course.getCode());
            pst.executeUpdate();

            System.out.println("A new author has been inserted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CourseDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public void updateCourse(Course course){

//        String sql=String.format("UPDATE courses SET teacher=%d,name=%s,code=%s WHERE course_id=%d;",course.getTeacher().getId(),course.getName(),course.getCode(),course.getId());
        String sql="UPDATE courses SET teacher=?,name=?,code=? WHERE course_id=?;";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,course.getTeacher().getId());
            pst.setString(2,course.getName());
            pst.setString(3,course.getCode());
            pst.setInt(4,course.getId());
            pst.executeUpdate();

            System.out.println("A new author has been inserted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CourseDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public void deleteCourse(Course course){

        String sql=String.format("DELETE FROM courses WHERE course_id=%d;",course.getId());

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();

            System.out.println("A course has been deleted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CourseDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }


    public Course getCourseById(int id){

//        String sql=String.format("SELECT * from courses WHERE  course_id=%d;",id);
        String sql="SELECT * from courses WHERE  course_id=?";
        Course course = null;
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                 course = new Course(rs.getInt(1),rs.getString(3),rs.getString(4),userDTO.getUser(rs.getInt(2)));
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CourseDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return course;
    }

    public Course getCourseByCode(String code){

//        String sql=String.format("SELECT * from courses WHERE code='%s';",code);
        String sql="SELECT * from courses WHERE code = ?;";
        Course course = null;
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1,code);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                 course = new Course(rs.getInt(1),rs.getString(3),rs.getString(4),userDTO.getUser(rs.getInt(2)));
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CourseDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return course;
    }

    public ArrayList<Course> getCoursesOfTeacher(int teacher_id){

        String sql="SELECT * from courses WHERE teacher = ?;";
        ArrayList<Course> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,teacher_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Course course = new Course(rs.getInt(1),rs.getString(3),rs.getString(4),userDTO.getUser(rs.getInt(2)));
                result.add(course);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CourseDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }


    public ArrayList<Course> getCourses(){

        String sql="SELECT * from courses;";
        ArrayList<Course> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Course course = new Course(rs.getInt(1),rs.getString(3),rs.getString(4),userDTO.getUser(rs.getInt(2)));
                result.add(course);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CourseDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }


}

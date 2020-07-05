package dto;

import entities.Course;
import entities.Course_Enrollment;
import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Course_EnrollmentDTO extends BaseDTO{

    UserDTO userDTO;
    CourseDTO courseDTO;
    public Course_EnrollmentDTO(Connection connection) {
        super(connection);
        userDTO = new UserDTO(connection);
        courseDTO = new CourseDTO(connection);
    }

    public void saveCourse_Enrollment(Course_Enrollment course_enrollment){

        String sql="INSERT INTO course_enrollments (course_id,student_id) VALUES (?,?);";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,course_enrollment.getCourse().getId());
            pst.setInt(2,course_enrollment.getStudent().getId());
            pst.executeUpdate();

            System.out.println("A new course_enrollment has been inserted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Course_EnrollmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public void updateCourse_Enrollment(Course_Enrollment course_enrollment){

//        String sql=String.format("UPDATE course_enrollments SET teacher=%d,name=%s,code=%s WHERE course_enrollment_id=%d;",course_enrollment.getTeacher().getId(),course_enrollment.getName(),course_enrollment.getCode(),course_enrollment.getId());
        String sql="UPDATE course_enrollments SET course_id=?,student_id=? WHERE course_enrollment_id=?;";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,course_enrollment.getCourse().getId());
            pst.setInt(2,course_enrollment.getStudent().getId());
            pst.setInt(3,course_enrollment.getId());
            pst.executeUpdate();

            System.out.println("Course_Enrollment has been updated.");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Course_EnrollmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public void deleteCourse_Enrollment(Course_Enrollment course_enrollment){

        String sql=String.format("DELETE FROM course_enrollments WHERE course_enrollment_id=%d;",course_enrollment.getId());

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();

            System.out.println("Course_Enrollment has been deleted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Course_EnrollmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }


    public Course_Enrollment getCourse_EnrollmentById(int id){

//        String sql=String.format("SELECT * from course_enrollments WHERE  course_enrollment_id=%d;",id);
        String sql="SELECT * from course_enrollments WHERE  course_enrollment_id=?";
        Course_Enrollment course_enrollment = null;
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                course_enrollment = new Course_Enrollment(rs.getInt(1),courseDTO.getCourseById(rs.getInt(2)),userDTO.getUser(rs.getInt(3)));
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Course_EnrollmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return course_enrollment;
    }


    public ArrayList<Course> getCoursesOfStudent(int student_id){

        String sql="SELECT * from course_enrollments WHERE student_id = ?;";
        ArrayList<Course> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,student_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Course_Enrollment course_enrollment = new Course_Enrollment(rs.getInt(1),courseDTO.getCourseById(rs.getInt(2)),userDTO.getUser(rs.getInt(3)));
                result.add(course_enrollment.getCourse());
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Course_EnrollmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }

    public ArrayList<User> getStudentsOfCourse(int course_id){

        String sql="SELECT * from course_enrollments WHERE course_id = ?;";
        ArrayList<User> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,course_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Course_Enrollment course_enrollment = new Course_Enrollment(rs.getInt(1),courseDTO.getCourseById(rs.getInt(2)),userDTO.getUser(rs.getInt(3)));
                result.add(course_enrollment.getStudent());
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Course_EnrollmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }


    public ArrayList<Course_Enrollment> getCourse_Enrollments(){

        String sql="SELECT * from course_enrollments;";
        ArrayList<Course_Enrollment> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Course_Enrollment course_enrollment = new Course_Enrollment(rs.getInt(1),courseDTO.getCourseById(rs.getInt(2)),userDTO.getUser(rs.getInt(3)));
                result.add(course_enrollment);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(Course_EnrollmentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }

}

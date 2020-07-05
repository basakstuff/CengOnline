package dto;

import entities.Course;
import entities.Post;
import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostDTO extends BaseDTO{

    UserDTO userDTO;
    CourseDTO courseDTO;

    public PostDTO(Connection connection) {
        super(connection);
        userDTO = new UserDTO(connection);
        courseDTO = new CourseDTO(connection);
    }

    public void savePost(Post post){

        String sql="INSERT INTO posts (course,teacher,description) VALUES (?,?,?);";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,post.getCourse().getId());
            pst.setInt(2,post.getTeacher().getId());
            pst.setString(3,post.getDescription());
            pst.executeUpdate();

            System.out.println("A new post has been inserted!");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(PostDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    public void updatePost(Post post){

        String sql="UPDATE posts SET course=?,teacher=?,description=? WHERE post_id=?;";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,post.getCourse().getId());
            pst.setInt(2,post.getTeacher().getId());
            pst.setString(3,post.getDescription());
            pst.setInt(4,post.getId());
            pst.executeUpdate();

            System.out.println("A post has been updated!");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(PostDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public void deletePost(Post post){

        String sql=String.format("DELETE FROM posts WHERE post_id=%d;",post.getId());

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();

            System.out.println("A post has been deleted!");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(PostDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }


    public Post getPostById(int id){

        String sql="SELECT * from posts WHERE  post_id=?";
        Post post = null;
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int post_id = rs.getInt(1);
                int course_id = rs.getInt(2);
                int user_id = rs.getInt(3);
                String description = rs.getString(4);
                post = new Post(post_id,courseDTO.getCourseById(course_id),userDTO.getUser(user_id), description);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(PostDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return post;
    }

    public ArrayList<Post> getPostOfTeacher(int teacher_id){

        String sql="SELECT * from posts WHERE teacher = ?;";
        ArrayList<Post> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,teacher_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int post_id = rs.getInt(1);
                int course_id = rs.getInt(2);
                int user_id = rs.getInt(3);
                String description = rs.getString(4);
                Post post = new Post(post_id,courseDTO.getCourseById(course_id),userDTO.getUser(user_id), description);
                result.add(post);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(PostDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }

    public ArrayList<Post> getPostOfCourse(int course_id){

        String sql="SELECT * from posts WHERE course = ?;";
        ArrayList<Post> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,course_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int post_id = rs.getInt(1);
                int user_id = rs.getInt(3);
                String description = rs.getString(4);
                Post post = new Post(post_id,courseDTO.getCourseById(course_id),userDTO.getUser(user_id), description);
                result.add(post);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(PostDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }

    public ArrayList<Post> getPosts(){

        String sql="SELECT * from posts;";
        ArrayList<Post> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int post_id = rs.getInt(1);
                int course_id = rs.getInt(2);
                int user_id = rs.getInt(3);
                String description = rs.getString(4);
                Post post = new Post(post_id,courseDTO.getCourseById(course_id),userDTO.getUser(user_id), description);
                result.add(post);
            }


        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(PostDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return result;
    }


}

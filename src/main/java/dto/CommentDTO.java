package dto;

import entities.Comment;
import entities.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommentDTO extends BaseDTO{

    UserDTO userDTO;
    PostDTO postDTO;

    public CommentDTO(Connection connection) {
        super(connection);
        userDTO = new UserDTO(connection);
        postDTO = new PostDTO(connection);
    }

    public void saveComment(Comment comment){

        String sql="INSERT INTO comments (user,post,comment) VALUES (?,?,?);";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,comment.getUser().getId());
            pst.setInt(2,comment.getPost().getId());
            pst.setString(3,comment.getComment());
            pst.executeUpdate();

            System.out.println("A new comment has been inserted!");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CommentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    public void deleteComment(Comment comment){

        String sql=String.format("DELETE FROM comments WHERE comment_id=%d;",comment.getId());

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.executeUpdate();

            System.out.println("A comment has been deleted!");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CommentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }


    public Comment getCommentById(int id){

        String sql="SELECT * from comments WHERE comment_id=?";
        Comment comment = null;
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int comment_id = rs.getInt(1);
                int user_id = rs.getInt(2);
                int post_id = rs.getInt(3);
                String comment_text = rs.getString(4);
                comment = new Comment(comment_id,userDTO.getUser(user_id),postDTO.getPostById(user_id), comment_text);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CommentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return comment;
    }

    public ArrayList<Comment> getCommentsOfPost(int post_id){

        String sql="SELECT * from comments WHERE post = ?;";
        ArrayList<Comment> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,post_id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int comment_id = rs.getInt(1);
                int user_id = rs.getInt(2);
                String comment_text = rs.getString(4);
                Comment comment = new Comment(comment_id,userDTO.getUser(user_id),postDTO.getPostById(post_id), comment_text);
                result.add(comment);
            }
        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(CommentDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;
    }
}

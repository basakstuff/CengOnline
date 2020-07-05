package dto;

import entities.Assignment;
import entities.Submission;
import entities.User;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubmissionDTO extends BaseDTO {

    AssignmentDTO assignmentDTO;
    UserDTO userDTO;
    public SubmissionDTO(Connection connection) {
        super(connection);
        assignmentDTO = new AssignmentDTO(connection);
        userDTO = new UserDTO(connection);
    }


    public void saveSubmissionFile(Submission submission,String path){

        if(path.contains("/")){
            submission.setFilename(path.substring(path.lastIndexOf("/")+1));
        }
        else if(path.contains("\\")){
            submission.setFilename(path.substring(path.lastIndexOf("\\")+1));
        }
        else
            submission.setFilename(path);

        String sql="INSERT INTO submissions (assignment_id,student_id,file_blob,filename) VALUES (?,?,?,?);";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,submission.getAssignment().getId());
            pst.setInt(2,submission.getStudent().getId());
            pst.setBytes(3,readFile(path));
            pst.setString(4,submission.getFilename());
            pst.executeUpdate();

            System.out.println("A new submission has been inserted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(MessageDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public void saveSubmissionString(Submission submission){

        String sql="INSERT INTO submissions (assignment_id,student_id,submission) VALUES (?,?,?);";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,submission.getAssignment().getId());
            pst.setInt(2,submission.getStudent().getId());
            pst.setString(3,submission.getSubmission());
            pst.executeUpdate();

            System.out.println("A new submission has been inserted");

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(MessageDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }
    public Submission getSubmissionById(int id){
        String sql="SELECT * from submissions WHERE  submission_id=?";
        Submission submission =null;
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                submission = new Submission();
                submission.setId(rs.getInt(1));
                submission.setAssignment(assignmentDTO.getAssignmentById(rs.getInt(2)));
                submission.setStudent(userDTO.getUser(rs.getInt(3)));
                submission.setSubmission(rs.getString(4));
                submission.setFilename(rs.getString(6));

//                announcement = new Announcement(rs.getInt(1),userDTO.getUserById(rs.getInt(2)),rs.getString(3),rs.getString(4));
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(SubmissionDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return submission;
    }
    public ArrayList<Submission> getAssignmentSubmissions(Assignment assignment){
        String sql="SELECT * from submissions WHERE assignment_id = ?;";
        ArrayList<Submission> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,assignment.getId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Submission submission = new Submission();
                submission.setId(rs.getInt(1));
                submission.setAssignment(assignmentDTO.getAssignmentById(rs.getInt(2)));
                submission.setStudent(userDTO.getUser(rs.getInt(3)));
                submission.setSubmission(rs.getString(4));
                submission.setFilename(rs.getString(6));
                result.add(submission);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(SubmissionDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;

    }

    public ArrayList<Submission> getSubmissionsOfStudent(User user){
        String sql="SELECT * from submissions WHERE student_id = ?;";
        ArrayList<Submission> result = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1,user.getId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Submission submission = new Submission();
                submission.setId(rs.getInt(1));
                submission.setAssignment(assignmentDTO.getAssignmentById(rs.getInt(2)));
                submission.setStudent(userDTO.getUser(rs.getInt(3)));
                submission.setSubmission(rs.getString(4));
                submission.setFilename(rs.getString(6));
                result.add(submission);
            }


        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(SubmissionDTO.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
        return result;

    }

    public void downloadSubmission(Submission submission,String path){

        String selectSQL = "SELECT file_blob FROM submissions WHERE submission_id=?";
        ResultSet rs = null;
        FileOutputStream fos = null;

        PreparedStatement pstmt = null;

        try {

            pstmt = connection.prepareStatement(selectSQL);
            pstmt.setInt(1, submission.getId());
            rs = pstmt.executeQuery();

            // write binary stream into file
            File file = new File(path);
            fos = new FileOutputStream(file);

            System.out.println("Writing BLOB to file " + file.getAbsolutePath());
            while (rs.next()) {
                InputStream input = rs.getBinaryStream("file_blob");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    fos.write(buffer);
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
                if (fos != null) {
                    fos.close();
                }

            } catch (SQLException | IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private  byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

}

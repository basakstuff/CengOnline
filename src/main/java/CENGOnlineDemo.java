import dto.*;
import entities.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class CENGOnlineDemo {


    public static void main(String[] args) throws SQLException {



        Connection connection= DatabaseConnector.getDatabaseConnection();
        AnnouncementDTO announcementDTO = new AnnouncementDTO(connection);
        AssignmentDTO assignmentDTO = new AssignmentDTO(connection);
        Course_EnrollmentDTO course_enrollmentDTO = new Course_EnrollmentDTO(connection);
        CourseDTO courseDTO = new CourseDTO(connection);
        PostDTO postDTO = new PostDTO(connection);
        CommentDTO commentDTO = new CommentDTO(connection);
        MessageDTO messageDTO = new MessageDTO(connection);
        UserDTO userDTO = new UserDTO(connection);
        SubmissionDTO submissionDTO = new SubmissionDTO(connection);
        Scanner keyboard = new Scanner(System.in);

        Hashtable<String,String> users;


        boolean is_active = true;
        boolean is_found = false;
        User user = null;
        while (is_active){



            while (!is_found){
                int choice_entry;
                users = userDTO.getUsers();

                System.out.println("Welcome to CENGOnline System! You must enter username and password to login system:");
                System.out.println("1) Sign in with account");
                System.out.println("2) Create Account");
                System.out.println("3) Exit");
                choice_entry = keyboard.nextInt();
                keyboard.nextLine();

                if(choice_entry==1) {
                    System.out.print("Username:");
                    String username = keyboard.nextLine();
                    System.out.print("Password:");
                    String password = keyboard.nextLine();

                    if(users.containsKey(username)){
                        if(users.get(username).equals(password)){
                            user= userDTO.getUser(username);
                            System.out.println("User Information:");
                            System.out.println(user);
                            is_found = true;
                        }
                        else{
                            System.out.println("Wrong password");
                        }
                    }
                    else
                        System.out.println("Username has not found");
                }
                else if(choice_entry==2){
                    System.out.print("Enter your username:");
                    String username = keyboard.nextLine();
                    System.out.print("Enter your password:");
                    String password = keyboard.nextLine();
                    System.out.print("Enter your type(0 for teacher,1 for student):");
                    int type = keyboard.nextInt();
                    keyboard.nextLine();
                    User new_user = new User(username,password,type);
                    userDTO.saveUser(new_user);
                }
                else if(choice_entry==3){
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
            }

            int choice;

            while(true) {
                System.out.println("1) Courses");
                System.out.println("2) Assignments");
                System.out.println("3) Announcements");
                System.out.println("4) Messaging");
                System.out.println("5) Submission");
                System.out.println("6) Stream");
                System.out.println("7) Back");

                choice = keyboard.nextInt();
                if(choice>0 && choice<8)
                    break;
                else
                    System.out.println("Wrong choice");
            }

            if(user.getType()==0){ //teacher

                switch(choice){
                    case 1:
                        int choice_course;
                        while(true){
                            System.out.println("1) Add course");
                            System.out.println("2) Edit course");
                            System.out.println("3) Delete course");
                            System.out.println("4) View courses");
                            System.out.println("5) Add student to course");
                            System.out.println("6) Back");

                            choice_course = keyboard.nextInt();
                            keyboard.nextLine();
                            if(choice_course>0 && choice_course<7)
                                break;
                            else
                                System.out.println("Wrong choice");

                        }

                        if(choice_course==1){
                            Course course = new Course();
                            course.setTeacher(user);
                            System.out.println("Enter the name of course for addition");
                            course.setName(keyboard.nextLine());
                            System.out.println("Enter the code of course for addition");
                            course.setCode(keyboard.nextLine());
                            courseDTO.saveCourse(course);

                        }
                        else if(choice_course==2){
                            System.out.println("Enter the code of course for finding:");
                            String code = keyboard.nextLine();
                            Course course = courseDTO.getCourseByCode(code);

                            if(course==null){
                                System.out.println("Code has not been found");
                            }
                            else{
                                if(course.getTeacher().getUsername().equals(user.getUsername())) {
                                    System.out.println("Enter the name of course for edition");
                                    course.setName(keyboard.nextLine());
                                    System.out.println("Enter the code of course for edition");
                                    course.setCode(keyboard.nextLine());
                                    courseDTO.updateCourse(course);
                                }
                                else
                                    System.out.println("You cannot edit other teacher's course");
                            }
                        }
                        else if(choice_course==3){
                            System.out.println("Enter the code of course for deletion:");
                            String code = keyboard.nextLine();
                            Course course = courseDTO.getCourseByCode(code);

                            if(course==null){
                                System.out.println("Code has not been found");
                            }
                            else{
                                if(course.getTeacher().getUsername().equals(user.getUsername()))
                                    courseDTO.deleteCourse(course);
                                else
                                    System.out.println("You cannot delete other teacher's course");
                            }

                        }
                        else if(choice_course==4){
                            ArrayList<Course> courses = courseDTO.getCourses();
                            for(Course course: courses){
                                System.out.println(course);
                            }
                        }
                        else if(choice_course==5){
                            System.out.println("Enter the code name of course");
                            String code = keyboard.nextLine();
                            Course course = courseDTO.getCourseByCode(code);
                            if(course!=null) {
                                if(course.getTeacher().getId()==user.getId()) {
                                    System.out.println("Enter the username of student");
                                    String student_username = keyboard.nextLine();

                                    User student=userDTO.getUser(student_username);

                                    if(student!=null){
                                        if(student.getType()==1){
                                            Course_Enrollment course_enrollment = new Course_Enrollment(course,student);
                                            course_enrollmentDTO.saveCourse_Enrollment(course_enrollment);
                                        }
                                        else
                                            System.out.println("You can not add teacher as student to course");
                                    }
                                    else
                                        System.out.println("Username has not found");
                                }
                                else
                                    System.out.println("You cannot add student to other teacher's courses.");
                            }
                            else
                                System.out.println("Course has not been found");
                        }
                        break;
                    case 2:
                        int choice_assignment;
                        while(true){
                            System.out.println("1) Add assignment");
                            System.out.println("2) Edit assignment");
                            System.out.println("3) Delete assignment");
                            System.out.println("4) View assignments");
                            System.out.println("5) Back");
                            choice_assignment = keyboard.nextInt();
                            keyboard.nextLine();
                            if(choice_assignment>0 && choice_assignment<6)
                                break;
                            else
                                System.out.println("Wrong choice");

                        }
                        if(choice_assignment==1){
                            Assignment assignment = new Assignment();
                            System.out.println("Enter the code name of course");
                            String code = keyboard.nextLine();
                            Course course = courseDTO.getCourseByCode(code);
                            assignment.setCourse(course);
                            if(course!=null) {
                                if(course.getTeacher().getId()==user.getId()) {
                                    System.out.println("Enter the title of assignment");
                                    assignment.setTitle(keyboard.nextLine());
                                    System.out.println("Enter the content of assignment");
                                    assignment.setContent(keyboard.nextLine());
                                    assignmentDTO.saveAssignment(assignment);
                                }
                                else
                                    System.out.println("You cannot add assignment to other teacher's courses.");
                            }
                            else
                                System.out.println("Course has not been found");



                        }

                        else if(choice_assignment==2){
                            System.out.println("Enter the id of assignment for update:");
                            int id = keyboard.nextInt();
                            keyboard.nextLine();
                            Assignment assignment = assignmentDTO.getAssignmentById(id);

                            if(assignment==null){
                                System.out.println("Assignment has not been found");
                            }
                            else{
                                if(assignment.getCourse().getTeacher().getId()==user.getId()){
                                    System.out.println("Enter the code name of course");
                                    String code = keyboard.nextLine();
                                    Course course = courseDTO.getCourseByCode(code);
                                    assignment.setCourse(course);
                                    if(course!=null) {
                                        if(course.getTeacher().getId()==user.getId()) {
                                            System.out.println("Enter the title of assignment");
                                            assignment.setTitle(keyboard.nextLine());
                                            System.out.println("Enter the content of assignment");
                                            assignment.setContent(keyboard.nextLine());
                                            assignmentDTO.updateAssignment(assignment);
                                        }
                                        else
                                            System.out.println("You cannot update assignment to other teacher's courses.");
                                    }
                                    else
                                        System.out.println("Course has not been found");
                                }
                                else
                                    System.out.println("You cannot delete other teacher's assignment");
                            }
                        }
                        else if(choice_assignment==3){
                            System.out.println("Enter the id of assignment for deletion:");
                            int id = keyboard.nextInt();
                            keyboard.nextLine();
                            Assignment assignment = assignmentDTO.getAssignmentById(id);

                            if(assignment==null){
                                System.out.println("Assignment has not been found");
                            }
                            else{
                                if(assignment.getCourse().getTeacher().getId()==user.getId())
                                    assignmentDTO.deleteAssignment(assignment);
                                else
                                    System.out.println("You cannot delete other teacher's assignment");
                            }
                        }
                        else if(choice_assignment==4){
                            ArrayList<Assignment> assignments = assignmentDTO.getAssignments();
                            for(Assignment assignment: assignments){
                                System.out.println(assignment);
                            }

                        }
                        else if(choice_assignment==5){
                            System.out.println("Enter the id of assignment for viewing submissions:");
                            int id = keyboard.nextInt();
                            keyboard.nextLine();
                            Assignment assignment = assignmentDTO.getAssignmentById(id);

                            if(assignment==null){
                                System.out.println("Assignment has not been found");
                            }
                            else{
                                ArrayList<Submission> submissions = submissionDTO.getAssignmentSubmissions(assignment);
                                for(Submission submission:submissions)
                                    System.out.println(submission);
                            }

                        }


                        break;
                    case 3:
                        int choice_announcements;
                        while(true){
                            System.out.println("1) Add announcement");
                            System.out.println("2) Edit announcement");
                            System.out.println("3) Delete announcement");
                            System.out.println("4) View announcements");
                            System.out.println("5) Back");
                            choice_announcements = keyboard.nextInt();
                            keyboard.nextLine();
                            if(choice_announcements>0 && choice_announcements<6)
                                break;
                            else
                                System.out.println("Wrong choice");

                        }
                        if(choice_announcements==1){

                            Announcement announcement = new Announcement();
                            announcement.setTeacher(user);
                            System.out.println("Enter the title of announcement for addition");
                            announcement.setTitle(keyboard.nextLine());
                            System.out.println("Enter the content of announcement for addition");
                            announcement.setContent(keyboard.nextLine());
                            announcementDTO.saveAnnouncement(announcement);


                        }
                        else if(choice_announcements==2){
                            System.out.println("Enter the id of announcement for edition:");
                            int id= keyboard.nextInt();
                            keyboard.nextLine();
                            Announcement announcement = announcementDTO.getAnnouncementById(id);

                            if(announcement==null){
                                System.out.println("Announcement has not been found");
                            }
                            else {
                                if (announcement.getTeacher().getUsername().equals(user.getUsername())) {
                                    System.out.println("Enter the title of announcement for edition");
                                    announcement.setTitle(keyboard.nextLine());
                                    System.out.println("Enter the content of announcement for edition");
                                    announcement.setContent(keyboard.nextLine());
                                    announcementDTO.updateAnnouncement(announcement);
                                }
                                else
                                    System.out.println("You can not modify other teacher's announcements");
                            }
                        }
                        else if(choice_announcements==3){
                            System.out.println("Enter the id of announcement for edition:");
                            int id= keyboard.nextInt();
                            keyboard.nextLine();
                            Announcement announcement = announcementDTO.getAnnouncementById(id);
                            if (announcement==null)
                                System.out.println("Announcement has not been found");
                            else {
                                if (announcement.getTeacher().getUsername().equals(user.getUsername())) {
                                    announcementDTO.deleteAnnouncement(announcement);
                                } else
                                    System.out.println("You can not delete other teacher's announcements");
                            }

                        }
                        else if(choice_announcements==4){
                            ArrayList<Announcement> announcements = announcementDTO.getAnnouncements();
                            for(Announcement announcement: announcements){
                                System.out.println(announcement);
                            }
                            System.out.println();
                        }
                        break;

                    case 4:
                        int messaging_choice;

                        while(true){
                            System.out.println("1) Inbox");
                            System.out.println("2) Outbox");
                            System.out.println("3) Send Message");
                            System.out.println("4) Back");

                            messaging_choice = keyboard.nextInt();
                            keyboard.nextLine();
                            if(messaging_choice>0 && messaging_choice<5){
                                break;
                            }
                            else
                                System.out.println("Wrong choice");
                        }

                        if(messaging_choice==1){
                            ArrayList<Message> inbox=messageDTO.getInbox(user.getId());
                            for (Message message:inbox) {
                                System.out.println(message);
                            }
                        }
                        else if(messaging_choice==2){
                            ArrayList<Message> outbox=messageDTO.getOutbox(user.getId());
                            for (Message message:outbox) {
                                System.out.println(message);
                            }
                        }
                        else if(messaging_choice==3) {
                            User to=null;

                            System.out.println("Enter the username for sending massage");
                            String username = keyboard.nextLine();
                            to=userDTO.getUser(username);

                            if(to!=null){
                                System.out.println("Enter the message");
                                String message = keyboard.nextLine();
                                messageDTO.saveMessage(new Message(user,to,message));
                            }
                            else
                                System.out.println("Username has not found");


                        }
                        break;
                    case 5:
                        int submission_choice;
                        while(true) {
                            System.out.println("1)View Submissions Of Assignment");
                            System.out.println("2)Download Submission");
                            System.out.println("3)Back");
                            submission_choice = keyboard.nextInt();
                            keyboard.nextLine();
                            if(submission_choice>0 && submission_choice<4)
                                break;
                        }

                        if(submission_choice==1){

                            System.out.println("Enter the assignment id to upload");
                            int assignment_id = keyboard.nextInt();
                            keyboard.nextLine();
                            Assignment assignment = assignmentDTO.getAssignmentById(assignment_id);

                            if (assignment == null) {
                                System.out.println("Assignment has not been found");
                            }
                            else{
                                ArrayList<Submission> submissions = submissionDTO.getAssignmentSubmissions(assignment);
                                for(Submission submission:submissions) //null
                                    System.out.println(submission);
                            }

                        }
                        else if(submission_choice==2) {
                            System.out.println("Enter the submission id to download");
                            int submission_id = keyboard.nextInt();
                            keyboard.nextLine();
                            Submission submission = submissionDTO.getSubmissionById(submission_id);

                            if (submission == null) {
                                System.out.println("Submission has not been found");
                            }
                            else{
                                if(submission.getFilename()!=null){
                                    System.out.println(submission);
                                    System.out.println("Enter the filepath to download");
                                    String path = keyboard.nextLine();
                                    submissionDTO.downloadSubmission(submission,path);
                                }
                                else
                                    System.out.println("This submission is not file");
                            }
                        }
                        break;
                    //STREAM
                    case 6:

                        int stream_choice;

                        while(true){
                            System.out.println("1) Add Post");
                            System.out.println("2) Edit Post");
                            System.out.println("3) Delete Post");
                            System.out.println("4) View Posts");
                            System.out.println("5) Back");

                            stream_choice = keyboard.nextInt();
                            keyboard.nextLine();
                            if(stream_choice>0 && stream_choice<6){
                                break;
                            }
                            else
                                System.out.println("Wrong choice");
                        }

                        if(stream_choice==1){
                            System.out.println("");
                            System.out.println("Add Post");
                            System.out.println("");

                            Post post = new Post();
                            post.setTeacher(user);
                            System.out.println("Enter the code of course for post");
                            String courseCode = keyboard.nextLine();
                            post.setCourse(courseDTO.getCourseByCode(courseCode));
                            System.out.println("Enter the description of course for post (enter will accept!)");
                            post.setDescription(keyboard.nextLine());
                            postDTO.savePost(post);
                        }
                        else if(stream_choice==2){
                            System.out.println("");
                            System.out.println("Edit Post");
                            System.out.println("");

                            System.out.println("Enter the id of post for edition:");
                            int id= keyboard.nextInt();
                            Post post = postDTO.getPostById(id);

                            if(post==null){
                                System.out.println("Post has not been found");
                            }
                            else {
                                if (post.getTeacher().getUsername().equals(user.getUsername())) {
                                    System.out.println("Enter the code of course to post edition");
                                    keyboard.nextLine();
                                    String courseCode = keyboard.nextLine();
                                    post.setCourse(courseDTO.getCourseByCode(courseCode));
                                    System.out.println("Enter the description of post for edition");
                                    post.setDescription(keyboard.nextLine());
                                    postDTO.updatePost(post);
                                }
                                else
                                    System.out.println("You can not modify other teachers's post");
                            }
                        }
                        else if(stream_choice==3) {
                            System.out.println("");
                            System.out.println("Delete Post");
                            System.out.println("");

                            System.out.println("Enter the id of post for delete operation:");
                            int id= keyboard.nextInt();
                            Post post = postDTO.getPostById(id);

                            if (post==null)
                                System.out.println("Post has not been found");
                            else {
                                if (post.getTeacher().getUsername().equals(user.getUsername())) {
                                    postDTO.deletePost(post);
                                } else
                                    System.out.println("You can not delete other teacher's posts");
                            }
                        }
                        else if(stream_choice==4) {
                            System.out.println("");
                            System.out.println("View Post Streams");
                            System.out.println("Courses:");

                            ArrayList<Course> courses = courseDTO.getCourses();
                            for (Course course : courses) {
                                System.out.println(course);
                            }

                            System.out.println("Enter the code name of course");
                            String code = keyboard.nextLine();
                            Course course = courseDTO.getCourseByCode(code);

                            if (course!=null) {
                                ArrayList<Post> posts = postDTO.getPostOfCourse(course.getId());
                                for (Post post : posts) {
                                    System.out.println(post);

                                    ArrayList<Comment> comments = commentDTO.getCommentsOfPost(post.getId());
                                    for (Comment comment : comments) {
                                        System.out.println(comment);
                                    }
                                }
                                System.out.println();

                                if(!posts.isEmpty()) {

                                    int post_choice;

                                    while (true) {
                                        System.out.println("1) Add Comment to Post");
                                        System.out.println("2) Delete Comment from Post");
                                        System.out.println("3) Back");

                                        post_choice = keyboard.nextInt();
                                        keyboard.nextLine();
                                        if (post_choice > 0 && post_choice < 4) {
                                            break;
                                        } else
                                            System.out.println("Wrong choice");
                                    }

                                    if (post_choice == 1) {
                                        System.out.println("");
                                        System.out.println("Add Comment");
                                        System.out.println("");

                                        Comment comment = new Comment();
                                        comment.setUser(user);
                                        System.out.println("Enter the id of post for comment");
                                        int postId = keyboard.nextInt();
                                        comment.setPost(postDTO.getPostById(postId));
                                        System.out.println("Enter your comment to post (enter will accept!)");
                                        keyboard.nextLine();
                                        comment.setComment(keyboard.nextLine());
                                        commentDTO.saveComment(comment);
                                    } else if (post_choice == 2) {
                                        System.out.println("");
                                        System.out.println("Delete Comment");
                                        System.out.println("");

                                        System.out.println("Enter the id of comment for delete operation:");
                                        int id = keyboard.nextInt();
                                        Comment comment = commentDTO.getCommentById(id);

                                        if (comment == null)
                                            System.out.println("Post has not been found");
                                        else {
                                            commentDTO.deleteComment(comment); //the is no limit for teachers to delete a comment
                                        }
                                    }
                                }else{
                                    System.out.println("We couldn't found any post");
                                }
                            }else{
                                System.out.println("We couldn't found any course like that");
                            }
                        }
                        break;
                        //STREAM
                    default:
                        break;
                }
            }
            else if(user.getType()==1){//student
                ArrayList<Course> student_courses = new ArrayList<>();
                switch(choice){
                    case 1:
                        ArrayList<Course> courses = course_enrollmentDTO.getCoursesOfStudent(user.getId());
                        for(Course course: courses){
                            System.out.println(course);
                        }
                        break;
                    case 2:
                        student_courses.clear();
                        student_courses = course_enrollmentDTO.getCoursesOfStudent(user.getId());
                        for(Course course: student_courses){
                            ArrayList<Assignment> course_assignments = assignmentDTO.getAssignmentsOfCourse(course.getId());
                            for (Assignment assignment : course_assignments) {
                                System.out.println(assignment);
                            }
                        }
                        break;
                    case 3:
                        ArrayList<Announcement> announcements = announcementDTO.getAnnouncements();
                        for(Announcement announcement: announcements){
                            System.out.println(announcement);
                        }
                        break;
                    case 4:

                        int messaging_choice;

                        while(true){
                            System.out.println("1) Inbox");
                            System.out.println("2) Outbox");
                            System.out.println("3) Send Message");
                            System.out.println("4) Back");

                            messaging_choice = keyboard.nextInt();
                            keyboard.nextLine();
                            if(messaging_choice>0 && messaging_choice<5){
                                break;
                            }
                            else
                                System.out.println("Wrong choice");
                        }

                        if(messaging_choice==1){
                            ArrayList<Message> inbox=messageDTO.getInbox(user.getId());
                            for (Message message:inbox) {
                                System.out.println(message);
                            }
                        }
                        else if(messaging_choice==2){
                            ArrayList<Message> outbox=messageDTO.getOutbox(user.getId());
                            for (Message message:outbox) {
                                System.out.println(message);
                            }
                        }
                        else if(messaging_choice==3) {
                            User to=null;

                            System.out.println("Enter the username for sending massage");
                            String username = keyboard.nextLine();
                            to=userDTO.getUser(username);

                            if(to!=null){
                                System.out.println("Enter the message");
                                String message = keyboard.nextLine();
                                messageDTO.saveMessage(new Message(user,to,message));
                            }
                            else
                                System.out.println("Username has not found");
                        }
                        break;
                    case 5:
                        int submission_choice;
                        while(true){
                            System.out.println("1) View submissions");
                            System.out.println("2) Upload submission");
                            System.out.println("3) Back");

                            submission_choice = keyboard.nextInt();
                            keyboard.nextLine();
                            if(submission_choice>0 && submission_choice<4){
                                break;
                            }
                            else
                                System.out.println("Wrong choice");
                        }

                        if(submission_choice==1){
                            ArrayList<Submission> submissions = submissionDTO.getSubmissionsOfStudent(user);
                            for(Submission submission:submissions) //null
                                System.out.println(submission);
                        }
                        else if(submission_choice==2){
                            System.out.println("Enter the assignment id to upload");
                            int assignment_id = keyboard.nextInt();
                            keyboard.nextLine();
                            Assignment assignment = assignmentDTO.getAssignmentById(assignment_id);

                            if(assignment==null){
                                System.out.println("Assignment has not been found");
                            }
                            else{
                                ArrayList<Course> courses_of_student = course_enrollmentDTO.getCoursesOfStudent(user.getId());
                                boolean is_student_take_course=false;
                                for(Course course:courses_of_student){
                                    if(assignment.getCourse().getId()==course.getId()){
                                        is_student_take_course=true;
                                        break;
                                    }
                                }
                                if(is_student_take_course){
                                    Submission submission = new Submission();
                                    submission.setAssignment(assignment);
                                    submission.setStudent(user);

                                    System.out.println("Which type of submission will you upload?(File->1 or String->2");
                                    int  upload_type=keyboard.nextInt();
                                    keyboard.nextLine();
                                    if(upload_type==1){
                                        System.out.println("Enter your file path");
                                        String path = keyboard.nextLine();
                                        submissionDTO.saveSubmissionFile(submission,path);
                                    }
                                    else if(upload_type==2){
                                        System.out.println("Enter your submission");
                                        String content = keyboard.nextLine();
                                        submission.setSubmission(content);
                                        submissionDTO.saveSubmissionString(submission);
                                    }
                                    else
                                        System.out.println("You have entered wrong choice");
                                }
                                else
                                    System.out.println("You cannot upload submission for this assignment.Because you are not enrolled course of assignment.");
                            }

                        }
                        break;
                    //STREAM
                    case 6:
                        System.out.println("");
                        System.out.println("View My Post Stream");
                        System.out.println("Courses:");

                        student_courses.clear();
                        student_courses = course_enrollmentDTO.getCoursesOfStudent(user.getId());
                        for(Course course: student_courses){
                            ArrayList<Assignment> course_assignments = assignmentDTO.getAssignmentsOfCourse(course.getId());
                            for (Assignment assignment : course_assignments) {
                                System.out.println(assignment);
                            }
                        }

                        System.out.println("Enter the code name of course");
                        keyboard.nextLine();
                        String code = keyboard.nextLine();
                        Course course = courseDTO.getCourseByCode(code);

                        if(course!=null) {
                            ArrayList<Post> posts = postDTO.getPostOfCourse(course.getId());
                            for (Post post : posts) {
                                System.out.println(post);

                                ArrayList<Comment> comments = commentDTO.getCommentsOfPost(post.getId());
                                for (Comment comment : comments) {
                                    System.out.println(comment);
                                }
                            }
                            System.out.println();

                            if(!posts.isEmpty()) {
                                int post_choice;

                                while (true) {
                                    System.out.println("1) Add Comment to Post");
                                    System.out.println("2) Back");

                                    post_choice = keyboard.nextInt();
                                    keyboard.nextLine();
                                    if (post_choice > 0 && post_choice < 3) {
                                        break;
                                    } else
                                        System.out.println("Wrong choice");
                                }

                                if (post_choice == 1) {
                                    System.out.println("");
                                    System.out.println("Add Comment");
                                    System.out.println("");

                                    Comment comment = new Comment();
                                    comment.setUser(user);
                                    System.out.println("Enter the id of post for comment");
                                    int postId = keyboard.nextInt();
                                    comment.setPost(postDTO.getPostById(postId));
                                    System.out.println("Enter your comment to post (enter will accept!)");
                                    keyboard.nextLine();
                                    comment.setComment(keyboard.nextLine());
                                    commentDTO.saveComment(comment);
                                }
                            }
                            else{
                                System.out.println("We couldn't found any post");
                            }
                        }else{
                            System.out.println("We couldn't found any course like that");
                        }
                        break;
                    //STREAM
                    default:
                        break;
                }
            }
            if(choice==7)
                is_found=false;
            System.out.println();
        }
    }
}

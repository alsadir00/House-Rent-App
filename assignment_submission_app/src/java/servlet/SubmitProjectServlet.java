package servlet;

import util.DBConnection;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/SubmitProjectServlet")
@MultipartConfig
public class SubmitProjectServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        // 1. Get logged-in user
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.getWriter().println("User not logged in");
            return;
        }

        int studentId = user.getUserId();

        // 2. Get assignment ID
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));

        // 3. Get uploaded file
        Part filePart = request.getPart("file");

        if (filePart == null || filePart.getSize() == 0) {
            response.getWriter().println("No file selected");
            return;
        }

        String fileName = filePart.getSubmittedFileName();

        // 4. Create upload folder
        String uploadPath = "C:/projectsubmission_uploads";
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 5. Save file
        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        // 6. Save submission to database
        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "INSERT INTO submission (student_id, assignment_id, file_path, submission_date) " +
                "VALUES (?, ?, ?, NOW())";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);
            ps.setInt(2, assignmentId);
            ps.setString(3, filePath);

            ps.executeUpdate();

            response.getWriter().println("Project submitted successfully");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database error");
        }
    }
}

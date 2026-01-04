package servlet;

import util.DBConnection;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/SubmitProjectPageServlet")
public class SubmitProjectPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            out.println("Please login first.");
            return;
        }

        int studentId = user.getUserId();

        out.println("<link rel='stylesheet' href='css/main.css'>");

        out.println("<div class='navbar'>");
        out.println("<h1>Student Dashboard</h1>");
        out.println("<div><a href='studentDashboard.html'>Back</a></div>");
        out.println("</div>");

        out.println("<div class='container'>");
        out.println("<div class='card'>");
        out.println("<h2>Submit Project</h2>");

        out.println("<form action='SubmitProjectServlet' method='post' enctype='multipart/form-data'>");

        out.println("<label>Select Assignment</label>");
        out.println("<select name='assignmentId' required>");
        out.println("<option value=''>-- Select Assignment --</option>");

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT a.assignment_id, a.title " +
                "FROM assignment a " +
                "LEFT JOIN submission s " +
                "ON a.assignment_id = s.assignment_id AND s.student_id = ? " +
                "WHERE s.submission_id IS NULL";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            boolean hasAssignments = false;

            while (rs.next()) {
                hasAssignments = true;
                out.println("<option value='" + rs.getInt("assignment_id") + "'>");
                out.println(rs.getString("title"));
                out.println("</option>");
            }

            if (!hasAssignments) {
                out.println("<option disabled>No assignments available</option>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("</select>");
            out.println("<p>Error loading assignments</p>");
        }

        out.println("</select><br><br>");

        out.println("<label>Select File</label>");
        out.println("<input type='file' name='file' required><br><br>");

        out.println("<input type='submit' value='Submit Project'>");

        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
    }
}

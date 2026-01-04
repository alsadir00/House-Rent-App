package servlet;

import util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/ViewGivenFeedbackServlet")
public class ViewGivenFeedbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<link rel='stylesheet' href='css/main.css'>");

        out.println("<div class='navbar'>");
        out.println("<h1>Instructor Dashboard</h1>");
        out.println("<div><a href='instructorDashboard.html'>Back</a></div>");
        out.println("</div>");

        out.println("<div class='container'>");
        out.println("<div class='card'>");
        out.println("<h2>Given Feedback</h2>");

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT f.feedback_id, f.feedback_text, f.grade, " +
                "s.submission_id, s.student_id, s.assignment_id " +
                "FROM feedback f " +
                "JOIN submission s ON f.submission_id = s.submission_id";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Submission ID</th>");
            out.println("<th>Student ID</th>");
            out.println("<th>Assignment ID</th>");
            out.println("<th>Feedback</th>");
            out.println("<th>Grade</th>");
            out.println("</tr>");

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("submission_id") + "</td>");
                out.println("<td>" + rs.getInt("student_id") + "</td>");
                out.println("<td>" + rs.getInt("assignment_id") + "</td>");
                out.println("<td>" + rs.getString("feedback_text") + "</td>");
                out.println("<td>" + rs.getString("grade") + "</td>");
                out.println("</tr>");
            }

            if (!hasData) {
                out.println("<tr><td colspan='5'>No feedback given yet.</td></tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error loading feedback list.");
        }

        out.println("</div>");
        out.println("</div>");
    }
}

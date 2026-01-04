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

@WebServlet("/GiveFeedbackPageServlet")
public class GiveFeedbackPageServlet extends HttpServlet {

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
        out.println("<h2>Give Feedback</h2>");

        out.println("<form action='GiveFeedbackServlet' method='post'>");

        out.println("<label>Select Submission</label>");
        out.println("<select name='submissionId' required>");
        out.println("<option value=''>-- Select Submission --</option>");

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT s.submission_id, s.student_id, s.assignment_id " +
                "FROM submission s " +
                "LEFT JOIN feedback f ON s.submission_id = f.submission_id " +
                "WHERE f.feedback_id IS NULL";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            boolean hasPending = false;

            while (rs.next()) {
                hasPending = true;
                out.println("<option value='" + rs.getInt("submission_id") + "'>");
                out.println("Submission " + rs.getInt("submission_id") +
                            " (Student " + rs.getInt("student_id") +
                            ", Assignment " + rs.getInt("assignment_id") + ")");
                out.println("</option>");
            }

            if (!hasPending) {
                out.println("<option disabled>No pending feedback</option>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("</select><br><br>");

        out.println("<label>Feedback</label>");
        out.println("<textarea name='feedbackText' rows='5' required></textarea><br><br>");

        out.println("<label>Grade</label>");
        out.println("<input type='text' name='grade' required><br><br>");

        out.println("<input type='submit' value='Submit Feedback'>");

        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
    }
}

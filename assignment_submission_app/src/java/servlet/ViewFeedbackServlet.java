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

@WebServlet("/ViewFeedbackServlet")
public class ViewFeedbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<link rel='stylesheet' href='css/main.css'>");
        out.println("<div class='container'>");


        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            out.println("Please login first");
            return;
        }

        int studentId = user.getUserId();

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT s.assignment_id, f.feedback_text, f.grade " +
                "FROM submission s " +
                "JOIN feedback f ON s.submission_id = f.submission_id " +
                "WHERE s.student_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            out.println("<h2>Your Feedback</h2>");

            boolean found = false;

            while (rs.next()) {
                found = true;
                out.println("<p>");
                out.println("<b>Assignment ID:</b> " + rs.getInt("assignment_id") + "<br>");
                out.println("Feedback: " + rs.getString("feedback_text") + "<br>");
                out.println("Grade: " + rs.getString("grade"));
                out.println("</p><hr>");
            }

            if (!found) {
                out.println("No feedback available yet.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error loading feedback");
        }
        out.println("</div>");

    }
}

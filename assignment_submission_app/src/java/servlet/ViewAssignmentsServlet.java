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

@WebServlet("/ViewAssignmentsServlet")
public class ViewAssignmentsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<link rel='stylesheet' href='css/main.css'>");
        out.println("<div class='container'>");
        out.println("<div class='card'>");
        out.println("<h2>Assignment List</h2>");

        // Get logged-in student
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            out.println("Please login first.");
            out.println("</div></div>");
            return;
        }

        int studentId = user.getUserId();

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "SELECT a.assignment_id, a.title, a.description, a.due_date, " +
                "CASE WHEN s.submission_id IS NULL THEN 'Not Submitted' ELSE 'Submitted' END AS status " +
                "FROM assignment a " +
                "LEFT JOIN submission s ON a.assignment_id = s.assignment_id AND s.student_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Title</th>");
            out.println("<th>Description</th>");
            out.println("<th>Due Date</th>");
            out.println("<th>Status</th>");
            out.println("</tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("assignment_id") + "</td>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getString("description") + "</td>");
                out.println("<td>" + rs.getDate("due_date") + "</td>");

                String status = rs.getString("status");
                if ("Submitted".equals(status)) {
                    out.println("<td style='color:green; font-weight:bold;'>Submitted</td>");
                } else {
                    out.println("<td style='color:red;'>Not Submitted</td>");
                }

                out.println("</tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error loading assignments.");
        }

        out.println("</div>");
        out.println("</div>");
    }
}

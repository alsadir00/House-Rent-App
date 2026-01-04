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

@WebServlet("/ViewInstructorAssignmentsServlet")
public class ViewInstructorAssignmentsServlet extends HttpServlet {

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
        out.println("<h2>My Assignments</h2>");

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT assignment_id, title, description, due_date FROM assignment";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Title</th>");
            out.println("<th>Description</th>");
            out.println("<th>Due Date</th>");
            out.println("<th>Actions</th>");
            out.println("</tr>");

            boolean found = false;

            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("assignment_id") + "</td>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getString("description") + "</td>");
                out.println("<td>" + rs.getDate("due_date") + "</td>");
                out.println("<td>");
                out.println("<a class='btn' href='EditAssignmentServlet?id=" +
                        rs.getInt("assignment_id") + "'>Edit</a> ");
                out.println("<a class='btn' href='DeleteAssignmentServlet?id=" +
                        rs.getInt("assignment_id") + "' " +
                        "onclick=\"return confirm('Are you sure?')\">Delete</a>");
                out.println("</td>");
                out.println("</tr>");
            }

            if (!found) {
                out.println("<tr><td colspan='5'>No assignments created yet.</td></tr>");
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

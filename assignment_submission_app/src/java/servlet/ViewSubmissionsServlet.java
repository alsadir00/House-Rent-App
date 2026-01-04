package servlet;

import util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/ViewSubmissionsServlet")
public class ViewSubmissionsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<link rel='stylesheet' href='css/main.css'>");
        out.println("<div class='container'>");


        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT submission_id, student_id, assignment_id, file_path FROM submission"
            );

            out.println("<h2>Submissions</h2>");
            
            while (rs.next()) {

    int submissionId = rs.getInt("submission_id");
    int studentId = rs.getInt("student_id");
    int assignmentId = rs.getInt("assignment_id");
    String filePath = rs.getString("file_path");

    String fileName = new java.io.File(filePath).getName();

    out.println("<p>");
    out.println("Submission ID: " + submissionId + "<br>");
    out.println("Student ID: " + studentId + "<br>");
    out.println("Assignment ID: " + assignmentId + "<br>");
    out.println("File: <a href='DownloadServlet?file=" + fileName + "'>"
            + fileName + "</a><br>");
    out.println("</p><hr>");
    
}


        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error loading submissions");
        }
        out.println("</div>");
    }
    
}

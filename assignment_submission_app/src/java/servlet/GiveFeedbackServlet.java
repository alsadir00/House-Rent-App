package servlet;

import dao.FeedbackDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/GiveFeedbackServlet")
public class GiveFeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        // 1. Read form data
        int submissionId = Integer.parseInt(request.getParameter("submissionId"));
        String feedbackText = request.getParameter("feedbackText");
        String grade = request.getParameter("grade");

        // 2. Save feedback
        FeedbackDAO dao = new FeedbackDAO();
        dao.addFeedback(submissionId, feedbackText, grade);

        // 3. Confirmation
        response.getWriter().println("Feedback submitted successfully");
    }
}

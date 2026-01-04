package dao;

import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class FeedbackDAO {

    public void addFeedback(int submissionId, String feedbackText, String grade) {

        try {
            Connection con = DBConnection.getConnection();

            String sql =
                "INSERT INTO feedback (submission_id, feedback_text, grade) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, submissionId);
            ps.setString(2, feedbackText);
            ps.setString(3, grade);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

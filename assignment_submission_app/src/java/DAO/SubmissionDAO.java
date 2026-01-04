package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import util.DBConnection;

public class SubmissionDAO {

    public void saveSubmission(int assignmentId, int studentId, String filePath) {

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO submission (assignment_id, student_id, file_path) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, assignmentId);
            ps.setInt(2, studentId);
            ps.setString(3, filePath);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

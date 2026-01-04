package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Assignment;
import util.DBConnection;

public class AssignmentDAO {

    // ==============================
    // CREATE ASSIGNMENT (Instructor)
    // ==============================
    public void createAssignment(String title, String description, String dueDate) {

        try {
            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO assignment (title, description, due_date) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, dueDate);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==============================
    // GET ALL ASSIGNMENTS (Student)
    // ==============================
    public List<Assignment> getAllAssignments() {

        List<Assignment> assignments = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM assignment";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Assignment a = new Assignment(
                        rs.getInt("assignment_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("due_date")
                );
                assignments.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return assignments;
    }
}
package model;

import java.util.Date;

public class Submission {

    private int submissionId;
    private int assignmentId;
    private int studentId;
    private Date submissionDate;
    private String filePath;

    public Submission(int submissionId, int assignmentId, int studentId, Date submissionDate, String filePath) {
        this.submissionId = submissionId;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submissionDate = submissionDate;
        this.filePath = filePath;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public String getFilePath() {
        return filePath;
    }
}

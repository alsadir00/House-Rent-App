package model;

import java.util.Date;

public class Assignment {

    private int assignmentId;
    private String title;
    private String description;
    private Date dueDate;

    public Assignment(int assignmentId, String title, String description, Date dueDate) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }
}

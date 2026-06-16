package com.medi360.DTO;

public class NotificationDTO {

    private int userID;
    private String message;
    private String category;
    private String status;

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
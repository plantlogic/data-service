package edu.csumb.spring19.capstone.models.card;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(allowGetters = true)
public class Comment {
    private Date dateCreated;
    private Date dateModified;
    private String author;
    private String userName;
    private String body;

    // Role: "none", "grower", "shipper"
    private String role;

    public Comment(String author, String userName, String body) {
        this.author = author;
        this.userName = userName;
        this.body = body;
        this.dateCreated = new Date();
        this.dateModified = this.dateCreated;
        this.role = "none";
    }

    public Comment(String author, String userName, String body, String role) {
        this.author = author;
        this.userName = userName;
        this.body = body;
        this.dateCreated = new Date();
        this.dateModified = this.dateCreated;
        this.role = role;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Date updateDateModified() {
        this.dateModified = new Date();
        return this.dateModified;
    }
}

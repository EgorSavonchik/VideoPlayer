package org.kursach.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;



@Entity
@Table(name = "comments")
public class Comment implements Serializable
{
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User ownerUser;
    private String commentText;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateOfCreate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;
    @ManyToOne
    @JoinColumn(name = "video_id", referencedColumnName = "videoId")
    private Video ownerVideo;

    public  Comment()
    {

    }

    public Comment(User ownerUser, String commentText, LocalDateTime dateOfCreate, int commentId, Video ownerVideo) {
        this.ownerUser = ownerUser;
        this.commentText = commentText;
        this.dateOfCreate = dateOfCreate;
        this.commentId = commentId;
        this.ownerVideo = ownerVideo;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDateTime getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(LocalDateTime date) {
        this.dateOfCreate = date;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @JsonBackReference
    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    @JsonBackReference
    public Video getOwnerVideo() {
        return ownerVideo;
    }

    public void setOwnerVideo(Video ownerVideo) {
        this.ownerVideo = ownerVideo;
    }
}

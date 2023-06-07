package org.kursach.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;


@Entity
@Table(name = "videos")
public class Video implements Serializable
{
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int videoId;
    private String videoLink;

    public String getVideoEndPoint() {
        return videoEndPoint;
    }

    public void setVideoEndPoint(String videoEndPoint) {
        this.videoEndPoint = videoEndPoint;
    }

    private String videoEndPoint;

    public Video()
    {

    }

    public Video(String name, List<Comment> comments, User author, String description , int videoId, String videoLink, String videoEndPoint) {
        this.name = name;
        this.comments = comments;
        this.author = author;
        this.videoId = videoId;
        this.videoLink = videoLink;
        this.description = description;
        this.videoEndPoint = videoEndPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonManagedReference
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @JsonBackReference
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}

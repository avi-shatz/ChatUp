package com.example.ex4.repo;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Name is mandatory")
    @NotNull(message = "Name is mandatory")
    private String userName;

    @NotEmpty(message = "Content is mandatory")
    @NotNull(message = "Content is mandatory")
    private String content;

    @CreationTimestamp
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @UpdateTimestamp
    private Date updatedAt;


    public Message() {}

    public Message(String userName, String content) {
        this.userName = userName;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", userName=" + userName + ", content=" + content + '}';
    }

}

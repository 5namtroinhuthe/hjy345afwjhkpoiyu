package com.namvn.shopping.persistence.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "feedback")
public class FeedBack {

    @Id
    @Column(name = "feed_back_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long feedBackId;

    private String email;
    private String subject;
    private String message;

    private String tel;
    private Date date;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

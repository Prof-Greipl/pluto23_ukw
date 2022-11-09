package de.hawlandshut.pluto23_ukw.model;

import java.util.Date;

public class Post {
    public String uid; // UID des Post-Absendes
    public String email; // E-Mail des Postabsenders
    public String title; // Titel des Posts
    public String body;
    public Date created_at; // Wann wurde der Post geschrieben
    public String firestoreKey;

    public Post(String uid, String email, String title, String body, Date created_at, String firestoreKey) {
        this.uid = uid;
        this.email = email;
        this.title = title;
        this.body = body;
        this.created_at = created_at;
        this.firestoreKey = firestoreKey;
    }
}

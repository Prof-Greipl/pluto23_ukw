package de.hawlandshut.pluto23_ukw.model;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

public class Post {
    public String uid; // UID des Post-Absendes
    public String email; // E-Mail des Postabsenders
    public String title; // Titel des Posts
    public String body;  // Text (message)
    public Date createdAt; // Wann wurde der Post geschrieben
    public String firestoreKey;

    private final static String TAG = "xx class Post";

    public Post(String uid, String email, String title, String body, Date createdAt, String firestoreKey) {
        this.uid = uid;
        this.email = email;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.firestoreKey = firestoreKey;
    }

    public static Post fromDocument( DocumentSnapshot document){
        Log.d(TAG, "Convert document");
        String uid = (String) document.get("uid");
        String email = (String) document.get("email");
        String title = (String) document.get("title");
        String body = (String) document.get("body");
        Date createdAt = ( (Timestamp) document.get("createdAt")).toDate();
        String firestoreKey = document.getId();
        return new Post(uid, email, title, body, createdAt,  firestoreKey);
    }
}

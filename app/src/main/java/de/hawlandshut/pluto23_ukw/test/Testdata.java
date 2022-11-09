package de.hawlandshut.pluto23_ukw.test;

import java.util.ArrayList;
import java.util.Date;

import de.hawlandshut.pluto23_ukw.model.Post;

public class Testdata {

    public static ArrayList<Post> createPostList(){
        ArrayList<Post> postList = new ArrayList<Post>();

        postList.add(
                new Post("uid",
                        "email@gmail.com",
                        "title",
                        "Lore ipsum se",
                        new Date(),
                        "fbkey"));

        postList.add(
                new Post("uid1",
                        "email1@gmail.com",
                        "title1",
                        "Lore ipsum se1",
                        new Date(),
                        "fbkey1"));

        return postList;
    }
}

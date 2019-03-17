package com.example.test1;

import java.io.Serializable;

public class Post {
    public String content, id,name;
    public Post() {
    }

    public Post(String content, String name,String id) {
        this.content = content;
        this.name = name;
        this.id = id;

    }
}

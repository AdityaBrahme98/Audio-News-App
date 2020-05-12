package com.example.news;

public class articleModel {

    String name,link;

    public articleModel()
    {
        //nothing
    }

    public articleModel(String name,String link)
    {
        this.name=name;
        this.link=link;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }
}

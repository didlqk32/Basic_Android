package com.example.myapplication;

public class kcal_information_data {
    private String title;
    private String link;
    private String description;
    private String thumbnail;

    public kcal_information_data(String title, String description, String thumbnail, String link) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}

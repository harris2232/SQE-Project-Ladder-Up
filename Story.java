package com.sqe.project.ladderup;

public class Story {
    String title;
    String description;
    String imageID;
    String date;
    String uploaderID;
    String category;

    public Story(String title, String description, String imageID, String date,String uploaderID, String category) {
        this.title = title;
        this.description = description;
        this.imageID = imageID;
        this.date = date;
        this.uploaderID = uploaderID;
        this.category = category;
    }

    public Story() {
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageID() {
        return imageID;
    }

    public String getDate() {
        return date;
    }

    public String getUploaderID() {
        return uploaderID;
    }

    public String getCategory() {
        return category;
    }
}

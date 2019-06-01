package com.sqe.project.ladderup;

public class Story {
    String title;
    String description;
    String imageID;
    String uploaderID;
    String date;
    String category;

    public Story(String title, String description, String imageID,
                 String uploaderID, String date, String category) {
        this.title = title;
        this.description = description;
        this.imageID = imageID;
        this.uploaderID = uploaderID;
        this.date = date;
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

    public String getUploaderID() {
        return uploaderID;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }
}

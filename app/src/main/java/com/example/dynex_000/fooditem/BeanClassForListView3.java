package com.example.dynex_000.fooditem;

/**
 * Created by admin on 3/22/2016.
 */
public class BeanClassForListView3 {

    private String image;
    private  String title;
    private String description;


    public BeanClassForListView3(String image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

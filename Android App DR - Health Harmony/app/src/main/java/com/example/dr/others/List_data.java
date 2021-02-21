package com.example.dr.others;

import java.io.Serializable;

public class List_data implements Serializable {

   private String name,img,value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List_data(String name, String img, String value) {
        this.name = name;
        this.img = img;
        this.value = value;


    }
}
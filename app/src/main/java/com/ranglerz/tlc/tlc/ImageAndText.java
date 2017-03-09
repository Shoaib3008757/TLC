package com.ranglerz.tlc.tlc;

/**
 * Created by Hafiz Adeel on 8/23/2016.
 */
public class ImageAndText {
    private String text;
    private int image;

    public ImageAndText(){}

    public ImageAndText(String text , int image)
    {
        this.text = text;
        this.image= image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}

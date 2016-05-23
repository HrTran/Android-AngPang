package com.example.admin.angpangii.Adapters;

/**
 * Created by Admin on 5/23/2016.
 */
public class AlbumItems {
    private String albumName;
    private String albumImage;

    public AlbumItems(String albumName, String albumImage) {
        this.albumName= albumName;
        this.albumImage= albumImage;
    }

    public String getAlbumName() { return albumName; }

    public void setAlbumName(String albumName) { this.albumName = albumName; }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }
}

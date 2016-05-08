package com.example.admin.angpangii.Items;

/**
 * Created by Admin on 5/7/2016.
 */
public class Health {
    private String childName;
    private String childAvatar;
    public Health(String childName, String childAvatar) {
        this.childName = childName;
        this.childAvatar = childAvatar;
    }
    public String getChildName() { return childName; }

    public void setChildName(String childName) { this.childName = childName; }

    public String getChildAvatar() {
        return childAvatar;
    }

    public void setChildAvatar(String avatarImage) {
        this.childAvatar = childAvatar;
    }
}

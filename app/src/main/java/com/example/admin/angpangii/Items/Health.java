package com.example.admin.angpangii.Items;

/**
 * Created by Admin on 5/7/2016.
 */
public class Health {
    private String childFName;
    private String childLName;
    private String childAvatar;
    public Health(String childFName, String childLName, String childAvatar) {
        this.childFName = childFName;
        this.childLName = childLName;
        this.childAvatar = childAvatar;
    }
    public String getChildFName() { return childFName; }

    public void setChildFName(String childName) { this.childFName = childFName; }

    public String getChildLName() { return childLName; }

    public void setChildLName(String childName) { this.childLName = childLName; }

    public String getChildAvatar() {
        return childAvatar;
    }

    public void setChildAvatar(String avatarImage) {
        this.childAvatar = childAvatar;
    }
}

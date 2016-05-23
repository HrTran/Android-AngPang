package com.example.admin.angpangii.Adapters;

/**
 * Created by Admin on 5/3/2016.
 */
public class MenuList {
    private String userMenu;
    private String userPost;


    public MenuList(String userMenu, String userPost ) {
        this.userMenu= userMenu;
        this.userPost= userPost;
    }

    public String getUserMenu() { return userMenu; }

    public void setUserMenu(String userMenu) { this.userMenu = userMenu; }

    public String getUserPost() {
        return userPost;
    }

    public void setUserPost(String userPost) {
        this.userPost = userPost;
    }


}

package com.example.admin.angpangii.Items;

/**
 * Created by Admin on 5/3/2016.
 */
public class NoticeItems {
    private String username;
    private String avatarImage;
    private String noticeAction;
    private String statusText;
    private String statusImage;

    public NoticeItems(String username, String avatarImage,String noticeAction ,String statusText, String statusImage) {
        this.username= username;            //ten user
        this.avatarImage= avatarImage;      //anh avatar cua user
        this.noticeAction= noticeAction;    //dong chu ghi su kien, VD:"user da dang 1 trang thai"
        this.statusText= statusText;        //trich lai stt cua user
        this.statusImage= statusImage;      //anh user dang len (neu co)
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.username = avatarImage;
    }

    public String getNoticeAction() {
        return noticeAction;
    }

    public void setNoticeAction(String noticeAction) { this.noticeAction = noticeAction; }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getStatusImage() {
        return statusImage;
    }

    public void setStatusImage(String statusImage) {
        this.statusImage = statusImage;
    }


}


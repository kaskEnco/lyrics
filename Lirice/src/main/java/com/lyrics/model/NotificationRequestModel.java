package com.lyrics.model;

import com.google.gson.annotations.SerializedName;

public class NotificationRequestModel {

    @SerializedName("notification")
    private NotificationData notification;
    @SerializedName("to")
    private String mTo;

    public NotificationData getNotification() {
        return notification;
    }

    public void setNotification(NotificationData data) {
    	notification = data;
    }

    public String getTo() {
        return mTo;
    }

    public void setTo(String to) {
        mTo = to;
    }
}
package com.lyrics.model;

import com.google.gson.annotations.SerializedName;

public class NotificationData {

    @SerializedName("body")
    private String body;

    @SerializedName("title")
    private String title;
    private String sound;
    private String priority;
    private String show_in_foreground;
    private String fcmMessageType ;
    private String show_in_background;
    private String click_action;
    private String icon;

    public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getClick_action() {
		return click_action;
	}

	public void setClick_action(String click_action) {
		this.click_action = click_action;
	}

	public String getShow_in_background() {
		return show_in_background;
	}

	public String isShow_in_background() {
		return show_in_background;
	}

	public void setShow_in_background(String string) {
		this.show_in_background = string;
	}

	public String getFcmMessageType() {
		return fcmMessageType;
	}

	public void setFcmMessageType(String fcmMessageType) {
		this.fcmMessageType = fcmMessageType;
	}

	public String getShow_in_foreground() {
		return show_in_foreground;
	}

	public void setShow_in_foreground(String string) {
		this.show_in_foreground = string;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getBody() {
        return body;
    }

    public void setBody(String body) {
    	this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
    	this.title = title;
    }
}

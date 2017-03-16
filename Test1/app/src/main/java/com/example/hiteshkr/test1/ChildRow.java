package com.example.hiteshkr.test1;

/**
 * Created by hiteshkr on 14/03/17.
 */

public class ChildRow {
    private int icon;
    private String text;
    private String id;
    private String userid;

    public ChildRow(int icon, String text, String id, String userid) {
        this.icon = icon;
        this.text = text;
        this.id = id;
        this.userid = userid;

    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.text = id;
    }

    public String getuserId() {
        return userid;
    }

    public void setuserId(String userid) {
        this.userid = userid;
    }
}

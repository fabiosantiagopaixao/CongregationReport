package com.fabio.congregationreport.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Local {

    private Integer id;
    private String url;
    public static String TABLE_NAME = "local";

    public Local() {
    }

    public Local(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

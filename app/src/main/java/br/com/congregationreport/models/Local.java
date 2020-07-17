package br.com.congregationreport.models;

import java.util.Date;

public class Local {

    private Integer id;
    private String url;
    private Date lastUpdated;
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

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

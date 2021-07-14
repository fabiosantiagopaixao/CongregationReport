package br.com.congregationreport.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

import br.com.congregationreport.util.UtilDateHour;

public class App {

    private Integer id;
    private String name;
    private Date lastUpdated;
    private Integer version;
    private String url;
    private boolean firstRun;
    public static String TABLE_NAME = "app";
    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
            + "id integer primary key autoincrement,"
            + " name text,"
            + " version integer,"
            + " url text,"
            + " last_updated integer,"
            + " first_run boolean"
            + ");";
    public static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public App() {
    }

    public App(String name,  Date lastUpdated, String url, Integer version) {
        this.name = name;
        this.lastUpdated = lastUpdated;
        this.url = url;
        this.version = version;
    }

    public static App convertJson(JSONObject jsonObject) {
        App app = new App();
        try {
            app.setName(jsonObject.getString("name"));
            app.setVersion(Integer.parseInt(jsonObject.getString("version")));
            try {
                Date lastUpdatedSheet = UtilDateHour.stringToDate(
                        UtilDateHour.DATE_HOUR, jsonObject.getString("lastUpdated")
                );
                app.setLastUpdated(lastUpdatedSheet);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return app;
    }

    public static App getUpdate(App app, App newApp, String url) {
        try {
            app.setName(newApp.getName());
            app.setVersion(newApp.getVersion());
            app.setLastUpdated(newApp.getLastUpdated());
            app.setUrl(url);
            app.setFirstRun(newApp.isFirstRun());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return app;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public boolean isFirstRun() {
        return firstRun;
    }

    public void setFirstRun(boolean firstRun) {
        this.firstRun = firstRun;
    }

    public void setUrl(String url) {
        this.url = url;
    }



}

package br.com.congregationreport.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Group {

    private Integer id;
    private String name;
    public static String TABLE_NAME = "group_congregation";
    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
            + "id integer primary key autoincrement,"
            + " name text"
            + ");";
    public static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Group() {
    }

    public static Group convertJson(JSONObject jsonObject) {
        Group group = new Group();
        try {
            group.setId(jsonObject.getInt("id"));
            group.setName(jsonObject.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return group;
    }

    public static Group getUpdate(Group group, Group newDataGroup) {
        try {
            group.setName(newDataGroup.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return group;
    }

    public Group(String name) {
        this.name = name;
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
}

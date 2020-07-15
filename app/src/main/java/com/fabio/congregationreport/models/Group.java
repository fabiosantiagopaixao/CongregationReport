package com.fabio.congregationreport.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Group {

    private Integer id;
    private String name;
    private String supGroup;
    public static String TABLE_NAME = "group_congregation";

    public Group() {
    }

    public static Group convertJson(JSONObject jsonObject) {
        Group group = new Group();
        try {
            group.setId(jsonObject.getInt("id"));
            group.setName(jsonObject.getString("name"));
            group.setSupGroup(jsonObject.getString("sup_group"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return group;
    }

    public static Group getUpdateGroup(Group group, Group newDataGroup){
        try {
            group.setName(newDataGroup.getName());
            group.setSupGroup(newDataGroup.getName());
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

    public String getSupGroup() {
        return supGroup;
    }

    public void setSupGroup(String supGroup) {
        this.supGroup = supGroup;
    }
}

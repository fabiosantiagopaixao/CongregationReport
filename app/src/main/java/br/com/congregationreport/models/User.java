package br.com.congregationreport.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private Integer id;
    private String userName;
    private String name;
    private String password;
    private String type;
    public static String TABLE_NAME = "user";

    public User() {
    }

    public static User convertJson(JSONObject jsonObject){
        User user = new User();
        try {
            user.setId(jsonObject.getInt("id"));
            user.setUserName(jsonObject.getString("user_name"));
            user.setName(jsonObject.getString("name"));
            user.setPassword(jsonObject.getString("password"));
            user.setType(jsonObject.getString("type"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User getUpdateUser(User user, User newDataUser){
        try {
            user.setUserName(newDataUser.getUserName());
            user.setName(newDataUser.getName());
            user.setPassword(newDataUser.getPassword());
            user.setType(newDataUser.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

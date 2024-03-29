package br.com.congregationreport.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Setting {

    private Integer id;
    private String nameCongregation;
    private String numberCongregation;
    public static String TABLE_NAME = "setting";
    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
            + "id integer primary key autoincrement,"
            + " name_congregation text,"
            + " number_congregation text"
            + ");";
    public static String DROP_TABLE= "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Setting() {
    }

    public static Setting convertJson(JSONObject jsonObject) {
        Setting setting = new Setting();
        try {
            setting.setNameCongregation(jsonObject.getString("name_congregation"));
            setting.setNumberCongregation(jsonObject.getString("number_congregation"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return setting;
    }

    public static Setting getUpdate(Setting setting, Setting newDataSetting) {
        try {
            setting.setNameCongregation(newDataSetting.getNameCongregation());
            setting.setNumberCongregation(newDataSetting.getNumberCongregation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setting;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameCongregation() {
        return nameCongregation;
    }

    public void setNameCongregation(String nameCongregation) {
        this.nameCongregation = nameCongregation;
    }

    public String getNumberCongregation() {
        return numberCongregation;
    }

    public void setNumberCongregation(String numberCongregation) {
        this.numberCongregation = numberCongregation;
    }
}

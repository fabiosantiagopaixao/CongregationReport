package br.com.congregationreport.models;

public class Login {

    private Integer id;
    private String userName;
    private String password;
    public static String TABLE_NAME = "login";
    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
            + "id integer primary key autoincrement,"
            + " user_name text,"
            + " password text"
            + ");";
    public static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Login() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package br.com.congregationreport.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * Este é o endereço onde o android salva os bancos de dados criado pela aplicação
     */
    private static final String DATABASE_NAME = "congregtion_report.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    // CREATES
    public final String CREATE_TABLE_GROUP = "CREATE TABLE group_congregation ( "
            + "id integer primary key autoincrement,"
            + " name text, "
            + " sup_group text"
            + ");";

    public final String CREATE_TABLE_PUBLISHER = "CREATE TABLE publisher ( "
            + "id integer primary key autoincrement,"
            + " name text,"
            + " last_name text,"
            + " birth text,"
            + " baptism text,"
            + " cell_phone text,"
            + " phones text,"
            + " email text,"
            + " city text,"
            + " neighborhood text,"
            + " address text,"
            + " number text,"
            + " notes text,"
            + " family_head text,"
            + " gender text,"
            + " group_congregation text,"
            + " publisher boolean,"
            + " regular_pioneer boolean,"
            + " special_pioneer boolean,"
            + " missionary boolean ,"
            + " deaf boolean,"
            + " elder boolean,"
            + " sup_group boolean,"
            + " servant_ministerial boolean,"
            + " group_help boolean,"
            + " anointed boolean"
            + ");";

    public final String CREATE_TABLE_SETTING = "CREATE TABLE setting ( "
            + "id integer primary key autoincrement,"
            + " name_congregation text,"
            + " number_congregation text"
            + ");";

    public final String CREATE_TABLE_USER = "CREATE TABLE user ( "
            + "id integer primary key autoincrement,"
            + " user_name text,"
            + " name text,"
            + " password text ,"
            + " type text"
            + ");";

    public final String CREATE_TABLE_LOCAL = "CREATE TABLE local ( "
            + "id integer primary key autoincrement,"
            + " url text,"
            + " last_updated date"
            + ");";

    // Delete
    public final String DROP_TABLEG_GROUP = "DROP TABLE IF EXISTS group_congregation";
    public static final String DROP_TABLE_PUBLISHER = "DROP TABLE IF EXISTS publisher";
    public static final String DROP_TABLE_SETTING = "DROP TABLE IF EXISTS setting";
    public static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS user";
    public static final String DROP_TABLE_LOCAL = "DROP TABLE IF EXISTS local";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_GROUP);
            db.execSQL(CREATE_TABLE_PUBLISHER);
            db.execSQL(CREATE_TABLE_SETTING);
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_LOCAL);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion != newVersion) {
                // Simplest implementation is to drop all old tables and recreate them
                db.execSQL(DROP_TABLEG_GROUP);
                db.execSQL(DROP_TABLE_PUBLISHER);
                db.execSQL(DROP_TABLE_SETTING);
                db.execSQL(DROP_TABLE_USER);
                db.execSQL(DROP_TABLE_LOCAL);
                onCreate(db);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
    }

}

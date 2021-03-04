package br.com.congregationreport.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.congregationreport.models.App;
import br.com.congregationreport.models.Assistance;
import br.com.congregationreport.models.Group;
import br.com.congregationreport.models.Login;
import br.com.congregationreport.models.Publisher;
import br.com.congregationreport.models.Report;
import br.com.congregationreport.models.Setting;
public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * Este é o endereço onde o android salva os bancos de dados criado pela aplicação
     */
    private static final String DATABASE_NAME = "congregation_report.db";
    private static final int DATABASE_VERSION = 2;
    private Context context;



    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Assistance.CREATE_TABLE);
            db.execSQL(Group.CREATE_TABLE);
            db.execSQL(App.CREATE_TABLE);
            db.execSQL(Login.CREATE_TABLE);
            db.execSQL(Publisher.CREATE_TABLE);
            db.execSQL(Report.CREATE_TABLE);
            db.execSQL(Setting.CREATE_TABLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion != newVersion) {
                // Simplest implementation is to drop all old tables and recreate them
                db.execSQL(Assistance.DROP_TABLE);
                db.execSQL(Group.DROP_TABLE);
                db.execSQL(App.DROP_TABLE);
                db.execSQL(Login.DROP_TABLE);
                db.execSQL(Publisher.DROP_TABLE);
                db.execSQL(Report.DROP_TABLE);
                db.execSQL(Setting.DROP_TABLE);
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

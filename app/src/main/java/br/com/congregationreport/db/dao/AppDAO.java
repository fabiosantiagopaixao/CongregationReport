package br.com.congregationreport.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.Date;

import br.com.congregationreport.db.GenericDAO;
import br.com.congregationreport.models.App;

public class AppDAO extends GenericDAO<App> {
    public AppDAO(Context context) {
        super(context, App.class);
    }

    public App getApp() {
        // Dados locais
        App app = null;

        // Pega o cursor com os dados
        Cursor cursor = findLimit("1");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            app = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return app;
    }

    public App save(App app) {
        // Salva
        Long result = null;
        try {
            result = getDb().insert(App.TABLE_NAME, null, getValues(app));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == -1) {
            return null;
        }
        app.setId(Integer.parseInt(result.toString()));
        return app;
    }


    public App update(App app) {
        // Salva
        Integer result = getDb().update(
                App.TABLE_NAME,
                getValues(app),
                KEY_ROWID + "=" + app.getId().toString(),
                null
        );
        if (result == -1) {
            return null;
        }
        app.setId(Integer.parseInt(result.toString()));
        return app;
    }

    public ContentValues getValues(App app) {
        ContentValues values = new ContentValues();
        values.put("name", app.getName());
        values.put("url", app.getUrl());
        values.put("last_updated", app.getLastUpdated().getTime());
        values.put("version", app.getVersion());
        values.put("url", app.getUrl());
        values.put("first_run", app.isFirstRun());
        return values;
    }

    private App readRow(Cursor cursor) {
        App app = new App();
        try {
            // As colunas são recuperadas na ordem que foram selecionadas
            app.setId(cursor.getInt(cursor.getColumnIndex("id")));
            app.setName(
                    cursor.getString(cursor.getColumnIndex("name"))
            );
            app.setVersion(
                    cursor.getInt(cursor.getColumnIndex("version"))
            );
            app.setUrl(
                    cursor.getString(cursor.getColumnIndex("url"))
            );
            if (
                    cursor.getString(cursor.getColumnIndex("last_updated")) != null
                            && !cursor.getString(cursor.getColumnIndex("last_updated")).isEmpty()
            ) {
                Date data = new Date();
                data.setTime(new Long(cursor.getString(cursor.getColumnIndex("last_updated"))));
                app.setLastUpdated(data);
            }
            app.setFirstRun(cursor.getInt(cursor.getColumnIndex("first_run")) == 1);
        } catch (Exception e) {
            System.out.println("Erro ao ler os dados. Erro: " + e.getMessage());
        }
        return app;
    }
}

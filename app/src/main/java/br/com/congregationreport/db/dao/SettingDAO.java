package br.com.congregationreport.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.com.congregationreport.db.GenericDAO;
import br.com.congregationreport.models.Setting;

import java.util.HashMap;
import java.util.Map;

public class SettingDAO extends GenericDAO<Setting> {
    public SettingDAO(Context context) {
        super(context, Setting.class);
    }

    public Setting getSetting() {
        // Configuração
        Setting setting = null;

        // Pega o cursor com os dados3
        Cursor cursor = findLimit("1");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            setting = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return setting;
    }

    public Setting save(Setting setting) {
        // Salva
        //this.open();
        Long result = getDb().insert(Setting.TABLE_NAME, null, getValues(setting));
        if (result == -1) {
            return null;
        }
        //this.close();
        setting.setId(Integer.parseInt(result.toString()));
        return setting;
    }

    public Setting update(Setting setting) {
        // Salva
        //this.open();
        Integer result = getDb().update(
                Setting.TABLE_NAME,
                getValues(setting),
                KEY_ROWID + "=" + setting.getId().toString(),
                null
        );
        if (result == -1) {
            return null;
        }
        //this.close();
        setting.setId(Integer.parseInt(result.toString()));
        return setting;
    }

    public ContentValues getValues(Setting setting) {
        ContentValues values = new ContentValues();
        values.put("name_congregation", setting.getNameCongregation());
        values.put("number_congregation", setting.getNumberCongregation());
        return values;
    }

    private Setting readRow(Cursor cursor) {
        Setting Setting = new Setting();
        try {
            // As colunas são recuperadas na ordem que foram selecionadas
            Setting.setId(cursor.getInt(cursor.getColumnIndex("id")));
            Setting.setNameCongregation(
                    cursor.getString(cursor.getColumnIndex("name_congregation"))
            );
            Setting.setNumberCongregation(
                    cursor.getString(cursor.getColumnIndex("number_congregation"))
            );
        } catch (Exception e) {
            System.out.println("Erro ao ler os dados. Erro: " + e.getMessage());
        }
        return Setting;
    }
}

package br.com.congregationreport.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.com.congregationreport.db.GenericDAO;
import br.com.congregationreport.models.Local;

public class LocalDAO extends GenericDAO<Local> {
    public LocalDAO(Context context) {
        super(context, Local.class);
    }

    public Local getDadosLocais() {
        // Dados locais
        Local local = new Local();

        // Pega o cursor com os dados
        Cursor cursor = findLimit("1");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            local = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return local;
    }

    public Local save(Local local) {
        // Salva
        //this.open();
        Long result = getDb().insert(Local.TABLE_NAME, null, getValues(local));
        if (result == -1) {
            return null;
        }
        //this.close();
        local.setId(Integer.parseInt(result.toString()));
        return local;
    }


    public Local update(Local local) {
        // Salva
        //this.open();
        Integer result = getDb().update(
                Local.TABLE_NAME,
                getValues(local),
                KEY_ROWID + "=" + local.getId().toString(),
                null
        );
        if (result == -1) {
            return null;
        }
        //this.close();
        local.setId(Integer.parseInt(result.toString()));
        return local;
    }

    public ContentValues getValues(Local local) {
        ContentValues values = new ContentValues();
        values.put("url", local.getUrl());
        values.put("last_updated", local.getLastUpdated().toString());
        return values;
    }

    private Local readRow(Cursor cursor) {
        Local local = new Local();
        try {
            // As colunas são recuperadas na ordem que foram selecionadas
            local.setId(cursor.getInt(cursor.getColumnIndex("id")));
            local.setUrl(
                    cursor.getString(cursor.getColumnIndex("url"))
            );
        } catch (Exception e) {
            System.out.println("Erro ao ler os dados. Erro: " + e.getMessage());
        }
        return local;
    }
}

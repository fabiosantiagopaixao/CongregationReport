package br.com.congregationreport.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.com.congregationreport.db.GenericDAO;
import br.com.congregationreport.models.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupDAO extends GenericDAO<Group> {
    public GroupDAO(Context context) {
        super(context, Group.class);
    }

    public Group findGroup(String name) {
        // Grupo
        Group group = null;

        // Pega o cursor com os dados
        Map<String[], String> filtros = new HashMap<>();
        String[] tipoColuna = new String[2];
        tipoColuna[0] = "STRING";
        tipoColuna[1] = "name";
        filtros.put(tipoColuna, name);
        Cursor cursor = findFilterByEq(filtros, "name ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            group = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return group;
    }

    public List<Group> findGroups() {
        List<Group> groups = new ArrayList<>();


        Cursor cursor = findAll("name ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Group group = readRow(cursor);
            groups.add(group);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return groups;
    }

    public Group save(Group group) {
        // Salva
        //this.open();
        Long result = getDb().insert(Group.TABLE_NAME, null, getValues(group));
        if (result == -1) {
            return null;
        }
        //this.close();
        group.setId(Integer.parseInt(result.toString()));
        return group;
    }

    public Group update(Group group) {
        // Salva
        //this.open();
        Integer result = getDb().update(
                Group.TABLE_NAME,
                getValues(group),
                KEY_ROWID + "=" + group.getId().toString(),
                null
        );
        if (result == -1) {
            return null;
        }
        //this.close();
        group.setId(Integer.parseInt(result.toString()));
        return group;
    }

    public ContentValues getValues(Group group) {
        ContentValues values = new ContentValues();
        values.put("name", group.getName());
        return values;
    }

    private Group readRow(Cursor cursor) {
        Group Group = new Group();
        try {
            // As colunas são recuperadas na ordem que foram selecionadas
            Group.setId(cursor.getInt(cursor.getColumnIndex("id")));
            Group.setName(
                    cursor.getString(cursor.getColumnIndex("name"))
            );
        } catch (Exception e) {
            System.out.println("Erro ao ler os dados. Erro: " + e.getMessage());
        }
        return Group;
    }
}

package br.com.congregationreport.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.com.congregationreport.db.GenericDAO;
import br.com.congregationreport.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO extends GenericDAO<User> {
    public UserDAO(Context context) {
        super(context, User.class);
    }

    public User getUser(String usuario) {
        // Usuários
        User user = new User();

        // Pega o cursor com os dados
        Map<String[], String> filtros = new HashMap<>();
        String[] tipoColuna = new String[2];
        tipoColuna[0] = "STRING";
        tipoColuna[1] = "user_name";
        filtros.put(tipoColuna, usuario);
        Cursor cursor = findFilterByEq(filtros, "name ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            user = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return user;
    }

    public List<User> findUsers() {
        List<User> users = new ArrayList<>();

        Cursor cursor = findAll("name ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = readRow(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return users;
    }

    public User save(User user) {
        // Salva
        //this.open();
        Long result = getDb().insert(User.TABLE_NAME, null, getValues(user));
        if (result == -1) {
            return null;
        }
        //this.close();
        user.setId(Integer.parseInt(result.toString()));
        return user;
    }


    public User update(User user) {
        // Salva
        //this.open();
        Integer result = getDb().update(
                User.TABLE_NAME,
                getValues(user),
                KEY_ROWID + "=" + user.getId().toString(),
                null
        );
        if (result == -1) {
            return null;
        }
        //this.close();
        user.setId(Integer.parseInt(result.toString()));
        return user;
    }

    public ContentValues getValues(User user) {
        ContentValues values = new ContentValues();
        values.put("user_name", user.getUserName());
        values.put("name", user.getName());
        values.put("password", user.getPassword());
        values.put("type", user.getType());
        return values;
    }

    private User readRow(Cursor cursor) {
        User user = new User();
        try {
            // As colunas são recuperadas na ordem que foram selecionadas
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setUserName(
                    cursor.getString(cursor.getColumnIndex("user_name"))
            );
            user.setName(
                    cursor.getString(cursor.getColumnIndex("name"))
            );
            user.setPassword(
                    cursor.getString(cursor.getColumnIndex("password"))
            );
            user.setType(
                    cursor.getString(cursor.getColumnIndex("type"))
            );
        } catch (Exception e) {
            System.out.println("Erro ao ler os dados. Erro: " + e.getMessage());
        }
        return user;
    }
}

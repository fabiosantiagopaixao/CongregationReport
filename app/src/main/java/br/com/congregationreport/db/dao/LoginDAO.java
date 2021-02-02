package br.com.congregationreport.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.com.congregationreport.db.GenericDAO;
import br.com.congregationreport.models.Login;

public class LoginDAO extends GenericDAO<Login> {

    public LoginDAO(Context context) {
        super(context, Login.class);
    }

    public Login getDataLogin() {
        // Dados locais
        Login Login = null;

        // Pega o cursor com os dados
        Cursor cursor = findLimit("1");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Login = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();
        // this.close();

        // Retorna os dados
        return Login;
    }

    public Login save(Login Login) {
        // Salva
        //this.open();
        Long result = getDb().insert(Login.TABLE_NAME, null, getValues(Login));
        if (result == -1) {
            return null;
        }
        //this.close();
        Login.setId(Integer.parseInt(result.toString()));
        return Login;
    }


    public Login update(Login login) {
        // Salva
        //this.open();
        Integer result = getDb().update(
                Login.TABLE_NAME,
                getValues(login),
                KEY_ROWID + "=" + login.getId().toString(),
                null
        );
        if (result == -1) {
            return null;
        }
        //this.close();
        login.setId(Integer.parseInt(result.toString()));
        return login;
    }

    public ContentValues getValues(Login login) {
        ContentValues values = new ContentValues();
        try {
            values.put("user_name", login.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("password", login.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    private Login readRow(Cursor cursor) {
        Login login = new Login();
        try {
            // As colunas são recuperadas na ordem que foram selecionadas
            login.setId(cursor.getInt(cursor.getColumnIndex("id")));
            try {
                login.setUserName(
                        cursor.getString(cursor.getColumnIndex("user_name"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                login.setPassword(
                        cursor.getString(cursor.getColumnIndex("password"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler os dados. Erro: " + e.getMessage());
        }
        return login;
    }
}

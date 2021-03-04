package br.com.congregationreport.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import br.com.congregationreport.models.Publisher;

public class GenericDAO<E> {

    public static SQLiteDatabase db;
    private DataBaseHelper helper;
    private Class<E> type;
    public static final String KEY_ROWID = "id";

    public GenericDAO(Context context, Class<E> type) {
        this.type = type;
        this.helper = new DataBaseHelper(context);
        if (db == null) {
            db = helper.getWritableDatabase();
        }
    }

    public Cursor findFilterByEq(Map<String[], String> filtros, String order) {
        // this.open();

        // String filtros
        String filtro = "";
        int i = 0;
        for (Map.Entry entry : filtros.entrySet()) {
            // Pega a string com tipo e nome da coluna
            String[] s = (String[]) entry.getKey();
            String tipo = s[0];
            String coluna = s[1];
            String valor = entry.getValue().toString();
            if (tipo.equals("STRING")) {
                valor = "\'" + entry.getValue().toString() + "\'";
            }

            if (i == (filtros.size() - 1)) {
                filtro = filtro + coluna + " = " + valor;
            } else {
                filtro = filtro + coluna + " = " + valor + " AND ";
            }
            i++;
        }
        Cursor cursor = db.query(getTableName(),
                null, // vetor dos campos ex: new String[]{campo, valor}
                filtro, // Filtro dos campos passados
                null, null, null, order);
        return cursor;
    }

    public Cursor findFilterWhere(String select) {
        Cursor cursor = db.rawQuery(select, null);
        return cursor;
    }

    private String getTableName() {
        String nome = type.getSimpleName().toLowerCase();
        if (nome.equals("group")) {
            nome = "group_congregation";
        }
        return nome;
    }


    public Cursor findFilterByEq(Map<String[], String> filtros, String order, String limite) {
        //this.open();

        // String filtros
        String filtro = "";
        int i = 0;
        for (Map.Entry entry : filtros.entrySet()) {
            // Pega a string com tipo e nome da coluna
            String[] s = (String[]) entry.getKey();
            String tipo = s[0];
            String coluna = s[1];
            String valor = entry.getValue().toString();
            if (tipo.equals("STRING")) {
                valor = "\'" + entry.getValue().toString() + "\'";
            }

            if (i == (filtros.size() - 1)) {
                filtro = filtro + coluna + " = " + valor;
            } else {
                filtro = filtro + coluna + " = " + valor + " AND ";
            }
            i++;
        }
        Cursor cursor = db.query(getTableName(),
                null, // vetor dos campos ex: new String[]{campo, valor}
                filtro, // Filtro dos campos passados
                null, null, null, order, limite);
        return cursor;
    }


    public Cursor findFilterByOr(Map<String[], String> filtros, String order) {
        //this.open();

        // String filtros
        String filtro = "";
        int i = 0;
        for (Map.Entry entry : filtros.entrySet()) {
            // Pega a string com tipo e nome da coluna
            String[] s = (String[]) entry.getKey();
            String tipo = s[0];
            String coluna = s[1];
            String valor = entry.getValue().toString();
            if (tipo.equals("STRING")) {
                valor = "\'" + entry.getValue().toString() + "\'";
            }

            if (i == (filtros.size() - 1)) {
                filtro = filtro + coluna + " = " + valor;
            } else {
                filtro = filtro + coluna + " = " + valor + " OR ";
            }
            i++;
        }
        Cursor cursor = db.query(getTableName(),
                null, // vetor dos campos ex: new String[]{campo, valor}
                filtro, // Filtro dos campos passados
                null, null, null, order);
        return cursor;
    }


    public Cursor findFilterQuery(String filtro, String order) {
        //this.open();
        String nome = type.getSimpleName().toLowerCase();
        Cursor cursor = db.query(nome,
                null, // vetor dos campos ex: new String[]{campo, valor}
                filtro, // Filtro dos campos passados
                null, null, null, order);
        return cursor;
    }

    public Cursor findAll() {
        // this.open();
        // Iremos buscar todos os dados do objeto passadi
        Cursor cursor;
        cursor = db.query(
                getTableName(), // Tabela
                null, // Colunas (String[] columns)
                null, // Selection
                null, // SelectionArgs
                null, // GroupBy
                null, // Having
                null, // Order by
                null
        );
        return cursor;
    }

    public Cursor findAll(String order) {
        // this.open();
        // Iremos buscar todos os dados do objeto passadi
        Cursor cursor;
        cursor = db.query(
                getTableName(), // Tabela
                null, // Colunas (String[] columns)
                null, // Selection
                null, // SelectionArgs
                null, // GroupBy
                null, // Having
                order, // Order by
                null
        );
        return cursor;
    }


    public Cursor findLimit(String limite, String order) {
        //this.open();
        // Iremos buscar todos os dados do objeto passadi
        Cursor cursor;
        cursor = db.query(
                getTableName(), // Tabela
                null, // Colunas (String[] columns)
                null, // Selection
                null, // SelectionArgs
                null, // GroupBy
                null, // Having
                order, // Order by
                limite
        );
        return cursor;
    }

    public Cursor findLimit(String limite) {
        //this.open();
        // Iremos buscar todos os dados do objeto passadi
        Cursor cursor;
        cursor = db.query(
                getTableName(), // Tabela
                null, // Colunas (String[] columns)
                null, // Selection
                null, // SelectionArgs
                null, // GroupBy
                null, // Having
                null, // Order by
                limite
        );
        return cursor;
    }


    public void delete(long id) {
        int delete = this.db.delete(getTableName(), KEY_ROWID + "=" + id, null);
        if (delete == 1) {
            System.out.println("Item excluido com sucesso.");
        }
        //this.close();
    }

    public void deleteAll() {
        this.db.execSQL("DELETE FROM " + getTableName());
        this.db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ = 0 WHERE NAME = '" + getTableName() + "';");
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public DataBaseHelper getHelper() {
        return helper;
    }

}

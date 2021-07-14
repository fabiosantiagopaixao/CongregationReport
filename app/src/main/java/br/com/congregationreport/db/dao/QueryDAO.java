package br.com.congregationreport.db.dao;

import android.content.Context;
import android.database.Cursor;
import java.io.IOException;
import br.com.congregationreport.db.GenericDAO;
import br.com.congregationreport.models.DataCongregation;
import br.com.congregationreport.util.Util;

public class QueryDAO extends GenericDAO<DataCongregation> {

    public QueryDAO(Context context) {
        super(context, DataCongregation.class);

    }

    public DataCongregation findDataCongregation() {
        DataCongregation data = null;

        String query = "";

        try {
            query = Util.parseInputStreamToString(
                    this.getContext()
                            .getAssets().open("find_report_deafult_congregation.sql")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Cursor cursor = findFilterWhere(query);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                data = readRow(cursor);
                cursor.moveToNext();
            }
            // Fecha o cursor
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retorna os dados
        return data;
    }

    private DataCongregation readRow(Cursor cursor) {
        DataCongregation data = new DataCongregation();
        try {
            data.setDeafSchool(cursor.getInt(cursor.getColumnIndex("deaf_school")));
            data.setDeafBaptism(cursor.getInt(cursor.getColumnIndex("deaf_baptism")));
            data.setDeafNoBaptism(cursor.getInt(cursor.getColumnIndex("deaf_no_baptism")));
            data.setDeafRegularPioneer(cursor.getInt(cursor.getColumnIndex("deaf_regular_pioneer")));
            data.setPublishers(cursor.getInt(cursor.getColumnIndex("publishers")));
            data.setPublishersBaptism(cursor.getInt(cursor.getColumnIndex("publishers_baptism")));
            data.setPublishersNoBaptism(cursor.getInt(cursor.getColumnIndex("publishers_no_baptism")));
            data.setTotalRegularPioneer(cursor.getInt(cursor.getColumnIndex("total_regular_pioneer")));

        } catch (Exception e) {
            System.out.println("Erro ao ler os dados. Erro: " + e.getMessage());
        }
        return data;
    }
}

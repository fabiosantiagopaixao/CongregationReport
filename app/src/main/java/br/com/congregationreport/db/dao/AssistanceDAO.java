package br.com.congregationreport.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.congregationreport.db.GenericDAO;
import br.com.congregationreport.models.Assistance;
import br.com.congregationreport.models.AssistanceReport;
import br.com.congregationreport.models.CongReport;
import br.com.congregationreport.models.DataReportS88;
import br.com.congregationreport.util.Util;

public class AssistanceDAO extends GenericDAO<Assistance> {

    private Context context;

    public AssistanceDAO(Context context) {
        super(context, Assistance.class);
        this.context = context;
    }

    public Assistance getAssistance(Integer id) {
        // Assistances
        Assistance assistance = null;

        // Pega o cursor com os dados
        Map<String[], String> filtros = new HashMap<>();
        String[] tipoColuna = new String[2];
        tipoColuna[0] = "INTEGER";
        tipoColuna[1] = "id";
        filtros.put(tipoColuna, id.toString());
        Cursor cursor = findFilterByEq(filtros, "id ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            assistance = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();

        // Retorna os dados
        return assistance;
    }

    public Assistance getAssistanceByDate(String date) {
        // Assistances
        Assistance assistance = null;

        // Pega o cursor com os dados
        Map<String[], String> filtros = new HashMap<>();
        String[] tipoColuna = new String[2];
        tipoColuna[0] = "STRING";
        tipoColuna[1] = "data";
        filtros.put(tipoColuna, date);
        Cursor cursor = findFilterByEq(filtros, "id ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            assistance = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();

        // Retorna os dados
        return assistance;
    }

    public List<Assistance> getAssistancePerMonthAndYear(String month, String year) {
        List<Assistance> assistances = new ArrayList<>();

        // Pega o cursor com os dados
        Map<String[], String> filtros = new HashMap<>();

        // By year
        String[] tipoColuna = new String[2];
        tipoColuna[0] = "INTEGER";
        tipoColuna[1] = "year";
        filtros.put(tipoColuna, year);
        // ------
        // By month
        tipoColuna = new String[2];
        tipoColuna[0] = "STRING";
        tipoColuna[1] = "month";
        filtros.put(tipoColuna, month);


        Cursor cursor = findFilterByEq(filtros, "id ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Assistance assistance = readRow(cursor);
            assistances.add(assistance);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();

        // Retorna os dados
        return assistances;
    }


    public DataReportS88 findReportS88(String year, int type, String typeWeek) {
        DataReportS88 dataReportS88 = new DataReportS88();
        Integer pYear = Integer.parseInt(year) - 1;
        try {
            Map<String, AssistanceReport> firstYear = this.findReportDataS88(year, type, typeWeek);
            Map<String, AssistanceReport> previousYearYear = this.findReportDataS88(pYear.toString(), type, typeWeek);
            dataReportS88.setTypeWeek(typeWeek);
            dataReportS88.setFirstYear(firstYear);
            dataReportS88.setType(type);
            dataReportS88.setPreviousYear(previousYearYear);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retorna os dados
        return dataReportS88;
    }


    private Map<String, AssistanceReport> findReportDataS88(String year, int type, String typeWeek) {
        Map<String, AssistanceReport> assistances = new HashMap<>();
        String query = "";
        try {
            switch (type) {
                case CongReport.TOTAL:
                    query = Util.parseInputStreamToString(this.context.getAssets().open("find_total_s88.sql"));
                    break;
                case CongReport.DEAF:
                    query = Util.parseInputStreamToString(this.context.getAssets().open("find_deaf_s88.sql"));
                    break;
            }

            query = query.replace("$P{YEAR}", year);
            query = query.replace("$P{TYPE}", typeWeek);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cursor cursor = findFilterWhere(query);

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AssistanceReport assistance = readRowReportS88(cursor);
            assistances.put(assistance.getMonth(), assistance);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();

        // Retorna os dados
        return assistances;
    }


    public Assistance getAssistanceWeekDay(String date) {
        // Assistances
        Assistance assistance = null;

        // Pega o cursor com os dados
        Map<String[], String> filtros = new HashMap<>();

        // By publisher
        String[] tipoColuna = new String[2];
        tipoColuna[0] = "STRING";
        tipoColuna[1] = "data";
        filtros.put(tipoColuna, date);


        Cursor cursor = findFilterByEq(filtros, "id ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            assistance = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();

        // Retorna os dados
        return assistance;
    }

    public List<Assistance> findAssistances() {
        List<Assistance> assistances = new ArrayList<>();

        Cursor cursor = findAll("id ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Assistance assistance = readRow(cursor);
            assistances.add(assistance);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();

        // Retorna os dados
        return assistances;
    }

    public Assistance save(Assistance assistance) {
        // Salva
        //this.open();
        Long result = getDb().insert(Assistance.TABLE_NAME, null, getValues(assistance));
        if (result == -1) {
            return null;
        }
        //this.close();
        assistance.setId(Integer.parseInt(result.toString()));
        return assistance;
    }


    public Assistance update(Assistance assistance) {
        // Salva
        //this.open();
        Integer result = getDb().update(
                Assistance.TABLE_NAME,
                getValues(assistance),
                KEY_ROWID + "=" + assistance.getId().toString(),
                null
        );
        if (result == -1) {
            return null;
        }
        //this.close();
        assistance.setId(Integer.parseInt(result.toString()));
        return assistance;
    }

    public ContentValues getValues(Assistance assistance) {
        ContentValues values = new ContentValues();
        try {
            values.put("year", assistance.getYear());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("month", assistance.getMonth());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("type", assistance.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("data", assistance.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("amount_deaf", assistance.getAmountDeaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("amount_total", assistance.getAmountTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    private Assistance readRow(Cursor cursor) {
        Assistance assistance = new Assistance();
        try {
            // As colunas são recuperadas na ordem que foram selecionadas
            assistance.setId(cursor.getInt(cursor.getColumnIndex("id")));
            assistance.setYear(
                    cursor.getInt(cursor.getColumnIndex("year"))
            );
            try {
                assistance.setMonth(
                        cursor.getString(cursor.getColumnIndex("month"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                assistance.setType(
                        cursor.getString(cursor.getColumnIndex("type"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                assistance.setDate(
                        cursor.getString(cursor.getColumnIndex("data"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                assistance.setAmountDeaf(
                        cursor.getInt(cursor.getColumnIndex("amount_deaf"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                assistance.setAmountTotal(
                        cursor.getInt(cursor.getColumnIndex("amount_total"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler os dados. Erro: " + e.getMessage());
        }
        return assistance;
    }

    private AssistanceReport readRowReportS88(Cursor cursor) {
        AssistanceReport assistance = new AssistanceReport();
        try {
            // As colunas são recuperadas na ordem que foram selecionadas
            assistance.setMonth(cursor.getString(cursor.getColumnIndex("month")));
            assistance.setNumberMeeting(
                    cursor.getInt(cursor.getColumnIndex("number_meeeting"))
            );
            assistance.setTotal(
                    cursor.getInt(cursor.getColumnIndex("total"))
            );
            assistance.setAverage(
                    cursor.getFloat(cursor.getColumnIndex("average"))
            );

        } catch (Exception e) {
            System.out.println("Erro ao ler os dados. Erro: " + e.getMessage());
        }
        return assistance;
    }
}

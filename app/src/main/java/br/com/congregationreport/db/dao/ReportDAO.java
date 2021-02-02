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
import br.com.congregationreport.models.CongReport;
import br.com.congregationreport.models.Publisher;
import br.com.congregationreport.models.Report;
import br.com.congregationreport.util.Util;
import br.com.congregationreport.util.UtilDataMemory;
import br.com.congregationreport.util.UtilDateHour;

public class ReportDAO extends GenericDAO<Report> {
    private PublisherDAO publisherDAO;
    private Context context;

    public ReportDAO(Context context) {
        super(context, Report.class);
        this.context = context;
        this.publisherDAO = UtilDataMemory.getPublisherDAO(context);
    }

    public Report getReport(Integer id) {
        // Reports
        Report report = null;

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
            report = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();

        // Retorna os dados
        return report;
    }

    public Report getReport(Publisher publisher, String month, String year) {
        // Reports
        Report report = null;

        // Pega o cursor com os dados
        Map<String[], String> filtros = new HashMap<>();

        // By publisher
        String[] tipoColuna = new String[2];
        tipoColuna[0] = "INTEGER";
        tipoColuna[1] = "id_publisher";
        filtros.put(tipoColuna, publisher.getId().toString());

        // By Month
        tipoColuna = new String[2];
        tipoColuna[0] = "STRING";
        tipoColuna[1] = "month";
        filtros.put(tipoColuna, month);

        // By year
        tipoColuna = new String[2];
        tipoColuna[0] = "INTEGER";
        tipoColuna[1] = "year";
        filtros.put(tipoColuna, year);


        Cursor cursor = findFilterByEq(filtros, "id ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            report = readRow(cursor);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();

        // Retorna os dados
        return report;
    }

    public Map<String, Report> findReportsByPublisherAndYear(Publisher publisher, String year) {
        Map<String, Report> reports = new HashMap<>();

        // Pega o cursor com os dados
        Map<String[], String> filtros = new HashMap<>();

        // By year
        String[] tipoColuna = new String[2];
        tipoColuna[0] = "INTEGER";
        tipoColuna[1] = "id_publisher";
        filtros.put(tipoColuna, publisher.getId().toString());
        // ------
        // By month
        tipoColuna = new String[2];
        tipoColuna[0] = "STRING";
        tipoColuna[1] = "year";
        filtros.put(tipoColuna, year);


        Cursor cursor = findFilterByEq(filtros, "id ASC");

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Report report = readRow(cursor);
            reports.put(report.getMonth(), report);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();

        // Retorna os dados
        return reports;
    }

    public Map<String, Report> findReporTotalByGroup(String year, int type) {
        Map<String, Report> reports = new HashMap<>();

        String query = "";
        try {
            query = Util.parseInputStreamToString(this.context.getAssets().open("find_total_s21.sql"));
            query = query.replace("$P{YEAR}", year);
            switch (type) {
                case CongReport.PUBLISHER:
                    query = query.replace("$P{FILTER}", "AND report.auxiliary_pioneer = 0 AND (publisher.deaf = 1 OR publisher.publisher = 1)");
                    break;
                case CongReport.DEAF:
                    query = query.replace("$P{FILTER}", "AND report.auxiliary_pioneer = 0 AND publisher.deaf = 1");
                    break;
                case CongReport.AUXILIARY:
                    query = query.replace("$P{FILTER}", "AND report.auxiliary_pioneer = 1");
                    break;
                case CongReport.REGULAR:
                    query = query.replace("$P{FILTER}", "AND (publisher.regular_pioneer = 1 OR publisher.special_pioneer = 1)");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Cursor cursor = findFilterWhere(query);

        // Pega o primeiro elemento
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Report report = readRow(cursor);
            reports.put(report.getMonth(), report);
            cursor.moveToNext();
        }
        // Fecha o cursor
        cursor.close();

        // Retorna os dados
        return reports;
    }

    public CongReport getReportByBetel(String month, String year, int type) {
        String query = "";

        try {
            switch (type) {
                case CongReport.TOTAL:
                    query = Util.parseInputStreamToString(this.context.getAssets().open("find_report_betel_total.sql"));
                    break;
                case CongReport.PUBLISHER:
                    query = Util.parseInputStreamToString(this.context.getAssets().open("find_report_betel_publisher.sql"));
                    break;
                case CongReport.AUXILIARY:
                    query = Util.parseInputStreamToString(this.context.getAssets().open("find_report_betel_auxialiary.sql"));
                    break;
                case CongReport.REGULAR:
                    query = Util.parseInputStreamToString(this.context.getAssets().open("find_report_betel_regular.sql"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        query = query.replace("$P{MONTH}", month);
        query = query.replace("$P{YEAR}", year);

        Cursor cursor = findFilterWhere(query);

        return this.getReportCongregation(cursor);
    }

    public CongReport getActivePublishersAndAssistance(String month, String year) {
        CongReport congReport = new CongReport();
        String query = "";

        try {
            query = Util.parseInputStreamToString(this.context.getAssets().open("find_report_betel_all_active_publishers.sql"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        query = query.replace("$P{MONTH}", month);
        query = query.replace("$P{YEAR}", year);

        Cursor cursor = findFilterWhere(query);

        congReport.setActivePublishers(this.getReportCongregation(cursor).getReports());

        // average
        try {
            query = Util.parseInputStreamToString(context.getAssets().open("asistance_average_weekend.sql"));
            query = query.replace("$P{MONTH}", month);
            query = query.replace("$P{YEAR}", year);

            cursor = findFilterWhere(query);

            cursor.moveToFirst();
            Float total = 0f;
            while (!cursor.isAfterLast()) {
                total = cursor.getFloat(cursor.getColumnIndex("total"));
                cursor.moveToNext();
            }
            // Fecha o cursor
            cursor.close();

            congReport.setAssistanceAverage(total);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return congReport;
    }


    private CongReport getReportCongregation(Cursor cursor) {
        CongReport report = new CongReport();
        try {
            // Pega o primeiro elemento
            Integer publications = 0;
            Integer videos = 0;
            Integer hours = 0;
            Integer revisited = 0;
            Integer courses = 0;
            Map<Integer, Integer> map = new HashMap<>();

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Report currentReport = readRow(cursor);

                if (!currentReport.getPublisher().isMissionary()) {
                    publications += currentReport.getPublications();
                    videos += currentReport.getVideos();
                    hours += currentReport.getHours();
                    revisited += currentReport.getRevisited();
                    courses += currentReport.getBibleCourses();

                    map.put(currentReport.getIdPublisher(), currentReport.getIdPublisher());
                }
                cursor.moveToNext();
            }
            // Fecha o cursor
            cursor.close();

            // ---
            report.setReports(map.size());
            report.setPublications(publications);
            report.setVideos(videos);
            report.setHours(hours);
            report.setRevisited(revisited);
            report.setCourses(courses);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }


    public Report save(Report report) {
        // Salva
        Long result = getDb().insert(Report.TABLE_NAME, null, getValues(report));
        if (result == -1) {
            return null;
        }
        report.setId(Integer.parseInt(result.toString()));
        return report;
    }


    public Report update(Report report) {
        // Salva
        //this.open();
        Integer result = getDb().update(
                Report.TABLE_NAME,
                getValues(report),
                KEY_ROWID + "=" + report.getId().toString(),
                null
        );
        if (result == -1) {
            return null;
        }
        //this.close();
        report.setId(Integer.parseInt(result.toString()));
        return report;
    }

    public ContentValues getValues(Report report) {
        ContentValues values = new ContentValues();
        try {
            values.put("id_publisher", report.getIdPublisher());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("year", report.getYear());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("month", report.getMonth());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("publications", report.getPublications());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("hours", report.getHours());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("videos", report.getVideos());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("revisited", report.getRevisited());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("bible_couses", report.getBibleCourses());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("credit", report.getCredit());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("auxiliary_pioneer", report.isAuxiliaryPioneer());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("auxiliary_pioneer_type", report.getAuxiliaryPioneerType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            values.put("preaching_fifteen_min_less", report.isPreachingFifteenMinLess());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (report.getNotes() != null && !report.getNotes().isEmpty()) {
                values.put("notes", report.getNotes());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    private Report readRow(Cursor cursor) {
        Report report = new Report();
        try {
            // As colunas são recuperadas na ordem que foram selecionadas
            report.setId(cursor.getInt(cursor.getColumnIndex("id")));
            report.setIdPublisher(
                    cursor.getInt(cursor.getColumnIndex("id_publisher"))
            );
            report.setPublisher(this.publisherDAO.getPublisher(report.getId()));
            try {
                report.setYear(
                        cursor.getInt(cursor.getColumnIndex("year"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setMonth(
                        cursor.getString(cursor.getColumnIndex("month"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setPublications(
                        cursor.getInt(cursor.getColumnIndex("publications"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setHours(
                        cursor.getInt(cursor.getColumnIndex("hours"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setVideos(
                        cursor.getInt(cursor.getColumnIndex("videos"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setRevisited(
                        cursor.getInt(cursor.getColumnIndex("revisited"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setBibleCourses(
                        cursor.getInt(cursor.getColumnIndex("bible_couses"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setCredit(
                        cursor.getInt(cursor.getColumnIndex("credit"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setAuxiliaryPioneer(cursor.getInt(cursor.getColumnIndex("auxiliary_pioneer")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setAuxiliaryPioneerType(cursor.getString(cursor.getColumnIndex("auxiliary_pioneer_type")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setPreachingFifteenMinLess(cursor.getInt(cursor.getColumnIndex("preaching_fifteen_min_less")) == 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setNotes(
                        cursor.getString(cursor.getColumnIndex("notes"))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler os dados. Erro: " + e.getMessage());
        }
        return report;
    }


}

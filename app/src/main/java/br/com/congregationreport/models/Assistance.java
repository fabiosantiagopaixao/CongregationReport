package br.com.congregationreport.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Assistance {
    private Integer id;
    private String order;
    private Integer year;
    private String month;
    private String date;
    private String type;
    private Integer amountDeaf;
    private Integer amountTotal;
    private String amountDeafM;
    private String amountTotalM;
    public static String TABLE_NAME = "assistance";
    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
            + "id integer primary key autoincrement,"
            + " year integer,"
            + " month text,"
            + " type text,"
            + " data text,"
            + " amount_deaf integer,"
            + " amount_total integer"
            + ");";
    public static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Assistance() {
    }

    public static Assistance convertJson(JSONObject jsonObject) {
        Assistance report = new Assistance();
        try {
            report.setId(jsonObject.getInt("id"));
            try {
                report.setYear(jsonObject.getInt("year"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setMonth(jsonObject.getString("month"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setType(jsonObject.getString("type"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setDate(jsonObject.getString("date"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setAmountDeaf(jsonObject.getInt("amount_deaf"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                report.setAmountTotal(jsonObject.getInt("amount_total"));
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return report;
    }

    public static JSONObject getJson(Assistance report) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", report.getId());
            object.put("year", report.getYear());
            object.put("month", report.getMonth());
            object.put("type", report.getType());
            object.put("date", report.getDate());
            object.put("amount_deaf", report.getAmountDeaf());
            object.put("amount_total", report.getAmountTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static Assistance getUpdate(Assistance report, Assistance newDataReport) {
        try {
            report.setYear(newDataReport.getYear());
            report.setMonth(newDataReport.getMonth());
            report.setDate(newDataReport.getDate());
            report.setAmountDeaf(newDataReport.getAmountDeaf());
            report.setAmountTotal(newDataReport.getAmountTotal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return report;
    }

    public Integer getId() {
        if (id == null) {
            return 0;
        }
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAmountDeaf() {
        return amountDeaf;
    }

    public void setAmountDeaf(Integer amountDeaf) {
        this.amountDeaf = amountDeaf;
    }

    public Integer getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(Integer amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getAmountDeafM() {
        return amountDeafM;
    }

    public void setAmountDeafM(String amountDeafM) {
        this.amountDeafM = amountDeafM;
    }

    public String getAmountTotalM() {
        return amountTotalM;
    }

    public void setAmountTotalM(String amountTotalM) {
        this.amountTotalM = amountTotalM;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

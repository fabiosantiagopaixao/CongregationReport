package br.com.congregationreport.models;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.congregationreport.util.UtilDataMemory;
import br.com.congregationreport.util.UtilDateHour;

public class Report {
    private Integer id;
    private Publisher publisher;
    private Integer idPublisher;
    private Integer year;
    private String month;
    private Integer publications;
    private Integer videos;
    private Integer hours;
    private Integer revisited;
    private Integer bibleCourses;
    private Integer credit;
    private boolean auxiliaryPioneer;
    private String auxiliaryPioneerType;
    private boolean preachingFifteenMinLess;
    private String notes;
    public static String TABLE_NAME = "report";
    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
            + "id integer primary key autoincrement,"
            + " id_publisher integer,"
            + " year integer,"
            + " month text,"
            + " publications integer,"
            + " videos integer,"
            + " hours integer,"
            + " revisited integer,"
            + " bible_couses integer,"
            + " credit integer,"
            + " auxiliary_pioneer boolean,"
            + " auxiliary_pioneer_type text,"
            + " preaching_fifteen_min_less boolean,"
            + " notes text"
            + ");";
    public static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static int TOTAL = 12;
    public static int AVERAGE = 13;

    public Report() {
        this.id = 0;
        this.publications = 0;
        this.videos = 0;
        this.hours = 0;
        this.revisited = 0;
        this.bibleCourses = 0;
        this.credit = 0;
        this.notes = "";
    }

    public static Report convertJson(JSONObject jsonObject) {
        Report report = new Report();
        try {
            report.setId(jsonObject.getInt("id"));
            try {
                report.setIdPublisher(jsonObject.getInt("id_publisher"));
            } catch (Exception e) {
            }
            try {
                report.setYear(jsonObject.getInt("year"));
            } catch (Exception e) {
            }
            try {
                report.setMonth(jsonObject.getString("month"));
            } catch (Exception e) {
            }
            try {
                report.setPublications(jsonObject.getInt("publications"));
            } catch (Exception e) {
            }
            try {
                report.setVideos(jsonObject.getInt("videos"));
            } catch (Exception e) {
            }
            try {
                report.setHours(jsonObject.getInt("hours"));
            } catch (Exception e) {
            }
            try {
                report.setRevisited(jsonObject.getInt("revisited"));
            } catch (Exception e) {
            }
            try {
                report.setBibleCourses(jsonObject.getInt("bible_couses"));
            } catch (Exception e) {
            }
            try {
                report.setCredit(jsonObject.getInt("credit"));
            } catch (Exception e) {
            }
            try {
                report.setAuxiliaryPioneer(jsonObject.getBoolean("auxiliary_pioneer"));
            } catch (Exception e) {
            }
            try {
                report.setAuxiliaryPioneerType(jsonObject.getString("auxiliary_pioneer_type"));
            } catch (Exception e) {
            }
            try {
                report.setPreachingFifteenMinLess(jsonObject.getBoolean("preaching_fifteen_min_less"));
            } catch (Exception e) {
            }
            try {
                report.setNotes(jsonObject.getString("notes"));
            } catch (Exception e) {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return report;
    }

    public static JSONObject getJson(Report report) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", report.getId());
            object.put("id_publisher", report.getIdPublisher());
            object.put("year", report.getYear());
            object.put("month", report.getMonth());
            object.put("publications", report.getPublications());
            object.put("videos", report.getVideos());
            object.put("hours", report.getHours());
            object.put("revisited", report.getRevisited());
            object.put("bible_couses", report.getBibleCourses());
            object.put("credit", report.getCredit());
            object.put("auxiliary_pioneer", report.isAuxiliaryPioneer() ? "TRUE" : "FALSE");
            object.put("auxiliary_pioneer_type", report.getAuxiliaryPioneerType());
            object.put("preaching_fifteen_min_less", report.isPreachingFifteenMinLess() ? "TRUE" : "FALSE");
            object.put("notes", report.getNotes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static Report getUpdate(Report report, Report newDataReport) {
        try {
            report.setIdPublisher(newDataReport.getIdPublisher());
            report.setYear(newDataReport.getYear());
            report.setMonth(newDataReport.getMonth());
            report.setPublications(newDataReport.getPublications());
            report.setVideos(newDataReport.getVideos());
            report.setHours(newDataReport.getHours());
            report.setRevisited(newDataReport.getRevisited());
            report.setBibleCourses(newDataReport.getBibleCourses());
            report.setCredit(newDataReport.getCredit());
            report.setAuxiliaryPioneer(newDataReport.isAuxiliaryPioneer());
            report.setAuxiliaryPioneerType(newDataReport.getAuxiliaryPioneerType());
            report.setPreachingFifteenMinLess(newDataReport.isPreachingFifteenMinLess());
            report.setNotes(newDataReport.getNotes());
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

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Integer getIdPublisher() {
        return idPublisher;
    }

    public void setIdPublisher(Integer idPublisher) {
        this.idPublisher = idPublisher;
    }

    public Integer getYear() {
        if (this.year == null) {
            this.year = Integer.parseInt(UtilDateHour.getCurrentYear());
        }
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

    public Integer getPublications() {
        return publications;
    }

    public void setPublications(Integer publications) {
        this.publications = publications;
    }

    public Integer getVideos() {
        return videos;
    }

    public void setVideos(Integer videos) {
        this.videos = videos;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getRevisited() {
        return revisited;
    }

    public void setRevisited(Integer revisited) {
        this.revisited = revisited;
    }

    public Integer getBibleCourses() {
        return bibleCourses;
    }

    public void setBibleCourses(Integer bibleCourses) {
        this.bibleCourses = bibleCourses;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public boolean isAuxiliaryPioneer() {
        return auxiliaryPioneer;
    }

    public void setAuxiliaryPioneer(boolean auxiliaryPioneer) {
        this.auxiliaryPioneer = auxiliaryPioneer;
    }

    public String getAuxiliaryPioneerType() {
        if (this.auxiliaryPioneerType == null) {
            return "";
        }
        return auxiliaryPioneerType;
    }

    public void setAuxiliaryPioneerType(String auxiliaryPioneerType) {
        this.auxiliaryPioneerType = auxiliaryPioneerType;
    }

    public boolean isPreachingFifteenMinLess() {
        return preachingFifteenMinLess;
    }

    public void setPreachingFifteenMinLess(boolean preachingFifteenMinLess) {
        this.preachingFifteenMinLess = preachingFifteenMinLess;
    }

    public String getNotes() {
        return this.notes == null ? "" : this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}

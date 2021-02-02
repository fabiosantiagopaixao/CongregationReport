package br.com.congregationreport.models;

public class CongReport {

    private Integer reports;
    private Integer publications;
    private Integer videos;
    private Integer hours;
    private Integer revisited;
    private Integer courses;
    private Integer activePublishers;
    private Float assistanceAverage;
    public static final int ASSISTANCE = 1;
    public static final int TOTAL = 2;
    public static final int PUBLISHER = 3;
    public static final int AUXILIARY = 4;
    public static final int REGULAR = 5;
    public static final int DEAF = 6;

    public CongReport() {
        this.reports = 0;
        this.publications = 0;
        this.videos = 0;
        this.hours = 0;
        this.revisited = 0;
        this.courses = 0;
        this.activePublishers = 0;
        this.assistanceAverage = 0f;
    }

    public Integer getReports() {
        return reports;
    }

    public void setReports(Integer reports) {
        this.reports = reports;
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

    public Integer getCourses() {
        return courses;
    }

    public void setCourses(Integer courses) {
        this.courses = courses;
    }

    public Integer getActivePublishers() {
        return activePublishers;
    }

    public void setActivePublishers(Integer activePublishers) {
        this.activePublishers = activePublishers;
    }

    public Float getAssistanceAverage() {
        return assistanceAverage;
    }

    public void setAssistanceAverage(Float assistanceAverage) {
        this.assistanceAverage = assistanceAverage;
    }
}

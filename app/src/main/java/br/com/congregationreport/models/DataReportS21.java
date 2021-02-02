package br.com.congregationreport.models;

import java.util.Map;

public class DataReportS21 {

    private Publisher publisher;
    private String year;
    private Map<String, Report> reports;

    public DataReportS21(Publisher publisher, Map<String, Report> reports) {
        this.publisher = publisher;
        this.reports = reports;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Map<String, Report> getReports() {
        return reports;
    }

    public void setReports(Map<String, Report> reports) {
        this.reports = reports;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

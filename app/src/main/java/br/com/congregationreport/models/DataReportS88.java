package br.com.congregationreport.models;

import java.util.Map;

public class DataReportS88 {

    private String typeWeek;
    private int type;
    private Map<String, AssistanceReport> firstYear;
    private Map<String, AssistanceReport> previousYear;

    public DataReportS88() {
    }

    public Map<String, AssistanceReport> getFirstYear() {
        return firstYear;
    }

    public void setFirstYear(Map<String, AssistanceReport> firstYear) {
        this.firstYear = firstYear;
    }

    public Map<String, AssistanceReport> getPreviousYear() {
        return previousYear;
    }

    public void setPreviousYear(Map<String, AssistanceReport> previousYear) {
        this.previousYear = previousYear;
    }

    public String getTypeWeek() {
        return typeWeek;
    }

    public void setTypeWeek(String typeWeek) {
        this.typeWeek = typeWeek;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

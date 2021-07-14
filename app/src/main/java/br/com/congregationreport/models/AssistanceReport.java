package br.com.congregationreport.models;

import android.content.Context;

import br.com.congregationreport.R;
import br.com.congregationreport.util.UtilConstants;

public class AssistanceReport {

    private String month;
    private Integer numberMeeting;
    private Integer total;
    private Float average;

    public AssistanceReport() {
        this.numberMeeting = 0;
        this.total = 0;
        this.average = 0f;
    }

    public AssistanceReport(Context context, Integer year, int type, String typeWeek, String month) {
        this.numberMeeting = 0;
        this.total = 0;
        this.average = 0f;
        switch (type) {
            case CongReport.TOTAL:
                this.createDataTotal(context, year, typeWeek, month);
                break;
            case CongReport.DEAF:
                this.createDataDeaf(context, year, typeWeek, month);
                break;
        }
    }


    private void createDataTotal(Context context, Integer year, String typeWeek, String month) {
        try {
            if (year == 2020 && typeWeek.equals(UtilConstants.MID_WEEK)) {
                if (month.equals(context.getResources().getString(R.string.september))) {
                    this.numberMeeting = 4;
                    this.total = 101;
                } else if (month.equals(context.getResources().getString(R.string.october))) {
                    this.numberMeeting = 5;
                    this.total = 125;
                } else if (month.equals(context.getResources().getString(R.string.november))) {
                    this.numberMeeting = 3;
                    this.total = 65;
                } else if (month.equals(context.getResources().getString(R.string.december))) {
                    this.numberMeeting = 4;
                    this.total = 83;
                } else if (month.equals(context.getResources().getString(R.string.january))) {
                    this.numberMeeting = 3;
                    this.total = 75;
                } else if (month.equals(context.getResources().getString(R.string.february))) {
                    this.numberMeeting = 4;
                    this.total = 101;
                } else if (month.equals(context.getResources().getString(R.string.march))) {
                    this.numberMeeting = 4;
                    this.total = 108;
                } else if (month.equals(context.getResources().getString(R.string.april))) {
                    this.numberMeeting = 5;
                    this.total = 128;
                } else if (month.equals(context.getResources().getString(R.string.may))) {
                    this.numberMeeting = 3;
                    this.total = 88;
                } else if (month.equals(context.getResources().getString(R.string.june))) {
                    this.numberMeeting = 4;
                    this.total = 86;
                } else if (month.equals(context.getResources().getString(R.string.july))) {
                    this.numberMeeting = 5;
                    this.total = 138;
                } else if (month.equals(context.getResources().getString(R.string.august))) {
                    this.numberMeeting = 5;
                    this.total = 138;
                }


            } else if (year == 2020 && typeWeek.equals(UtilConstants.WEEKEND)) {
                if (month.equals(context.getResources().getString(R.string.september))) {
                    this.numberMeeting = 5;
                    this.total = 142;
                } else if (month.equals(context.getResources().getString(R.string.october))) {
                    this.numberMeeting = 4;
                    this.total = 88;
                } else if (month.equals(context.getResources().getString(R.string.november))) {
                    this.numberMeeting = 4;
                    this.total = 101;
                } else if (month.equals(context.getResources().getString(R.string.december))) {
                    this.numberMeeting = 4;
                    this.total = 103;
                } else if (month.equals(context.getResources().getString(R.string.january))) {
                    this.numberMeeting = 4;
                    this.total = 108;
                } else if (month.equals(context.getResources().getString(R.string.february))) {
                    this.numberMeeting = 3;
                    this.total = 73;
                } else if (month.equals(context.getResources().getString(R.string.march))) {
                    this.numberMeeting = 5;
                    this.total = 128;
                } else if (month.equals(context.getResources().getString(R.string.april))) {
                    this.numberMeeting = 4;
                    this.total = 108;
                } else if (month.equals(context.getResources().getString(R.string.may))) {
                    this.numberMeeting = 5;
                    this.total = 138;
                } else if (month.equals(context.getResources().getString(R.string.june))) {
                    this.numberMeeting = 4;
                    this.total = 107;
                } else if (month.equals(context.getResources().getString(R.string.july))) {
                    this.numberMeeting = 4;
                    this.total = 111;
                } else if (month.equals(context.getResources().getString(R.string.august))) {
                    this.numberMeeting = 4;
                    this.total = 104;
                }
            }
            this.average = Float.parseFloat(this.total.toString()) / this.numberMeeting;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createDataDeaf(Context context, Integer year, String typeWeek, String month) {
        try {
            if (year == 2020 && typeWeek.equals(UtilConstants.MID_WEEK)) {
                if (month.equals(context.getResources().getString(R.string.september))) {
                    this.numberMeeting = 4;
                    this.total = 39;
                } else if (month.equals(context.getResources().getString(R.string.october))) {
                    this.numberMeeting = 5;
                    this.total = 37;
                } else if (month.equals(context.getResources().getString(R.string.november))) {
                    this.numberMeeting = 3;
                    this.total = 12;
                } else if (month.equals(context.getResources().getString(R.string.december))) {
                    this.numberMeeting = 4;
                    this.total = 22;
                } else if (month.equals(context.getResources().getString(R.string.january))) {
                    this.numberMeeting = 3;
                    this.total = 20;
                } else if (month.equals(context.getResources().getString(R.string.february))) {
                    this.numberMeeting = 4;
                    this.total = 26;
                } else if (month.equals(context.getResources().getString(R.string.march))) {
                    this.numberMeeting = 4;
                    this.total = 38;
                } else if (month.equals(context.getResources().getString(R.string.april))) {
                    this.numberMeeting = 5;
                    this.total = 38;
                } else if (month.equals(context.getResources().getString(R.string.may))) {
                    this.numberMeeting = 3;
                    this.total = 22;
                } else if (month.equals(context.getResources().getString(R.string.june))) {
                    this.numberMeeting = 4;
                    this.total = 31;
                } else if (month.equals(context.getResources().getString(R.string.july))) {
                    this.numberMeeting = 5;
                    this.total = 51;
                } else if (month.equals(context.getResources().getString(R.string.august))) {
                    this.numberMeeting = 5;
                    this.total = 50;
                }

            } else if (year == 2020 && typeWeek.equals(UtilConstants.WEEKEND)) {
                if (month.equals(context.getResources().getString(R.string.september))) {
                    this.numberMeeting = 5;
                    this.total = 57;
                } else if (month.equals(context.getResources().getString(R.string.october))) {
                    this.numberMeeting = 4;
                    this.total = 32;
                } else if (month.equals(context.getResources().getString(R.string.november))) {
                    this.numberMeeting = 4;
                    this.total = 27;
                } else if (month.equals(context.getResources().getString(R.string.december))) {
                    this.numberMeeting = 4;
                    this.total = 30;
                } else if (month.equals(context.getResources().getString(R.string.january))) {
                    this.numberMeeting = 4;
                    this.total = 34;
                } else if (month.equals(context.getResources().getString(R.string.february))) {
                    this.numberMeeting = 3;
                    this.total = 26;
                } else if (month.equals(context.getResources().getString(R.string.march))) {
                    this.numberMeeting = 5;
                    this.total = 42;
                } else if (month.equals(context.getResources().getString(R.string.april))) {
                    this.numberMeeting = 4;
                    this.total = 34;
                } else if (month.equals(context.getResources().getString(R.string.may))) {
                    this.numberMeeting = 5;
                    this.total = 40;
                } else if (month.equals(context.getResources().getString(R.string.june))) {
                    this.numberMeeting = 4;
                    this.total = 34;
                } else if (month.equals(context.getResources().getString(R.string.july))) {
                    this.numberMeeting = 4;
                    this.total = 41;
                } else if (month.equals(context.getResources().getString(R.string.august))) {
                    this.numberMeeting = 4;
                    this.total = 37;
                }
            }
            this.average = Float.parseFloat(this.total.toString()) / this.numberMeeting;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getNumberMeeting() {
        return numberMeeting;
    }

    public void setNumberMeeting(Integer numberMeeting) {
        this.numberMeeting = numberMeeting;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }
}

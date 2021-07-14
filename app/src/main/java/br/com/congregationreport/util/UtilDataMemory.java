package br.com.congregationreport.util;

import android.content.Context;

import br.com.congregationreport.db.dao.AssistanceDAO;
import br.com.congregationreport.db.dao.GroupDAO;
import br.com.congregationreport.db.dao.AppDAO;
import br.com.congregationreport.db.dao.PublisherDAO;
import br.com.congregationreport.db.dao.ReportDAO;
import br.com.congregationreport.models.App;
import br.com.congregationreport.models.Publisher;
import br.com.congregationreport.models.Setting;
import br.com.congregationreport.ui.assistance.AssistanceActivity;
import br.com.congregationreport.ui.report.ReportActivity;

public class UtilDataMemory {

    public static Setting setting;
    public static Publisher publisher;
    public static GroupDAO groupDAO;
    public static PublisherDAO publisherDAO;
    public static AppDAO appDAO;
    public static ReportDAO reportDAO;
    public static AssistanceDAO assistanceDAO;
    public static ReportActivity reportActivity;
    public static AssistanceActivity assistanceActivity;

    public static PublisherDAO getPublisherDAO(Context context) {
        if (publisherDAO == null) {
            publisherDAO = new PublisherDAO(context);
        }
        return publisherDAO;
    }

    public static GroupDAO getGroupDAO(Context context) {
        if (groupDAO == null) {
            groupDAO = new GroupDAO(context);
        }
        return groupDAO;
    }

    public static AppDAO getLocalDAO(Context context) {
        if (appDAO == null) {
            appDAO = new AppDAO(context);
        }
        return appDAO;
    }

    public static ReportDAO getReportDAO(Context context) {
        if (reportDAO == null) {
            reportDAO = new ReportDAO(context);
        }
        return reportDAO;
    }

    public static AssistanceDAO getAssistanceDAO(Context context) {
        if (assistanceDAO == null) {
            assistanceDAO = new AssistanceDAO(context);
        }
        return assistanceDAO;
    }


}

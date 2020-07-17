package br.com.congregationreport.util;

import android.content.Context;

import br.com.congregationreport.db.dao.GroupDAO;
import br.com.congregationreport.db.dao.PublisherDAO;
import br.com.congregationreport.db.dao.SettingDAO;
import br.com.congregationreport.db.dao.UserDAO;
import br.com.congregationreport.models.User;

public class UtilDataMemory {

    public static User userConectded;
    public static UserDAO userDAO;
    public static GroupDAO groupDAO;
    public static PublisherDAO publisherDAO;

    public static UserDAO getUserDAO(Context context) {
        if (userDAO == null) {
            userDAO = new UserDAO(context);
        }
        return userDAO;
    }

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
}

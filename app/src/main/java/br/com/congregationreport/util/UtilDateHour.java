package br.com.congregationreport.util;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import br.com.congregationreport.R;
import br.com.congregationreport.enumerator.EnumMonth;

/**
 * Classe principal para formatar data e hora
 *
 * <table>
 * <tr>
 * <th>Autor</th>
 * <th>Empresa</th>
 * <th>Data</th>
 * </tr>
 * <tr>
 * <th>Fábio Santiago</th>
 * <th>Shopseg Sistemas</th>
 * <th>29/11/2016</th>
 * </tr>
 * </table>
 */
public class UtilDateHour {

    public static final String DATE_HOUR_DEFAULT_CALENDAR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_HOUR = "dd/MM/yyyy HH:mm:ss";
    public static final String HOUR = "HH:mm:ss";
    public static final String DATE = "dd/MM/yyyy";
    public static final String YEAR = "yyyy";
    public static final String DIA = "dd";
    public static final String MONTH = "MM";
    public static final String MONTH_ONE_DIGIT = "M";
    public static final String MONTH_YEAR = "MM/yyyy";
    public static final String DATE_PG = "yyyy-MM-dd";
    public static final String MIDNIGHT = "00:00:00";

    public UtilDateHour() {
    }

    public static Date addDias(Date data, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(5, dias);
        return calendar.getTime();
    }

    public static Date addHoras(Date data, int horas) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(11, horas);
        return calendar.getTime();
    }

    public static Date addMilissegundos(Date data, int milissegundos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(14, milissegundos);
        return calendar.getTime();
    }

    public static Date getDataHoraAtual() {
        return GregorianCalendar.getInstance().getTime();
    }


    public static String formataDataHora(String formato, Date data) {
        if (formato == null) {
            formato = "ddMMyyyyHHmmss";
        }
        if (data == null) {
            data = getDataHoraAtual();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        return formatter.format(data);
    }

    public static String formataDataHora(String formato, Date data, TimeZone timeZone) {
        if (formato == null) {
            formato = "ddMMyyyyHHmmss";
        }
        if (data == null) {
            data = getDataHoraAtual();
        }
        if (timeZone == null) {
            timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        formatter.setTimeZone(timeZone);
        return formatter.format(data);
    }

    public static Date stringToDate(String formato, String data)
            throws ParseException {
        if (formato == null) {
            formato = "ddMMyyyyHHmmss";
        }
        if (data == null) {
            data = formataDataHora(formato, getDataHoraAtual());
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        return formatter.parse(data);
    }

    public static Date stringToDate(String formato, String data, TimeZone timeZone)
            throws ParseException {
        if (formato == null) {
            formato = "ddMMyyyyHHmmss";
        }
        if (data == null) {
            data = formataDataHora(formato, getDataHoraAtual());
        }
        if (timeZone == null) {
            timeZone = TimeZone.getTimeZone("America/Sao_Paulo");
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        formatter.setTimeZone(timeZone);
        return formatter.parse(data);
    }

    public static boolean isAnoBissexto(int ano) {
        if (ano % 100 != 0) {
            return ano % 4 == 0;
        }
        return ano % 400 == 0;
    }

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String getCurrentYear() {
        String currentYear = "";
        try {
            Date date = new Date();

            String year = UtilDateHour.formataDataHora(UtilDateHour.YEAR, date);
            String month = UtilDateHour.formataDataHora(UtilDateHour.MONTH, date);

            switch (month) {
                case "01":
                case "02":
                case "03":
                case "04":
                case "05":
                case "06":
                case "07":
                case "08":
                    currentYear = year;
                    break;
                case "09":
                case "10":
                case "11":
                case "12":
                    currentYear = (year + 1);
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return currentYear;
    }

    public static String getCurrentMonthForReport() {
        String currentMonth = "";
        try {
            Date date = new Date();
            Integer result = Integer.parseInt(UtilDateHour.formataDataHora(UtilDateHour.MONTH_ONE_DIGIT, date));
            Integer monthResult = result == 1 ? 12 : result - 1;
            String month = monthResult > 9 ? monthResult.toString() : "0" + monthResult.toString();
            currentMonth = EnumMonth.getMonth(month);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return currentMonth;
    }

    public static String getMonthReportByValue(Context context, String value) {
        String currentMonth = "";
        try {
            switch (value) {
                case "01":
                    currentMonth = context.getResources().getString(R.string.january);
                    break;
                case "02":
                    currentMonth = context.getResources().getString(R.string.february);
                    break;
                case "03":
                    currentMonth = context.getResources().getString(R.string.march);
                    break;
                case "04":
                    currentMonth = context.getResources().getString(R.string.april);
                    break;
                case "05":
                    currentMonth = context.getResources().getString(R.string.may);
                    break;
                case "06":
                    currentMonth = context.getResources().getString(R.string.june);
                    break;
                case "07":
                    currentMonth = context.getResources().getString(R.string.july);
                    break;
                case "08":
                    currentMonth = context.getResources().getString(R.string.august);
                    break;
                case "09":
                    currentMonth = context.getResources().getString(R.string.september);
                    break;
                case "10":
                    currentMonth = context.getResources().getString(R.string.october);
                    break;
                case "11":
                    currentMonth = context.getResources().getString(R.string.november);
                    break;
                case "12":
                    currentMonth = context.getResources().getString(R.string.december);
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return currentMonth;
    }

    public static String getMonthReportByName(Context context, String name) {
        String currentMonth = "";
        try {
            switch (name) {
                case "JANUARY":
                    currentMonth = context.getResources().getString(R.string.january);
                    break;
                case "FEBRUARY":
                    currentMonth = context.getResources().getString(R.string.february);
                    break;
                case "MARCH":
                    currentMonth = context.getResources().getString(R.string.march);
                    break;
                case "APRIL":
                    currentMonth = context.getResources().getString(R.string.april);
                    break;
                case "MAY":
                    currentMonth = context.getResources().getString(R.string.may);
                    break;
                case "JUNE":
                    currentMonth = context.getResources().getString(R.string.june);
                    break;
                case "JULY":
                    currentMonth = context.getResources().getString(R.string.july);
                    break;
                case "AUGUST":
                    currentMonth = context.getResources().getString(R.string.august);
                    break;
                case "SEPTEMBER":
                    currentMonth = context.getResources().getString(R.string.september);
                    break;
                case "OCTOBER":
                    currentMonth = context.getResources().getString(R.string.october);
                    break;
                case "NOVEMBER":
                    currentMonth = context.getResources().getString(R.string.november);
                    break;
                case "DECEMBER":
                    currentMonth = context.getResources().getString(R.string.december);
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return currentMonth;
    }

    public static String getMonthBySelected(Context context, String name) {
        String currentMonth = "";
        try {

            if (name.equals(context.getResources().getString(R.string.january))) {
                currentMonth = EnumMonth.getMonth("01");
            }
            if (name.equals(context.getResources().getString(R.string.february))) {
                currentMonth = EnumMonth.getMonth("02");
            }
            if (name.equals(context.getResources().getString(R.string.march))) {
                currentMonth = EnumMonth.getMonth("03");
            }
            if (name.equals(context.getResources().getString(R.string.april))) {
                currentMonth = EnumMonth.getMonth("04");
            }
            if (name.equals(context.getResources().getString(R.string.may))) {
                currentMonth = EnumMonth.getMonth("05");
            }
            if (name.equals(context.getResources().getString(R.string.june))) {
                currentMonth = EnumMonth.getMonth("06");
            }
            if (name.equals(context.getResources().getString(R.string.july))) {
                currentMonth = EnumMonth.getMonth("07");
            }
            if (name.equals(context.getResources().getString(R.string.august))) {
                currentMonth = EnumMonth.getMonth("08");
            }
            if (name.equals(context.getResources().getString(R.string.september))) {
                currentMonth = EnumMonth.getMonth("09");
            }
            if (name.equals(context.getResources().getString(R.string.october))) {
                currentMonth = EnumMonth.getMonth("10");
            }
            if (name.equals(context.getResources().getString(R.string.november))) {
                currentMonth = EnumMonth.getMonth("11");
            }
            if (name.equals(context.getResources().getString(R.string.december))) {
                currentMonth = EnumMonth.getMonth("12");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return currentMonth;
    }

    public static String getCurrentMonth(Context context) {
        String currentMonth = "";
        try {
            Date date = new Date();
            String month = UtilDateHour.formataDataHora(UtilDateHour.MONTH, date);

            switch (month) {
                case "01":
                    currentMonth = context.getResources().getString(R.string.january);
                    break;
                case "02":
                    currentMonth = context.getResources().getString(R.string.february);
                    break;
                case "03":
                    currentMonth = context.getResources().getString(R.string.march);
                    break;
                case "04":
                    currentMonth = context.getResources().getString(R.string.april);
                    break;
                case "05":
                    currentMonth = context.getResources().getString(R.string.may);
                    break;
                case "06":
                    currentMonth = context.getResources().getString(R.string.june);
                    break;
                case "07":
                    currentMonth = context.getResources().getString(R.string.july);
                    break;
                case "08":
                    currentMonth = context.getResources().getString(R.string.august);
                    break;
                case "09":
                    currentMonth = context.getResources().getString(R.string.september);
                    break;
                case "10":
                    currentMonth = context.getResources().getString(R.string.october);
                    break;
                case "11":
                    currentMonth = context.getResources().getString(R.string.november);
                    break;
                case "12":
                    currentMonth = context.getResources().getString(R.string.december);
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return currentMonth;
    }

    public static String getMonthByDate(Context context, Date date) {
        String currentMonth = "";
        try {
            String month = UtilDateHour.formataDataHora(UtilDateHour.MONTH, date);
            currentMonth = EnumMonth.getMonth(month);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return currentMonth;
    }


    public static String getTypeAssistanceByDate(Context context, Date date, boolean label) {
        String currentType = "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            calendar.getMinimalDaysInFirstWeek();
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            switch (dayOfWeek) {
                case Calendar.MONDAY:
                case Calendar.TUESDAY:
                case Calendar.WEDNESDAY:
                case Calendar.THURSDAY:
                case Calendar.FRIDAY:
                    if (label) {
                        currentType = context.getResources().getString(R.string.type_middle_week_label);
                    } else {
                        currentType = UtilConstants.MID_WEEK;
                    }
                    break;
                case Calendar.SATURDAY:
                case Calendar.SUNDAY:
                    if (label) {
                        currentType = context.getResources().getString(R.string.type_weekend_label);
                    } else {
                        currentType = UtilConstants.WEEKEND;
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return currentType;
    }


    public static int getNumberWeekByDate(Date date) {
        Integer week = 1;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            week = calendar.get(Calendar.WEEK_OF_MONTH);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    week = week - 1;
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return week;
    }


}

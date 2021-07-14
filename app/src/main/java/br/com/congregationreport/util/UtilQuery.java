package br.com.congregationreport.util;

public class UtilQuery {

    public static String getQueryFindReportTotal() {
        return "SELECT \n" +
                "\t\n" +
                "\treport.id,\n" +
                "\treport.id_publisher,\n" +
                "\treport.year,\n" +
                "\treport.month,\n" +
                "\treport.publications,\n" +
                "\treport.videos,\n" +
                "\treport.hours,\n" +
                "\treport.revisited,\n" +
                "\treport.bible_couses,\n" +
                "\treport.credit,\n" +
                "\treport.auxiliary_pioneer,\n" +
                "\treport.auxiliary_pioneer_type,\n" +
                "\treport.preaching_fifteen_min_less,\n" +
                "\treport.notes\n" +
                "\n" +
                "FROM report\n" +
                "\n" +
                "WHERE \n" +
                "\treport.month = '$P{MONTH}'\n" +
                "\tAND report.year = $P{YEAR}\n" +
                "\tAND report.hours > 0\n" +
                "\t\n" +
                "ORDER BY report.id ASC";
    }

    public static String getQueryFindReportByPublisher() {
        return "SELECT \n" +
                "\t\n" +
                "\treport.id,\n" +
                "\treport.id_publisher,\n" +
                "\treport.year,\n" +
                "\treport.month,\n" +
                "\treport.publications,\n" +
                "\treport.videos,\n" +
                "\treport.hours,\n" +
                "\treport.revisited,\n" +
                "\treport.bible_couses,\n" +
                "\treport.credit,\n" +
                "\treport.auxiliary_pioneer,\n" +
                "\treport.auxiliary_pioneer_type,\n" +
                "\treport.preaching_fifteen_min_less,\n" +
                "\treport.notes\n" +
                "\n" +
                "FROM report\n" +
                "INNER JOIN publisher ON publisher.id = report.id_publisher\n" +
                "\n" +
                "WHERE \n" +
                "\treport.month = '$P{MONTH}'\n" +
                "\tAND report.year = $P{YEAR}\n" +
                "\tAND publisher.publisher = 1\n" +
                "\tAND report.hours > 0\n" +
                "\tAND report.auxiliary_pioneer = 0\n" +
                "\t\n" +
                "ORDER BY publisher.id ASC";
    }

    public static String getQueryFindReportByAuxiliary() {
        return "SELECT \n" +
                "\t\n" +
                "\treport.id,\n" +
                "\treport.id_publisher,\n" +
                "\treport.year,\n" +
                "\treport.month,\n" +
                "\treport.publications,\n" +
                "\treport.videos,\n" +
                "\treport.hours,\n" +
                "\treport.revisited,\n" +
                "\treport.bible_couses,\n" +
                "\treport.credit,\n" +
                "\treport.auxiliary_pioneer,\n" +
                "\treport.auxiliary_pioneer_type,\n" +
                "\treport.preaching_fifteen_min_less,\n" +
                "\treport.notes\n" +
                "\n" +
                "FROM report\n" +
                "\n" +
                "WHERE \n" +
                "\treport.month = '$P{MONTH}'\n" +
                "\tAND report.year = $P{YEAR}\n" +
                "\tAND report.hours > 0\n" +
                "\tAND report.auxiliary_pioneer = 1\n" +
                "\t\n" +
                "ORDER BY report.id ASC";
    }

    public static String getQueryFindReportByRegular() {
        return "SELECT \n" +
                "\t\n" +
                "\treport.id,\n" +
                "\treport.id_publisher,\n" +
                "\treport.year,\n" +
                "\treport.month,\n" +
                "\treport.publications,\n" +
                "\treport.videos,\n" +
                "\treport.hours,\n" +
                "\treport.revisited,\n" +
                "\treport.bible_couses,\n" +
                "\treport.credit,\n" +
                "\treport.auxiliary_pioneer,\n" +
                "\treport.auxiliary_pioneer_type,\n" +
                "\treport.preaching_fifteen_min_less,\n" +
                "\treport.notes\n" +
                "\n" +
                "FROM report\n" +
                "INNER JOIN publisher ON publisher.id = report.id_publisher\n" +
                "\n" +
                "WHERE \n" +
                "\treport.month = '$P{MONTH}'\n" +
                "\tAND report.year = $P{YEAR}\n" +
                "\tAND publisher.regular_pioneer = 1\n" +
                "\tAND report.hours > 0 \n" +
                "\t\n" +
                "ORDER BY publisher.id ASC";
    }


}

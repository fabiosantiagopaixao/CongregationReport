package br.com.congregationreport.enumerator;

public enum EnumMonth {

    SEPTEMBER("SEPTEMBER", "09"),
    OCTOBER("OCTOBER", "10"),
    NOVEMBER("NOVEMBER", "11"),
    DECEMBER("DECEMBER", "12"),
    JANUARY("JANUARY", "01"),
    FEBRUARY("FEBRUARY", "02"),
    MARCH("MARCH", "03"),
    APRIL("APRIL", "04"),
    MAY("MAY", "05"),
    JUNE("JUNE", "06"),
    JULY("JULY", "07"),
    AUGUST("AUGUST", "08");

    private String name;
    private String value;

    EnumMonth(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static String getMonth(String value) {
        String month = "";
        for (EnumMonth enumM : EnumMonth.values()) {
            if (enumM.value.equals(value)) {
                month = enumM.name;
                break;
            }
        }
        return month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

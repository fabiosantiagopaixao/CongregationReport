package br.com.congregationreport.ui.report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.congregationreport.R;
import br.com.congregationreport.db.dao.ReportDAO;
import br.com.congregationreport.enumerator.EnumMonth;
import br.com.congregationreport.models.Report;
import br.com.congregationreport.ui.adapters.ExpandableListAdapterReport;
import br.com.congregationreport.ui.congregationreports.CongregationReportActivity;
import br.com.congregationreport.util.Util;
import br.com.congregationreport.util.UtilDataMemory;
import br.com.congregationreport.util.UtilDateHour;

public class ReportActivity extends AppCompatActivity implements Serializable {

    private ExpandableListView elvData;
    private ReportDAO reportDAO;
    private List<String> lstMonths;
    private HashMap<String, List<Report>> lstItems;
    private ExpandableListAdapterReport adapterItemReport;
    private TextView txtTitle;
    private TextView txtSubTitle;
    private TextView txtMessage;
    private Spinner spYear;
    private String yearSelected = "2021";
    private String[] years = {"2021", "2020"};
    public static String PUBLISHER = "PUBLISHER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        this.init();
    }


    public void init() {
        try {
            this.reportDAO = UtilDataMemory.getReportDAO(this);
            this.elvData = (ExpandableListView) this.findViewById(R.id.lvData);
            this.txtTitle = (TextView) this.findViewById(R.id.txtTitle);
            this.txtSubTitle = (TextView) this.findViewById(R.id.txtSubTitle);
            this.txtMessage = (TextView) this.findViewById(R.id.txtMessage);
            this.spYear = (Spinner) this.findViewById(R.id.spYear);
            this.createTitle();
            this.createData();
            this.createDataSpinner();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTitle() {
        try {
            String title = this.getResources().getString(R.string.your_service);
            txtTitle.setText(title);
        } catch (Exception ex) {

        }
    }

    private void createDataSpinner() {
        try {
            this.spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    yearSelected = years[position];
                    createData();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.simple_spinner_item, this.years);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            this.spYear.setAdapter(arrayAdapter);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void createData() {
        try {
            this.lstItems = new HashMap<>();

            // Group
            this.lstMonths = new ArrayList<>();

            Integer publications = 0, hours = 0,
                    videos = 0, revisited = 0, bibleCourses = 0, credit = 0;

            for (int i = 0; i < EnumMonth.values().length; i++) {
                EnumMonth currentMonth = EnumMonth.values()[i];

                // Searche the report
                Report report = this.reportDAO.getReport(UtilDataMemory.publisher, currentMonth.getName(), this.yearSelected);
                if (report != null) {
                    String nameMonth = UtilDateHour.getMonthReportByValue(this, currentMonth.getValue());
                    this.lstMonths.add(nameMonth);
                    List<Report> items = new ArrayList<>();

                    publications = publications + (report.getPublications() == null ? 0 : report.getPublications());
                    hours = hours + (report.getHours() == null ? 0 : report.getHours());
                    videos = videos + (report.getVideos() == null ? 0 : report.getVideos());
                    revisited = revisited + (report.getRevisited() == null ? 0 : report.getRevisited());
                    bibleCourses = bibleCourses + (report.getBibleCourses() == null ? 0 : report.getBibleCourses());
                    credit = credit + (report.getCredit() == null ? 0 : report.getCredit());

                    items.add(report);
                    this.lstItems.put(nameMonth, items);
                }
            }
            if (this.lstMonths.size() > 0) {
                List<Report> items = new ArrayList<>();
                Report reportTotal = new Report();
                reportTotal.setPublications(publications);

                reportTotal.setPublications(publications);
                reportTotal.setHours(hours);
                reportTotal.setVideos(videos);
                reportTotal.setRevisited(revisited);
                reportTotal.setBibleCourses(bibleCourses);
                reportTotal.setCredit(credit);

                items.add(reportTotal);
                this.lstMonths.add(this.getResources().getString(R.string.total));
                this.lstItems.put(this.getResources().getString(R.string.total), items);
            }
            this.setData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setData() {
        try {
            if (this.lstMonths.size() == 0) {
                this.txtSubTitle.setVisibility(View.VISIBLE);
                this.txtMessage.setVisibility(View.GONE);
                this.elvData.setAdapter((BaseExpandableListAdapter) null);
            } else {
                this.txtSubTitle.setVisibility(View.GONE);
                this.txtMessage.setVisibility(View.VISIBLE);
                this.adapterItemReport = new ExpandableListAdapterReport(
                        this,
                        this.lstMonths,
                        this.lstItems
                );

                this.elvData.setAdapter(this.adapterItemReport);
                this.elvData.expandGroup(this.lstMonths.size() - 1);
                this.elvData.requestFocus();
            }
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    private void openReporS21() {
        try {
            Intent it = new Intent(this, CongregationReportActivity.class);
            Bundle extras = new Bundle();
            extras.putBoolean(PUBLISHER, true);
            it.putExtras(extras);
            startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addReport() {
        try {
            String month = UtilDateHour.getCurrentMonthForReport();
            if (this.validateDate()) {
                String currentYear = UtilDateHour.getCurrentYear();

                Report report = this.reportDAO.getReport(UtilDataMemory.publisher, month, currentYear);
                if (report == null) {

                    if (Util.isDeviceOnline(this)) {
                        Intent it = new Intent(ReportActivity.this, AddReportActivity.class);
                        UtilDataMemory.reportActivity = this;
                        ReportActivity.this.startActivity(it);
                    } else {
                        Util.createMessageAlert(
                                this,
                                this.getResources().getString(R.string.msg_no_internet_report)
                        );
                    }


                } else {
                    Util.createMessageAlert(
                            ReportActivity.this,
                            this.getResources().getString(R.string.msg_cannot_resubimit_report)
                                    + UtilDateHour.getMonthReportByName(this, month)
                    );
                }
            } else {
                Util.createMessageAlert(
                        this,
                        this.getResources().getString(R.string.msg_cannot_send_on_this_date)
                                + UtilDateHour.getMonthReportByName(this, month)
                                + this.getResources().getString(R.string.msg_cannot_send_on_this_date2)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateDate() {
        try {
            Date currentDate =
                    UtilDateHour.stringToDate(
                            UtilDateHour.DATE_HOUR,
                            UtilDateHour.formataDataHora(
                                    UtilDateHour.DATE, new Date()
                            ) + " " + UtilDateHour.MIDNIGHT
                    );
            Integer month = Integer.parseInt(UtilDateHour.formataDataHora(UtilDateHour.MONTH, new Date()));
            String year = UtilDateHour.formataDataHora(UtilDateHour.YEAR, currentDate);

            String dateFirstDayString = "01/" + month + "/" + year + " " + UtilDateHour.MIDNIGHT;
            String dateTenDayString = "10/" + month + "/" + year + " " + UtilDateHour.MIDNIGHT;

            Date dataFirstDay = UtilDateHour.stringToDate(UtilDateHour.DATE, dateFirstDayString);
            Date dataFifteentDay = UtilDateHour.stringToDate(UtilDateHour.DATE, dateTenDayString);

            return currentDate.getTime() >= dataFirstDay.getTime() && currentDate.getTime() <= dataFifteentDay.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assistance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.itemAdd:
                this.addReport();
                break;
            case R.id.itemS21:
                this.openReporS21();
                break;
            default:
                this.finish();
        }
        return true;
    }


}

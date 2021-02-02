package br.com.congregationreport.ui.assistance;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.congregationreport.R;
import br.com.congregationreport.db.dao.AssistanceDAO;
import br.com.congregationreport.enumerator.EnumMonth;
import br.com.congregationreport.models.Assistance;
import br.com.congregationreport.ui.adapters.ExpandableListAdapterAssistance;
import br.com.congregationreport.util.Util;
import br.com.congregationreport.util.UtilDataMemory;
import br.com.congregationreport.util.UtilDateHour;
import br.com.congregationreport.util.UtilFormatte;

public class AssistanceActivity extends AppCompatActivity {

    private ExpandableListView elvData;
    private AssistanceDAO assistanceDAO;
    private List<String> lstMonths;
    private HashMap<String, List<Assistance>> lstItems;
    private ExpandableListAdapterAssistance adapterItemAssistance;
    private TextView txtTitle;
    private TextView txtSubTitle;
    private RelativeLayout llFooter;
    private TextView txtAmountDeafYear;
    private TextView txtAmountTotalYear;
    private Spinner spYear;
    private String yearSelected = "2021";
    private String[] years = {"2021", "2020"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistance);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        this.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.itemAdd:
                this.addAssistance();
                break;
            default:
                this.finish();
        }
        return true;
    }

    private void addAssistance() {
        try {
            if (Util.isDeviceOnline(this)) {
                Intent it = new Intent(AssistanceActivity.this, AddAssistanceActivity.class);
                UtilDataMemory.assistanceActivity = this;
                AssistanceActivity.this.startActivity(it);
            } else {
                Util.createMessageAlert(
                        this,
                        this.getResources().getString(R.string.msg_no_internet_assistance)
                );
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void init() {
        try {
            this.assistanceDAO = UtilDataMemory.getAssistanceDAO(this);
            this.elvData = (ExpandableListView) this.findViewById(R.id.lvData);
            this.txtTitle = (TextView) this.findViewById(R.id.txtTitle);
            this.txtSubTitle = (TextView) this.findViewById(R.id.txtSubTitle);
            this.llFooter = (RelativeLayout) this.findViewById(R.id.llFooter);
            this.txtAmountDeafYear = (TextView) this.findViewById(R.id.txtAmountDeafYear);
            this.txtAmountTotalYear = (TextView) this.findViewById(R.id.txtAmountTotalYear);
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
            String title = this.getResources().getString(R.string.your_assistance);
            txtTitle.setText(title);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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

            Float amountDeaf = 0f;
            Float amountTotal = 0f;
            Integer itemsTotal = 0;

            for (int i = 0; i < EnumMonth.values().length; i++) {
                EnumMonth enumMonth = EnumMonth.values()[i];
                String nameMonth = UtilDateHour.getMonthReportByValue(this, enumMonth.getValue());

                // Searche the report
                List<Assistance> assistances =
                        this.assistanceDAO.getAssistancePerMonthAndYear(
                                enumMonth.getName(),
                                this.yearSelected
                        );
                Float amountDeafM = 0f;
                Float amountTotalM = 0f;
                if (assistances.size() > 0) {
                    this.lstMonths.add(nameMonth);
                    for (int count = 0; count < assistances.size(); count++) {
                        itemsTotal++;
                        Assistance assistance = assistances.get(count);

                        Date date = UtilDateHour.stringToDate(UtilDateHour.DATE_HOUR, assistance.getDate() + " 00:00:00");
                        Integer order = UtilDateHour.getNumberWeekByDate(date);
                        assistance.setOrder(this.getResources().getString(R.string.label_abr_week) + order.toString());

                        amountDeafM = amountDeafM + (assistance.getAmountDeaf() == null ? 0 : assistance.getAmountDeaf());
                        amountTotalM = amountTotalM + (assistance.getAmountTotal() == null ? 0 : assistance.getAmountTotal());

                        amountDeaf = amountDeaf + (assistance.getAmountDeaf() == null ? 0 : assistance.getAmountDeaf());
                        amountTotal = amountTotal + (assistance.getAmountTotal() == null ? 0 : assistance.getAmountTotal());
                    }
                    this.lstItems.put(nameMonth, assistances);
                }
                // Media
                if (assistances.size() > 1) {
                    Assistance assistance = new Assistance();
                    assistance.setOrder(this.getResources().getString(R.string.average_a));
                    assistance.setAmountDeafM(UtilFormatte.getFormateRemoveZero(amountDeafM / assistances.size()));
                    assistance.setAmountTotalM(UtilFormatte.getFormateRemoveZero(amountTotalM / assistances.size()));
                    assistances.add(assistance);
                }
            }
            if (this.lstMonths.size() > 0) {
                Assistance assistanceTotal = new Assistance();
                assistanceTotal.setAmountDeaf(Integer.parseInt(amountDeaf.toString().replace(".0", "")));
                assistanceTotal.setAmountTotal(Integer.parseInt(amountTotal.toString().replace(".0", "")));
                // ----
                assistanceTotal.setAmountDeafM(UtilFormatte.getFormateRemoveZero(amountDeaf / itemsTotal));
                assistanceTotal.setAmountTotalM(UtilFormatte.getFormateRemoveZero(amountTotal / itemsTotal));

                this.txtAmountDeafYear.setText(assistanceTotal.getAmountDeaf() + " | " + assistanceTotal.getAmountDeafM());
                this.txtAmountTotalYear.setText(assistanceTotal.getAmountTotal() + " | " + assistanceTotal.getAmountTotalM());
                this.llFooter.setVisibility(View.VISIBLE);
            } else {
                this.llFooter.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.setData();
    }

    private void setData() {
        try {
            if (this.lstMonths.size() == 0) {
                this.txtSubTitle.setVisibility(View.VISIBLE);
                this.elvData.setAdapter((BaseExpandableListAdapter) null);
            } else {
                this.txtSubTitle.setVisibility(View.GONE);
                this.adapterItemAssistance = new ExpandableListAdapterAssistance(
                        this,
                        this.lstMonths,
                        this.lstItems
                );

                this.elvData.setAdapter(this.adapterItemAssistance);
                this.elvData.requestFocus();
            }
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }
}
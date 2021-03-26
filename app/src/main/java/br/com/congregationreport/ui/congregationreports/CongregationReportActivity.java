package br.com.congregationreport.ui.congregationreports;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.congregationreport.R;
import br.com.congregationreport.db.dao.AssistanceDAO;
import br.com.congregationreport.db.dao.GroupDAO;
import br.com.congregationreport.db.dao.PublisherDAO;
import br.com.congregationreport.db.dao.QueryDAO;
import br.com.congregationreport.db.dao.ReportDAO;
import br.com.congregationreport.enumerator.EnumMonth;
import br.com.congregationreport.models.AssistanceReport;
import br.com.congregationreport.models.CongReport;
import br.com.congregationreport.models.DataCongregation;
import br.com.congregationreport.models.DataReportS21;
import br.com.congregationreport.models.DataReportS88;
import br.com.congregationreport.models.Group;
import br.com.congregationreport.models.Params;
import br.com.congregationreport.models.Publisher;
import br.com.congregationreport.models.Report;
import br.com.congregationreport.task.BaseTask;
import br.com.congregationreport.task.TaskRunner;
import br.com.congregationreport.ui.adapters.SpinnerPublisherAdapter;
import br.com.congregationreport.ui.report.ReportActivity;
import br.com.congregationreport.util.Util;
import br.com.congregationreport.util.UtilConstants;
import br.com.congregationreport.util.UtilDataMemory;
import br.com.congregationreport.util.UtilDateHour;
import br.com.congregationreport.util.UtilFormatte;

public class CongregationReportActivity extends AppCompatActivity {

    private RadioGroup rgOptions;
    private ScrollView rlData;
    private Spinner spYear;
    private String yearSelected = "2021";
    private String[] years = {"2021", "2020"};
    private Spinner spPublisher;
    private CheckBox ckbATotalMonth;
    private int publisherSelected;
    private List<Publisher> publishers;
    private Spinner spMonth;
    private List<String> months;
    private String monthSelected;
    private int monthIndex = 0;
    private RadioGroup rgOptionsGroupS21;
    private RelativeLayout rlFilter;
    private int oldTxtData;
    private List<DataReportS21> listDataS21;
    private List<DataReportS88> listDataS88;
    private RadioGroup rgOptionsGroupS88;
    private LinearLayout llInfoReportS21;
    private TextView txtInfoLabel21;
    private TextView txtSubTitle;
    private TextView txtInfoS21;
    private TaskRunner runner;
    private ReportDAO reportDAO;
    private PublisherDAO publisherDAO;
    private AssistanceDAO assistanceDAO;
    private GroupDAO groupDAO;
    private QueryDAO queryDAO;
    private boolean openReportPublisher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congregation_report);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            Bundle bundle = this.getIntent().getExtras();
            if (bundle != null) {
                openReportPublisher = bundle.getBoolean(ReportActivity.PUBLISHER);

                if (openReportPublisher) {
                    RadioButton rbS21 = this.findViewById(R.id.rbS21);
                    rbS21.setChecked(true);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        this.init();
    }

    public void init() {
        try {
            this.runner = new TaskRunner();
            this.rgOptions = (RadioGroup) this.findViewById(R.id.rgOptions);
            this.rgOptionsGroupS21 = (RadioGroup) this.findViewById(R.id.rgOptionsGroupS21);
            this.rgOptionsGroupS88 = (RadioGroup) this.findViewById(R.id.rgOptionsGroupS88);
            this.rlData = (ScrollView) this.findViewById(R.id.rlData);
            this.spYear = (Spinner) this.findViewById(R.id.spYear);
            this.spMonth = (Spinner) this.findViewById(R.id.spMonth);
            this.spPublisher = (Spinner) this.findViewById(R.id.spPublisher);
            this.ckbATotalMonth = (CheckBox) this.findViewById(R.id.ckbATotalMonth);
            this.rlFilter = (RelativeLayout) this.findViewById(R.id.rlFilter);
            this.llInfoReportS21 = (LinearLayout) this.findViewById(R.id.llInfoReportS21);
            this.txtInfoLabel21 = (TextView) this.findViewById(R.id.txtInfoLabel21);
            this.txtSubTitle = (TextView) this.findViewById(R.id.txtSubTitle);
            this.txtInfoS21 = (TextView) this.findViewById(R.id.txtInfoS21);


            this.months = new ArrayList<>();
            this.publishers = new ArrayList<>();
            this.reportDAO = UtilDataMemory.getReportDAO(this);
            this.publisherDAO = UtilDataMemory.getPublisherDAO(this);
            this.assistanceDAO = UtilDataMemory.getAssistanceDAO(this);
            this.groupDAO = UtilDataMemory.getGroupDAO(this);
            this.queryDAO = new QueryDAO(this);

            this.initListeners();
            this.createDataSpinner();

            this.runner.executeAsync(new LoadingData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initScreenSelected() {
        try {
            this.cleanDataScreen();
            switch (this.rgOptions.getCheckedRadioButtonId()) {
                case R.id.rbBetel:
                    this.createBetelReport();
                    break;
                case R.id.rbS21:
                    this.createS21Report();
                    break;
                case R.id.rbS88:
                    this.createS88Report();
                    break;
                case R.id.rbSummary:
                    this.createSummaryReport();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListeners() {
        try {
            this.rgOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedNowId) {
                    runner.executeAsync(new LoadingData());
                }
            });
            this.rgOptionsGroupS21.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedNowId) {
                    runner.executeAsync(new LoadingData());
                }
            });
            this.ckbATotalMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    disableFilter();
                    runner.executeAsync(new LoadingData());
                }
            });
            this.rgOptionsGroupS88.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedNowId) {
                    runner.executeAsync(new LoadingData());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDataSpinner() {
        try {
            this.createDataSpinnerYear();
            this.createDataSpinnerMonth();
            this.createDataSpinnerPublisher();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void createDataSpinnerYear() {
        try {
            this.spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (!yearSelected.equals(years[position])) {
                        yearSelected = years[position];
                        runner.executeAsync(new LoadingData());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            if (this.openReportPublisher) {
                this.createNewDataSppinnerYear();
            }

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.simple_spinner_item_report, this.years);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            this.spYear.setAdapter(arrayAdapter);
            this.spYear.requestLayout();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void createDataSpinnerMonth() {
        try {
            String currentMonth = UtilDateHour.getMonthReportByName(this, UtilDateHour.getCurrentMonthForReport());
            for (int i = 0; i < this.getResources().getStringArray(R.array.months).length; i++) {
                String month = this.getResources().getStringArray(R.array.months)[i];
                if (month.equals(currentMonth)) {
                    this.monthIndex = i;
                    this.monthSelected = month;
                }
                this.months.add(month);
            }

            this.spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (monthIndex != position) {
                        monthIndex = position;
                        monthSelected = months.get(position);
                        runner.executeAsync(new LoadingData());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.simple_spinner_item_report, this.months);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            this.spMonth.setAdapter(arrayAdapter);
            this.spMonth.setSelection(this.monthIndex);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void createDataSpinnerPublisher() {
        try {
            this.publishers = new ArrayList();
            this.publishers.add(new Publisher(this.getResources().getString(R.string.label_select_publisher)));
            List<Publisher> dataDB = this.publisherDAO.findPublishers();
            this.publishers.addAll(dataDB);
            if (this.openReportPublisher) {
                for (int i = 0; i < this.publishers.size(); i++) {
                    Publisher p = this.publishers.get(i);
                    if (p.getId().equals(UtilDataMemory.publisher.getId())) {
                        this.publisherSelected = i;
                        break;
                    }
                }
            }
            this.spPublisher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    publisherSelected = position;
                    disableFilter();

                    if (position > 0) {
                        years = new String[]{
                                CongregationReportActivity.this.getResources().getString(R.string.label_all),
                                "2021",
                                "2020"
                        };
                        createDataSpinnerYear();
                        runner.executeAsync(new LoadingData());
                    } else if (years.length == 3) {
                        years = new String[]{
                                "2021",
                                "2020"
                        };
                        createDataSpinnerYear();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //Creating the ArrayAdapter instance having the country list
            SpinnerPublisherAdapter arrayAdapter = new SpinnerPublisherAdapter(this, R.layout.simple_spinner_item_report, this.publishers);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            this.spPublisher.setAdapter(arrayAdapter);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void createNewDataSppinnerYear() {
        try {
            this.years = new String[]{
                    CongregationReportActivity.this.getResources().getString(R.string.label_all),
                    "2021",
                    "2020"
            };
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void disableFilter() {
        try {
            if (this.ckbATotalMonth.isChecked()) {
                for (int i = 0; i < this.rgOptionsGroupS21.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) this.rgOptionsGroupS21.getChildAt(i);
                    radioButton.setEnabled(false);
                }
                this.spMonth.setEnabled(false);
                this.spPublisher.setEnabled(false);
                this.llInfoReportS21.setVisibility(View.GONE);
            } else {
                this.spMonth.setEnabled(true);
                this.spPublisher.setEnabled(true);
                for (int i = 0; i < this.rgOptionsGroupS21.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) this.rgOptionsGroupS21.getChildAt(i);
                    radioButton.setEnabled(publisherSelected == 0);
                }
                this.llInfoReportS21.setVisibility(publisherSelected == 0 ? View.VISIBLE : View.GONE);
                this.ckbATotalMonth.setEnabled(publisherSelected == 0);

                this.rgOptionsGroupS21.requestLayout();
                this.ckbATotalMonth.requestLayout();
            }
            this.llInfoReportS21.requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void createBetelReport() {
        try {
            this.cleanS21();
            this.txtSubTitle.setVisibility(View.GONE);
            this.spMonth.setVisibility(View.VISIBLE);
            this.llInfoReportS21.setVisibility(View.GONE);
            this.llInfoReportS21.requestLayout();
            this.ckbATotalMonth.setVisibility(View.GONE);
            this.spPublisher.setVisibility(View.GONE);
            this.rgOptionsGroupS88.setVisibility(View.GONE);
            this.rgOptionsGroupS21.setVisibility(View.GONE);
            this.rlFilter.setVisibility(View.VISIBLE);
            this.rlFilter.requestLayout();

            LayoutInflater layoutInflater =
                    (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.relative_layout, null);

            this.createReports(view);

            this.rlData.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSummaryReport() {
        try {
            this.cleanS21();
            this.txtSubTitle.setVisibility(View.GONE);
            this.rlFilter.setVisibility(View.GONE);
            this.rlFilter.requestLayout();

            LayoutInflater layoutInflater =
                    (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.relative_layout, null);

            this.createSummary(view);

            this.rlData.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanS21() {
        try {
            if (this.yearSelected.equals(
                    CongregationReportActivity.this.getResources().getString(R.string.label_all)
            ) || this.years.length == 3) {
                this.yearSelected = "2021";
                years = new String[]{
                        "2021",
                        "2020"
                };
                this.createDataSpinnerYear();
            }
            this.publisherSelected = 0;
            this.createDataSpinnerPublisher();
            RadioButton rbS21 = this.findViewById(R.id.rbAll);
            rbS21.setChecked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createS21Report() {
        try {
            if (openReportPublisher) {
                // Disabble
                this.spPublisher.setVisibility(View.GONE);
                this.spMonth.setVisibility(View.GONE);
                this.rgOptionsGroupS21.setVisibility(View.GONE);
                this.rgOptionsGroupS88.setVisibility(View.GONE);
                this.ckbATotalMonth.setVisibility(View.GONE);
                this.rgOptions.requestLayout();
            } else {
                this.spMonth.setVisibility(View.GONE);
                this.llInfoReportS21.setVisibility(View.VISIBLE);
                this.ckbATotalMonth.setVisibility(View.VISIBLE);
                this.rgOptionsGroupS88.setVisibility(View.GONE);
                this.rgOptionsGroupS21.setVisibility(View.VISIBLE);
                this.spPublisher.setVisibility(View.VISIBLE);

                // Muda o label
                String label = "";
                switch (rgOptionsGroupS21.getCheckedRadioButtonId()) {
                    case R.id.rbAll:
                        label = this.getResources().getString(R.string.number_reports);
                        break;
                    case R.id.rbPublishers:
                        label = this.getResources().getString(R.string.number_reports_publishers);
                        break;
                    case R.id.rdbAuxiliary:
                        label = this.getResources().getString(R.string.number_reports_auxiliary);
                        break;
                    case R.id.rdbRegular:
                        label = this.getResources().getString(R.string.number_reports_regular);
                        break;
                    case R.id.rdbDeaf:
                        label = this.getResources().getString(R.string.number_reports_deafs);
                        break;
                }
                label = label + ": ";
                this.txtInfoLabel21.setText(label);
                Integer total = this.listDataS21.size();
                this.txtInfoS21.setText(total == 0 ? "" : total.toString());
                if (total == 0 || this.publisherSelected > 0 || this.ckbATotalMonth.isChecked()) {
                    this.llInfoReportS21.setVisibility(View.GONE);
                }
                this.llInfoReportS21.requestLayout();
            }

            this.rlFilter.setVisibility(View.VISIBLE);
            this.rlFilter.requestLayout();

            LayoutInflater layoutInflater =
                    (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.relative_layout, null);

            for (int i = 0; i < this.listDataS21.size(); i++) {
                DataReportS21 reportS21 = this.listDataS21.get(i);
                this.createScreenReportS21(view, reportS21, i);
            }
            this.txtSubTitle.setVisibility(this.listDataS21.size() == 0 ? View.VISIBLE : View.GONE);
            if (this.listDataS21.size() > 0) {
                this.rlData.addView(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createScreenReportS21(View viewScreen, DataReportS21 reportS21, int row) {
        RelativeLayout relative = (RelativeLayout) viewScreen;
        try {
            boolean group = this.ckbATotalMonth.isChecked();
            Publisher publisher = reportS21.getPublisher();
            LayoutInflater layoutInflater =
                    (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.report_21, null);

            LinearLayout firstRow = view.findViewById(R.id.firstRow);
            TextView txtName = view.findViewById(R.id.txtName);
            String name = group ? publisher.getName() : publisher.getName() + " " + publisher.getLastName();
            txtName.setText(name);
            // ----------
            LinearLayout secondRow = view.findViewById(R.id.secondRow);
            TextView txtDateBirth = view.findViewById(R.id.txtDateBirth);
            txtDateBirth.setText(group ? "" : publisher.getBirth());
            // ----------
            LinearLayout thirdRow = view.findViewById(R.id.thirdRow);
            TextView txtBaptism = view.findViewById(R.id.txtBaptism);
            txtBaptism.setText(group ? "" : publisher.getBaptism() == null ? "" : publisher.getBaptism());
            // ----------
            RelativeLayout rlBoxCheckers = view.findViewById(R.id.rlBoxCheckers);
            LinearLayout fourthRow = view.findViewById(R.id.fourthRow);
            CheckBox ckbMan = view.findViewById(R.id.ckbMan);
            ckbMan.setChecked(group ? false : publisher.getGender().equals(UtilConstants.Man));

            CheckBox ckbElder = view.findViewById(R.id.ckbElder);
            ckbElder.setChecked(group ? false : publisher.isElder());

            CheckBox ckbServant = view.findViewById(R.id.ckbServant);
            ckbServant.setChecked(group ? false : publisher.isServantMinisterial());
            // ----------
            LinearLayout fifthRow = view.findViewById(R.id.fifthRow);
            CheckBox ckbWoman = view.findViewById(R.id.ckbWoman);
            ckbWoman.setChecked(group ? false : publisher.getGender().equals(UtilConstants.Woman));

            CheckBox ckbPioneer = view.findViewById(R.id.ckbPioneer);
            ckbPioneer.setChecked(group ? false : publisher.isRegularPioneer());
            // ----------
            LinearLayout sixthRow = view.findViewById(R.id.sixthRow);
            CheckBox ckbOthers = view.findViewById(R.id.ckbOthers);
            ckbOthers.setChecked(group ? false : !publisher.isAnointed());


            CheckBox ckbAnointed = view.findViewById(R.id.ckbAnointed);
            ckbOthers.setChecked(group ? false : publisher.isAnointed());
            // ----------
            HorizontalScrollView rlDataS21 = view.findViewById(R.id.rlDataS21);
            TableLayout tableDataS21 = view.findViewById(R.id.tableDataS21);
            TextView txtLabelYearService = view.findViewById(R.id.txtLabelYearService);
            String year = this.yearSelected.equals(this.getResources().getString(R.string.label_all)) ? reportS21.getYear() : this.yearSelected;
            txtLabelYearService.setText(this.getResources().getString(R.string.label_year_service_s21) + year);
            LinearLayout llFooter = view.findViewById(R.id.llFooter);


            // Change ids
            if (row > 0) {
                int idTxtData = Util.getRamdomBumber();
                view.setId(idTxtData);

                Params margin = new Params();
                margin.setTop(20);
                this.addComponenteBelow(view, this.oldTxtData, margin);
                // ----------
                int idFirstRow = Util.getRamdomBumber();
                firstRow.setId(idFirstRow);

                int idTxtName = Util.getRamdomBumber();
                txtName.setId(idTxtName);
                // ----------
                int idSecondRow = Util.getRamdomBumber();
                secondRow.setId(idSecondRow);
                this.addComponenteBelow(secondRow, idFirstRow, null);

                int idTxtDateBirth = Util.getRamdomBumber();
                txtDateBirth.setId(idTxtDateBirth);
                // ----------
                int idThirdRow = Util.getRamdomBumber();
                thirdRow.setId(idThirdRow);
                this.addComponenteBelow(thirdRow, idSecondRow, null);

                int idTxtBaptism = Util.getRamdomBumber();
                txtBaptism.setId(idTxtBaptism);

                int idRlBoxCheckers = Util.getRamdomBumber();
                rlBoxCheckers.setId(idRlBoxCheckers);
                margin = new Params();
                margin.setTop(10);
                margin.setBottom(10);
                this.addComponenteBelow(rlBoxCheckers, idThirdRow, margin);
                // ----------
                int idFourthRow = Util.getRamdomBumber();
                fourthRow.setId(idFourthRow);

                int idCkbMan = Util.getRamdomBumber();
                ckbMan.setId(idCkbMan);

                int idCkbElder = Util.getRamdomBumber();
                ckbElder.setId(idCkbElder);

                int idCkbServant = Util.getRamdomBumber();
                ckbServant.setId(idCkbServant);
                // ----------
                int idFifthRow = Util.getRamdomBumber();
                fifthRow.setId(idFifthRow);
                this.addComponenteBelow(fifthRow, idFourthRow, null);

                int idCkbWoman = Util.getRamdomBumber();
                ckbWoman.setId(idCkbWoman);

                int idCkbPioneer = Util.getRamdomBumber();
                ckbPioneer.setId(idCkbPioneer);
                // ----------
                int idSixthRow = Util.getRamdomBumber();
                sixthRow.setId(idSixthRow);
                this.addComponenteBelow(sixthRow, idFifthRow, null);

                int idCkbOthers = Util.getRamdomBumber();
                ckbOthers.setId(idCkbOthers);

                int idCkbAnointed = Util.getRamdomBumber();
                ckbAnointed.setId(idCkbAnointed);
                // ----------
                int idRlDataS21 = Util.getRamdomBumber();
                rlDataS21.setId(idRlDataS21);
                margin = new Params();
                margin.setTop(10);
                this.addComponenteBelow(rlDataS21, idRlBoxCheckers, null);
                // ----------
                int idTableDataS21 = Util.getRamdomBumber();
                tableDataS21.setId(idTableDataS21);
                // ----------
                int idTxtYearService = Util.getRamdomBumber();
                txtLabelYearService.setId(idTxtYearService);
                // ----------
                int idFooter = Util.getRamdomBumber();
                llFooter.setId(idFooter);
                this.addComponenteBelow(llFooter, idTableDataS21, null);
            }

            // Create lines tables
            this.createTableS21(tableDataS21, reportS21);

            // Set ids previous line
            this.oldTxtData = view.getId();
            relative.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTableS21(TableLayout tableDataS21, DataReportS21 reportS21) {
        try {
            Float publications = 0f, hours = 0f,
                    videos = 0f, revisited = 0f, bibleCourses = 0f, credit = 0f;
            Integer totalReports = 0;

            boolean group = this.ckbATotalMonth.isChecked();
            int lengthList = EnumMonth.values().length + 2;

            for (int i = 0; i < lengthList; i++) {
                EnumMonth enumMonth = null;
                if (i <= 11) {
                    enumMonth = EnumMonth.values()[i];
                } else {
                    System.out.println("Teste");
                }
                String nameMonth = enumMonth == null ? ""
                        : UtilDateHour.getMonthReportByValue(this, enumMonth.getValue());

                String currentMonth = i == Report.TOTAL ? this.getResources().getString(R.string.label_total)
                        : i == Report.AVERAGE ? this.getResources().getString(R.string.label_average)
                        : nameMonth;

                boolean bold = i == Report.TOTAL || i == Report.AVERAGE;

                Report report = null;
                if (i <= 11) {
                    report = reportS21.getReports().get(enumMonth.getName());
                }
                Publisher publisher = reportS21.getPublisher();

                if (report == null) {
                    report = new Report();
                } else {
                    publications = publications + (report.getPublications() == null ? 0f : report.getPublications());
                    hours = hours + (report.getHours() == null ? 0f : report.getHours());
                    videos = videos + (report.getVideos() == null ? 0f : report.getVideos());
                    revisited = revisited + (report.getRevisited() == null ? 0f : report.getRevisited());
                    bibleCourses = bibleCourses + (report.getBibleCourses() == null ? 0f : report.getBibleCourses());
                    credit = credit + (report.getCredit() == null ? 0f : report.getCredit());
                    if (report.getHours() > 0 || report.isPreachingFifteenMinLess()) {
                        totalReports++;
                    }
                }

                // Create Line
                TableRow tableRow = this.createRowTable();
                tableRow.setId(Util.getRamdomBumber());

                int colorLine = group ? R.color.colorWhite
                        : report.isAuxiliaryPioneer() ? R.color.colorOrange
                        : report.isPreachingFifteenMinLess() ? R.color.colorPink
                        : report.isPreachingFifteenMinLess() ? R.color.colorBlue
                        : report.getId() != 0 && report.getHours() == 0 ? R.color.colorRed
                        : R.color.colorWhite;
                int colorYellow = R.color.colorYellow;
                int colorText = report.isPreachingFifteenMinLess() ? R.color.colorYellow
                        : i == Report.TOTAL || i == Report.AVERAGE ? R.color.colorBlack : R.color.colorGrayDark;

                int colorRed = R.color.colorRed;

                // Month
                Params margin = new Params();
                margin.setLeft(1);
                Params padding = new Params();
                padding.setLeft(3);
                LinearLayout linearLayout = this.createLinearLayout();
                TextView txtMonth = this.createTextViewS21(
                        UtilFormatte.stringToUpCaseOnlyFirstLetter(currentMonth),
                        bold,
                        false,
                        colorText,
                        colorLine,
                        null,
                        padding
                );
                linearLayout.addView(txtMonth);
                tableRow.addView(linearLayout);
                // ------------------------------
                // -- Publications
                boolean alert = report.getHours() > 0 && report.getPublications() == 0;
                linearLayout = this.createLinearLayout();
                String text = i ==
                        Report.TOTAL && publications > 0 ? UtilFormatte.parseInteger(publications).toString()
                        : i == Report.AVERAGE && publications > 0 ? UtilFormatte.getFormate(publications / totalReports)
                        : report.getPublications() == 0 ? "" : report.getPublications().toString();

                TextView txtPublications = this.createTextViewS21(
                        text,
                        bold,
                        true,
                        colorText,
                        alert ? colorYellow : colorLine,
                        margin,
                        null
                );
                linearLayout.addView(txtPublications);
                tableRow.addView(linearLayout);
                // ------------------------------
                // -- Videos
                alert = report.getHours() > 0 && report.getVideos() == 0;
                linearLayout = this.createLinearLayout();
                text = i ==
                        Report.TOTAL && publications > 0 ? UtilFormatte.parseInteger(videos).toString()
                        : i == Report.AVERAGE && videos > 0 ? UtilFormatte.getFormate(videos / totalReports)
                        : report.getVideos() == 0 ? "" : report.getVideos().toString();
                TextView txtVideos = this.createTextViewS21(
                        text,
                        bold,
                        true,
                        colorText,
                        alert ? colorYellow : colorLine,
                        margin,
                        null
                );
                linearLayout.addView(txtVideos);
                tableRow.addView(linearLayout);
                // ------------------------------
                // -- Hours
                Integer hoursAux = report.isAuxiliaryPioneer() && report.getAuxiliaryPioneerType().equals("50 HORAS") ? 50
                        : report.isAuxiliaryPioneer() && report.getAuxiliaryPioneerType().equals("30 HORAS") ? 30 : 0;
                alert = (publisher.isRegularPioneer() && (report.getHours() > 0 && report.getHours() < 70))
                        || (report.isAuxiliaryPioneer() && report.getHours() < hoursAux);
                linearLayout = this.createLinearLayout();
                text = i ==
                        Report.TOTAL && publications > 0 ? UtilFormatte.parseInteger(hours).toString()
                        : i == Report.AVERAGE && hours > 0 ? UtilFormatte.getFormate(hours / totalReports)
                        : report.getHours() == 0 ? "" : report.getHours().toString();
                TextView txtHours = this.createTextViewS21(
                        text,
                        bold,
                        true,
                        alert ? colorRed : colorText,
                        colorLine,
                        margin,
                        null
                );
                linearLayout.addView(txtHours);
                tableRow.addView(linearLayout);
                // ------------------------------
                // -- Revisited
                alert = report.getHours() > 0 && report.getRevisited() == 0;
                linearLayout = this.createLinearLayout();
                text = i ==
                        Report.TOTAL && publications > 0 ? UtilFormatte.parseInteger(revisited).toString()
                        : i == Report.AVERAGE && revisited > 0 ? UtilFormatte.getFormate(revisited / totalReports)
                        : report.getRevisited() == 0 ? "" : report.getRevisited().toString();
                TextView txtRevisited = this.createTextViewS21(
                        text,
                        bold,
                        true,
                        alert ? colorRed : colorText,
                        colorLine,
                        margin,
                        null
                );
                linearLayout.addView(txtRevisited);
                tableRow.addView(linearLayout);
                // ------------------------------
                // -- Courses
                alert = report.getHours() > 0 && report.getBibleCourses() == 0;
                linearLayout = this.createLinearLayout();
                text = i ==
                        Report.TOTAL && publications > 0 ? UtilFormatte.parseInteger(bibleCourses).toString()
                        : i == Report.AVERAGE && bibleCourses > 0 ? UtilFormatte.getFormate(bibleCourses / totalReports)
                        : report.getBibleCourses() == 0 ? "" : report.getBibleCourses().toString();
                TextView txtCourses = this.createTextViewS21(
                        text,
                        bold,
                        true,
                        alert ? colorRed : colorText,
                        colorLine,
                        margin,
                        null
                );
                linearLayout.addView(txtCourses);
                tableRow.addView(linearLayout);
                // ------------------------------
                // -- Notes
                text = i ==
                        Report.TOTAL && publications > 0 ? UtilFormatte.parseInteger(credit).toString()
                        : i == Report.AVERAGE && credit > 0 ? UtilFormatte.getFormate(credit / totalReports)
                        : report.getCredit().toString();

                String creditText = !text.equals("0") ? "[" + this.getResources().getString(R.string.credit) + " " + text + "]" : "";
                String notes = report.getNotes().isEmpty() ? creditText : report.getNotes() + " " + creditText;
                linearLayout = this.createLinearLayout();
                TextView txtNotes = this.createTextViewS21(
                        group ? "" : notes,
                        false,
                        true,
                        colorText,
                        colorLine,
                        margin,
                        null
                );
                linearLayout.addView(txtNotes);
                tableRow.addView(linearLayout);

                // Add line
                tableDataS21.addView(tableRow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createS88Report() {
        try {
            this.cleanS21();
            this.spMonth.setVisibility(View.GONE);
            this.txtSubTitle.setVisibility(View.GONE);
            this.llInfoReportS21.setVisibility(View.GONE);
            this.spPublisher.setVisibility(View.GONE);
            this.rgOptionsGroupS21.setVisibility(View.GONE);
            this.ckbATotalMonth.setVisibility(View.GONE);
            this.rgOptionsGroupS88.setVisibility(View.VISIBLE);
            this.rlFilter.setVisibility(View.VISIBLE);
            this.rlFilter.requestLayout();

            LayoutInflater layoutInflater =
                    (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout rlDataReportS88 = (RelativeLayout) layoutInflater.inflate(R.layout.report_s88, null);

            for (DataReportS88 reportS88 : this.listDataS88) {
                if (reportS88.getTypeWeek().equals(UtilConstants.MID_WEEK)) {
                    this.createMiddleWeek(rlDataReportS88, reportS88);
                }
                if (reportS88.getTypeWeek().equals(UtilConstants.WEEKEND)) {
                    this.createWeekend(rlDataReportS88, reportS88);
                }
            }
            this.rlData.addView(rlDataReportS88);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createMiddleWeek(View view, DataReportS88 reportS88) {
        try {

            Integer result = Integer.parseInt(yearSelected) - 1;

            // Middle week
            TableLayout tableDataMiddleWeekS88 = view.findViewById(R.id.tableDataMiddleWeekS88);
            TextView txtLabelYearServiceMiddleWeekS88 =
                    view.findViewById(R.id.txtLabelYearServiceMiddleWeekS88);
            TextView txtMiddleAveragePreviousYear = view.findViewById(R.id.txtMiddleAveragePreviousYear);
            TextView txtMiddleAverageCurrentYear = view.findViewById(R.id.txtMiddleAverageCurrentYear);
            txtLabelYearServiceMiddleWeekS88.setText(
                    this.getResources().getString(R.string.label_year_service_s21)
                            + result
            );

            TextView txtLabelYearServiceMiddleWeek2S88 =
                    view.findViewById(R.id.txtLabelYearServiceMiddleWeek2S88);

            txtLabelYearServiceMiddleWeek2S88.setText(
                    this.getResources().getString(R.string.label_year_service_s21)
                            + yearSelected
            );


            // Create lines tables
            this.createTableS88(tableDataMiddleWeekS88, reportS88, txtMiddleAveragePreviousYear, txtMiddleAverageCurrentYear);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createWeekend(View view, DataReportS88 reportS88) {
        try {

            Integer result = Integer.parseInt(yearSelected) - 1;


            // Weekend
            TableLayout tableDataWeekendS88 = view.findViewById(R.id.tableDataWeekendS88);
            TextView txtLabelYearServiceWeekendS88 = view.findViewById(R.id.txtLabelYearServiceWeekendS88);
            TextView txtWeekendAveragePreviousYear = view.findViewById(R.id.txtWeekendAveragePreviousYear);
            TextView txtWeekendAverageCurrentYear = view.findViewById(R.id.txtWeekendAverageCurrentYear);


            txtLabelYearServiceWeekendS88.setText(
                    this.getResources().getString(R.string.label_year_service_s21)
                            + result
            );

            TextView txtLabelYearServiceWeekend2S88 = view.findViewById(R.id.txtLabelYearServiceWeekend2S88);

            txtLabelYearServiceWeekend2S88.setText(
                    this.getResources().getString(R.string.label_year_service_s21)
                            + yearSelected
            );

            // Create lines tables
            this.createTableS88(tableDataWeekendS88, reportS88, txtWeekendAveragePreviousYear, txtWeekendAverageCurrentYear);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTableS88(
            TableLayout tableDataS88,
            DataReportS88 reportS88,
            TextView txtPreviousYear,
            TextView txtCurrentYear
    ) {
        try {
            Float totalFirstYear = 0f;
            Integer totalReportsFirst = 0;
            Float totalPreviousYear = 0f;
            Integer totalReportsPrevious = 0;

            int lengthList = EnumMonth.values().length;

            Integer currentYear = Integer.parseInt(yearSelected);
            Integer yearPrevious = Integer.parseInt(yearSelected) - 1;
            for (int i = 0; i < lengthList; i++) {
                EnumMonth enumMonth = EnumMonth.values()[i];
                String nameMonth = UtilDateHour.getMonthReportByValue(this, enumMonth.getValue());

                boolean bold = i == Report.TOTAL || i == Report.AVERAGE;
                int colorLine = R.color.colorWhite;
                int colorText = R.color.colorGrayDark;

                AssistanceReport firstAssistance = reportS88.getFirstYear().get(enumMonth.getName());
                if (firstAssistance == null) {
                    if (currentYear == 2020) {
                        firstAssistance = new AssistanceReport(
                                this,
                                currentYear,
                                reportS88.getType(),
                                reportS88.getTypeWeek(),
                                nameMonth
                        );
                        totalFirstYear = totalFirstYear + firstAssistance.getAverage();
                        totalReportsFirst++;
                    } else {
                        firstAssistance = new AssistanceReport();
                    }

                } else {
                    totalFirstYear = totalFirstYear + firstAssistance.getAverage();
                    totalReportsFirst++;
                }

                AssistanceReport previsousAssistance = reportS88.getPreviousYear().get(enumMonth.getName());
                if (previsousAssistance == null) {
                    if (yearPrevious == 2020) {
                        previsousAssistance = new AssistanceReport(
                                this,
                                yearPrevious,
                                reportS88.getType(),
                                reportS88.getTypeWeek(),
                                nameMonth
                        );
                        totalPreviousYear = totalPreviousYear + previsousAssistance.getAverage();
                        totalReportsPrevious++;
                    } else {
                        previsousAssistance = new AssistanceReport();
                    }
                } else {
                    totalPreviousYear = totalPreviousYear + previsousAssistance.getAverage();
                    totalReportsPrevious++;
                }

                // Create Line
                TableRow tableRow = this.createRowTable();
                tableRow.setId(Util.getRamdomBumber());

                // Previous year
                // Month
                Params margin = new Params();
                margin.setLeft(1);
                Params padding = new Params();
                padding.setLeft(3);
                LinearLayout linearLayout = this.createLinearLayout();

                TextView txtMonth = this.createTextViewS21(
                        UtilFormatte.stringToUpCaseOnlyFirstLetter(nameMonth),
                        bold,
                        false,
                        colorText,
                        colorLine,
                        null,
                        padding
                );
                linearLayout.addView(txtMonth);
                tableRow.addView(linearLayout);
                // ------------------------------
                // -- Number
                linearLayout = this.createLinearLayout();
                TextView txtNumber = this.createTextViewS21(
                        previsousAssistance.getNumberMeeting() == 0 ? ""
                                : previsousAssistance.getNumberMeeting().toString(),
                        bold,
                        true,
                        colorText,
                        colorLine,
                        margin,
                        null
                );
                linearLayout.addView(txtNumber);
                tableRow.addView(linearLayout);
                // ------------------------------
                // -- Total
                linearLayout = this.createLinearLayout();
                TextView txtTotal = this.createTextViewS21(
                        previsousAssistance.getTotal() == 0 ? ""
                                : previsousAssistance.getTotal().toString(),
                        bold,
                        true,
                        colorText,
                        colorLine,
                        margin,
                        null
                );
                linearLayout.addView(txtTotal);
                tableRow.addView(linearLayout);
                // ------------------------------
                // -- Average
                linearLayout = this.createLinearLayout();
                TextView txtHours = this.createTextViewS21(
                        previsousAssistance.getAverage() == 0 ? ""
                                : UtilFormatte.getFormate(previsousAssistance.getAverage()),
                        bold,
                        true,
                        colorText,
                        colorLine,
                        margin,
                        null
                );
                linearLayout.addView(txtHours);
                tableRow.addView(linearLayout);
                // -----------------------------------------------
                // Current year
                // Month
                linearLayout = this.createLinearLayout();
                txtMonth = this.createTextViewS21(
                        UtilFormatte.stringToUpCaseOnlyFirstLetter(nameMonth),
                        bold,
                        false,
                        colorText,
                        colorLine,
                        margin,
                        padding

                );
                linearLayout.addView(txtMonth);
                tableRow.addView(linearLayout);
                // ------------------------------
                // -- Number
                linearLayout = this.createLinearLayout();
                txtNumber = this.createTextViewS21(
                        firstAssistance.getNumberMeeting() == 0 ? ""
                                : firstAssistance.getNumberMeeting().toString(),
                        bold,
                        true,
                        colorText,
                        colorLine,
                        margin,
                        null
                );
                linearLayout.addView(txtNumber);
                tableRow.addView(linearLayout);
                // ------------------------------
                // -- Total
                linearLayout = this.createLinearLayout();
                txtTotal = this.createTextViewS21(
                        firstAssistance.getTotal() == 0 ? ""
                                : firstAssistance.getTotal().toString(),
                        bold,
                        true,
                        colorText,
                        colorLine,
                        margin,
                        null
                );
                linearLayout.addView(txtTotal);
                tableRow.addView(linearLayout);
                // ------------------------------
                // -- Average
                linearLayout = this.createLinearLayout();
                txtHours = this.createTextViewS21(
                        firstAssistance.getAverage() == 0 ? ""
                                : UtilFormatte.getFormate(previsousAssistance.getAverage()),
                        bold,
                        true,
                        colorText,
                        colorLine,
                        margin,
                        null
                );
                linearLayout.addView(txtHours);
                tableRow.addView(linearLayout);


                // Add line
                tableDataS88.addView(tableRow);
            }
            if (totalPreviousYear > 0) {
                Float previousResult = totalPreviousYear / totalReportsPrevious;
                txtPreviousYear.setText(previousResult == 0f ? "" : UtilFormatte.getFormate(previousResult));
            }
            // ----
            if (totalFirstYear > 0) {
                Float currentResult = totalFirstYear / totalReportsFirst;
                txtCurrentYear.setText(currentResult == 0f ? "" : UtilFormatte.getFormate(currentResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextView createTextViewS21(
            String text,
            boolean bold,
            boolean centerHorizontal,
            int colorText,
            int background,
            Params margin,
            Params padding
    ) {
        TextView textView = new TextView(this);
        try {
            textView.setText(text);
            textView.setTextColor(this.getResources().getColor(colorText));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            if (centerHorizontal) {
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
            }
            if (bold) {
                textView.setTypeface(null, Typeface.BOLD);
            } else {
                textView.setTypeface(null, Typeface.NORMAL);
            }
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            if (margin != null) {
                params.setMargins(
                        Util.convertDps(this, margin.getLeft()),
                        Util.convertDps(this, margin.getTop()),
                        Util.convertDps(this, margin.getRight()),
                        Util.convertDps(this, margin.getBottom())
                );
            }
            if (padding != null) {
                textView.setPadding(
                        Util.convertDps(this, padding.getLeft()),
                        Util.convertDps(this, padding.getTop()),
                        Util.convertDps(this, padding.getRight()),
                        Util.convertDps(this, padding.getBottom())
                );
            }
            textView.setBackground(this.getResources().getDrawable(background));
            textView.setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textView;
    }

    private TableRow createRowTable() {
        TableRow tableRow = new TableRow(this);
        try {
            tableRow.setBackground(this.getResources().getDrawable(R.color.colorGrayAccent));
            tableRow.setPadding(1, 1, 1, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableRow;
    }

    private LinearLayout createLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(this);
        try {
            linearLayout.setBackground(this.getResources().getDrawable(R.color.colorGrayAccent));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linearLayout;
    }

    private void createReports(View viewScreen) {
        RelativeLayout view = (RelativeLayout) viewScreen;
        try {
            List<Publisher> publishersNorSendReport =
                    this.publisherDAO.findPublisherNotSendReport(
                            UtilDateHour.getMonthBySelected(this, this.monthSelected),
                            this.yearSelected
                    );

            List<Integer> reports = new ArrayList<>();
            if (publishersNorSendReport.size() > 0) {
                reports.add(CongReport.SEND);
            }
            reports.add(CongReport.ASSISTANCE);
            reports.add(CongReport.TOTAL);
            reports.add(CongReport.PUBLISHER);
            reports.add(CongReport.AUXILIARY);
            reports.add(CongReport.REGULAR);

            for (Integer i = 0; i < reports.size(); i++) {
                Integer previousId = reports.get(i) - 1;
                Integer id = reports.get(i);
                LinearLayout linearLayout = new LinearLayout(view.getContext());
                linearLayout.setId(id);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setBackground(view.getResources().getDrawable(R.color.colorWhite));
                RelativeLayout.LayoutParams params =
                        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                linearLayout.setLayoutParams(params);
                linearLayout.setPadding(20, 20, 20, 20);

                if (i > 0) {
                    params.addRule(RelativeLayout.BELOW, previousId);
                    linearLayout.setLayoutParams(params);
                    params.setMargins(0, 20, 0, 0);
                }


                // Create fields
                if (id == CongReport.ASSISTANCE) {
                    CongReport report = this.reportDAO.getActivePublishersAndAssistance(
                            UtilDateHour.getMonthBySelected(this, this.monthSelected),
                            this.yearSelected
                    );


                    // -----
                    linearLayout.addView(
                            this.createTextView(
                                    viewScreen.getResources().getString(R.string.label_active_publishers),
                                    false,
                                    view.getResources().getColor(R.color.colorGrayAccent),
                                    15,
                                    true)
                    );
                    TextView txtActivePublishers = this.createTextView(
                            report.getActivePublishers().toString(),
                            false,
                            view.getResources().getColor(R.color.colorGrayDark),
                            18,
                            false
                    );
                    linearLayout.addView(txtActivePublishers);
                    // -----
                    linearLayout.addView(
                            this.createTextView(
                                    viewScreen.getResources().getString(R.string.label_assistance_average),
                                    false,
                                    view.getResources().getColor(R.color.colorGrayAccent),
                                    15,
                                    true)
                    );
                    TextView txtAverage = this.createTextView(
                            report.getAssistanceAverage() == 0 ? ""
                                    : UtilFormatte.getFormate(report.getAssistanceAverage()),
                            false,
                            view.getResources().getColor(R.color.colorGrayDark),
                            18,
                            false
                    );
                    linearLayout.addView(txtAverage);
                } else if (id == CongReport.SEND) {
                    String title = viewScreen.getResources().getString(R.string.label_not_send_report);
                    linearLayout.addView(
                            this.createTextView(
                                    title,
                                    true,
                                    view.getResources().getColor(R.color.colorBlack),
                                    20,
                                    false
                            )
                    );
                    List<Group> groups = this.groupDAO.findGroups();
                    for (Group group : groups) {
                        Publisher supGroup = this.publisherDAO.findSupGroup(group.getName());


                        // Name Group
                        TextView txtGroup = this.createTextView(
                                "   " + group.getName() + " (" + supGroup.getFullName() + ")",
                                true,
                                view.getResources().getColor(R.color.colorAccent),
                                15,
                                true);
                        linearLayout.addView(txtGroup);

                        publishersNorSendReport =
                                this.publisherDAO.findPublisherNotSendReportByGroup(
                                        group.getName(),
                                        UtilDateHour.getMonthBySelected(this, this.monthSelected),
                                        this.yearSelected
                                );
                        if (publishersNorSendReport.size() == 0) {
                            TextView txtNamePublisher = this.createTextView(
                                    this.getResources().getString(R.string.all_send_report),
                                    false,
                                    view.getResources().getColor(R.color.colorGreen),
                                    15,
                                    true);
                            linearLayout.addView(txtNamePublisher);
                        }
                        for (Publisher publisher : publishersNorSendReport) {
                            TextView txtNamePublisher = this.createTextView(
                                    "     * " + publisher.getFullName(),
                                    false,
                                    view.getResources().getColor(R.color.colorGrayAccent),
                                    15,
                                    true);
                            linearLayout.addView(txtNamePublisher);
                        }
                    }


                } else {
                    CongReport report = this.reportDAO.getReportByBetel(
                            UtilDateHour.getMonthBySelected(this, this.monthSelected),
                            this.yearSelected,
                            id
                    );

                    String title = "";
                    switch (id) {
                        case CongReport.TOTAL:
                            title = viewScreen.getResources().getString(R.string.label_totals);
                            break;
                        case CongReport.PUBLISHER:
                            title = viewScreen.getResources().getString(R.string.publishers);
                            break;
                        case CongReport.AUXILIARY:
                            title = viewScreen.getResources().getString(R.string.label_auxiliary_pioneer);
                            break;
                        case CongReport.REGULAR:
                            title = viewScreen.getResources().getString(R.string.label_regular_pioneer);
                            break;
                    }

                    linearLayout.addView(
                            this.createTextView(
                                    title,
                                    true,
                                    view.getResources().getColor(R.color.colorBlack),
                                    20,
                                    false
                            )
                    );

                    if (id == CongReport.AUXILIARY && report.getReports() == 0) {

                        TextView txtNoAuxiliary = this.createTextView(
                                viewScreen.getResources().getString(R.string.label_no_auxiliary),
                                false,
                                view.getResources().getColor(R.color.colorGrayAccent),
                                15,
                                true);
                        txtNoAuxiliary.setTextColor(view.getResources().getColor(R.color.colorRed));
                        linearLayout.addView(txtNoAuxiliary);
                    } else {
                        // -----
                        linearLayout.addView(
                                this.createTextView(
                                        viewScreen.getResources().getString(R.string.number_reports),
                                        false,
                                        view.getResources().getColor(R.color.colorGrayAccent),
                                        15,
                                        true)
                        );
                        TextView txtTotalReports = this.createTextView(
                                report.getReports().toString(),
                                false,
                                view.getResources().getColor(R.color.colorGrayDark),
                                18,
                                false
                        );
                        linearLayout.addView(txtTotalReports);
                        // -----
                        linearLayout.addView(
                                this.createTextView(
                                        viewScreen.getResources().getString(R.string.publications_print),
                                        false,
                                        view.getResources().getColor(R.color.colorGrayAccent),
                                        15,
                                        true)
                        );
                        TextView txtTotalPublications = this.createTextView(
                                report.getPublications().toString(),
                                false,
                                view.getResources().getColor(R.color.colorGrayDark),
                                18,
                                false
                        );
                        linearLayout.addView(txtTotalPublications);
                        // -----
                        linearLayout.addView(
                                this.createTextView(
                                        viewScreen.getResources().getString(R.string.showed_videos),
                                        false,
                                        view.getResources().getColor(R.color.colorGrayAccent),
                                        15,
                                        true)
                        );
                        TextView txtTotalVideos = this.createTextView(
                                report.getVideos().toString(),
                                false,
                                view.getResources().getColor(R.color.colorGrayDark),
                                18,
                                false
                        );
                        linearLayout.addView(txtTotalVideos);
                        // -----
                        linearLayout.addView(
                                this.createTextView(
                                        viewScreen.getResources().getString(R.string.hours),
                                        false,
                                        view.getResources().getColor(R.color.colorGrayAccent),
                                        15,
                                        true)
                        );
                        TextView txtTotalHoras = this.createTextView(
                                report.getHours().toString(),
                                false,
                                view.getResources().getColor(R.color.colorGrayDark),
                                18,
                                false
                        );
                        linearLayout.addView(txtTotalHoras);
                        // -----
                        linearLayout.addView(
                                this.createTextView(
                                        viewScreen.getResources().getString(R.string.revisited),
                                        false,
                                        view.getResources().getColor(R.color.colorGrayAccent),
                                        15,
                                        true)
                        );
                        TextView txtTotalRevisited = this.createTextView(
                                report.getRevisited().toString(),
                                false,
                                view.getResources().getColor(R.color.colorGrayDark),
                                18,
                                false
                        );
                        linearLayout.addView(txtTotalRevisited);
                        // -----
                        linearLayout.addView(
                                this.createTextView(
                                        viewScreen.getResources().getString(R.string.report_bible_courses),
                                        false,
                                        view.getResources().getColor(R.color.colorGrayAccent),
                                        15,
                                        true)
                        );
                        TextView txtTotalCourses = this.createTextView(
                                report.getCourses().toString(),
                                false,
                                view.getResources().getColor(R.color.colorGrayDark),
                                18,
                                false
                        );
                        linearLayout.addView(txtTotalCourses);
                    }

                }

                //Add
                view.addView(linearLayout);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createSummary(View viewScreen) {
        RelativeLayout relative = (RelativeLayout) viewScreen;
        try {
            DataCongregation dataCongregation = this.queryDAO.findDataCongregation();
            LayoutInflater layoutInflater =
                    (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.data_report_congregation, null);

            // txts
            TextView txtDeafSchool = view.findViewById(R.id.txtDeafSchool);
            txtDeafSchool.setText(dataCongregation.getDeafSchool().toString());

            TextView txtDeafBaptism = view.findViewById(R.id.txtDeafBaptism);
            txtDeafBaptism.setText(dataCongregation.getDeafBaptism().toString());

            TextView txtDeafNoBaptism = view.findViewById(R.id.txtDeafNoBaptism);
            txtDeafNoBaptism.setText(dataCongregation.getDeafNoBaptism().toString());

            TextView txtDeafRegularPioneer = view.findViewById(R.id.txtDeafRegularPioneer);
            txtDeafRegularPioneer.setText(dataCongregation.getDeafRegularPioneer().toString());

            TextView txtQtdPublishers = view.findViewById(R.id.txtQtdPublishers);
            txtQtdPublishers.setText(dataCongregation.getPublishers().toString());

            TextView txtPublishersBaptism = view.findViewById(R.id.txtPublishersBaptism);
            txtPublishersBaptism.setText(dataCongregation.getPublishersBaptism().toString());

            TextView txtQtdNoPublishers = view.findViewById(R.id.txtQtdNoPublishers);
            txtQtdNoPublishers.setText(dataCongregation.getPublishersNoBaptism().toString());

            TextView txtTotalRegularPioneer = view.findViewById(R.id.txtTotalRegularPioneer);
            txtTotalRegularPioneer.setText(dataCongregation.getPublishersNoBaptism().toString());


            //Add
            relative.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addComponenteBelow(View view, int previousId, Params margin) {
        try {
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
            params.addRule(RelativeLayout.BELOW, previousId);
            if (margin != null) {
                params.setMargins(
                        Util.convertDps(this, margin.getLeft()),
                        Util.convertDps(this, margin.getTop()),
                        Util.convertDps(this, margin.getRight()),
                        Util.convertDps(this, margin.getBottom())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TextView createTextView(
            String text,
            boolean bold,
            int color,
            float size,
            boolean marginTop
    ) {
        TextView textView = new TextView(this);
        try {
            textView.setText(text);
            textView.setTextSize(size);
            textView.setTextColor(color);
            if (bold) {
                textView.setTypeface(null, Typeface.BOLD);
            } else {
                textView.setTypeface(null, Typeface.NORMAL);
            }
            if (marginTop) {
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 20, 0, 0);
                textView.setLayoutParams(params);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return textView;
    }

    private void cleanDataScreen() {
        try {
            this.rlData.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class LoadingData extends BaseTask {

        private RelativeLayout layoutLoading;
        private TextView txtMessage;
        private Activity activity;

        @Override
        public void setUiForLoading() {
            this.activity = CongregationReportActivity.this;
            this.layoutLoading = (RelativeLayout) this.activity.findViewById(
                    R.id.layoutLoading);
            this.layoutLoading.setVisibility(View.VISIBLE);
            this.txtMessage = (TextView) this.activity.findViewById(
                    R.id.txtMessage);
            this.txtMessage.setText(this.activity.getResources().getString(R.string.msg_loading_data));
        }

        @Override
        public Object callInBackground() {
            try {
                switch (rgOptions.getCheckedRadioButtonId()) {
                    case R.id.rbS21:
                        this.findDataS21();
                        break;
                    case R.id.rbS88:
                        if (yearSelected.equals(CongregationReportActivity.this.getResources().getString(R.string.label_all))) {
                            yearSelected = "2021";
                        }
                        this.findDataS88();
                        break;
                }
            } catch (Exception e) {
                return Util.NOT_FOUND;
            }
            return Util.OK;
        }


        @Override
        public void setDataAfterLoading(Object object) {
            Integer result = (Integer) object;
            if (result == Util.OK) {
                initScreenSelected();
            }

            if (result == Util.NOT_FOUND) {
                Toast toast = Toast.makeText(
                        this.activity,
                        this.activity.getResources().getString(R.string.msg_loading_data_error),
                        Toast.LENGTH_LONG
                );
                toast.show();
                cleanDataScreen();
            }
            this.layoutLoading.setVisibility(View.GONE);
        }

        private void findDataS21() {
            try {
                // Busca os dados
                listDataS21 = new ArrayList<>();
                if (ckbATotalMonth.isChecked()) {
                    this.finReportS21ByGroup();
                    return;
                }
                if (publisherSelected > 0) {
                    Publisher publisher = publishers.get(publisherSelected);
                    if (yearSelected == this.activity.getResources().getString(R.string.label_all)) {
                        for (int i = 1; i < years.length; i++) {
                            String currentYear = years[i];

                            Map<String, Report> reports = reportDAO.findReportsByPublisherAndYear(
                                    publisher,
                                    currentYear
                            );

                            DataReportS21 data = new DataReportS21(publisher, reports);
                            data.setYear(currentYear);

                            // Add new item
                            listDataS21.add(data);
                        }
                    } else {
                        Map<String, Report> reports = reportDAO.findReportsByPublisherAndYear(
                                publisher,
                                yearSelected
                        );

                        DataReportS21 data = new DataReportS21(publisher, reports);

                        // Add new item
                        listDataS21.add(data);
                    }
                } else {
                    switch (rgOptionsGroupS21.getCheckedRadioButtonId()) {
                        case R.id.rbAll:
                            this.findAll();
                            break;
                        case R.id.rbPublishers:
                            this.findPublisher(CongReport.PUBLISHER);
                            break;
                        case R.id.rdbAuxiliary:
                            this.findPublisher(CongReport.AUXILIARY);
                            break;
                        case R.id.rdbRegular:
                            this.findPublisher(CongReport.REGULAR);
                            break;
                        case R.id.rdbDeaf:
                            this.findPublisher(CongReport.DEAF);
                            break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void findAll() {
            try {
                List<Publisher> publishers = publisherDAO.findPublishers();
                for (Publisher publisher : publishers) {

                    // Find Reports
                    Map<String, Report> reports = reportDAO.findReportsByPublisherAndYear(
                            publisher,
                            yearSelected
                    );

                    DataReportS21 data = new DataReportS21(publisher, reports);

                    // Add new item
                    listDataS21.add(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void finReportS21ByGroup() {
            try {
                List<Integer> types = new ArrayList<>();
                types.add(CongReport.PUBLISHER);
                types.add(CongReport.DEAF);
                types.add(CongReport.AUXILIARY);
                types.add(CongReport.REGULAR);

                for (Integer type : types) {
                    String label = "";
                    switch (type) {
                        case CongReport.PUBLISHER:
                            label = this.activity.getResources().getString(R.string.label_group_publishers);
                            break;
                        case CongReport.DEAF:
                            label = this.activity.getResources().getString(R.string.label_group_deaf);
                            break;
                        case CongReport.AUXILIARY:
                            label = this.activity.getResources().getString(R.string.label_group_regular_auxiliary);
                            break;
                        case CongReport.REGULAR:
                            label = this.activity.getResources().getString(R.string.label_group_regular_special);
                            break;
                    }
                    Publisher publisher = new Publisher(label);
                    Map<String, Report> reports = reportDAO.findReporTotalByGroup(yearSelected, type);

                    // Add new item
                    DataReportS21 data = new DataReportS21(publisher, reports);
                    listDataS21.add(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void findPublisher(int type) {
            try {
                List<Publisher> publishers = publisherDAO.findPublishersByType(this.activity, type);
                for (Publisher publisher : publishers) {

                    // Find Reports
                    Map<String, Report> reports = reportDAO.findReportsByPublisherAndYear(
                            publisher,
                            yearSelected
                    );

                    DataReportS21 data = new DataReportS21(publisher, reports);


                    // Add new item
                    listDataS21.add(data);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void findDataS88() {
            try {
                listDataS88 = new ArrayList<>();
                switch (rgOptionsGroupS88.getCheckedRadioButtonId()) {
                    case R.id.rbTotal:
                        this.findS88ByType(CongReport.TOTAL);
                        break;
                    case R.id.rbDeaf:
                        this.findS88ByType(CongReport.DEAF);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void findS88ByType(int type) {
            try {
                DataReportS88 middleWeek = assistanceDAO.findReportS88(
                        yearSelected,
                        type,
                        UtilConstants.MID_WEEK
                );
                listDataS88.add(middleWeek);

                DataReportS88 weekend = assistanceDAO.findReportS88(
                        yearSelected,
                        type,
                        UtilConstants.WEEKEND
                );
                listDataS88.add(weekend);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reports, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.itemPrint:
                this.print();
                break;
            default:
                this.finish();
        }
        return true;
    }

    private void print() {
        // Cria o dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_message);
        String msg = this.getString(R.string.msg_function_not_available);

        // Pega os componentes
        TextView text = (TextView) dialog.findViewById(R.id.txtMessage);
        text.setText(msg);

        // Executa a ação
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}
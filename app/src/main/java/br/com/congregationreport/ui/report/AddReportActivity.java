package br.com.congregationreport.ui.report;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import br.com.congregationreport.R;
import br.com.congregationreport.async.SendData;
import br.com.congregationreport.models.Publisher;
import br.com.congregationreport.models.Report;
import br.com.congregationreport.task.TaskRunner;
import br.com.congregationreport.util.Util;
import br.com.congregationreport.util.UtilDataMemory;
import br.com.congregationreport.util.UtilDateHour;
import br.com.congregationreport.util.UtilConstants;

public class AddReportActivity extends AppCompatActivity {


    private RelativeLayout rlAuxiliaryPioneer;
    private CheckBox ckbAuxiliaryPioneer;
    private RadioGroup rdbAuxiliaryType;
    private TextView txtTitle;
    private EditText txtPublications;
    private EditText txtVideos;
    private EditText txtHours;
    private EditText txtRevisits;
    private EditText txtStudies;
    private EditText txtCredit;
    private EditText txtNotes;
    private Button btnAdd;
    private Button btnPreachingFifteenMinLess;
    private LinearLayout llRad;
    private String type;
    private JSONObject data;
    private Publisher publisher;
    private TaskRunner runner;
    private Report currentReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        this.init();
        this.initOnClickListener();
    }


    private void init() {
        try {
            this.runner = new TaskRunner();
            this.publisher = UtilDataMemory.publisher;
            this.txtTitle = (TextView) this.findViewById(R.id.txtTitle);
            this.rlAuxiliaryPioneer = (RelativeLayout) this.findViewById(R.id.rlAuxiliaryPioneer);
            this.ckbAuxiliaryPioneer = (CheckBox) this.findViewById(R.id.ckbAuxiliaryPioneer);
            this.rdbAuxiliaryType = (RadioGroup) this.findViewById(R.id.rdbAuxiliaryType);
            this.txtPublications = (EditText) this.findViewById(R.id.txtPublications);
            this.txtVideos = (EditText) this.findViewById(R.id.txtVideos);
            this.txtHours = (EditText) this.findViewById(R.id.txtHours);
            this.txtRevisits = (EditText) this.findViewById(R.id.txtRevisits);
            this.txtStudies = (EditText) this.findViewById(R.id.txtStudies);
            this.txtCredit = (EditText) this.findViewById(R.id.txtCredit);
            this.llRad = (LinearLayout) this.findViewById(R.id.llRad);
            this.txtNotes = (EditText) this.findViewById(R.id.txtNotes);
            this.btnPreachingFifteenMinLess = (Button) this.findViewById(R.id.btnPreachingFifteenMinLess);
            this.btnAdd = (Button) this.findViewById(R.id.btnAdd);
            this.type = UtilConstants.CREATE;

            if (this.publisher.isPublisherBaptized()) {
                this.rlAuxiliaryPioneer.setVisibility(View.VISIBLE);
            }
            this.createTitle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTitle() {
        try {
            String currentMonth = UtilDateHour.getCurrentMonthForReport();
            String title = this.getResources().getString(R.string.your_report) + UtilDateHour.getMonthReportByName(this, currentMonth);
            this.txtTitle.setText(title);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void initOnClickListener() {
        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    createDataJSON(false);
                }
            }
        });

        this.btnPreachingFifteenMinLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDataJSON(true);
            }
        });
        this.ckbAuxiliaryPioneer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                llRad.setVisibility(checkBox.isChecked() ? View.VISIBLE : View.GONE);
            }
        });
    }


    private void createConfirmData(boolean preachingFifteenMinLess) {
        try {
            // Cria o dialog
            final Dialog dialog = new Dialog(AddReportActivity.this);
            if (preachingFifteenMinLess) {
                dialog.setContentView(R.layout.dialog_option);

                TextView text = (TextView) dialog.findViewById(R.id.txtMessage);
                text.setText(this.getResources().getString(R.string.msg_is_preaching_fifteen_min_less));
            } else {
                dialog.setContentView(R.layout.data_report);

                TextView txtPublications = (TextView) dialog.findViewById(R.id.txtPublications);
                TextView txtVideos = (TextView) dialog.findViewById(R.id.txtVideos);
                TextView txtHour = (TextView) dialog.findViewById(R.id.txtHour);
                TextView txtRevisited = (TextView) dialog.findViewById(R.id.txtRevisited);
                TextView txtCourses = (TextView) dialog.findViewById(R.id.txtCourses);
                TextView txtCredit = (TextView) dialog.findViewById(R.id.txtCredit);
                TextView txtNotes = (TextView) dialog.findViewById(R.id.txtNotes);

                try {
                    txtPublications.setText(this.currentReport.getPublications() == null
                            ? ""
                            : this.currentReport.getPublications().toString());
                    // -------------------------------------------------------------
                    txtVideos.setText(this.currentReport.getVideos() == null
                            ? ""
                            : this.currentReport.getVideos().toString());
                    // -------------------------------------------------------------
                    txtHour.setText(this.currentReport.getHours() == null
                            ? ""
                            : this.currentReport.getHours().toString());
                    // -------------------------------------------------------------
                    txtRevisited.setText(this.currentReport.getRevisited() == null
                            ? ""
                            : this.currentReport.getRevisited().toString());
                    // -------------------------------------------------------------
                    txtCourses.setText(this.currentReport.getBibleCourses() == null
                            ? ""
                            : this.currentReport.getBibleCourses().toString());
                    // -------------------------------------------------------------
                    txtCredit.setText(this.currentReport.getCredit() == null
                            ? ""
                            : this.currentReport.getCredit().toString());
                    // -------------------------------------------------------------
                    txtNotes.setText(this.currentReport.getNotes() == null
                            ? ""
                            : this.currentReport.getNotes().toString());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }


                // Pega os componentes
                TextView text = (TextView) dialog.findViewById(R.id.txtMessage);
                text.setText(this.getResources().getString(R.string.msg_is_information_correct));
            }


            // Fecha o dialog
            Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    runner.executeAsync(new SendData(AddReportActivity.this, data));
                }
            });

            // Fecha o dialog
            Button btnCancelar = (Button) dialog.findViewById(R.id.btnCancelar);
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private boolean validation() {
        boolean validation = true;
        try {
            String hour = this.txtHours.getText().toString();
            if (this.ckbAuxiliaryPioneer.isChecked()) {
                boolean selected = false;
                for (int i = 0; i < this.rdbAuxiliaryType.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) this.rdbAuxiliaryType.getChildAt(i);
                    if (radioButton.isChecked()) {
                        selected = true;
                        break;
                    }
                }
                if (!selected) {
                    Util.createToast(this, this.getResources().getString(R.string.msg_selected_auxiliary));
                    return false;
                }
            }
            if (hour.trim().isEmpty()) {
                this.txtHours.setError(this.getResources().getString(R.string.label_hour_requeride));
                this.txtHours.requestFocus();
                return false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return validation;
    }

    private void createDataJSON(boolean preachingFifteenMinLess) {
        try {
            this.data = new JSONObject();
            boolean auxiliaryPioneer = this.ckbAuxiliaryPioneer.isChecked();
            String publications = this.txtPublications.getText().toString();
            String videos = this.txtVideos.getText().toString();
            String hours = this.txtHours.getText().toString();
            String revisits = this.txtRevisits.getText().toString();
            String studies = this.txtStudies.getText().toString();
            String credit = this.txtCredit.getText().toString();
            String notes = this.txtNotes.getText().toString();

            this.data.put("type", this.type);
            this.data.put("model", "report");

            String currentMonth = UtilDateHour.getCurrentMonthForReport();

            this.currentReport = new Report();
            this.currentReport.setIdPublisher(UtilDataMemory.publisher.getId());

            this.currentReport.setPublications(publications.isEmpty() ? 0 : Integer.parseInt(publications));
            this.currentReport.setVideos(videos.isEmpty() ? 0 : Integer.parseInt(videos));
            this.currentReport.setHours(hours.isEmpty() ? 0 : Integer.parseInt(hours));
            this.currentReport.setRevisited(revisits.isEmpty() ? 0 : Integer.parseInt(revisits));
            this.currentReport.setBibleCourses(studies.isEmpty() ? 0 : Integer.parseInt(studies));
            this.currentReport.setCredit(credit.isEmpty() ? 0 : Integer.parseInt(credit));
            this.currentReport.setAuxiliaryPioneer(auxiliaryPioneer);

            String type = "";
            if (this.currentReport.isAuxiliaryPioneer()) {
                if (this.rdbAuxiliaryType.getCheckedRadioButtonId() == R.id.thirtyHours) {
                    type = UtilConstants.HOURS_30;
                }
                if (this.rdbAuxiliaryType.getCheckedRadioButtonId() == R.id.fiftyHours) {
                    type = UtilConstants.HOURS_50;
                }
            }
            this.currentReport.setAuxiliaryPioneerType(type);
            this.currentReport.setPreachingFifteenMinLess(preachingFifteenMinLess);

            this.currentReport.setNotes(notes);
            this.currentReport.setMonth(currentMonth);
            this.data.put("object", Report.getJson(this.currentReport));

            createConfirmData(preachingFifteenMinLess);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void onConfigurationChanged(Configuration cfg) {
        super.onConfigurationChanged(cfg);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }


}
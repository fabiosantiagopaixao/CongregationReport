package br.com.congregationreport.ui.assistance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import br.com.congregationreport.R;
import br.com.congregationreport.async.SendData;
import br.com.congregationreport.db.dao.AssistanceDAO;
import br.com.congregationreport.models.Assistance;
import br.com.congregationreport.util.Util;
import br.com.congregationreport.util.UtilDataMemory;
import br.com.congregationreport.util.UtilDateHour;
import br.com.congregationreport.util.UtilConstants;

public class AddAssistanceActivity extends AppCompatActivity {

    private TextView txtTitle;
    private EditText txtDate;
    private EditText txtAmountDeaf;
    private EditText txtAmountTotal;
    private ImageButton btnDate;
    private Button btnAdd;
    private String type;
    private JSONObject data;
    private Assistance currentAssistance;
    private AssistanceDAO assistanceDAO;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assistance);
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
            this.assistanceDAO = UtilDataMemory.getAssistanceDAO(this);
            this.txtTitle = (TextView) this.findViewById(R.id.txtTitle);
            this.txtDate = (EditText) this.findViewById(R.id.txtDate);
            this.txtAmountDeaf = (EditText) this.findViewById(R.id.txtAmountDeaf);
            this.txtAmountTotal = (EditText) this.findViewById(R.id.txtAmountTotal);
            this.btnDate = (ImageButton) findViewById(R.id.btnDate);
            this.btnAdd = (Button) findViewById(R.id.btnAdd);
            this.type = UtilConstants.CREATE;

            this.createTitle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTitle() {
        try {
            String title = this.getResources().getString(R.string.your_assistance) + " " + UtilDateHour.getCurrentMonth(this);
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
                    createDataJSON();
                    createConfirmData();
                }
            }
        });
        this.btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddAssistanceActivity.this,
                        R.style.MyDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void createConfirmData() {
        try {
            // Cria o dialog
            final Dialog dialog = new Dialog(AddAssistanceActivity.this);
            dialog.setContentView(R.layout.data_assistance);


            TextView txtType = (TextView) dialog.findViewById(R.id.txtType);
            TextView txtDate = (TextView) dialog.findViewById(R.id.txtDate);
            TextView txtAmountDeaf = (TextView) dialog.findViewById(R.id.txtAmountDeaf);
            TextView txtAmountTotal = (TextView) dialog.findViewById(R.id.txtAmountTotal);

            try {
                txtDate.setText(this.currentAssistance.getDate() == null
                        ? ""
                        : this.currentAssistance.getDate());
                // -------------------------------------------------------------
                txtAmountDeaf.setText(this.currentAssistance.getAmountDeaf() == null
                        ? ""
                        : this.currentAssistance.getAmountDeaf().toString());
                // -------------------------------------------------------------
                txtAmountTotal.setText(this.currentAssistance.getAmountTotal() == null
                        ? ""
                        : this.currentAssistance.getAmountTotal().toString());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            // Pega os componentes
            Date dateFormatter = UtilDateHour.stringToDate(UtilDateHour.DATE_HOUR, this.currentAssistance.getDate() + " 00:00:00");
            txtType.setText(UtilDateHour.getTypeAssistanceByDate(this, dateFormatter, true));
            // -----
            TextView text = (TextView) dialog.findViewById(R.id.txtMessage);
            text.setText(this.getResources().getString(R.string.msg_is_information_correct));


            // Fecha o dialog
            Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    new SendData(AddAssistanceActivity.this, data).execute();
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
        try {
            String date = this.txtDate.getText().toString();
            String amountDeaf = this.txtAmountDeaf.getText().toString();
            String amountTotal = this.txtAmountTotal.getText().toString();
            if (date.trim().isEmpty()) {
                this.txtDate.setError(this.getResources().getString(R.string.label_date_requeride));
                Toast.makeText(this, this.getResources().getString(R.string.label_date_requeride), Toast.LENGTH_SHORT).show();
                this.txtDate.requestFocus();
                return false;
            }
            Date data = UtilDateHour.stringToDate(UtilDateHour.DATE_HOUR, date + " 00:00:00");
            Date d = UtilDateHour.stringToDate(UtilDateHour.DATE_HOUR, UtilDateHour.formataDataHora(UtilDateHour.DATE, new Date()) + " 00:00:00");
            Date futureDate = UtilDateHour.addDias(d, 1);
            if (data.getTime() >= futureDate.getTime()) {
                this.txtDate.setError(this.getResources().getString(R.string.label_date_error_future));
                Toast.makeText(this, this.getResources().getString(R.string.label_date_error_future), Toast.LENGTH_SHORT).show();
                this.txtDate.requestFocus();
                return false;
            }

            if (amountDeaf.trim().isEmpty()) {
                this.txtAmountDeaf.setError(this.getResources().getString(R.string.label_amount_requeride));
                this.txtAmountDeaf.requestFocus();
                return false;
            }
            if (amountTotal.trim().isEmpty()) {
                this.txtAmountTotal.setError(this.getResources().getString(R.string.label_amount_requeride));
                this.txtAmountTotal.requestFocus();
                return false;
            }

            if (Integer.parseInt(amountTotal) < Integer.parseInt(amountDeaf)) {
                this.txtAmountTotal.setError(this.getResources().getString(R.string.label_amount_less_than));
                this.txtAmountTotal.requestFocus();
                return false;
            }

            Assistance assistanceDB = this.assistanceDAO.getAssistanceByDate(date);
            if (assistanceDB != null) {
                Util.createMessageAlert(this, this.getResources().getString(R.string.alredy_assistance_count));
                return false;
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private void createDataJSON() {
        try {
            this.data = new JSONObject();
            String date = this.txtDate.getText().toString();
            String amountDeaf = this.txtAmountDeaf.getText().toString();
            String amountTotal = this.txtAmountTotal.getText().toString();

            this.data.put("type", this.type);
            this.data.put("model", "assistance");

            this.currentAssistance = new Assistance();
            this.currentAssistance.setYear(Integer.parseInt(UtilDateHour.getCurrentYear()));
            this.currentAssistance.setMonth(UtilDateHour.getMonthByDate(this, UtilDateHour.stringToDate(UtilDateHour.DATE, date)));
            Date data = UtilDateHour.stringToDate(UtilDateHour.DATE_HOUR, date + " 00:00:00");
            this.currentAssistance.setType(UtilDateHour.getTypeAssistanceByDate(this, data, false));
            this.currentAssistance.setAmountDeaf(Integer.parseInt(amountDeaf));
            this.currentAssistance.setDate(date);
            this.currentAssistance.setAmountTotal(Integer.parseInt(amountTotal));
            this.data.put("object", Assistance.getJson(this.currentAssistance));
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
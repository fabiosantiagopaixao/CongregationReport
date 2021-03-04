package br.com.congregationreport.ui.publisher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.congregationreport.R;
import br.com.congregationreport.db.dao.GroupDAO;
import br.com.congregationreport.db.dao.PublisherDAO;
import br.com.congregationreport.models.Group;
import br.com.congregationreport.models.Params;
import br.com.congregationreport.models.Publisher;
import br.com.congregationreport.task.BaseTask;
import br.com.congregationreport.task.TaskRunner;
import br.com.congregationreport.util.Util;
import br.com.congregationreport.util.UtilConstants;
import br.com.congregationreport.util.UtilDataMemory;
import br.com.congregationreport.util.UtilHtml;
import br.com.congregationreport.util.UtilPDF;


public class PublisherActivity extends AppCompatActivity {

    private ScrollView rlData;
    private RadioGroup rgOptionsGroups;
    private TextView txtNumbersPublishers;
    private List<Publisher> publishers;
    private PublisherDAO publisherDAO;
    private GroupDAO groupDAO;
    private TaskRunner runner;
    private int oldTxtData;
    private String groupSelected;
    private RelativeLayout layoutLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher);
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
        getMenuInflater().inflate(R.menu.menu_reports, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void print() {
        try {
            this.runner.executeAsync(new GenerateDataForPrint());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void init() {
        try {
            this.runner = new TaskRunner();
            this.publisherDAO = UtilDataMemory.getPublisherDAO(this);
            this.groupDAO = UtilDataMemory.getGroupDAO(this);
            this.rlData = this.findViewById(R.id.rlData);
            this.txtNumbersPublishers = this.findViewById(R.id.txtNumbersPublishers);
            this.rgOptionsGroups = this.findViewById(R.id.rgOptionsGroups);
            this.createRadionsButtons();
            this.initListeners();
            this.runner.executeAsync(new LoadingData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createRadionsButtons() {
        try {
            List<Group> groups = this.groupDAO.findGroups();
            for (Group group : groups) {
                RadioButton rd = new RadioButton(this);
                rd.setText(group.getName());

                this.rgOptionsGroups.addView(rd);
                this.rgOptionsGroups.requestLayout();
            }
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

    }

    private void initListeners() {
        try {
            this.rgOptionsGroups.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedNowId) {
                    runner.executeAsync(new LoadingData());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initScreenSelected() {
        try {
            this.cleanDataScreen();
            this.createPublisherReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createPublisherReport() {
        try {
            Integer size = this.publishers.size();
            this.txtNumbersPublishers.setText(size.toString());

            LayoutInflater layoutInflater =
                    (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.relative_layout, null);

            // Create data
            for (int i = 0; i < this.publishers.size(); i++) {
                Publisher publisher = this.publishers.get(i);
                this.createScreen(view, publisher, i);
            }
            this.rlData.addView(view);
        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
        }

    }

    private void createScreen(View viewScreen, Publisher publisher, int row) {
        RelativeLayout relative = (RelativeLayout) viewScreen;
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.detail_publisher, null);

            TextView txtName = view.findViewById(R.id.txtName);
            String name = publisher.getLastName() == null || publisher.getLastName().isEmpty()
                    ? publisher.getName() : publisher.getName() + " " + publisher.getLastName();
            txtName.setText(name);
            // ----------
            RelativeLayout rlPersonalData = view.findViewById(R.id.rlPersonalData);
            TextView txtTitlePersonal = view.findViewById(R.id.txtTitlePersonal);
            TableLayout tablePersonalData = view.findViewById(R.id.tablePersonalData);
            TextView txtGender = view.findViewById(R.id.txtGender);
            txtGender.setText(publisher.getGender().equals(UtilConstants.Man)
                    ? this.getResources().getString(R.string.male)
                    : this.getResources().getString(R.string.female));
            TextView txtBirth = view.findViewById(R.id.txtBirth);
            txtBirth.setText(publisher.getBirth() == null ? "" : publisher.getBirth());
            TextView txtAddress = view.findViewById(R.id.txtAddress);
            txtAddress.setText(publisher.getAddress() == null ? "" : publisher.getAddress());
            TextView txtMobile = view.findViewById(R.id.txtMobile);
            final String mobile = publisher.getCellPhone() == null ? "" : publisher.getCellPhone();
            txtMobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialogContact(mobile);
                }
            });
            txtMobile.setText(publisher.getCellPhone() == null ? "" : publisher.getCellPhone());
            txtMobile.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            TextView txtEmail = view.findViewById(R.id.txtEmail);
            txtEmail.setText(publisher.getEmail() == null ? "" : publisher.getEmail());
            // ------------
            RelativeLayout rlEmergencyContact = view.findViewById(R.id.rlEmergencyContact);
            TextView txtTitleContact = view.findViewById(R.id.txtTitleContact);
            HorizontalScrollView hsEmergencyContactData = view.findViewById(R.id.hsEmergencyContactData);
            //------------
            // Contact Data 1
            TableLayout tableEmergency1 = view.findViewById(R.id.tableEmergency1);
            TextView txtName1 = view.findViewById(R.id.txtName1);
            txtName1.setText(publisher.getContactName1() == null ? "" : publisher.getContactName1());
            TextView txtNota1 = view.findViewById(R.id.txtNota1);
            txtNota1.setText(publisher.getContactNote1() == null ? "" : publisher.getContactNote1());
            CheckBox txtJehovas1 = view.findViewById(R.id.txtJehovas1);
            txtJehovas1.setChecked(publisher.isJehovahsWitness1());
            TextView txtMobile1 = view.findViewById(R.id.txtMobile1);
            txtMobile1.setText(publisher.getContactPhone1() == null ? "" : publisher.getContactPhone1());
            txtMobile1.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            final String mobile1 = publisher.getContactPhone1() == null ? "" : publisher.getContactPhone1();
            txtMobile1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialogContact(mobile1);
                }
            });
            TextView txtAddress1 = view.findViewById(R.id.txtAddress1);
            txtAddress1.setText(publisher.getContactAddress1() == null ? "" : publisher.getContactAddress1());
            // Contact Data 2
            TableLayout tableEmergency2 = view.findViewById(R.id.tableEmergency2);
            TextView txtName2 = view.findViewById(R.id.txtName2);
            txtName2.setText(publisher.getContactName2() == null ? "" : publisher.getContactName2());
            TextView txtNota2 = view.findViewById(R.id.txtNota2);
            txtNota2.setText(publisher.getContactNote2() == null ? "" : publisher.getContactNote2());
            CheckBox txtJehovas2 = view.findViewById(R.id.txtJehovas2);
            txtJehovas2.setChecked(publisher.isJehovahsWitness2());
            TextView txtMobile2 = view.findViewById(R.id.txtMobile2);
            txtMobile2.setText(publisher.getContactPhone2() == null ? "" : publisher.getContactPhone2());
            txtMobile2.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            final String mobile2 = publisher.getContactPhone2() == null ? "" : publisher.getContactPhone2();
            txtMobile2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialogContact(mobile2);
                }
            });
            TextView txtAddress2 = view.findViewById(R.id.txtAddress2);
            txtAddress2.setText(publisher.getContactAddress2() == null ? "" : publisher.getContactAddress2());


            // Change ids
            if (row > 0) {
                int idRlDataPublisher = Util.getRamdomBumber();
                view.setId(idRlDataPublisher);
                Params margin = new Params();
                margin.setTop(20);
                this.addComponenteBelow(view, this.oldTxtData, margin);
                margin = new Params();
                margin.setTop(10);
                // ----------
                int idTxtName = Util.getRamdomBumber();
                txtName.setId(idTxtName);
                // ----------
                int idRlPersonalData = Util.getRamdomBumber();
                rlPersonalData.setId(idRlPersonalData);
                this.addComponenteBelow(rlPersonalData, idTxtName, margin);
                // ----------
                int idTxtTitlePersonal = Util.getRamdomBumber();
                txtTitlePersonal.setId(idTxtTitlePersonal);
                // ----------
                int idTablePersonalData = Util.getRamdomBumber();
                tablePersonalData.setId(idTablePersonalData);
                this.addComponenteBelow(tablePersonalData, idTxtTitlePersonal, margin);
                // ----------
                int idTxtGender = Util.getRamdomBumber();
                txtGender.setId(idTxtGender);
                // ----------
                int idTxtBirth = Util.getRamdomBumber();
                txtBirth.setId(idTxtBirth);
                // ----------
                int idTxtAddress = Util.getRamdomBumber();
                txtAddress.setId(idTxtAddress);
                // ----------
                int idTxtMobile = Util.getRamdomBumber();
                txtMobile.setId(idTxtMobile);
                // ----------
                int idTxtEmail = Util.getRamdomBumber();
                txtEmail.setId(idTxtEmail);
                // ----------
                int idRlEmergencyContact = Util.getRamdomBumber();
                rlEmergencyContact.setId(idRlEmergencyContact);
                this.addComponenteBelow(rlEmergencyContact, idRlPersonalData, margin);
                // ----------
                int idTxtTitleContact = Util.getRamdomBumber();
                txtTitleContact.setId(idTxtTitleContact);
                // ----------
                int idHsEmergencyContactData = Util.getRamdomBumber();
                hsEmergencyContactData.setId(idHsEmergencyContactData);
                this.addComponenteBelow(hsEmergencyContactData, idTxtTitleContact, margin);
                // ----------
                int idTableEmergency1 = Util.getRamdomBumber();
                tableEmergency1.setId(idTableEmergency1);
                // ----------
                int idTxtName1 = Util.getRamdomBumber();
                txtName1.setId(idTxtName1);
                // ----------
                int idTxtNota1 = Util.getRamdomBumber();
                txtNota1.setId(idTxtNota1);
                // ----------
                int idTxtJehovas1 = Util.getRamdomBumber();
                txtJehovas1.setId(idTxtJehovas1);
                // ----------
                int idTxtMobile1 = Util.getRamdomBumber();
                txtMobile1.setId(idTxtMobile1);
                int idTxtAddress1 = Util.getRamdomBumber();
                txtAddress1.setId(idTxtAddress1);
                // ----------
                // Contact 2
                int idTableEmergency2 = Util.getRamdomBumber();
                tableEmergency2.setId(idTableEmergency2);
                this.addComponenteBelow(tableEmergency2, idTableEmergency1, margin);
                // ----------
                int idTxtName2 = Util.getRamdomBumber();
                txtName2.setId(idTxtName2);
                // ----------
                int idTxtNota2 = Util.getRamdomBumber();
                txtNota2.setId(idTxtNota2);
                // ----------
                int idTxtJehovas2 = Util.getRamdomBumber();
                txtJehovas2.setId(idTxtJehovas2);
                // ----------
                int idTxtMobile2 = Util.getRamdomBumber();
                txtMobile2.setId(idTxtMobile2);
                // ----------
                int idTxtAddress2 = Util.getRamdomBumber();
                txtAddress2.setId(idTxtAddress2);
            }
            // Set ids previous line
            this.oldTxtData = view.getId();
            relative.addView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDialogContact(final String number) {
        try {
            // Cria o dialog
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_contact);

            // Executa a ação
            Button btnCall = (Button) dialog.findViewById(R.id.btnCall);
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    call(number);
                }
            });

            Button btnWhatsapp = (Button) dialog.findViewById(R.id.btnWhatsapp);
            btnWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callWhatsap(number);
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callWhatsap(String number) {
        try {
            String text = number.contains("+") ? number : "+591" + number;

            Uri uri = Uri.parse("smsto:" + text);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(i, ""));

        } catch (Exception e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void call(String number) {
        try {
            Intent intent = new Intent(
                    Intent.ACTION_DIAL,
                    Uri.fromParts("tel", number, null)
            );
            startActivity(intent);
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
                        Util.convertDps(view.getContext(), margin.getLeft()),
                        Util.convertDps(view.getContext(), margin.getTop()),
                        Util.convertDps(view.getContext(), margin.getRight()),
                        Util.convertDps(view.getContext(), margin.getBottom())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanDataScreen() {
        try {
            this.rlData.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class LoadingData extends BaseTask {

        private TextView txtMessage;
        private Activity activity;

        public LoadingData() {
            this.activity = (Activity) PublisherActivity.this;
        }

        @Override
        public void setUiForLoading() {
            layoutLoading = (RelativeLayout) this.activity.findViewById(
                    R.id.layoutLoading);
            this.txtMessage = (TextView) this.activity.findViewById(
                    R.id.txtMessage);
            this.txtMessage.setText(this.activity.getResources().getString(R.string.msg_loading_data));
            layoutLoading.requestLayout();
            layoutLoading.setVisibility(View.VISIBLE);
        }

        @Override
        public Object callInBackground() {
            try {
                String group = "";
                for (int i = 0; i < rgOptionsGroups.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) rgOptionsGroups.getChildAt(i);
                    if (radioButton.isChecked()) {
                        group = radioButton.getText().toString();
                    }
                }
                if (group.equals(this.activity.getResources().getString(R.string.label_all))) {
                    groupSelected = "";
                    this.findAll();
                } else {
                    groupSelected = group;
                    this.findByGroup(group);
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
            }
            layoutLoading.setVisibility(View.GONE);
        }


        private void findAll() {
            try {
                publishers = publisherDAO.findPublishers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void findByGroup(String group) {
            try {
                publishers = publisherDAO.findPublisherByGroup(group);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class GenerateDataForPrint extends BaseTask {

        private TextView txtMessage;
        private Activity activity;
        private String html;

        public GenerateDataForPrint() {
            this.activity = (Activity) PublisherActivity.this;
        }

        @Override
        public void setUiForLoading() {
            layoutLoading = (RelativeLayout) this.activity.findViewById(
                    R.id.layoutLoading);
            this.txtMessage = (TextView) this.activity.findViewById(
                    R.id.txtMessage);
            this.txtMessage.setText(this.activity.getResources().getString(R.string.msg_generating_data_printing));
            layoutLoading.requestLayout();
            layoutLoading.setVisibility(View.VISIBLE);
        }

        @Override
        public Object callInBackground() {
            boolean generated = false;
            try {
                generated = this.generatingData();
            } catch (Exception e) {
                return Util.ERROR;
            }
            return generated ? Util.OK : Util.ERROR;
        }


        @Override
        public void setDataAfterLoading(Object object) {
            Integer result = (Integer) object;
            if (result == Util.OK) {
                printData();
            }
            if (result == Util.ERROR) {
                Toast toast = Toast.makeText(
                        this.activity,
                        this.activity.getResources().getString(R.string.msg_generating_data_print_error),
                        Toast.LENGTH_LONG
                );
                toast.show();
            }
        }


        private boolean generatingData() {
            try {
                String topHtml = UtilHtml.getPublisherPageTop();

                String pages = "";
                int numberPage = 1;
                Float resultPage = publishers.size() / 2f;
                Integer totalPage = resultPage.toString().contains(".5")
                        ? Integer.parseInt(
                        resultPage.toString().replace(".5", "")
                ) + 1 : Integer.parseInt(
                        resultPage.toString().replace(".0", ""));

                for (int i = 0; i < publishers.size(); i += 2) {

                    // Init page
                    String name = this.activity.getResources().getString(R.string.label_emergency_contacts);
                    String g = groupSelected == null || groupSelected.isEmpty() ? "" : " (" + groupSelected + ")";
                    String page = UtilHtml.getPublisherPageInit(name + g);

                    // Add Content 1
                    Publisher publisher = publishers.get(i);
                    String content = UtilHtml.getPublisherContentHtml(this.activity, publisher, groupSelected != null);
                    page = page + content;

                    int secondValue = i + 1;
                    if (secondValue < publishers.size()) {
                        Publisher publisherSecond = publishers.get(secondValue);
                        String contentSecond = UtilHtml.getPublisherContentHtml(this.activity, publisherSecond, groupSelected != null);
                        page = page + contentSecond;
                    }

                    // End Page
                    String pageEnd = UtilHtml.getPublisherPageEnd(numberPage + "/" + totalPage);
                    page = page + pageEnd;

                    // Add page to pages
                    pages = pages + page;


                    numberPage++;
                }


                String bottom = "\n" +
                        "\t</body>\n" +
                        "</html>";

                this.html = topHtml + pages + bottom;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        private void printData() {
            try {
                UtilPDF.generatePdfFromHtlm(
                        this.activity,
                        this.html
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.layoutLoading != null) {
            this.layoutLoading.setVisibility(View.GONE);
        }
    }
}
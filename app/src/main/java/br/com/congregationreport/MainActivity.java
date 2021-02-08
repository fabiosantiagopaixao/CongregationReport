package br.com.congregationreport;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.congregationreport.async.SendData;
import br.com.congregationreport.db.dao.AppDAO;
import br.com.congregationreport.db.dao.LoginDAO;
import br.com.congregationreport.models.App;
import br.com.congregationreport.models.Assistance;
import br.com.congregationreport.models.Login;
import br.com.congregationreport.models.Publisher;
import br.com.congregationreport.service.NotificationService;
import br.com.congregationreport.task.TaskRunner;
import br.com.congregationreport.ui.assistance.AddAssistanceActivity;
import br.com.congregationreport.util.Util;
import br.com.congregationreport.util.UtilConstants;
import br.com.congregationreport.util.UtilDataMemory;
import br.com.congregationreport.util.UtilDateHour;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private ImageView imgUser;
    private TextView txtUser;
    private TextView txtInfo;
    private TextView txtNameCongregation;
    private TextView txtVersion;
    private LoginDAO loginDAO;
    private TextView txtPassword;
    private TextView txtNewPassword;
    private Login login;
    private AppDAO appDAO;
    private App app;
    private TaskRunner runner;
    private static String CHANNEL_ID = "REPORT_CHANNEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        this.navigationView = findViewById(R.id.nav_view);

        Publisher publisher = UtilDataMemory.publisher;
        if (publisher.isElder()) {
            this.navigationView.inflateMenu(R.menu.menu_lateral_adm);
        }
        // Passing each menu ID as a set of Ids because each


        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_group)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(this.navigationView, navController);

        this.init();
    }

    private void init() {
        this.runner = new TaskRunner();
        View headerLayout = this.navigationView.getHeaderView(0);
        this.imgUser = (ImageView) headerLayout.findViewById(R.id.imgUser);
        String female = UtilConstants.Woman;
        if (UtilDataMemory.publisher.getGender().equals(female)) {
            this.imgUser.setImageResource(R.mipmap.user_women);
        }
        this.txtUser = (TextView) headerLayout.findViewById(R.id.txtUser);
        this.txtUser.setText(UtilDataMemory.publisher.getCustomName(this));
        this.txtInfo = (TextView) headerLayout.findViewById(R.id.txtInfo);
        this.txtInfo.setText(UtilDataMemory.publisher.getBottomName(this));
        this.txtNameCongregation = (TextView) headerLayout.findViewById(R.id.txtNameCongregation);
        this.txtVersion = (TextView) headerLayout.findViewById(R.id.txtVersion);

        // Find App
        this.txtNameCongregation.setText(UtilDataMemory.publisher.getBottomName(this));
        this.txtNameCongregation.setText(UtilDataMemory.setting.getNameCongregation() + " " + UtilDataMemory.setting.getNumberCongregation());
        PackageInfo pInfo = null;
        try {
            pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo == null ? "1.0" : pInfo.versionName;
        this.txtVersion.setText(version);

        this.loginDAO = new LoginDAO(this);
        this.login = this.loginDAO.getDataLogin();
        this.appDAO = new AppDAO(this);
        this.app = this.appDAO.getApp();
        if (this.app.isFirstRun()) {
            this.createMessageChangePassword();
        }
    }

    private void createMessageChangePassword() {
        try {
            // Cria o dialog
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_change_password);
            dialog.setTitle(this.getResources().getString(R.string.title_save_data_login));

            // Pega os componentes
            this.txtPassword = (TextView) dialog.findViewById(R.id.txtPassword);
            this.txtNewPassword = (TextView) dialog.findViewById(R.id.txtNewPassword);

            // Fecha o dialog
            Button btnNao = (Button) dialog.findViewById(R.id.btnCancelar);
            btnNao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    app.setFirstRun(false);
                    appDAO.update(app);
                    dialog.dismiss();
                }
            });

            // Executa a ação
            Button btnSim = (Button) dialog.findViewById(R.id.btnOk);
            btnSim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateNewDataPassword()) {
                        sendNewPassword();
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private boolean validateNewDataPassword() {
        try {
            String password = this.txtPassword.getText().toString();
            String newPassword = this.txtNewPassword.getText().toString();

            if (this.login != null && password.equals(this.login.getPassword())) {
                Util.createToast(this, this.getResources().getString(R.string.password_cannot_be_the_same));
                return false;
            }
            if (!password.equals(newPassword)) {
                Util.createToast(this, this.getResources().getString(R.string.password_are_diffetent));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean sendNewPassword() {
        try {
            String password = this.txtPassword.getText().toString();

            // Senda update
            this.runner.executeAsync(new SendData(MainActivity.this, createDataJSON(password)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private JSONObject createDataJSON(String password) {
        JSONObject data = new JSONObject();
        try {

            data.put("type", UtilConstants.UPDATE);
            data.put("model", "publisher");

            UtilDataMemory.publisher.setPassword(password);

            data.put("object", Publisher.getJson(UtilDataMemory.publisher));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id) {
            /*case R.id.action_settings:
                break;*/
            case R.id.action_close:
                try {
                    this.exitApp();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case R.id.action_change_user:
                try {
                    this.changeUser();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case R.id.action_logout:
                this.logout();
                break;
            case R.id.itemPrint:
                this.print();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //startService(new Intent(this, NotificationService.class));
    }

    private void changeUser() {
        // Cria o dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_option);
        String msg = this.getString(R.string.msg_change_user);

        // Pega os componentes
        TextView text = (TextView) dialog.findViewById(R.id.txtMessage);
        text.setText(msg);

        // Fecha o dialog
        Button btnNao = (Button) dialog.findViewById(R.id.btnCancelar);
        btnNao.setText(this.getString(R.string.label_no));
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Executa a ação
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (login != null && login.getId() > 0) {
                    loginDAO.delete(login.getId());
                }
                Intent it = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(it);
            }
        });
        dialog.show();
    }

    private void logout() {
        // Cria o dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_option);
        String msg = this.getString(R.string.msg_re_login);

        // Pega os componentes
        TextView text = (TextView) dialog.findViewById(R.id.txtMessage);
        text.setText(msg);

        // Fecha o dialog
        Button btnNao = (Button) dialog.findViewById(R.id.btnCancelar);
        btnNao.setText(this.getString(R.string.label_no));
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Executa a ação
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Login login = loginDAO.getDataLogin();
                if (login != null && login.getId() > 0) {
                    loginDAO.delete(login.getId());
                }
                Intent it = new Intent(MainActivity.this, ConnectionActivity.class);
                MainActivity.this.startActivity(it);
            }
        });
        dialog.show();
    }

    private void exitApp() {
        // Cria o dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_option);
        String msg = this.getString(R.string.msg_logout);

        // Pega os componentes
        TextView text = (TextView) dialog.findViewById(R.id.txtMessage);
        text.setText(msg);

        // Fecha o dialog
        Button btnNao = (Button) dialog.findViewById(R.id.btnCancelar);
        btnNao.setText(this.getString(R.string.label_no));
        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Executa a ação
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        dialog.show();
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Disables back button so user cannot skip accepting terms and conditions
    @Override
    public void onBackPressed() {
    }
}
package br.com.congregationreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import java.util.Date;
import br.com.congregationreport.async.DownloadDataGoogleSheetTask;
import br.com.congregationreport.db.dao.AppDAO;
import br.com.congregationreport.models.App;
import br.com.congregationreport.task.TaskRunner;
import br.com.congregationreport.util.Util;
import br.com.congregationreport.util.UtilDataMemory;
import br.com.congregationreport.util.UtilDateHour;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private AppDAO appDAO;
    private TaskRunner runner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.runner = new TaskRunner();
        this.appDAO = UtilDataMemory.getLocalDAO(this);
        final App app = this.appDAO.getApp();
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (app == null || app.getUrl() == null) {
                    Intent it = new Intent(
                            SplashScreenActivity.this,
                            ConnectionActivity.class
                    );
                    startActivity(it);
                } else {
                    Date dateUpdateData = UtilDateHour.addHoras(app.getLastUpdated(), 12);

                    boolean deviceOnline = Util.isDeviceOnline(SplashScreenActivity.this);
                    Date dateNow = new Date();
                    if (dateNow.getTime() >= dateUpdateData.getTime() && deviceOnline) {
                        runner.executeAsync(new DownloadDataGoogleSheetTask(
                                SplashScreenActivity.this,
                                app.getUrl())
                        );
                    } else {
                        Intent it = new Intent(
                                SplashScreenActivity.this,
                                LoginActivity.class
                        );
                        SplashScreenActivity.this.startActivity(it);
                    }

                }
            }
        }, SPLASH_TIME_OUT);

    }

    //Disables back button so user cannot skip accepting terms and conditions
    @Override
    public void onBackPressed() {
    }
}
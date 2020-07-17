package br.com.congregationreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import br.com.congregationreport.db.dao.LocalDAO;
import br.com.congregationreport.models.Local;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private LocalDAO localDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.localDAO = new LocalDAO(this);
        final Local local = this.localDAO.getDadosLocais();

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                if (local == null || local.getUrl() == null) {
                    Intent it = new Intent(
                            SplashScreenActivity.this,
                            ConnectionActivity.class
                    );
                    startActivity(it);
                } else {
                    Intent it = new Intent(
                            SplashScreenActivity.this,
                            LoginActivity.class
                    );
                    startActivity(it);
                }
            }
        }, SPLASH_TIME_OUT);

    }
}
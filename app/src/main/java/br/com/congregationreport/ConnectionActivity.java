package br.com.congregationreport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.congregationreport.async.DownloadDataGoogleSheetTask;
import br.com.congregationreport.task.TaskRunner;
import br.com.congregationreport.util.Util;


public class ConnectionActivity extends AppCompatActivity {

    private Button btnConectar;
    private EditText txtUrl;
    private TaskRunner runner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        this.init();
        this.initOnClickListener();
    }

    private void init() {
        this.runner = new TaskRunner();
        this.btnConectar = (Button) findViewById(R.id.btnConectar);
        this.txtUrl = (EditText) findViewById(R.id.txtUrl);
    }

    private void initOnClickListener() {
        this.btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtUrl.getText().toString().isEmpty()) {
                    txtUrl.setError(getResources().getString(R.string.msg_conectar_url_vazia));
                    return;
                }
                if (Util.isDeviceOnline(ConnectionActivity.this)) {
                    if (validatenUrl(txtUrl.getText().toString())) {
                        String url = txtUrl.getText().toString() + "&key=" + view.getResources().getString(R.string.app_key);
                        runner.executeAsync(new DownloadDataGoogleSheetTask(
                                ConnectionActivity.this,
                                url)
                        );
                    }
                } else {
                    Util.createMessageAlert(
                            ConnectionActivity.this,
                            ConnectionActivity.this.getResources().getString(R.string.msg_no_internet)
                    );
                }
            }
        });
    }

    private boolean validatenUrl(String url) {
        try {
            if (!url.contains("https://script.google.com")) {
                Util.createMessageAlert(this, this.getResources().getString(R.string.msg_url_invalid));
                return false;
            }
            if (!url.contains("exec?spreadsheetId=")) {
                Util.createMessageAlert(this, this.getResources().getString(R.string.msg_url_invalid));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //Disables back button so user cannot skip accepting terms and conditions
    @Override
    public void onBackPressed() {
    }

}
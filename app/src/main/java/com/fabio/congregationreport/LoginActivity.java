package com.fabio.congregationreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fabio.congregationreport.db.dao.UserDAO;
import com.fabio.congregationreport.models.Group;
import com.fabio.congregationreport.models.Publisher;
import com.fabio.congregationreport.models.Setting;
import com.fabio.congregationreport.models.User;
import com.fabio.congregationreport.util.HttpRequestUtil;
import com.fabio.congregationreport.util.UtilDataMemory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText txtUsuario;
    private EditText txtSenha;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.initComponents();
        this.initOnClickListener();
    }

    private void initComponents() {
        this.userDAO = new UserDAO(this);
        this.btnLogin = (Button) findViewById(R.id.btnConectar);
        this.txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        this.txtSenha = (EditText) findViewById(R.id.txtSenha);
    }

    private void initOnClickListener() {
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BaixarBancoDadosTask().execute();
            }
        });
    }

    // AsyncTask<P1, P2, P3>
    // P1 = doInBackground parameter
    // P2 = onProgressUpdate parameter
    // P3 = onPostExecute parameter
    private class BaixarBancoDadosTask extends AsyncTask<Void, Void, String> {

        private RelativeLayout layoutLoading;
        private TextView txtMessage;
        private String json;

        protected void onPreExecute() {
            this.layoutLoading = (RelativeLayout) LoginActivity.this.findViewById(
                    R.id.layoutLoading);
            this.layoutLoading.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        protected String doInBackground(Void... params) {
            try {
                if (txtUsuario.getText().toString() == null || txtUsuario.getText().toString().isEmpty()) {
                    txtUsuario.setError(getResources().getString(R.string.msg_login_usuario_vazio));
                    return "2";
                }
                if (txtSenha.getText().toString() == null || txtSenha.getText().toString().isEmpty()) {
                    txtSenha.setError(getResources().getString(R.string.msg_login_senha_vazia));
                    return "2";
                }
                User user = userDAO.getUser(txtUsuario.getText().toString());
                UtilDataMemory.userConectded = user;
                if (user.getId() != null && user.getPassword().equals(txtSenha.getText().toString())) {
                    return "1";
                } else {
                    return "0";
                }

            } catch (Exception e) {
                return "0";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            layoutLoading.setVisibility(View.GONE);
            if (result.equals("0")) {
                Toast.makeText(
                        LoginActivity.this,
                        getResources().getString(R.string.msg_login_invalido),
                        Toast.LENGTH_SHORT
                ).show();
            } else if (result.equals("1")) {
                Intent it = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(it);
                LoginActivity.this.finish();
            }
            super.onPostExecute(result);
        }
    }

    //Disables back button so user cannot skip accepting terms and conditions
    @Override
    public void onBackPressed() {
    }
}
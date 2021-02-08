package br.com.congregationreport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.com.congregationreport.db.dao.AppDAO;
import br.com.congregationreport.db.dao.LoginDAO;
import br.com.congregationreport.db.dao.PublisherDAO;
import br.com.congregationreport.db.dao.SettingDAO;
import br.com.congregationreport.models.Login;
import br.com.congregationreport.models.Publisher;
import br.com.congregationreport.task.BaseTask;
import br.com.congregationreport.task.TaskRunner;
import br.com.congregationreport.util.Util;
import br.com.congregationreport.util.UtilDataMemory;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText txtUsuario;
    private EditText txtSenha;
    private PublisherDAO publisherDAO;
    private AppDAO appDAO;
    private SettingDAO settingDAO;
    private LoginDAO loginDAO;
    private boolean saveDataLogin;
    private Login login;
    private TaskRunner runner;
    private Publisher publisher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.init();
        this.initOnClickListener();
        this.loginSaveData();
    }

    private void loginSaveData() {
        try {
            this.login = this.loginDAO.getDataLogin();
            if (this.login != null && login.getId() > 0) {
                this.runner.executeAsync(new LoginTask());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void init() {
        this.runner = new TaskRunner();
        this.publisherDAO = UtilDataMemory.getPublisherDAO(this);
        this.appDAO = new AppDAO(this);
        this.loginDAO = new LoginDAO(this);
        this.settingDAO = new SettingDAO(this);
        this.btnLogin = (Button) findViewById(R.id.btnConectar);
        this.txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        this.txtSenha = (EditText) findViewById(R.id.txtSenha);
    }

    private void initOnClickListener() {
        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    createMessageSaveData();
                }
            }
        });
    }

    private void createMessageSaveData() {
        try {
            // Cria o dialog
            final Dialog dialog = new Dialog(LoginActivity.this);
            dialog.setContentView(R.layout.dialog_option);
            dialog.setTitle(this.getResources().getString(R.string.title_save_data_login));

            // Pega os componentes
            TextView text = (TextView) dialog.findViewById(R.id.txtMessage);
            text.setText(this.getResources().getString(R.string.msg_save_data_login));

            // Fecha o dialog
            Button btnNao = (Button) dialog.findViewById(R.id.btnCancelar);
            btnNao.setText(LoginActivity.this.getString(R.string.label_no));
            btnNao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    saveDataLogin = false;
                    runner.executeAsync(new LoginTask());
                }
            });

            // Executa a ação
            Button btnSim = (Button) dialog.findViewById(R.id.btnOk);
            btnSim.setText(LoginActivity.this.getString(R.string.label_yes));
            btnSim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    saveDataLogin = true;
                    runner.executeAsync(new LoginTask());
                }
            });
            dialog.show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private boolean validate() {
        try {
            if (txtUsuario.getText().toString() == null || txtUsuario.getText().toString().isEmpty()) {
                this.txtUsuario.setError(getResources().getString(R.string.msg_login_usuario_vazio));
                return false;
            }
            if (txtSenha.getText().toString() == null || txtSenha.getText().toString().isEmpty()) {
                this.txtSenha.setError(getResources().getString(R.string.msg_login_senha_vazia));
                return false;
            }
            this.publisher = publisherDAO.findPublisherByUser(txtUsuario.getText().toString());
            if (this.publisher == null) {
                Toast.makeText(
                        LoginActivity.this,
                        getResources().getString(R.string.msg_login_user_not_found),
                        Toast.LENGTH_SHORT
                ).show();
                return false;
            }
            if (!this.publisher.getPassword().equals(txtSenha.getText().toString())) {
                Toast.makeText(
                        LoginActivity.this,
                        getResources().getString(R.string.msg_login_password_invalido),
                        Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // AsyncTask<P1, P2, P3>
    // P1 = doInBackground parameter
    // P2 = onProgressUpdate parameter
    // P3 = onPostExecute parameter
    private class LoginTask extends BaseTask {

        private RelativeLayout layoutLoading;
        private TextView txtMessage;

        @Override
        public void setUiForLoading() {
            this.layoutLoading = (RelativeLayout) LoginActivity.this.findViewById(
                    R.id.layoutLoading);
            this.layoutLoading.setVisibility(View.VISIBLE);
            this.txtMessage = (TextView) LoginActivity.this.findViewById(
                    R.id.txtMessage);
            this.txtMessage.setText(LoginActivity.this.getResources().getString(R.string.validate_login));
        }

        @Override
        public Object callInBackground() throws Exception {
            try {

                String user, password;
                if (login == null || login.getId() == 0) {
                    user = txtUsuario.getText().toString();
                    password = txtSenha.getText().toString();
                } else {
                    user = login.getUserName();
                    password = login.getPassword();
                }

                if (publisher == null) {
                    Login login = loginDAO.getDataLogin();
                    publisher = publisherDAO.findPublisherByUser(login.getUserName());
                }
                UtilDataMemory.publisher = publisher;
                UtilDataMemory.setting = settingDAO.getSetting();

                if (saveDataLogin) {
                    Login login = new Login();
                    login.setPassword(password);
                    login.setUserName(user);
                    loginDAO.save(login);
                }

                return "1";
            } catch (Exception e) {
                return "0";
            }
        }

        @Override
        public void setDataAfterLoading(Object object) {
            String result = (String) object;
            if (result.equals("0")) {
                Toast.makeText(
                        LoginActivity.this,
                        getResources().getString(R.string.msg_login__erro),
                        Toast.LENGTH_SHORT
                ).show();
            } else if (result.equals("1")) {
                Intent it = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(it);
                LoginActivity.this.finish();
            }
            this.layoutLoading.setVisibility(View.GONE);
        }


    }

    //Disables back button so user cannot skip accepting terms and conditions
    @Override
    public void onBackPressed() {
    }
}